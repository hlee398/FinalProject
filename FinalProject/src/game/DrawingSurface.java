package game;
import java.awt.Point;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;


/**
 * Draws all the things that will be displayed on the window
 * @author Harrison, Will
 *
 */
public class DrawingSurface extends PApplet
{
	private Survivor s;
	private Wall w,w2;
	
	private ArrayList<MovingEntity> movingEntities = new ArrayList<>();
	private ArrayList<StaticEntity> staticEntities = new ArrayList<>();
	private String username;
	private PFont f;
	private PImage background;
	private Game g;
	
	public static final String SURVIVOR_IMAGE = "Stickman.png";
	public static final String WALL_IMAGE = "Wall.jpg";
	public static final String ZOMBIE_IMAGE = "this has no image... enjoy the error";
	public static final String BACKGROUND_IMAGE = "cbble.png";
	
	public DrawingSurface() {
		
	}
	
	/**
	 * Creates a drawing surface 
	 * @param game
	 */
	public DrawingSurface(Game game)
	{
		username = game.getUserName();
		InetAddress localhost = null;
		try {
			localhost = InetAddress.getByName("localhost");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		s = new Survivor(username, 100,100, 0, localhost, 4444,"SURVIVOR_IMAGE");
		g = game;
		
		w = new Wall(300, 350, 50, 300);
		staticEntities.add(w);
		w2 = new Wall(600,300,200,100);
		staticEntities.add(w2);
	}
	
	public void setup()
	{
		f = createFont("Arial", 12,true);
		textFont(f);
		background = loadImage(BACKGROUND_IMAGE);
	}
	
	public void draw() //draws all objects in world
	{
		background(background);
		//this.fill(255);
		
		//Updates survivors
		for (int i = 0; i < movingEntities.size(); i++)
		{
			if (movingEntities.get(i) instanceof Survivor) {
				Survivor sOther = (Survivor)movingEntities.get(i);
				fill(0);
				text(sOther.getUsername(), sOther.getX() + 15, sOther.getY() + 5);
				fill(255);
				sOther.draw(this, sOther.getDir(), SURVIVOR_IMAGE);
			}
		}
		
		//Checks if this is the server drawing surface (crashes because username will be null for servers)
		if (!g.getisServer())
		{
			fill(0);
			text(username, s.getX() + 15, s.getY() + 5);
			fill(255);
			s.draw(this, mouseX, mouseY, SURVIVOR_IMAGE);
			s.move();
			
			// Send a message to the server with our (s) new coordinate
			String cmd = "01," + username + "," + s.getX() + "," + s.getY() + "," + s.getDir();
			byte[] data = cmd.getBytes();
			g.getClient().send(data);
		}
		
		s.checkWall(w);
		s.checkWall(w2);
		w.draw(this, WALL_IMAGE);
		w2.draw(this, WALL_IMAGE);

		generateBlindSpot(s, w);
		generateBlindSpot(s, w2);	
				
	}

	public void keyPressed()
	{
		if(key == 'w')
		{
			s.setYVelocity(-5);
		}
		if(key == 'a')
		{
			s.setXVelocity(-5);
		}
		if(key == 's')
		{
			s.setYVelocity(5);
		}
		if(key == 'd')
		{
			s.setXVelocity(5);
		}
	}
	
	public void keyReleased()
	{
		if(key == 'w')
		{
			s.setYVelocity(0);
		}
		if(key == 'a')
		{
			s.setXVelocity(0);
		}
		if(key == 's')
		{
			s.setYVelocity(0);
		}
		if(key == 'd')
		{
			s.setXVelocity(0);
		}

			
	}
	
	public void mouseClicked()
	{
		
	}
	
	public void mouseDragged()
	{
		
	}
	
	public void mouseReleased() 
	{
		
	}

	public void addSurvivor(Survivor s)
	{
		movingEntities.add(s);
	}
	
	public void addSurvivor(String username, int x, int y, float dir, InetAddress ip, int port)
	{
		Survivor sNew = new Survivor(username, x,y, dir, ip, port, "");
		movingEntities.add(sNew);
	}
	
	public MovingEntity getME()
	{
		return s;
	}

	public ArrayList<MovingEntity> getMovingEntities()
	{
		return movingEntities;
	}
	
	/**
	 * @author William (thank you for your pain and suffering)
	 * @param player
	 * @param w
	 */
	public void generateBlindSpot(MovingEntity player, Wall w)
	{
		
		int pX = player.getX() + player.getWidth()/2;
		int pY = player.getY() + player.getHeight()/2;
		
		Point p1 = new Point(w.getX(), w.getY()); // top l
		Point p2 = new Point(w.getX() + w.getWidth(), w.getY()); // top r
		Point p3 = new Point(w.getX() + w.getWidth(), w.getY() + w.getHeight()); // bottom r
		Point p4 = new Point(w.getX(), w.getY() + w.getHeight()); // bottom l

		if(pX <= w.getX())
		{
			if(pY < w.getY()) // top left
			{
				beginShape();
				
				fill(0);
				
				vertex((float)p4.getX(), (float)p4.getY());
				vertex((float)p3.getX(), (float)p3.getY());
				vertex((float)p2.getX(), (float)p2.getY());
				
				int endpt1X = (this.width);
				int endpt1Y = pY + (w.getY() - pY) * (this.width - pX) / (w.getX() + w.getWidth() - pX);
				
				vertex(endpt1X, endpt1Y);
				vertex(this.width, this.height);
				
				int endpt2X = pX + ((this.height - pY) * (w.getX() - pX)) / (w.getHeight() + w.getY() - pY);
				int endpt2Y = this.height;
				
				vertex(endpt2X, endpt2Y);
				vertex((float)p4.getX(), (float)p4.getY());
				
				endShape(CLOSE);
			}
			else if(pY >= w.getY() + w.getHeight()) // bottom left
			{
				
				beginShape();
				
				fill(0);
				
				vertex((float)p1.getX(), (float)p1.getY());
				vertex((float)p2.getX(), (float)p2.getY());
				vertex((float)p3.getX(), (float)p3.getY());
				
				int endpt1X = (this.width);
				int endpt1Y = pY - ((this.width - pX) * (pY - w.getHeight() - w.getY()) / (w.getX() + w.getWidth() - pX));
				
				vertex(endpt1X, endpt1Y);
				vertex(this.width, 0);
				
				int endpt2X = pX + ((w.getX() - pX) * pY) / (pY - w.getY());
				int endpt2Y = 0;
				
				vertex(endpt2X, endpt2Y);
				vertex((float)p1.getX(), (float)p1.getY());
				
				endShape(CLOSE);
			}
			else // direct left
			{
				beginShape();
				
				fill(0);
				
				vertex((float)p1.getX(), (float)p1.getY());
				vertex((float)p2.getX(), (float)p2.getY());
				vertex((float)p3.getX(), (float)p3.getY());
				vertex((float)p4.getX(), (float)p4.getY());
				
				int endpt1X = (this.width);
				int endpt1Y = (int) (pY + ((this.width - pX) * (p4.getY() - pY)) / (p4.getX() - pX));
				
				vertex(endpt1X, endpt1Y);
				vertex(this.width, 0);
				
				int endpt2X = (this.width);
				int endpt2Y = (int) (pY - ((this.width - pX) * (p1.getY() - pY)) / (pX - p1.getX()));
				
				vertex(endpt2X, endpt2Y);
				vertex((float)p1.getX(), (float)p1.getY());
				
				endShape(CLOSE);
				
			}
		}
		else if(pX < w.getX() + w.getWidth() && pX > w.getX()) 
		{
			if(pY < w.getY()) // direct top
			{
				beginShape();
				
				fill(0);
				
				vertex((float)p2.getX(), (float)p2.getY());
				vertex((float)p3.getX(), (float)p3.getY());
				vertex((float)p4.getX(), (float)p4.getY());
				vertex((float)p1.getX(), (float)p1.getY());
				
				int endpt1X = pX - (this.height - pY) * (pX - w.getX()) / (w.getY() - pY);
				int endpt1Y = this.height;
				
				vertex(endpt1X, endpt1Y);
				vertex(0, this.height);
				
				int endpt2X = pX + ((w.getX() + w.getWidth()) - pX) * (this.height - pY) / (w.getY() - pY);
				int endpt2Y = this.height;
				
				vertex(endpt2X, endpt2Y);
				vertex((float)p2.getX(), (float)p2.getY());
				
				endShape(CLOSE);
			}
			else // direct bottom
			{
				beginShape();
				
				fill(0);
				
				vertex((float)p4.getX(), (float)p4.getY());
				vertex((float)p1.getX(), (float)p1.getY());
				vertex((float)p2.getX(), (float)p2.getY());
				vertex((float)p3.getX(), (float)p3.getY());
				
				int endpt1X = pX + (w.getX() + w.getWidth() - pX) * (pY) / (pY - w.getY() - w.getHeight());
				int endpt1Y = 0;
				
				vertex(endpt1X, endpt1Y);
				vertex(0, 0);
				
				int endpt2X = pX - (pX - w.getX()) * (pY) / (pY - w.getY() - w.getHeight());
				int endpt2Y = 0;
				
				vertex(endpt2X, endpt2Y);
				vertex((float)p4.getX(), (float)p4.getY());
				
				endShape(CLOSE);
			}
		}
		else if(pX >= w.getX() + w.getWidth())
		{
			if(pY <= w.getY()) // top right
			{
				beginShape();
				
				fill(0);
				
				vertex((float)p1.getX(), (float)p1.getY());
				vertex((float)p4.getX(), (float)p4.getY());
				vertex((float)p3.getX(), (float)p3.getY());
				
				int endpt1X = pX - (pX - w.getX() - w.getWidth()) * (this.height - pY) / (w.getY() + w.getHeight() - pY);
				int endpt1Y = this.height;
				
				vertex(endpt1X, endpt1Y);
				vertex(0, this.height);
				
				int endpt2X = 0;
				int endpt2Y = pY + (w.getY() - pY) * (pX) / (pX - w.getX());
				
				vertex(endpt2X, endpt2Y);
				vertex((float)p1.getX(), (float)p1.getY());
				
				endShape(CLOSE);
			}
			else if(pY >= w.getY() + w.getHeight()) // bottom Right
			{
				beginShape();
				
				fill(0);
				
				vertex((float)p2.getX(), (float)p2.getY());
				vertex((float)p1.getX(), (float)p1.getY());
				vertex((float)p4.getX(), (float)p4.getY());
				
				int endpt1X = 0;
				int endpt1Y = pY - (pY - w.getY() - w.getHeight()) * (pX) / (pX - w.getX());
				
				vertex(endpt1X, endpt1Y);
				vertex(0, 0);
				
				int endpt2X = pX - (pX - w.getX() - w.getWidth()) * (pY) / (pY - w.getY());
				int endpt2Y = 0;
				
				vertex(endpt2X, endpt2Y);
				vertex((float)p2.getX(), (float)p2.getY());
				
				endShape(CLOSE);
			}
			else // Direct Right
			{
				beginShape();
				
				fill(0);
				
				vertex((float)p2.getX(), (float)p2.getY());
				vertex((float)p1.getX(), (float)p1.getY());
				vertex((float)p4.getX(), (float)p4.getY());
				vertex((float)p3.getX(), (float)p3.getY());
				
				int endpt1X = 0;
				int endpt1Y = pY + (w.getY() + w.getHeight() - pY) * (pX) / (pX - w.getX() - w.getWidth());
				
				vertex(endpt1X, endpt1Y);
				vertex(0, 0);
				
				int endpt2X = 0;
				int endpt2Y = pY - (pY - w.getY()) * (pX) / (pX - w.getX() - w.getWidth());
				
				vertex(endpt2X, endpt2Y);
				vertex((float)p2.getX(), (float)p2.getY());
				
				endShape(CLOSE);
			}
		}
	}
}
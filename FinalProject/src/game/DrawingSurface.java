package game;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PFont;


/**
 * Draws all the things that will be displayed on the window
 * @author Harrison, Will
 *
 */
public class DrawingSurface extends PApplet
{
	private Survivor s;
	private ArrayList<MovingEntity> movingEntities = new ArrayList<>();
	private String username;
	private PFont f;
	private Game g;
	
	public DrawingSurface() {
		s = new Survivor(100,100,"");
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
		s = new Survivor(username, 100,100, 0, localhost, 0,"");
		g = game;
	}
	
	public void setup()
	{
		f = createFont("Arial", 12,true);
		textFont(f);
	}
	
	public void draw() //draws all objects in world
	{
		
		background(255,255,255);
		this.fill(255);
		
		//Updates survivors
		for (int i = 0; i < movingEntities.size(); i++)
		{
			if (movingEntities.get(i) instanceof Survivor) {
				Survivor sOther = (Survivor)movingEntities.get(i);
				fill(0);
				text(sOther.getUsername(), sOther.getX() + 15, sOther.getY() + 5);
				fill(255);
				sOther.draw(this, sOther.getX(), sOther.getY());
			}
		}
		
		//Checks if this is the server drawing surface (crashes because username will be null for servers)
		if (!g.getisServer())
		{
			fill(0);
			text(username, s.getX() + 15, s.getY() + 5);
			fill(255);
			s.draw(this, s.getX(), s.getY());
			s.setDir(mouseX, mouseY);
			s.move();
			
			// Send a message to the server with our (s) new coordinate
			String cmd = "01," + username + "," + s.x + "," + s.y + "," + s.dir;
			byte[] data = cmd.getBytes();
			g.getClient().send(data);
		}
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
}
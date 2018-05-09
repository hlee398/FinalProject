package game;
import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PFont;

public class DrawingSurface extends PApplet
{
	private Survivor s;
	private ArrayList<Survivor> otherPlayers = new ArrayList<>();
	private String username;
	private PFont f;
	
	public DrawingSurface(String username)
	{
		s = new Survivor(100,100,"", username);
		this.username = username;
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
		
		String playerName = username;
		
		for (int i = 0; i < otherPlayers.size(); i++)
		{
			Survivor sOther = otherPlayers.get(i);
			fill(0);
			text(sOther.getUsername(), sOther.getX() + 15, sOther.getY() + 5);
			fill(255);
			sOther.draw(this, sOther.getX(), sOther.getY());
		}
		
		fill(0);
		text(playerName, s.getX() + 15, s.getY() + 5);
		fill(255);
		s.draw(this, s.getX(), s.getY());
		s.setDir(mouseX, mouseY);
		s.move();
		
		// Send a message to the server with our (s) new coordinate
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
	
	public void addSurvivor(String username, int x, int y, float dir)
	{
		Survivor sNew = new Survivor(x,y, "", username);
		otherPlayers.add(sNew);
	}
	
	public MovingEntity getME()
	{
		return s;
	}

	public ArrayList<Survivor> getOtherPlayers()
	{
		return otherPlayers;
	}
}
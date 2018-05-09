package game;
import processing.core.PApplet;

public class Survivor extends MovingEntity{

	private String username;

	
	public Survivor(int xP, int yP, String img) {
		super(xP, yP, img);
	}
	
	
	public Survivor(int xP, int yP, String img, String username) {
		super(xP, yP, img);
		this.setUsername(username);
	}
	
	
	public void draw(PApplet drawer, float width, float height)
	{
		super.draw(drawer, width, height);
		
		drawer.ellipse(getX(), getY(), 10, 10);
		
		
		
		drawer.line(getX(), getY(), (int)(10*Math.cos(super.getDir()) + getX()), (int)(10*Math.sin(super.getDir()) + getY()));
		
		
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}
}

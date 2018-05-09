package game;
import processing.core.PApplet;

public class Zombie extends MovingEntity{


	public Zombie(int xP, int yP, String img) {
		super(xP, yP, img);
	}
	
	
	public void draw(PApplet drawer, float width, float height)
	{
		super.draw(drawer, width, height);
		
		drawer.ellipse(getX(), getY(), 10, 10);
		
		
		
		drawer.line(getX(), getY(), (int)(10*Math.cos(super.getDir()) + getX()), (int)(10*Math.sin(super.getDir()) + getY()));
		
		
	}
	
}

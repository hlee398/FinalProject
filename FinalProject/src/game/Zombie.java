package game;
import processing.core.PApplet;

public class Zombie extends MovingEntity{


	/**
	 * 
	 * @param xP
	 * @param yP
	 * @param img
	 */
	public Zombie(int xP, int yP, int width, int height) {
		super(xP, yP, width, height);
	}
	
	
	public void draw(PApplet drawer, int mouseX, int mouseY, String img)
	{
		super.draw(drawer, mouseX, mouseY, img);
		
		drawer.ellipse(getX(), getY(), 10, 10);
		
		
		
		drawer.line(getX(), getY(), (int)(10*Math.cos(super.getDir()) + getX()), (int)(10*Math.sin(super.getDir()) + getY()));
		
		
	}
	
}

/**
 * 
 * @author Will
 * 
 */
import processing.core.PApplet;

public class Survivor extends MovingEntity{


	public Survivor(int xP, int yP, String img) {
		super(xP, yP, img);
	}
	
	
	public void draw(PApplet drawer, int width, int height, int mouseX, int mouseY)
	{
		//TODO make compatible with images
		super.draw(drawer, width, height, mouseX, mouseY);
		
		drawer.ellipse(getX(), getY(), 10, 10);
		
		drawer.line(getX(), getY(), (int)(10*Math.cos(super.getDir()) + getX()), (int)(10*Math.sin(super.getDir()) + getY()));
		
		
	}
	
	public void shoot()
	{
		//TODO shoot method
	}
	
}

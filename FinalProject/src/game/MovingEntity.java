package game;

import processing.core.PApplet;

/**
 * 
 * @author Will
 *
 */
public abstract class MovingEntity extends Entity{

	private int xVel,yVel;
	
	public MovingEntity(int xP, int yP, int width, int height) {
		super(xP, yP,width,height);
		xVel = 0;
		yVel = 0;
	}

	public void moveTo(int x, int y)
	{
		super.setX(x);
		super.setY(y);
	}	

	public void setXVelocity(int x) // sets players movement velocity
	{
		xVel = x;
	}
	public void setYVelocity(int y) // sets players movement velocity
	{
		yVel = y;
	}
	
	public void move() // moves the player
	{
		super.setX(getX() + xVel);
		super.setY(getY() + yVel);
	}
	
	/**
	 * Draws a survivor looking at point (mouseX, mouseY)
	 * 
	 * @param drawer The object used to draw
	 * @param mouseX x coordinate of the point that the survivor will point towards
	 * @param mouseY y coordinate of the point that the survivor will point towards
	 * @param img The name of the image that will represent the survivor
	 */
	public void draw(PApplet drawer, int mouseX, int mouseY, String img)
 	{
 		super.pointTowards(mouseX, mouseY);
		super.draw(drawer, img);
 	}
	/**
	 * Draws a survivor using a direction
	 * 
	 * @param drawer The object used to draw
	 * @param dir the direction in radians which to draw the survivor
	 * @param img The name of the image that will represent the survivor
	 */
	public void draw(PApplet drawer, float dir, String img)
 	{
 		super.setDir(dir);
		super.draw(drawer, img);
 	}
	
	
}
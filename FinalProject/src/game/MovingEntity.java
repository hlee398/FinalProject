package game;
import processing.core.PApplet;

/**
 * A specific type of Entity that can translate about the map and interact with other Entities
 * @author Will
 *
 */
public abstract class MovingEntity extends Entity{

	private int xVel,yVel;
	/**
	 * Construct a MovingEntity at (xP, yP) with dimensions width and height
	 * @param xP the x coordinate of the MovingEntity
	 * @param yP the y coordinate of the MovingEntity
	 * @param width the width of the MovingEntity
	 * @param height the height of the MovingEntity
	 */
	public MovingEntity(int xP, int yP, int width, int height) {
		super(xP, yP,width,height);
		xVel = 0;
		yVel = 0;
	}
/**
 * Moves the sets the getX() to x and the getY() to y
 * @param x the new getX() value
 * @param y the new getY() value
 */
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
	/**
	 * moves the entity by setXVelocty value and setYVelocity value
	 */
	public void move() // moves the player
	{
		super.setX(getX() + xVel);
		super.setY(getY() + yVel);
	}
	
	/**
	 * Draws a MovingEntity looking at point (mouseX, mouseY)
	 * 
	 * @param drawer The object used to draw
	 * @param mouseX x coordinate of the point that the MovingEntity will point towards
	 * @param mouseY y coordinate of the point that the MovingEntity will point towards
	 * @param img The name of the image that will represent the MovingEntity
	 */
	public void draw(PApplet drawer, int mouseX, int mouseY, String img)
 	{
 		super.pointTowards(mouseX, mouseY);
		super.draw(drawer, img);
 	}
	/**
	 * Draws a MovingEntity pointing in a specified direction in radians
	 * 
	 * @param drawer The object used to draw
	 * @param dir the direction in radians which to draw the MovingEntity
	 * @param img The name of the image that will represent the MovingEntity
	 */
	public void draw(PApplet drawer, float dir, String img)
 	{
 		super.setDir(dir);
		super.draw(drawer, img);
 	}
	
	
}
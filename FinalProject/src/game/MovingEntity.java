package game;
import processing.core.PApplet;

/**
 * A specific type of Entity that can translate about the map and interact with other Entities
 * @author Will
 *
 */
public abstract class MovingEntity extends Entity{

	private int xVel,yVel,health;
	private boolean isAlive;
	/**
	 * Construct a MovingEntity at (xP, yP) with dimensions width and height equal to radius
	 * @param xP the x coordinate of the MovingEntity
	 * @param yP the y coordinate of the MovingEntity
	 * @param radius the width and height of the MovingEntity
	 */
	public MovingEntity(int xP, int yP, int radius) {
		super(xP, yP,radius);
		xVel = 0;
		yVel = 0;
		health = 100;
		isAlive = true;
	}	

	public void setXVelocity(int x) // sets players movement velocity
	{
		xVel = x;
	}
	public void setYVelocity(int y) // sets players movement velocity
	{
		yVel = y;
	}
	
	public int getXVelocity() 
	{
		return xVel;
	}
	public int getYVelocity() 
	{
		return yVel;
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
	/**
	 * Checks to see if this MovingEntity has hit the specified wall
	 * @param w the wall that is to be checked with for a collision with this MovingEntity
	 * @post The MovingEntity will be moved out of the wall
	 */
	public void checkWall(Wall w)
	{
		if(getBounds().intersects(w.getBounds()))
		{
			int wX = w.getX() + w.getWidth()/2;
			int wY = w.getY() + w.getHeight()/2;
			int sX = getX() + getWidth()/2;
			int sY = getY() + getHeight()/2;
			
			if(Math.abs(sX - wX) < w.getWidth()/2) // colliding vertically
			{
				if(sY - wY == 0)
				{
					
				}
				if(sY > wY) // contact from bottom
				{
					setY(wY + w.getHeight()/2);
				}
				else // contact from top
				{
					setY(wY - (getHeight()+w.getHeight()/2));
				}
			}
			if(Math.abs(sY - wY) < w.getHeight()/2) // colliding horizontally
			{
				if(sX - wX == 0)
				{
					
				}
				if(sX > wX) // contact from right
				{
					setX(wX + w.getWidth()/2);
				}
				else // contact from left
				{
					setX(wX - (w.getWidth()/2 + getWidth()));
				}
			}
			
		}
	}
	
	public void damage(int dmg)
	{
		health -= dmg;
		if(health <= 0)
		{
			isAlive = false;
		}
	}
	
	
}
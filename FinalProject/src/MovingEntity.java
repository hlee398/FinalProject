import processing.core.PApplet;

/**
 * 
 * @author Will
 *
 */
public abstract class MovingEntity extends Entity{

	private int xVel,yVel;
	
	public MovingEntity(int xP, int yP, String img) {
		super(xP, yP, img);
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
	
	public void draw(PApplet drawer, int width, int height, int mouseX, int mouseY)
	{
		super.pointTowards(mouseX, mouseY);
		super.draw(drawer, width, height);
		
	}
}
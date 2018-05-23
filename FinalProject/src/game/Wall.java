package game;
/**
 * A wall that can be created like the one on the border with the US and Mexico :D
 * @author Will
 *
 */
public class Wall extends StaticEntity{

	/**
	 * Creates a wall object at the specified points with the specified width and height
	 * @param xP x position of the wall
	 * @param yP y position of the wall
	 * @param width width of the wall
	 * @param height height of the wall
	 */
	public Wall(int xP, int yP, int width, int height) {
		super(xP, yP, width, height);
	}
	
	/**
	 * checks to see if a point is inside the wall
	 * @param x x position of the player
	 * @param y y position of the player
	 * @return true if they are not inside a wall, false otherwise
	 */
	public boolean isPointInside(double x, double y)
	{
		//TODO work with rotation
		return false;
	}
}
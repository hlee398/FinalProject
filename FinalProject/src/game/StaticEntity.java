package game;
import processing.core.PApplet;
/**
 * A specific type of Entity that can not move or translate about the world
 * @author Will
 *
 */
public class StaticEntity extends Entity{
	/**
	 * Construct a StaticEntity at (xP, yP) with dimensions width and height
	 * @param xP the x coordinate of the StaticEntity
	 * @param yP the y coordinate of the StaticEntity
	 * @param width the width of the StaticEntity
	 * @param height the height of the StaticEntity
	 */
	public StaticEntity(int xP, int yP, int width, int height) {
		super(xP, yP,width,height);
	}
	/**
	 * Draws a StaticEntity pointing in a specified direction in radians
	 * 
	 * @param drawer The object used to draw
	 * @param dir the direction in radians which to draw the StaticEntity
	 * @param img The name of the image that will represent the StaticEntity
	 */
	public void draw(PApplet drawer, float dir, String img)
	{
		super.setDir(getDir());
		super.draw(drawer, img);
		
	}
	


}
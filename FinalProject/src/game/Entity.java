package game;
import java.awt.geom.Rectangle2D;

import processing.core.PApplet;

/**
 * Defines an entity that is drawn onto the world map and can interact with other entities
 * @author Will
 *
 */
public abstract class Entity {

	private int x,y,width,height;
	private float dir;
	private String image;
	private Rectangle2D bounds = new Rectangle2D.Float();
	
	/**
	 * Construct an entity at position (xP,yP) with width and height
	 * @param xP the x coordinate of the entity
	 * @param yP the y coordinate of the entity
	 * @param width The width of the entity, used to draw image
	 * @param height The height of the entity, used to draw image
	 */
	public Entity(int xP, int yP,int width ,int height)
	{
		x = xP;
		y = yP;
		this.width = width;
		this.height = height;
		
		bounds.setRect(xP, yP, width, height);
	}
	/**
	 * Construct an entity at position (xP,yP) with width and height equal to radius
	 * @param xP the x coordinate of the entity
	 * @param yP the y coordinate of the entity
	 * @param radius the radius of the Entity which translates into the width and the height
	 */
	public Entity(int xP, int yP, int radius)
	{
		x = xP;
		y = yP;
		width = radius;
		height = radius;
		
		bounds.setRect(xP, yP, width, height);
	}
	/**
	 * Draws an entity at (getX(), getY()) , getWidth() wide and getHeight() tall, rotated by getDir() radians about the center
	 * @param drawer the obejct used to draw the entity
	 * @param img the String containing the image name to be used to draw the entity
	 */
	public void draw(PApplet drawer, String img)
	{
		drawer.pushMatrix();
		
		drawer.image(drawer.loadImage(img), x, y, width, height);
		drawer.translate(x + width, y + height);
		drawer.rotate((float)dir);
		
		bounds.setFrame(x, y, bounds.getWidth(), bounds.getHeight());
		
		drawer.popMatrix();
	}

	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public String getImage()
	{
		return image;
	}
	
	public void setX(int x)
	{
		this.x = x;
	}
	
	public void setY(int y)
	{
		this.y = y;
	}
	/**
	 * Sets the getDir() value equal to radian value of angle from (getX(),getY()) to (xPos,Ypos)
	 * @param xPos the x coordinate of the directional point
	 * @param yPos the y coordinate of the directional point
	 */
	public void pointTowards(int xPos, int yPos)
 	{
 		float xDif = (float) (x - xPos);
 		float yDif = (float) (y - yPos);
 		
 		dir = (float) Math.atan(yDif/xDif);
 		
 		if(xDif > 0)
 		{
 			dir += Math.PI;
 		}
 
 		if(xDif == 0)
 		{
 			dir += Math.PI;
 			if(yDif == 0)
 			{
 				dir = 0;
 			}
 		}
 	}
	
	public void setDir(float radians)
 	{
 		dir = radians;
 	}
 	
 	public float getDir()
 	{
 		return dir;
 	}
 	
 	public int getWidth()
 	{
 		return width;
 	}
 	
 	public int getHeight()
 	{
 		return height;
 	}
 	
 	public void setWidth( int width)
 	{
 		this.width = width;
 	}
 		
 	public void setHeight( int height)
 	{
 		this.height = height;
 	}
 		
 	
	public void setImage(String img)
	{
		image = img;
	}
	
	public Rectangle2D getBounds()
	{
		return bounds;
	}
	
	
}

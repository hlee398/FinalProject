import processing.core.PApplet;
import processing.core.PShape;
import processing.core.*;

public abstract class Entity {

	private int x,y;
	private String image;
	
	public Entity(int xP, int yP, String img)
	{
		x = xP;
		y = yP;
		image = img;
	}
	
	public void draw(PApplet drawer, float width, float height)
	{
		//drawer.image(drawer.loadImage(image), x, y, width, height);
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
	
	
	public void setImage(String img)
	{
		image = img;
	}
	
	
}

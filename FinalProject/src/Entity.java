import processing.core.PApplet;

/**
 * 
 * @author Will
 *
 */
public abstract class Entity {

	private int x,y;
	private float dir;
	private String image;
	
	public Entity(int xP, int yP, String img)
	{
		x = xP;
		y = yP;
		dir = 0;
		image = img;
	}
	
	public void draw(PApplet drawer, int width, int height)
	{
		//TODO
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
	
	public void setImage(String img)
	{
		image = img;
	}
	
	
}

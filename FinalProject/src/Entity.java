import processing.core.PApplet;

/**
 * 
 * @author Will
 *
 */
public abstract class Entity {

	private int x,y,width,height;
	private float dir;
	private String image;
	
	public Entity(int xP, int yP,int width ,int height ,String img)
	{
		x = xP;
		y = yP;
		dir = 0;
		image = img;
		this.width = width;
		this.height = height;
	}
	
	public void draw(PApplet drawer)
	{
		drawer.pushMatrix();
		
		drawer.translate(x,y);
		drawer.rotate((float) (dir + Math.PI/2));
		drawer.image(drawer.loadImage(image), -width/2, -height/2, width, height);
		drawer.rotate((float)-(dir + Math.PI/2));
		
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
	
	
}

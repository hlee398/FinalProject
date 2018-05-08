import processing.core.PApplet;

public class StaticEntity extends Entity{

	private int width, height;
	
	public StaticEntity(int xP, int yP, int width, int height, String img) {
		super(xP, yP, img);
		this.width = width;
		this.height = height;
	}
	
	public void draw(PApplet drawer, float dir)
	{
		super.setDir(dir);
		super.draw(drawer, width, height);
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}

}

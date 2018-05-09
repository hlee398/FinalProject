import processing.core.PApplet;

public class StaticEntity extends Entity{
	
	public StaticEntity(int xP, int yP, int width, int height, String img) {
		super(xP, yP,width,height, img);
	}
	
	public void draw(PApplet drawer, float dir)
	{
		super.setDir(dir);
		super.draw(drawer);
		
	}
	


}

import processing.core.PApplet;

public class Wall extends StaticEntity{

	public Wall(int xP, int yP, int width, int height, String img) {
		super(xP, yP, width, height, img);
	}

	public void draw(PApplet drawer, float dir)
	//TODO WALL ROTATION
	{
		super.draw(drawer, dir);
		
		drawer.rect(getX(), getY(), getWidth(), getHeight());
	}
}

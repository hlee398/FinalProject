package game;
/**
 * A wall that can be created like the one on the border with the US and Mexico :D
 * @author Will
 *
 */
public class Wall extends StaticEntity{

	public Wall(int xP, int yP, int width, int height) {
		super(xP, yP, width, height);
	}
	
	public boolean isPointInside(double x, double y)
	{
		//TODO work with rotation
		return false;
	}
}
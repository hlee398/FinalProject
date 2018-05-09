/**
 * 
 * @author Will
 *
 */
public class Wall extends StaticEntity{

	public Wall(int xP, int yP, int width, int height, String img) {
		super(xP, yP, width, height, img);
	}
	
	public boolean isPointInside(double x, double y)
	{
		//TODO work with rotation
		return false;
	}
}

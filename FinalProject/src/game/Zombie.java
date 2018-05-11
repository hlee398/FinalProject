package game;

import willB.Shapes.Line;

/**
 * A MovingEntity that can interact with other Entities
 * @author Will
 *
 */
public class Zombie extends MovingEntity{


/**
 * Construct a Zombie at (xP, yP) with dimensions width and height
 * @param xP the x coordinate of the zombie
 * @param yP the y coordinate of the zombie
 * @param width the width of the zombie
 * @param height the height of the zombie
 */
	public Zombie(int xP, int yP, int radius) {
		super(xP, yP, radius);
	}
	
	
	public void attack()
	{
		
	}
	
	public boolean isHit(Line shot)
	{
		float shotSlope = (float) ((shot.getX2() - shot.getX()) / (shot.getY2() - shot.getY()));
		Line hitDetector;
		return false;
	}
}

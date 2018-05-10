package game;
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
	public Zombie(int xP, int yP, int width, int height) {
		super(xP, yP, width, height);
	}
	
	
	public void attack()
	{
		
	}
}

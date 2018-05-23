package game;
/**
 * A StaticEntity that can be picked up by MovingEntities
 * 
 * Unused as of the current build of the game due to time constraints
 * 
 * @author wborlik179
 *
 */
public class Pickupable extends StaticEntity{

	private int health, bullets;
	/**
	 * Creates a Pickupable object that gives health and/or bullets
	 * @param xP the x coordinate of the Pickupable
	 * @param yP the y coordinate of the Pickupable
	 * @param size the width and the height of the Pickupable
	 * @param health the health to be given to the MovingEntity that picks up this resource
	 * @param bullets the bullets to be given to the MovingEntity that picks up this resource
	 */
	public Pickupable(int xP, int yP, int size, int health, int bullets) {
		super(xP, yP, size, size);
		this.health = health;
		this.bullets = bullets;	
	}
	public int getHealth()
	{
		return health;
	}
	public int getBullets()
	{
		return bullets;
	}
	
	
	
}

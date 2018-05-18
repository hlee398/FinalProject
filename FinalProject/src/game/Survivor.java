package game;
import java.net.InetAddress;

/**
 * A MovingEntity that can interact with other Entities
 * @author Will
 *
 */
public class Survivor extends Player{
	private int bullets,loadedBullets;
	private final int MAX_LOADED = 8;
/**
 * Construct a Survivor at (xP, yP) with dimensions width and height
 * @param xP the x coordinate of the survivor
 * @param yP the y coordinate of the survivor
 * @param width the width of the survivor
 * @param height the height of the survivor
 */
	public Survivor(int xP, int yP, int radius) {
		super(xP, yP, radius);
		bullets = 5;
		loadedBullets = MAX_LOADED;
	}
	
	/**
	 * @author Harrison
	 * @param xP
	 * @param yP
	 * @param img
	 * @param username
	 * @param ipAddress
	 * @param port
	 */
	public Survivor(String username, int xP, int yP, float dir, InetAddress ipAddress, int port, String img) {
		super(username, xP, yP, dir, ipAddress, port, img);
		bullets = 5;
		loadedBullets = MAX_LOADED;
	}
	
	/**
	 * removes a bullet from the survivor's loaded bullets (getLoadedBullets())
	 */
	public void shootBullet()
	{
		if(loadedBullets >= 0)
		{
			loadedBullets--;
		}
	}
	/**
	 * takes bullets from getBullets() and adds them to getLoadedBullets(), until getBullets() == 0 or getLoadedBullets() == MAX_LOADED
	 */
	public void reload()
	{
		if(loadedBullets < MAX_LOADED)
		{
			int missing = MAX_LOADED - loadedBullets;
			if(bullets >= missing)
			{
				bullets -= missing;
				loadedBullets += missing;
			}
			else
			{
				loadedBullets += bullets;
				bullets = 0;
			}
		}
	}
	
	/**
	 * 
	 * @return the amount of extra bullets that the survivor has, not those loaded in the gun
	 */
	public int getBullets()
	{
		return bullets;
	}
	/**
	 * 
	 * @return the amount of bullets that the survivor has loaded in the gun
	 */
	public int getLoadedBullets()
	{
		return loadedBullets;
	}
}

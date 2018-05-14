package game;
import java.net.InetAddress;

import processing.core.PApplet;

/**
 * A MovingEntity that can interact with other Entities
 * @author Will
 *
 */
public class Survivor extends Player{
	
/**
 * Construct a Survivor at (xP, yP) with dimensions width and height
 * @param xP the x coordinate of the survivor
 * @param yP the y coordinate of the survivor
 * @param width the width of the survivor
 * @param height the height of the survivor
 */
	public Survivor(int xP, int yP, int radius) {
		super(xP, yP, radius);
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
	}
}

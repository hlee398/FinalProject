package game;
import java.net.InetAddress;

import processing.core.PApplet;

/**
 * A MovingEntity that can interact with other Entities
 * @author Will
 *
 */
public class Survivor extends MovingEntity{

	private String username;
	private InetAddress ipAddress;
	private int port;
	
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
		super(xP, yP, 10); // HARDCODED RADIUS TO CHANGE HERE OR ALTER THIS CONSTRUCTOR
		this.setUsername(username);
		this.setIpAddress(ipAddress);
		this.setPort(port);
	}
	
	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public InetAddress getIpAddress() {
		return ipAddress;
	}


	public void setIpAddress(InetAddress ipAddress) {
		this.ipAddress = ipAddress;
	}


	public int getPort() {
		return port;
	}


	public void setPort(int port) {
		this.port = port;
	}
}

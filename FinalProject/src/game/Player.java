package game;

import java.net.InetAddress;

/**
 * Represents a player, a player can be either a surivor or zombie
 * @author Harrison
 *
 */
public abstract class Player extends MovingEntity{

	protected String username;
	protected InetAddress ipAddress;
	protected int port;

	/**
	 * Default constructor
	 * @param xP x position
	 * @param yP y position
	 * @param radius radius of the circle for the hitbox
	 */
	public Player(int xP, int yP, int radius) {
		super(xP, yP, radius);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Constructor for a player that has a username and ipAddress as well as port
	 * @param username username of the player
	 * @param xP x position
	 * @param yP y position
	 * @param dir direction player is facing
	 * @param ipAddress ipAddress of the client hosting the player
	 * @param port port of the client hosting the player
	 * @param img string that contains a location for an image to represent a player
	 */
	public Player(String username, int xP, int yP, float dir, InetAddress ipAddress, int port, String img) {
		super(xP, yP, 50); // HARDCODED RADIUS TO CHANGE HERE OR ALTER THIS CONSTRUCTOR
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

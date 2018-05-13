package game;

import java.net.InetAddress;

import willB.Shapes.Line;

/**
 * A MovingEntity that can interact with other Entities
 * @author Will
 *
 */
public class Zombie extends MovingEntity{

	
	private String username;
	private InetAddress ipAddress;
	private int port;

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
	
	public Zombie(String username, int xP, int yP, float dir, InetAddress ipAddress, int port, String img)
	{
		super(xP, yP, 30);
		this.username = username;
		this.setIpAddress(ipAddress);
		this.setPort(port);
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

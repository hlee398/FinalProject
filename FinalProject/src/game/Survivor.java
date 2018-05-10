package game;
import java.net.InetAddress;

import processing.core.PApplet;

/**
 * 
 * @author Will
 *
 */
public class Survivor extends MovingEntity{

	private String username;
	private InetAddress ipAddress;
	private int port;
	
/**
 * 
 * @param xP
 * @param yP
 * @param width
 * @param height
 */
	public Survivor(int xP, int yP, int width, int height) {
		super(xP, yP, width, height);
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
		super(xP, yP, 10, 10); // HARDCODED WIDTH AND HEIGHT TO CHANGE HERE OR ALTER THIS CONSTRUCTOR
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

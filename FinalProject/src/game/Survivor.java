package game;
import java.net.InetAddress;

import processing.core.PApplet;

public class Survivor extends MovingEntity{

	private String username;
	private InetAddress ipAddress;
	private int port;
	
	/**
	 * 
	 * @param xP
	 * @param yP
	 * @param img
	 */
	public Survivor(int xP, int yP, String img) {
		super(xP, yP, img);
	}
	
	/**
	 * 
	 * @param xP
	 * @param yP
	 * @param img
	 * @param username
	 * @param ipAddress
	 * @param port
	 */
	public Survivor(String username, int xP, int yP, float dir, InetAddress ipAddress, int port, String img) {
		super(xP, yP, img);
		this.setUsername(username);
		this.setIpAddress(ipAddress);
		this.setPort(port);
	}
	
	
	public void draw(PApplet drawer, float width, float height)
	{
		super.draw(drawer, width, height);
		
		drawer.ellipse(getX(), getY(), 10, 10);
		
		
		
		drawer.line(getX(), getY(), (int)(10*Math.cos(super.getDir()) + getX()), (int)(10*Math.sin(super.getDir()) + getY()));
		
		
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

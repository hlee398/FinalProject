package game;

import java.net.InetAddress;

public abstract class Player extends MovingEntity{

	protected String username;
	protected InetAddress ipAddress;
	protected int port;
	
	public Player(int xP, int yP, int radius) {
		super(xP, yP, radius);
		// TODO Auto-generated constructor stub
	}
	
	public Player(String username, int xP, int yP, float dir, InetAddress ipAddress, int port, String img) {
		super(xP, yP, 30); // HARDCODED RADIUS TO CHANGE HERE OR ALTER THIS CONSTRUCTOR
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

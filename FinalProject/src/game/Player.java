package game;
import java.net.InetAddress;

public class Player {
	
	private String username;
	private InetAddress ipAddress;
	private int port, x, y;
	private float dir;
	
	public Player(String username, InetAddress ipAddress, int port, int x, int y, float dir)
	{
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

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public float getDir() {
		return dir;
	}

	public void setDir(float dir) {
		this.dir = dir;
	}
}

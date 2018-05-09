package game;
import java.net.InetAddress;

public class Player {
	
	private String username;
	private InetAddress ipAddress;
	private int port;
	
	public Player(String username, InetAddress ipAddress, int port)
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
}

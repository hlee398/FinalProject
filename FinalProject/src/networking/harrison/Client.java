package networking.harrison;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Client {
	
	/*
	 * Code snippets from TheChernoProject on his game making tutorials
	 */
	
	public enum Error{
		NONE, INVALID_HOST, SOECKET_EXCEPTION
	}
	private Error errorCode = Error.NONE;
	
	private String ipAdress;
	private int port;
	
	private InetAddress serverAddress;	
	private DatagramSocket socket;
	
	private final static byte[] PACKET_HEADER = new byte[] {0x40, 0x40};
	private final static byte PACKET_TYPE_CONNECT = 0x01;
	
	/**
	 * Exampe format: 191.168.1.1:5000
	 * @param host
	 */
	public Client (String host)
	{
		String[] parts = host.split(":");
		if (parts.length != 2)
		{
			errorCode = Error.INVALID_HOST;
			return;
		}
		ipAdress = parts[0];
		try {
			port = Integer.parseInt(parts[1]);
		} catch (NumberFormatException e){
			errorCode = Error.INVALID_HOST;
			return;
		}
	}
	
	/**
	 * 
	 * @param port Example: 192.168.1.1
	 * @param host Example 5000
	 */
	public Client (int port, String host)
	{
		this.port = port;
		ipAdress = host;
	}
	
	public boolean connect()
	{
		try {
			serverAddress = InetAddress.getByName(ipAdress);

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			errorCode = Error.INVALID_HOST;
			return false;
		}
		
		try {
			socket = new DatagramSocket();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			errorCode = Error.SOECKET_EXCEPTION;
			return false;
		}
		
		sendConnectionPacket();
		//Waits for server to reply here
		return true;
	}
	
	
	public void send(byte[] data)
	{
		//Checks if the socket exists
		assert(socket.isConnected());
		
		//Creates a packet and sends it
		DatagramPacket packet = new DatagramPacket(data, data.length, serverAddress, port);
		try {
			socket.send(packet);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Error getErrorCode()
	{
		return errorCode;
	}
	
	private void sendConnectionPacket()
	{
		byte[] data = PACKET_HEADER;
		//byte[] data = "ConnectionPacket".getBytes();
		send(data);
	}
}

package networking.harrison;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.PortUnreachableException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.nio.channels.IllegalBlockingModeException;

import game.Game;
import game.Player;

public class Client {
	
	/*
	 * Code snippets from TheChernoProject on his game making tutorials
	 */
	
	public enum Error{
		NONE, INVALID_HOST, SOCKET_EXCEPTION
	}
	private Error errorCode = Error.NONE;
	
	private String ipAdress;
	private String username;
	private int port;
	
	private InetAddress serverAddress;	
	private DatagramSocket socket;

	private boolean listening = false;
	private Thread thread;
	
	private Game game;
	
	private final int MAX_PACKET_SIZE = 1024;
	private byte[] receivedDataBuffer = new byte[MAX_PACKET_SIZE * 10];
	
	/*
	private final static byte[] PACKET_HEADER = new byte[] {0x40, 0x40};
	private final static byte PACKET_TYPE_CONNECT = 0x01;
	*/
	
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
	
	public Client (int port, String host, String username, Game game)
	{
		this.port = port;
		ipAdress = host;
		this.username = username;
		
		this.game = game;
		
	}
	
	public void start()
	{
		//Creating a socket
		try {
			socket = new DatagramSocket();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		listening = true;
		//Lambda function creating new thread for listening for sockets
		thread = new Thread(() -> listen(), "Client-ListenThread");
		thread.start();
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
		
//		try {
//			socket = new DatagramSocket();
//		} catch (SocketException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			errorCode = Error.SOCKET_EXCEPTION;
//			return false;
//		}
		
//		System.out.println("Client connected to " + socket.getInetAddress());
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
	
	//Test method
	private void sendConnectionPacket()
	{
		//byte[] data = PACKET_HEADER;
		//byte[] data = "ConnectionPacket".getBytes();
		String cmd = "00," + username + "," + game.getDrawing().getME().getX() + "," + game.getDrawing().getME().getY() + "," + game.getDrawing().getME().getDir();
		byte[] data = cmd.getBytes();
		send(data);
	}
	
	public void listen() {
		while (listening)
		{
			DatagramPacket packet = new DatagramPacket(receivedDataBuffer, MAX_PACKET_SIZE);
			try {
				socket.receive(packet);
			} catch (IllegalBlockingModeException e) {
				e.printStackTrace();
			} catch (PortUnreachableException e) {
				e.printStackTrace();
			} catch (SocketTimeoutException e) {
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			process(packet);
		}
	}
	
	public void process(DatagramPacket packet)
	{
		String message = new String(packet.getData()).trim().substring(0, packet.getLength());
		System.out.println("Client received message " + message);
		String[] dataArray = message.split(",");
		String command = dataArray[0];
		String username = dataArray[1];
		int x = Integer.parseInt(dataArray[2]);
		int y = Integer.parseInt(dataArray[3]);
		float dir = Float.parseFloat(dataArray[4]);
		//System.out.println(command);
		//System.out.println(username);
		
		if (command.equals("00"))
		{
			Player newPlayer = new Player(username, packet.getAddress(), packet.getPort(), x, y, dir);
			
			game.players.add(newPlayer);
			System.out.println(game.players.toString());
			game.getDrawing().addSurvivor(username, x, y, dir);
		}
	}
}
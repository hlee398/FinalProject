package networking.harrison;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.PortUnreachableException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.nio.channels.IllegalBlockingModeException;

import game.Game;
import game.Survivor;

public class Server{
	
	/*
	 * Code snipets from TheChernoProject on his networking tutorials for game design
	 */
	
	//Use UDP for coordinate data (real time data)
	//Use TCP for hit detection, items
	
	private int port;
	private Thread thread;
	private boolean listening = false;
	private DatagramSocket socket;
	private Game game;
	
	//public static ArrayList<Player> players = new ArrayList<Player>();
	
	private final int MAX_PACKET_SIZE = 1024;
	private byte[] receivedDataBuffer = new byte[MAX_PACKET_SIZE * 10];
	
	public Server(Game game, int port)
	{
		this.game = game;
		this.port = port;
	}
	
	/**
	 * @return the port of the server
	 */
	public int getPort()
	{
		return port;
	}
	
	/**
	 * Starts the server
	 */
	public void start()
	{
		//Creating a socket
		try {
			socket = new DatagramSocket(port);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		listening = true;
		//Lambda function creating new thread for listening for sockets
		thread = new Thread(() -> listen(), "Server-ListenThread");
		thread.start();
	}
	
	//Listens for connections
	private void listen()
	{
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
	
	/**
	 * Sends data from the server to a client
	 * @param data 
	 * @param address InetAddress of the client
	 * @param port port number of the client
	 */
	public void send(byte[] data, InetAddress address, int port)
	{
		//Checks if the socket exists
		assert(socket.isConnected());
		
		//Creates a packet and sends it
		DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
		try {
			socket.send(packet);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void process(DatagramPacket packet)
	{
		/*
		byte[] data = packet.getData();
		InetAddress address = packet.getAddress();
		int port = packet.getPort();
		*/
		
		//Parses out information to be interpreted
		String message = new String(packet.getData()).trim().substring(0, packet.getLength());
		//System.out.println("Server received message " + message);
		String[] dataArray = message.split(",");
		String command = dataArray[0];
		String username = dataArray[1];
		int x = Integer.parseInt(dataArray[2]);
		int y = Integer.parseInt(dataArray[3]);
		float dir = Float.parseFloat(dataArray[4]);
		//System.out.println(command);
		//System.out.println(username);
		
//		String test = "xx,SendingMessageToClient";
//		byte[] dat = test.getBytes();
//		send( dat, packet.getAddress(), packet.getPort() );
		
		if (command.equals("00"))
		{
			Survivor newSurvivor = new Survivor(username, x, y, dir, packet.getAddress(), packet.getPort(), "");
			String cmd = "00," + newSurvivor.getUsername() + "," + x + "," + y + "," + dir;
			byte[] data = cmd.getBytes();
			
			// Sends newplayer to everybody
			for (int i = 0; i < game.getDrawing().getMovingEntities().size(); i++)
			{
				if (game.getDrawing().getMovingEntities().get(i) instanceof Survivor)
				{
					Survivor s = (Survivor)game.getDrawing().getMovingEntities().get(i);
					send(data, s.getIpAddress(), s.getPort());
				}
			}
		
			// Send everybody to newplayer
			for (int i = 0; i < game.getDrawing().getMovingEntities().size(); i++)
			{
				if (game.getDrawing().getMovingEntities().get(i) instanceof Survivor)
				{
					Survivor s = (Survivor)game.getDrawing().getMovingEntities().get(i);
					String cmd2 = "00," + s.getUsername() + "," + s.getX() + "," + s.getY() + "," + s.getDir();
					byte[] data2 = cmd2.getBytes();
					send(data2, newSurvivor.getIpAddress(), newSurvivor.getPort());
				}
			}
			
			//Adds new player to drawing surface
			game.getDrawing().addSurvivor(newSurvivor);

			System.out.println(game.getDrawing().toString());
		}
		else if (command.equals("01"))
		{
			//Updates the coordinate positions of the clients
			for (int i = 0; i < game.getDrawing().getMovingEntities().size(); i++)
			{
				if (game.getDrawing().getMovingEntities().get(i) instanceof Survivor)
				{
					Survivor s = (Survivor)game.getDrawing().getMovingEntities().get(i);
					if (s.getUsername().equals(username))
					{
						s.setX(x);
						s.setY(y);
						s.setDir(dir);
					}
					else
					{
						byte[] data = message.getBytes();
						send(data, s.getIpAddress(), s.getPort());
					}
				}
			}
			
		}
	}
}

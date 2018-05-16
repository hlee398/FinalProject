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
import game.Player;
import game.Survivor;
import game.Zombie;

/**
 * Server class that will make a server and recieve and send messages to clients connected to it
 * All data that is passed into the server will be in the format 0n,username,x,y,dir where n is the command specified to do this task
 * 00 = adding a new player
 * 01 = moving players 
 * 02 = adding a new zombie
 * 03 = disconnecting a player
 * 04 = updating health
 * @author Harrison
 *
 */
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
	
	/**
	 * Creates a server
	 * @param game a game object provided by the game class
	 * @param port the port that the server will use (any port)
	 */
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
	
	/**
	 * Lisetns for connections from clients
	 */
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
	 * @param data data in the format 0n,username,x,y,dir
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
	
	/**
	 * Process incoming data based on what command is used
	 * 00 = adding a player
	 * 01 = moving a player
	 * @param packet the incoming data
	 */
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
		
		if (command.equals("00")) //Adding survivors
		{
			Survivor newSurvivor = new Survivor(username, x, y, dir, packet.getAddress(), packet.getPort(), "");
			String cmd = "00," + newSurvivor.getUsername() + "," + x + "," + y + "," + dir;
			byte[] data = cmd.getBytes();
			
			// Sends newplayer to everybody
			for (int i = 0; i < game.getDrawing().getPlayers().size(); i++)
			{
				Player p = game.getDrawing().getPlayers().get(i);
				send(data, p.getIpAddress(), p.getPort());
			}
		
			// Send everybody to newplayer
			for (int i = 0; i < game.getDrawing().getPlayers().size(); i++)
			{
				Player p = game.getDrawing().getPlayers().get(i);
				String cmd2 = (p instanceof Survivor ? "00," : "02,") + p.getUsername() + "," + p.getX() + "," + p.getY() + "," + p.getDir();
				byte[] data2 = cmd2.getBytes();
				send(data2, newSurvivor.getIpAddress(), newSurvivor.getPort());
			}
			
			//Adds new player to drawing surface
			game.getDrawing().addSurvivor(newSurvivor);

			//System.out.println(game.getDrawing().toString() + " Player " + newSurvivor.getUsername() + " has connected");
		}
		else if (command.equals("01")) //Moving players
		{
			//int health = Integer.parseInt(dataArray[5]);
			//Updates the coordinate positions of the clients
			for (int i = 0; i < game.getDrawing().getPlayers().size(); i++)
			{
				Player p = game.getDrawing().getPlayers().get(i);
				if (p.getUsername().equals(username))
				{
					p.setX(x);
					p.setY(y);
					p.setDir(dir);
					//p.setHealth(health);
				}
				else
				{
					byte[] data = message.getBytes();
					send(data, p.getIpAddress(), p.getPort());
				}
			}
			
		}
		else if (command.equals("02")) //Adding a zombie 
		{
			Zombie newZombie = new Zombie(username, x, y, dir, packet.getAddress(), packet.getPort(), "");
			String cmd = "02," + newZombie.getUsername() + "," + x + "," + y + "," + dir;
			byte[] data = cmd.getBytes();
			
			// Sends new Zombie to everybody
			for (int i = 0; i < game.getDrawing().getPlayers().size(); i++)
			{
				Player p = game.getDrawing().getPlayers().get(i);
				send(data, p.getIpAddress(), p.getPort());
			}
		
			// Send everybody to new Zombie
			for (int i = 0; i < game.getDrawing().getPlayers().size(); i++)
			{
				Player p = game.getDrawing().getPlayers().get(i);
				String cmd2 = (p instanceof Survivor ? "00," : "02,") + p.getUsername() + "," + p.getX() + "," + p.getY() + "," + p.getDir();
				byte[] data2 = cmd2.getBytes();
				send(data2, newZombie.getIpAddress(), newZombie.getPort());
			}
			
			//Adds new player to drawing surface
			game.getDrawing().addZombie(newZombie);
		}
		else if (command.equals("03")) // Disconnects player from the server
		{	
			//Removes player from the array list
			for (int i = 0; i < game.getDrawing().getPlayers().size(); i++)
			{
				if (game.getDrawing().getPlayers().get(i).getUsername().equals(username))
				{
					game.getDrawing().getPlayers().remove(i);
					break;
				}
			}
			
			//Sending information about removal to every other client
			for (int j = 0; j < game.getDrawing().getPlayers().size(); j++)
			{
				Player p = game.getDrawing().getPlayers().get(j);
				String cmd = "03," + username + "," + p.getX() + "," + p.getY() + "," + p.getDir();
				byte[] data = cmd.getBytes();
				send(data, p.getIpAddress(), p.getPort());
			}
		}
		else if (command.equals("04")) //Has health so if player is hit, they will die once health = 0
		{
			int health = Integer.parseInt(dataArray[5]);
			//Updates the coordinate positions of the clients
			for (int i = 0; i < game.getDrawing().getPlayers().size(); i++)
			{
				Player p = game.getDrawing().getPlayers().get(i);
				if (p.getUsername().equals(username))
				{
					p.setX(x);
					p.setY(y);
					p.setDir(dir);
					p.setHealth(health);
				}
				
				if (packet.getAddress() != p.getIpAddress())
				{
					byte[] data = message.getBytes();
					send(data, p.getIpAddress(), p.getPort());
				}
			}
		}
	}
}

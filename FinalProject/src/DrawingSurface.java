

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

import javax.swing.JList;

import networking.SchoolClient;
import networking.SchoolServer;
import networking.PeerDiscovery;
import networking.NetworkDataObject;
import networking.NetworkMessenger;
import networking.NetworkListener;
import processing.core.PApplet;

public class DrawingSurface extends PApplet
{

	MovingEntity s;
	
	private InetAddress myIP;
	private PeerDiscovery discover;
	//private PeerDiscovery discover;
	
	private SchoolServer ss;
	private SchoolClient sc;
	private String programID;
	private int maxPerServer;
	private NetworkListener clientProgram;
	private JList<InetAddress> hostList, connectedList;
	private NetworkMessenger nm;
	private static final String messageTypePress = "MOUSE_PRESS";
	
	private static final int TCP_PORT = 4444;
	private static final int BROADCAST_PORT = 4444;
	
	public DrawingSurface()
	{
		programID = "Test";
		
		try {
			myIP = InetAddress.getLocalHost();
			System.out.println("Your Hostname/IP address is " + myIP);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			discover = new PeerDiscovery(InetAddress.getByName("255.255.255.255"),BROADCAST_PORT);
			System.out.println("\nBroadcast discovery server running on " + BROADCAST_PORT);
		} catch (IOException e1) {
			e1.printStackTrace();
			System.out.println("\nError starting broadcast discovery server on port " + BROADCAST_PORT + "\nCannot discover or be discovered.");
			//discoverButton.setEnabled(false);
		}
		
		s = new Survivor(100,100,"");

	}
	
	
	
	public void draw() //draws all objects in world
	{
		
		background(255,255,255);
		this.fill(255);
		
		s.draw(this, s.getX(), s.getY());
		s.setDir(mouseX, mouseY);
		s.move();
		
		
		
	}

	public void keyPressed()
	{
		if(key == 'w')
		{
			s.setYVelocity(-5);
			//if (nm != null)
			//	nm.sendMessage(NetworkDataObject.MESSAGE, messageTypePress, 'w');
		}
		if(key == 'a')
		{
			s.setXVelocity(-5);
		}
		if(key == 's')
		{
			s.setYVelocity(5);
		}
		if(key == 'd')
		{
			s.setXVelocity(5);
		}
		if(key == 'p')
		{
			programID = "Test";
			maxPerServer = 10;
			
			ss = new SchoolServer(programID, myIP);
			ss.setMaxConnections(maxPerServer);
			ss.waitForConnections(TCP_PORT);
			System.out.println("\nTCP server running on " + TCP_PORT);
//			if (discover != null)
//				discover.setDiscoverable(true);
			connect(myIP);
		}
		if(key == 'o')
		{
			//InetAddress host = hostList.getSelectedValue();
			String host = "192.168.1.70";
			connect(host);
		}
	}
	
	public void keyReleased()
	{
		if(key == 'w')
		{
			s.setYVelocity(0);
		}
		if(key == 'a')
		{
			s.setXVelocity(0);
		}
		if(key == 's')
		{
			s.setYVelocity(0);
		}
		if(key == 'd')
		{
			s.setXVelocity(0);
		}

			
	}
	
	public void mouseDragged()
	{
		
	}
	
	public void mouseReleased()
	{
		
	}
	
	private void disconnect() {
		if (sc != null) {
			sc.disconnect();
			sc = null;
		}
		//setButtons(true);
	}

	private void connect(String host) {
		try {
			connect(InetAddress.getByName(host));
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void connect(InetAddress host) {
		if (host != null) {
			disconnect();
			sc = new SchoolClient(programID, myIP);
			boolean success = sc.connect(host,TCP_PORT);
			if (!success) {
				System.out.println("\nCould not connect to "+host+" on " + TCP_PORT);
				sc.disconnect();
				sc = null;
			} else {
				System.out.println("\nConnected to "+host+" on " + TCP_PORT);
				
				
//				sc.addNetworkListener(clientProgram);
				sc.addNetworkListener(new NetworkMessageHandler());
//				clientProgram.connectedToServer(sc);
				//setButtons(false);
			}
		}
	}

	private class NetworkMessageHandler implements NetworkListener {
		@Override
		public void networkMessageReceived(NetworkDataObject ndo) {

			if (ndo.messageType.equals(NetworkDataObject.CLIENT_LIST)) {
				System.out.println("\nClient list updated.");
				connectedList.setListData(Arrays.copyOf(ndo.message, ndo.message.length, InetAddress[].class));
			} else if (ndo.messageType.equals(NetworkDataObject.DISCONNECT)) {
				System.out.println("\nDisconnected from " + ndo.dataSource);
				connectedList.setListData(new InetAddress[]{});
			}
			else if (ndo.messageType.equals(NetworkDataObject.MESSAGE)) 
			{
				if (ndo.message[0].equals(messageTypePress))
				{
					if(ndo.message[1].equals('w'))
					{
						s.setYVelocity(-5);
					}
				}
			}

		}

		@Override
		public void connectedToServer(NetworkMessenger nm) {
			// TODO Auto-generated method stub
		}

	}
}
package game;

import java.awt.Dimension;

import javax.swing.JFrame;

import networking.harrison.Client;
import networking.harrison.Server;
import processing.awt.PSurfaceAWT;
import processing.core.PApplet;
import javax.swing.JOptionPane;

/**
 * Creates a server, client and drawing surface
 * @author Harrison
 *
 */
public class Game {
	
	//Port number for School computers is 4444
	  
	  public static final int screenWidth = (java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width);
	  public static final int screenHeight = (java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height);

	private Server server;
	private Client client;
	private DrawingSurface drawing;
	private String username, ipAddressServer;
	private boolean isServer;
	
	
	
	public Game()
	{
		
		int nReply = JOptionPane.showConfirmDialog(null, "Run Server?");
		
		this.isServer = (nReply == 0);
		
		if (isServer)
		{
			server = new Server(this, 4444);
			server.start();
			
			drawing = new DrawingSurface(this);
			PApplet.runSketch(new String[]{""}, drawing);
			PSurfaceAWT surf = (PSurfaceAWT) drawing.getSurface();
			PSurfaceAWT.SmoothCanvas canvas = (PSurfaceAWT.SmoothCanvas) surf.getNative();
			JFrame window = (JFrame)canvas.getFrame();
			window.setTitle("Server");
			
			window.setBounds(0, 0, screenWidth, screenHeight);
			window.setMinimumSize(new Dimension(100,100));
			window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			window.setResizable(true);
	
			window.getHeight();
			
			window.setVisible(true);
		}
		else
		{
			username = JOptionPane.showInputDialog("Enter a username", "");
			ipAddressServer = JOptionPane.showInputDialog("Enter IP Address of server", "localhost");
			
			drawing = new DrawingSurface(this);
			PApplet.runSketch(new String[]{""}, drawing);
			PSurfaceAWT surf = (PSurfaceAWT) drawing.getSurface();
			PSurfaceAWT.SmoothCanvas canvas = (PSurfaceAWT.SmoothCanvas) surf.getNative();
			JFrame window = (JFrame)canvas.getFrame();
			window.setTitle(username);
			
			window.setBounds(0, 0, screenWidth, screenHeight);
			window.setMinimumSize(new Dimension(100,100));
			window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			window.setResizable(true);
	
			window.getHeight();
			
			window.setVisible(true);
			client = new Client(4444, ipAddressServer, username, this);
			client.start();
			if (!client.connect())
			{
				System.out.println("Client did not connect");
			}
		}
	}

	public DrawingSurface getDrawing() {
		return drawing;
	}
	
	public Client getClient()
	{
		return client;
	}
	
	public String getUserName()
	{
		return username;
	}
	
	public boolean getisServer()
	{
		return isServer;
	}
}

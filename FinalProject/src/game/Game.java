package game;

import java.awt.Dimension;

import javax.swing.JFrame;

import networking.harrison.Client;
import networking.harrison.Server;
import processing.awt.PSurfaceAWT;
import processing.core.PApplet;
import javax.swing.JOptionPane;

public class Game {

	private Server server;
	private Client client;
	private DrawingSurface drawing;
	private String username;
	private boolean isServer;
	
	public Game()
	{
		
		int nReply = JOptionPane.showConfirmDialog(null, "Run Server?");
		
		this.isServer = (nReply == 0);
		
		if (isServer)
		{
			server = new Server(this, 2048);
			server.start();
			
			drawing = new DrawingSurface(this);
			PApplet.runSketch(new String[]{""}, drawing);
			PSurfaceAWT surf = (PSurfaceAWT) drawing.getSurface();
			PSurfaceAWT.SmoothCanvas canvas = (PSurfaceAWT.SmoothCanvas) surf.getNative();
			JFrame window = (JFrame)canvas.getFrame();
			window.setTitle("Server");
			
			window.setSize(400, 300);
			window.setMinimumSize(new Dimension(100,100));
			window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			window.setResizable(true);
	
			window.getHeight();
			
			window.setVisible(true);
		}
		else
		{
			username = JOptionPane.showInputDialog("Enter a username", "");
			
			drawing = new DrawingSurface(this);
			PApplet.runSketch(new String[]{""}, drawing);
			PSurfaceAWT surf = (PSurfaceAWT) drawing.getSurface();
			PSurfaceAWT.SmoothCanvas canvas = (PSurfaceAWT.SmoothCanvas) surf.getNative();
			JFrame window = (JFrame)canvas.getFrame();
			window.setTitle(username);
			
			window.setSize(400, 300);
			window.setMinimumSize(new Dimension(100,100));
			window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			window.setResizable(true);
	
			window.getHeight();
			
			window.setVisible(true);
			client = new Client(2048, "localhost", username, this);
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

package game;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import networking.harrison.Client;
import networking.harrison.Server;
import processing.awt.PSurfaceAWT;
import processing.core.PApplet;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

/**
 * Creates a server, client and drawing surface
 * Also creates a menu for player inputs
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
	private boolean isServer, isSurvivor, gameStart, zombieWin, playerWin, tie;
	
	public Game()
	{
		setGameStart(false);
		setZombieWin(false);
		setPlayerWin(false);
		setTie(false);
		
		int nReply = JOptionPane.showConfirmDialog(null, "Run Server?");
		this.isServer = (nReply == 0);
		
		if (isServer)
		{
			server = new Server(this, 4444);
			server.start();
			
			drawing = new DrawingSurface(this, isSurvivor);
			PApplet.runSketch(new String[]{""}, drawing);
			PSurfaceAWT surf = (PSurfaceAWT) drawing.getSurface();
			PSurfaceAWT.SmoothCanvas canvas = (PSurfaceAWT.SmoothCanvas) surf.getNative();
			JFrame window = (JFrame)canvas.getFrame();
			window.setTitle("Shelby's Unknown Zombie Grounds Server");
			
			window.setBounds(0, 0, screenWidth, screenHeight);
			window.setMinimumSize(new Dimension(100,100));
			window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			window.setResizable(true);
	
			window.getHeight();
			
			window.setVisible(true);
			
			window.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
			window.addWindowListener(new WindowAdapter() {
				public void windowClosing (WindowEvent e)
				{
					int option = JOptionPane.showConfirmDialog(window, "Are you sure you want to close the game?", "Close Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					if (option == JOptionPane.YES_OPTION)
					{
						System.exit(0);
					}
				}
			});
		}
		else
		{
			//Object someVar = new Object();
			Menu menu = new Menu(this);

			/*
			try {
				someVar.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			*/
			
			
			
		}
	}
	
	public void done(String username, int playerType, String ipAddress) {

		/*
		username = JOptionPane.showInputDialog("Enter a username", "");
		int playertype = JOptionPane.showConfirmDialog(null, "Are you a player?");
		this.isSurvivor = (playertype == 0);
		ipAddressServer = JOptionPane.showInputDialog("Enter IP Address of server", "localhost");
		*/
		
		this.username = username;
		int playertype = playerType;
		this.isSurvivor = (playertype == 0);
		this.ipAddressServer = ipAddress;
		
		drawing = new DrawingSurface(this, isSurvivor);
		PApplet.runSketch(new String[]{""}, drawing);
		PSurfaceAWT surf = (PSurfaceAWT) drawing.getSurface();
		PSurfaceAWT.SmoothCanvas canvas = (PSurfaceAWT.SmoothCanvas) surf.getNative();
		JFrame window = (JFrame)canvas.getFrame();
		window.setTitle("Shelby's Unknown Zombie Grounds");		
		
		//set to not resizable because glitches happen with the shadows if it resizes
		window.setBounds(0, 0, screenWidth, screenHeight);
		window.setMinimumSize(new Dimension(screenWidth,screenHeight));
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
	
	public Server getServer()
	{
		return server;
	}
	
	public boolean getisSurvivor()
	{
		return isSurvivor;
	}

	public boolean isGameStart() {
		return gameStart;
	}

	public void setGameStart(boolean gameStart) {
		this.gameStart = gameStart;
	}

	public boolean isZombieWin() {
		return zombieWin;
	}

	public void setZombieWin(boolean zombieWin) {
		this.zombieWin = zombieWin;
	}

	public boolean isPlayerWin() {
		return playerWin;
	}

	public void setPlayerWin(boolean playerWin) {
		this.playerWin = playerWin;
	}

	public boolean isTie() {
		return tie;
	}

	public void setTie(boolean tie) {
		this.tie = tie;
	}
	
	public boolean isGameInProgess()
	{
		return !playerWin && !zombieWin && !tie;
	}
}

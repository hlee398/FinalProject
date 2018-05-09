package game;

import java.util.ArrayList;

import networking.harrison.Client;
import networking.harrison.Server;

public class Game {
	
	public ArrayList<Player> players = new ArrayList<Player>();
	private Server server;
	private Client client;
	private DrawingSurface drawing;
	
	public Game(boolean isServer, String username, DrawingSurface drawing)
	{
		this.drawing = drawing;
		
		if (isServer)
		{
			server = new Server(this, 2048);
			server.start();
		}
		else
		{
			
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
}

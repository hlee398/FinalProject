package game;

import java.util.ArrayList;
import java.util.Scanner;

import networking.harrison.Client;
import networking.harrison.Server;

public class Game {
	
	public ArrayList<Player> players = new ArrayList<Player>();
	private Server server;
	private Client client;
	
	public Game(boolean isServer)
	{
		if (isServer)
		{
			server = new Server(this, 2048);
			server.start();
		}
		else
		{
			System.out.println("Enter Your Username");
			Scanner scan = new Scanner(System.in);
			String username = scan.nextLine();
			//System.out.println(username);
			scan.close();
			
			client = new Client(2048, "localhost", username, this);
			if (!client.connect())
			{
				System.out.println("Client did not connect");
			}
			client.start();
			
		}
	}
}

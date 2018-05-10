package game;

import java.util.Scanner;

public class Main {
	
	private static Game game;
	
	public static void main(String args[]) {
		
		
		System.out.println("Press 1 to start a server, Press 2 if you are a client of the server");
		Scanner s = new Scanner(System.in);
		int n = s.nextInt();
		
		if (n == 1)
		{
			game = new Game(true, "");
		}
		else 
		{
			System.out.println("Enter Your Username");
			String username = s.next();
			//System.out.println(username);

			game = new Game(false, username);



		}
	}

}

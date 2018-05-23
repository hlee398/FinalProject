package game;


/**
 * This class runs the game class
 * @author Harrison, Will, Ran
 *
 */
public class Main {
	
	private static Game game;
	
	/**
	 * runs the game
	 * @param args
	 */
	public static void main(String args[]) {
		
		/*
		System.out.println("Press 1 to start a server, Press 2 if you are a client of the server");
		Scanner s = new Scanner(System.in);
		int n = s.nextInt();
		*/
		
		game = new Game();
	}

}

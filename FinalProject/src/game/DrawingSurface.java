package game;

import java.awt.Point;
import java.awt.geom.Line2D;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

/**
 * Draws all the things that will be displayed on the window
 * 
 * @author Harrison, Will
 *
 */
public class DrawingSurface extends PApplet {
	private Player p;
	private Wall w, w2;

	private ArrayList<Player> players = new ArrayList<>();
	private ArrayList<StaticEntity> staticEntities = new ArrayList<>();
	private String username;
	private PFont f;
	private PImage background, gameOverImage, tempImage;
	private Game g;
	public static final String SURVIVOR_IMAGE = "pics/survivor.png";
	public static final String WALL_IMAGE = "pics/8TileWall.png";
	public static final String ZOMBIE_IMAGE = "pics/zombie.png";
	//Dimensions of image are 1024 x 640

	private boolean isSurvivor, clientInitialized, isReloading;
	private long lastShot, reloadStart;

	public static final int MAX_SHOT_DIST = 200; // The farthest a shot can travel by a survivor - Should be slightly
													// more than their vision limit
	// Dimensions of image are 1024 x 640
	public static final String BACKGROUND_IMAGE = "cbble.png";
	// Dimensions of image are 470 x 402
	public static final String BACKGROUND_IMAGE2 = "cobble.png";
	public static final String GAME_OVER_IMAGE = "GameOverScreen.png";
	public final int TIME_BETWEEN_SHOTS = 500;
	public final int RELOAD_TIME = 2000;

	public DrawingSurface() {
		
	}

	/**
	 * Creates a drawing surface
	 * 
	 * @param game
	 */
	public DrawingSurface(Game game, boolean isSurvivor) {
		g = game;
		username = g.getUserName();
		this.isSurvivor = isSurvivor;
		this.clientInitialized = false;
		InetAddress localhost = null;
		try {
			localhost = InetAddress.getByName("localhost");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (this.isSurvivor) {
			p = new Survivor(username, 100, 100, 0, localhost, 4444, "SURVIVOR_IMAGE");
		} else {
			p = new Zombie(username, 50, 50, 0, localhost, 4444, ZOMBIE_IMAGE);
		}

		w = new Wall(300, 350, 50, 300);
		staticEntities.add(w);
		w2 = new Wall(600, 300, 200, 100);
		staticEntities.add(w2);

		// testZ = new Zombie(50, 50, 30);
		// addZombie(testZ);
	}

	public void setup() {
		f = createFont("Arial", 20, true);
		textFont(f);
		background = loadImage(BACKGROUND_IMAGE2);
		tempImage = loadImage(BACKGROUND_IMAGE);
		gameOverImage = loadImage(GAME_OVER_IMAGE);
	}

	public void draw() // draws all objects in world
	{
		if (isReloading) {

			if (System.currentTimeMillis() - reloadStart >= RELOAD_TIME) {
				isReloading = false;
			}

		}

		if (g.getisServer()) {
			// Checks to start game if there are at least 1 zombie and 1 survivor
			if (!g.isGameStart()) {
				int zombieCount = 0;
				int survivorCount = 0;
				for (int i = 0; i < players.size(); i++) {
					Player p = players.get(i);
					if (p instanceof Survivor) {
						survivorCount++;
					} else {
						zombieCount++;
					}
				}
				if (zombieCount > 0 && survivorCount > 0) {
					g.setGameStart(true);
					String cmd = "06," + "(null)," + 0 + "," + 0 + "," + 0;
					byte[] data = cmd.getBytes();
					for (int i = 0; i < players.size(); i++) {
						Player p = players.get(i);
						g.getServer().send(data, p.getIpAddress(), p.getPort());
					}
				}
			}

			// Checks if a team has won
			if (g.isGameStart()) {
				int zombieCount = 0;
				int survivorCount = 0;
				for (int i = 0; i < players.size(); i++) {
					Player p = players.get(i);
					if (p instanceof Survivor) {
						if (p.getHealth() > 0)
							survivorCount++;
					} else {
						if (p.getHealth() > 0)
							zombieCount++;
					}
				}
				if (zombieCount <= 0 || survivorCount <= 0) {
					g.setGameStart(false);
					String cmd = "";
					if (zombieCount <= 0 && survivorCount <= 0) // tie
					{
						cmd = "05," + "(null)," + 0 + "," + 0 + "," + 0 + "," + 0;
					} else if (zombieCount <= 0) // Survivor win
					{
						cmd = "05," + "(null)," + 0 + "," + 0 + "," + 0 + "," + 1;
					} else { // zombie win
						cmd = "05," + "(null)," + 0 + "," + 0 + "," + 0 + "," + -1;
					}
					byte[] data = cmd.getBytes();
					for (int i = 0; i < players.size(); i++) {
						Player p = players.get(i);
						g.getServer().send(data, p.getIpAddress(), p.getPort());
					}
				}
			}
		}

		// Moving the window code
		translate(this.width / 2 - p.getX(), this.height / 2 - p.getY());
		 background(0);
		// 1st column
		image(background, 0, 0);
		image(background, 0, 402);
		image(background, 0, 402 * 2);

		// 2nd column
		image(background, 470, 0);
		image(background, 470, 402);
		image(background, 470, 402 * 2);

		// 3rd column
		image(background, 470 * 2, 0);
		image(background, 470 * 2, 402);
		image(background, 470 * 2, 402 * 2);

		// 4th column
		image(background, 470 * 3, 0);
		image(background, 470 * 3, 402);
		image(background, 470 * 3, 402 * 2);

		// 4th column
		image(background, 470 * 4, 0);
		image(background, 470 * 4, 402);
		image(background, 470 * 4, 402 * 2);

		// this.fill(255);

		// Updates survivors
		for (int i = 0; i < players.size(); i++) {
			if (players.get(i) instanceof Survivor) {
				Survivor sOther = (Survivor) players.get(i);
				fill(255, 25, 0);
				text(sOther.getUsername() + " " + sOther.getHealth(), sOther.getX(), sOther.getY() + 60);
				fill(255);
				sOther.draw(this, sOther.getDir(), SURVIVOR_IMAGE);
			} else {
				Zombie zOther = (Zombie) players.get(i);
				fill(0);
				text(zOther.getUsername() + " " + zOther.getHealth(), zOther.getX() + 15, zOther.getY() + 60);
				fill(255);
				zOther.draw(this, zOther.getDir(), ZOMBIE_IMAGE);
			}
		}

		for (int i = 0; i < staticEntities.size(); i++) // STATICENTITY LOOP
		{
			if (staticEntities.get(i) instanceof Wall) {
				Wall current = ((Wall) staticEntities.get(i));
				p.checkWall(current);
				current.draw(this, WALL_IMAGE);
			} else if (staticEntities.get(i) instanceof Pickupable) // give health or bullets - need to check if p is
																	// Survivor or Zombie
			{

			}
		}

		// Checks if this is the server drawing surface
		if (!g.getisServer()) {
			// Drawing yourself if alive
			if (p.getisAlive()) {
				fill(0, 255, 216);
				text(username + " " + p.getHealth(), p.getX(), p.getY() + 60);
				fill(255);

				p.draw(this, mouseX - (this.width / 2 - p.getX()), mouseY - (this.height / 2 - p.getY()),
						p instanceof Survivor ? SURVIVOR_IMAGE : ZOMBIE_IMAGE);

				if (g.isGameStart()) {
					fill(0, 255, 0);
					rect(0, 0, 50, 50);
				} else {
					fill(255, 0, 0);
					rect(0, 0, 50, 50);

				}
			} else {
				// Draws a black screen with a game over message
				// image(gameOverImage, 0, 0);
			}

		

			// Checks if a zombie is in range, attacks and decreases health of player if the
			// zombie hits
			boolean survivorDamage = false;
			if (p instanceof Survivor) {
				Survivor s = (Survivor) p;
				for (int i = 0; i < players.size(); i++) {
					Player tempPlayer = players.get(i);
					if (tempPlayer instanceof Zombie) {
						Zombie tempZombie = (Zombie) tempPlayer;
						if (tempZombie.canAttack(s)) {
							s.damage(1);
							survivorDamage = true;
						}
					}
				}

				// System.out.println(s.getLoadedBullets() + " / " + s.getBullets());
			}

			// Allows player to move and send server messages if the player is alive
			if (p.getHealth() > 0) {
				p.move();
				// Send a message to the server with our (s) new coordinate
				String cmd = "04," + username + "," + p.getX() + "," + p.getY() + "," + p.getDir() + ","
						+ p.getHealth();
				byte[] data = cmd.getBytes();
				g.getClient().send(data);
				// Shoudl replace with isGameOver method but this is just a temp thing
				// if(g.isGameStart())
				// {
				generateBlindSpot(p, w);
				generateBlindSpot(p, w2);
				// }
			} else {
				// If the player died, then he can no longer move and is removed from the game
				if (p.getisAlive()) {
					// Set isAlive false
					p.setisAlive(false);
					// Send 03 msg to server
					// String cmd = "03," + username + "," + p.getX() + "," + p.getY() + "," +
					// p.getDir();
					// byte[] data = cmd.getBytes();
					// g.getClient().send(data);

					String cmd = "04," + username + "," + p.getX() + "," + p.getY() + "," + p.getDir() + ","
							+ p.getHealth();
					byte[] data = cmd.getBytes();
					g.getClient().send(data);
				}
			}
			
			if (g.isPlayerWin()) {
				// Draws a black screen with a game over message
				image(gameOverImage, p.getX() - displayWidth/2,p.getY() - displayHeight/2, displayWidth, displayHeight);
			}
			if (g.isZombieWin()) {
				// Draws a black screen with a game over message
				image(gameOverImage, p.getX() - displayWidth/2,p.getY() - displayHeight/2, displayWidth, displayHeight);
			}
			if (g.isTie()) {
				// Draws a black screen with a game over message
				image(gameOverImage, p.getX() - displayWidth/2,p.getY() - displayHeight/2, displayWidth, displayHeight);
			}
		}
	}

	public void keyPressed() {
		if(g.isPlayerWin() || g.isZombieWin() || g.isTie())
		{
			
		}
		else {
			int speed = (p instanceof Survivor) ? (3) : (5);
			if (speed == 3 && key == 'r') {
				((Survivor) p).reload();

				//System.out.println("RELOADING");

				isReloading = true;
				reloadStart = System.currentTimeMillis();
			}
			if (key == 'w') {
				p.setYVelocity(-speed);
			}
			if (key == 'a') {
				p.setXVelocity(-speed);
			}
			if (key == 's') {
				p.setYVelocity(speed);
			}
			if (key == 'd') {
				p.setXVelocity(speed);
			}
			if (key == 'p') {
				if (!p.getisAlive() && g.isGameInProgess()) {
					p.setisAlive(true);
					p.setHealth(100);
				}
			}
		}

	}

	public void keyReleased() {
		if (key == 'w') {
			p.setYVelocity(0);
		}
		if (key == 'a') {
			p.setXVelocity(0);
		}
		if (key == 's') {
			p.setYVelocity(0);
		}
		if (key == 'd') {
			p.setXVelocity(0);
		}
	}

	public void mousePressed() {
		long currentTime = System.currentTimeMillis();

		if (p instanceof Survivor && ((Survivor) p).getLoadedBullets() > 0
				&& currentTime - lastShot >= TIME_BETWEEN_SHOTS && !isReloading) {

			lastShot = System.currentTimeMillis();
			((Survivor) p).shootBullet();

			int sX = p.getX() + p.getWidth() / 2;
			int sY = p.getY() + p.getHeight() / 2;
			int difX = mouseX - (this.width / 2 - p.getX()) - sX;
			int difY = mouseY - (this.height / 2 - p.getY()) - sY;
			float scaler = (float) (MAX_SHOT_DIST / (Math.sqrt(difY * difY + difX * difX)));
			float x2 = (float) (sX + scaler * difX);
			float y2 = (float) (sY + scaler * difY);
			float minDist = -1;
			Zombie closest = null;

			Line2D.Float shot = new Line2D.Float(sX, sY, x2, y2);

			for (int i = 0; i < getPlayers().size(); i++) {
				if (players.get(i) instanceof Zombie) {
					Zombie target = (Zombie) players.get(i);
					if (target.isHit(shot))// IF TARGET IS HIT BY SHOT
					{
						float dist = dist(sX, sY, target.getX() + target.getWidth() / 2,
								target.getY() + target.getHeight() / 2);
						if (minDist == -1 || dist < minDist) {
							minDist = dist;
							closest = target;
						}
					}
				}
				if (closest != null) {
					closest.damage((int) (30 + Math.random() * 20)); // does 30 - 50 damage
					String cmd = "04," + closest.getUsername() + "," + closest.getX() + "," + closest.getY() + ","
							+ closest.getDir() + "," + closest.getHealth();
					byte[] data = cmd.getBytes();
					g.getClient().send(data);
				}

			}
			// TODO REMOVE BELOW
			line(sX, sY, x2, y2); // DRAWS SHOT TRAJECTORY TO BE REMOVED FOR FINAL GAME

			//System.out.println("SHOOT");
			//System.out.println(((Survivor) p).getLoadedBullets() + " / " + ((Survivor) p).getBullets());
		} else // no loaded bullets -- or reloading -- or shooting too fast
		{
			// play click noise
		}
	}

	public void mouseDragged() {

	}

	public void mouseReleased() {

	}

	public void addSurvivor(Survivor s) {
		players.add(s);
	}

	public void addSurvivor(String username, int x, int y, float dir, InetAddress ip, int port) {
		Survivor sNew = new Survivor(username, x, y, dir, ip, port, "");
		players.add(sNew);
	}

	public void addZombie(Zombie z) {
		players.add(z);
	}

	public void addZombie(String username, int x, int y, float dir, InetAddress ip, int port) {
		Zombie zNew = new Zombie(username, x, y, dir, ip, port, "");
		players.add(zNew);
	}

	public Player getME() {
		return p;
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	/**
	 * Creates a shadow behind the Wall from the perspective of the Player
	 * 
	 * @author William (thank you for your pain and suffering)
	 * @param player
	 *            player to create shadows for
	 * @param w
	 *            wall to create shadows for
	 */
	public void generateBlindSpot(Player player, Wall w) {

		int pX = player.getX() + player.getWidth() / 2;
		int pY = player.getY() + player.getHeight() / 2;

		Point p1 = new Point(w.getX(), w.getY()); // top l
		Point p2 = new Point(w.getX() + w.getWidth(), w.getY()); // top r
		Point p3 = new Point(w.getX() + w.getWidth(), w.getY() + w.getHeight()); // bottom r
		Point p4 = new Point(w.getX(), w.getY() + w.getHeight()); // bottom l

		if (pX <= w.getX()) {
			if (pY < w.getY()) // top left
			{
				beginShape();

				fill(0);

				vertex((float) p4.getX(), (float) p4.getY());
				vertex((float) p3.getX(), (float) p3.getY());
				vertex((float) p2.getX(), (float) p2.getY());

				int endpt1X = (this.width);
				int endpt1Y = pY + (w.getY() - pY) * (this.width - pX) / (w.getX() + w.getWidth() - pX);

				vertex(endpt1X, endpt1Y);
				vertex(this.width, this.height);

				int endpt2X = pX + ((this.height - pY) * (w.getX() - pX)) / (w.getHeight() + w.getY() - pY);
				int endpt2Y = this.height;

				vertex(endpt2X, endpt2Y);
				vertex((float) p4.getX(), (float) p4.getY());

				endShape(CLOSE);
			} else if (pY >= w.getY() + w.getHeight()) // bottom left
			{

				beginShape();

				fill(0);

				vertex((float) p1.getX(), (float) p1.getY());
				vertex((float) p2.getX(), (float) p2.getY());
				vertex((float) p3.getX(), (float) p3.getY());

				int endpt1X = (this.width);
				int endpt1Y = pY
						- ((this.width - pX) * (pY - w.getHeight() - w.getY()) / (w.getX() + w.getWidth() - pX));

				vertex(endpt1X, endpt1Y);
				vertex(this.width, 0);

				int endpt2X = pX + ((w.getX() - pX) * pY) / (pY - w.getY());
				int endpt2Y = 0;

				vertex(endpt2X, endpt2Y);
				vertex((float) p1.getX(), (float) p1.getY());

				endShape(CLOSE);
			} else // direct left
			{
				beginShape();

				fill(0);

				vertex((float) p1.getX(), (float) p1.getY());
				vertex((float) p2.getX(), (float) p2.getY());
				vertex((float) p3.getX(), (float) p3.getY());
				vertex((float) p4.getX(), (float) p4.getY());

				int endpt1X = (this.width);
				int endpt1Y = (int) (pY + ((this.width - pX) * (p4.getY() - pY)) / (p4.getX() - pX));

				vertex(endpt1X, endpt1Y);
				vertex(this.width, 0);

				int endpt2X = (this.width);
				int endpt2Y = (int) (pY - ((this.width - pX) * (p1.getY() - pY)) / (pX - p1.getX()));

				vertex(endpt2X, endpt2Y);
				vertex((float) p1.getX(), (float) p1.getY());

				endShape(CLOSE);

			}
		} else if (pX < w.getX() + w.getWidth() && pX > w.getX()) {
			if (pY < w.getY()) // direct top
			{
				beginShape();

				fill(0);

				vertex((float) p2.getX(), (float) p2.getY());
				vertex((float) p3.getX(), (float) p3.getY());
				vertex((float) p4.getX(), (float) p4.getY());
				vertex((float) p1.getX(), (float) p1.getY());

				int endpt1X = pX - (this.height - pY) * (pX - w.getX()) / (w.getY() - pY);
				int endpt1Y = this.height;

				vertex(endpt1X, endpt1Y);
				vertex(0, this.height);

				int endpt2X = pX + ((w.getX() + w.getWidth()) - pX) * (this.height - pY) / (w.getY() - pY);
				int endpt2Y = this.height;

				vertex(endpt2X, endpt2Y);
				vertex((float) p2.getX(), (float) p2.getY());

				endShape(CLOSE);
			} else // direct bottom
			{
				beginShape();

				fill(0);

				vertex((float) p4.getX(), (float) p4.getY());
				vertex((float) p1.getX(), (float) p1.getY());
				vertex((float) p2.getX(), (float) p2.getY());
				vertex((float) p3.getX(), (float) p3.getY());

				int endpt1X = pX + (w.getX() + w.getWidth() - pX) * (pY) / (pY - w.getY() - w.getHeight());
				int endpt1Y = 0;

				vertex(endpt1X, endpt1Y);
				vertex(0, 0);

				int endpt2X = pX - (pX - w.getX()) * (pY) / (pY - w.getY() - w.getHeight());
				int endpt2Y = 0;

				vertex(endpt2X, endpt2Y);
				vertex((float) p4.getX(), (float) p4.getY());

				endShape(CLOSE);
			}
		} else if (pX >= w.getX() + w.getWidth()) {
			if (pY <= w.getY()) // top right
			{
				beginShape();

				fill(0);

				vertex((float) p1.getX(), (float) p1.getY());
				vertex((float) p4.getX(), (float) p4.getY());
				vertex((float) p3.getX(), (float) p3.getY());

				int endpt1X = pX
						- (pX - w.getX() - w.getWidth()) * (this.height - pY) / (w.getY() + w.getHeight() - pY);
				int endpt1Y = this.height;

				vertex(endpt1X, endpt1Y);
				vertex(0, this.height);

				int endpt2X = 0;
				int endpt2Y = pY + (w.getY() - pY) * (pX) / (pX - w.getX());

				vertex(endpt2X, endpt2Y);
				vertex((float) p1.getX(), (float) p1.getY());

				endShape(CLOSE);
			} else if (pY >= w.getY() + w.getHeight()) // bottom Right
			{
				beginShape();

				fill(0);

				vertex((float) p2.getX(), (float) p2.getY());
				vertex((float) p1.getX(), (float) p1.getY());
				vertex((float) p4.getX(), (float) p4.getY());

				int endpt1X = 0;
				int endpt1Y = pY - (pY - w.getY() - w.getHeight()) * (pX) / (pX - w.getX());

				vertex(endpt1X, endpt1Y);
				vertex(0, 0);

				int endpt2X = pX - (pX - w.getX() - w.getWidth()) * (pY) / (pY - w.getY());
				int endpt2Y = 0;

				vertex(endpt2X, endpt2Y);
				vertex((float) p2.getX(), (float) p2.getY());

				endShape(CLOSE);
			} else // Direct Right
			{
				beginShape();

				fill(0);

				vertex((float) p2.getX(), (float) p2.getY());
				vertex((float) p1.getX(), (float) p1.getY());
				vertex((float) p4.getX(), (float) p4.getY());
				vertex((float) p3.getX(), (float) p3.getY());

				int endpt1X = 0;
				int endpt1Y = pY + (w.getY() + w.getHeight() - pY) * (pX) / (pX - w.getX() - w.getWidth());

				vertex(endpt1X, endpt1Y);
				vertex(0, 0);

				int endpt2X = 0;
				int endpt2Y = pY - (pY - w.getY()) * (pX) / (pX - w.getX() - w.getWidth());

				vertex(endpt2X, endpt2Y);
				vertex((float) p2.getX(), (float) p2.getY());

				endShape(CLOSE);
			}

		}
	}

	public void exit() {
		if (!g.getisServer()) {
			String cmd = "03," + username + "," + p.getX() + "," + p.getY() + "," + p.getDir();
			byte[] data = cmd.getBytes();
			g.getClient().send(data);
		}

		super.exit();
	}
}
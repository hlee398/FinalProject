package game;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.net.InetAddress;

/**
 * A MovingEntity that can interact with other Entities
 * Zombies can attack survivors and damage them if they touch them
 * @author Will
 */
public class Zombie extends Player{

/**
 * Construct a Zombie at (xP, yP) with dimensions width and height
 * @param xP the x coordinate of the zombie
 * @param yP the y coordinate of the zombie
 * @param width the width of the zombie
 * @param height the height of the zombie
 */
	public Zombie(int xP, int yP, int radius) {
		super(xP, yP, radius);
	}
	
	public Zombie(String username, int xP, int yP, float dir, InetAddress ipAddress, int port, String img)
	{
		super(username, xP, yP, dir, ipAddress, port, img);
	}
	/**
	 * Tells whether or not the zombie can attack the survivor
	 * @param s the survivor being checked
	 * @return True if zombie is close enough to the survivor. Otherwise False 
	 */
	public boolean canAttack(Survivor s)
	{
		if(this.getisAlive()) {
			int zX = getX() + getWidth()/2;
			int zY = getY() + getHeight()/2;
			int sX = s.getX() + s.getWidth()/2;
			int sY = s.getY() + s.getHeight()/2;
			
			Point2D.Float sCenter = new Point2D.Float(sX,sY);
			Point2D.Float zCenter = new Point2D.Float(zX,zY);
			
			if(zCenter.distance(sCenter) < getWidth()/2 + s.getWidth()/2)
			{
				return true;
			}
		}
		else {
			return false;
		}
		return false;
	}

	/**
	 * checks if the player has been hit
	 * @param shot Line directed at where the player shot
	 * @return true if the lines intersect, false otherwise
	 */
	public boolean isHit(Line2D.Float shot)
	{
		if(getBounds().intersectsLine(shot))
		{
			return true;
		}
		return false;
	}
}

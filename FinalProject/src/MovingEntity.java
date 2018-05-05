
public abstract class MovingEntity extends Entity{

	private float dir;
	private int xVel,yVel;
	
	public MovingEntity(int xP, int yP, String img) {
		super(xP, yP, img);
		dir = 0;
		xVel = 0;
		yVel = 0;
	}

	public void moveTo(int x, int y)
	{
		super.setX(x);
		super.setY(y);
	}	
	public void setDir(int mouseX, int mouseY)
	{
		//TODO  arctan math wrong -pi to pi 
		float xDif = (float) (getX() - mouseX);
		float yDif = (float) (getY() - mouseY);
		
		dir = (float) Math.atan(yDif/xDif);
		
		if(xDif > 0)
		{
			dir += Math.PI;
		}

		if(xDif == 0)
		{
			dir += Math.PI;
			if(yDif == 0)
			{
				dir = 0;
			}
		}
		System.out.println(xDif + " , " + yDif + "   :   " + dir);
		
	}
	
	public float getDir()
	{
		return dir;
	}
	
	public void setXVelocity(int x) // sets players movement velocity
	{
		xVel = x;
	}
	public void setYVelocity(int y) // sets players movement velocity
	{
		yVel = y;
	}
	
	public void move() // moves the player
	{
		super.setX(getX() + xVel);
		super.setY(getY() + yVel);
	}
}
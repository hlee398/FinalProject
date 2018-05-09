

import processing.core.PApplet;


public class DrawingSurface extends PApplet{

	MovingEntity s;
	StaticEntity w;
	

	public DrawingSurface()
	{
		s = new Survivor(100,100,30,60, "Stickman.png");
		w = new Wall(200, 200, 100, 10, "Wall.jpg");
	}
	
	public void draw() //draws all objects in world
	{
		
		background(255,255,255);
		this.fill(255);
		
		s.draw(this, s.getX(), s.getY(), mouseX, mouseY);
		s.move();
		
		w.draw(this,1);
	}

	public void keyPressed()
	{
		if(key == 'w')
		{
			s.setYVelocity(-5);
		}
		if(key == 'a')
		{
			s.setXVelocity(-5);
		}
		if(key == 's')
		{
			s.setYVelocity(5);
		}
		if(key == 'd')
		{
			s.setXVelocity(5);
		}

	}
	
	public void keyReleased()
	{
		if(key == 'w')
		{
			s.setYVelocity(0);
		}
		if(key == 'a')
		{
			s.setXVelocity(0);
		}
		if(key == 's')
		{
			s.setYVelocity(0);
		}
		if(key == 'd')
		{
			s.setXVelocity(0);
		}

			
	}
	
	public void mouseDragged()
	{
		
	}
	
	public void mouseReleased()
	{
		
	}




}
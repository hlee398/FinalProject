package game;

import processing.core.PApplet;

public class StaticEntity extends Entity{
	
	public StaticEntity(int xP, int yP, int width, int height) {
		super(xP, yP,width,height);
	}
	
	public void draw(PApplet drawer, float dir, String img)
	{
		super.setDir(getDir());
		super.draw(drawer, img);
		
	}
	


}
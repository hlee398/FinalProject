package game;
import java.awt.Dimension;
import java.util.Scanner;

import javax.swing.JFrame;

import processing.awt.PSurfaceAWT;
import processing.core.PApplet;

public class Main {
	
	static Game game;
	
	public static void main(String args[]) {
		
		
		System.out.println("Press 1 to start a server, Press 2 if you are a client of the server");
		Scanner s = new Scanner(System.in);
		int n = s.nextInt();
		
		if (n == 1)
		{
			game = new Game(true);
		}
		else 
		{
			game = new Game(false);
			
			DrawingSurface drawing = new DrawingSurface();
			PApplet.runSketch(new String[]{""}, drawing);
			PSurfaceAWT surf = (PSurfaceAWT) drawing.getSurface();
			PSurfaceAWT.SmoothCanvas canvas = (PSurfaceAWT.SmoothCanvas) surf.getNative();
			JFrame window = (JFrame)canvas.getFrame();
			
			window.setSize(400, 300);
			window.setMinimumSize(new Dimension(100,100));
			window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			window.setResizable(true);
	
			window.getHeight();
			
			window.setVisible(true);
		}
	}

}

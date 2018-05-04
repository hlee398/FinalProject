import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

/**
   TODO Write a one-sentence summary of your class here.
   TODO Follow it with additional details about its purpose, what abstraction
   it represents, and how to use it.

   @author  TODO Your Name
   @version TODO Date

   Period - TODO Your Period
   Assignment - TODO Name of Assignment

   Sources - TODO list collaborators
 */
public class SimpleWindow extends JPanel implements MouseMotionListener
{
	// TODO Your Instance Variables Here

	private double x,y;
	private Image duck;
	
	public SimpleWindow () {
		super();

		setBackground(Color.WHITE);
		addMouseMotionListener(this);

		try {
			duck = ImageIO.read(new File("duck.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);  // Call JPanel's paintComponent method to paint the background

		int width = getWidth();
		int height = getHeight();

		double ratioX = width/800.0;
		double ratioY = height/600.0;
		
		g = g.create();
		
		Graphics2D g2 = (Graphics2D)g;
		
		g2.scale(ratioX, ratioY);
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 800, 600);
		
		Ellipse2D.Double fogShape = new Ellipse2D.Double(x-75,y-75,150,150);
		
		g2.setClip(fogShape);

		g.drawImage(duck, 0, 0, 800, 600, this);
		// TODO Add any custom drawings here
		
		g2.setPaint(new RadialGradientPaint((float)x, (float)y, (float)(fogShape.width/2), new float[]{0f,1f}, new Color[]{new Color(0,0,0,0), new Color(0,0,0,255)}));
		g2.fill(fogShape);
	}


	// As your program grows, you may want to...
	//   1) Move this main method into its own 'main' class
	//   2) Customize the JFrame by writing a class that extends it, then creating that type of object in your main method instead.
	//   3) Rename this class (SimpleWindow is not a good name - this class actually represents the *Panel*, not the window)
	public static void main(String[] args)
	{
		JFrame w = new JFrame("Simple Window");
		w.setBounds(100, 100, 800, 600);
		w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SimpleWindow panel = new SimpleWindow();
		w.add(panel);
		w.setResizable(true);
		w.setVisible(true);
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		int width = getWidth();
		int height = getHeight();

		double ratioX = width/800.0;
		double ratioY = height/600.0;
		
		x = arg0.getX()/ratioX;
		y = arg0.getY()/ratioY;
		
		repaint();
	}
}

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;

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
public class SimpleWindow extends JPanel implements KeyListener
{
  // TODO Your Instance Variables Here
  private Link hero;

  public SimpleWindow () {
	  super();
	  setBackground(Color.WHITE);
	  hero = new Link(100,400, this);
	  // TODO Add more customizations to the panel

  }

  public void paintComponent(Graphics g)
  {
    super.paintComponent(g);  // Call JPanel's paintComponent method to paint the background

    int width = getWidth();
    int height = getHeight();
    
    Graphics2D g2 = (Graphics2D)g;
    
    hero.draw(g2, this);
	// TODO Add any custom drawings here
    
    g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
    g.drawString("Left Arrow: Block", 10, 30);
    g.drawString("Right Arrow: Slash", 10, 50);
  }
  
  
  @Override
  public void keyPressed(KeyEvent arg0) {
	  int code = arg0.getKeyCode();
	  if (code == KeyEvent.VK_LEFT) {
		  hero.block();
	  } else if (code == KeyEvent.VK_RIGHT) {
		  hero.slash();
	  }
	  repaint();
  }

  @Override
  public void keyReleased(KeyEvent arg0) {

  }

  @Override
  public void keyTyped(KeyEvent arg0) {
  	
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
    w.addKeyListener(panel);
    w.add(panel);
    w.setResizable(true);
    w.setVisible(true);
  }




}

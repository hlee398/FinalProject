package game;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Menu extends JPanel{
	
	
	public Menu ()
	{
		JFrame window = new JFrame("Client Configuration");
		window.setBounds(300, 300, 700, 480);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.add(this);
		window.setVisible(true);
	}
}

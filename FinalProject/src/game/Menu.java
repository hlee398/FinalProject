package game;
import java.awt.*;

import javax.swing.*;

public class Menu extends JPanel {

	private JFrame frame;
	
	private Game m;
	
	public Menu(Game m)
	{
		this.m = m;
		frame = new JFrame("Client Configuration");
		frame.setBounds(0, 0, 400, 400);
		frame.add(this);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		//JPanel p = new JPanel(new BorderLayout());
		
	}
	
	
	public void done() {
		m.done();
	}
}

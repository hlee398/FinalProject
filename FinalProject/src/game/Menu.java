package game;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Menu extends JPanel {

	private JFrame frame;
	private String username;
	private Game m;
	
	public Menu(Game m)
	{
		JTextField usernameArea = new JTextField();
		usernameArea.addActionListener(new ActionListener()
				{

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						username = usernameArea.getText();
					}
			
				});
		usernameArea.setEditable(true);
		
		BorderLayout b = new BorderLayout();
		this.setLayout(b);
		this.add(usernameArea, BorderLayout.EAST);
		
		this.m = m;
		frame = new JFrame("Client Configuration");
		frame.setBounds(0, 0, 400, 400);
		frame.add(this);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		//m.done();
	}
	
	
	public void done() {
		
		m.done();
	}

}

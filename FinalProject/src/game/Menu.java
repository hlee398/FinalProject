package game;

import java.awt.Dialog;

import javax.swing.*;

public class Menu {

	private JFrame frame;
	private JDialog dialog;
	
	public Menu()
	{
		frame = new JFrame("Client Configuration");
		frame.setBounds(0, 0, 400, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frame.add(this);

		
		dialog = new JDialog(frame, "Client Configuration", Dialog.ModalityType.APPLICATION_MODAL);
		frame.setVisible(true);
	}
}

package gui;

import javax.swing.JOptionPane;

import networking.frontend.NetworkManagementPanel;

public class Main {
	  public static void main(String[] args)
	  {
			String[] options = { "Chat", "Draw" };
			int demo = JOptionPane.showOptionDialog(null, "Which demo?", "Networking Demo", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
			
			if (demo == 0) {
				ChatPanel panel = new ChatPanel();
				
				// To open the network management window, just create an object of type NetworkManagementPanel.
				NetworkManagementPanel nmp = new NetworkManagementPanel("Chat", 20, panel);  
			} else if (demo == 1) {
				DrawingPanel panel = new DrawingPanel();
				NetworkManagementPanel nmp = new NetworkManagementPanel("Draw", 6, panel);
			}

	  }
}

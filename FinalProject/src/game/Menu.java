package game;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.swing.*;

/**
 * Creates a menu that takes in a username, ip address, and sets whether you are a zombie or not
 * @author Harrison
 *
 */
public class Menu extends JPanel implements ActionListener{

	private JFrame frame;
	private String username, ipAddress;
	private Game m;
	private JButton doneButton;
	private JRadioButton survivorBut, zombieBut;
	private ButtonGroup group;
	private JLabel usernameLabel, selectButtons, ipLabel;
	private JTextField usernameInput, ipInput;
	private int playerType;
	
	/**
	 * Creates a menu by taking in a game object
	 * @param m
	 */
	public Menu(Game m)
	{
		this.m = m;
		frame = new JFrame("Client Configuration");
		frame.setBounds(0, 0, 400, 200);
		frame.add(this);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		
		playerType = 0;
		
		//title = new JLabel("Enter your information:");
		
		JPanel userPanel = new JPanel(new GridLayout(1,2));
		usernameLabel = new JLabel("Enter your username:");
		userPanel.add(usernameLabel);
		usernameInput = new JTextField();
		userPanel.add(usernameInput);
		usernameInput.addActionListener(this);
		
		JPanel ipPanel = new JPanel(new GridLayout(1, 2));
		ipLabel = new JLabel("Enter your IP Address:");
		ipPanel.add(ipLabel);
		ipInput = new JTextField("localhost");
		ipPanel.add(ipInput);
		
		selectButtons = new JLabel("Select player type:");
		
		survivorBut = new JRadioButton("Survivor");
		survivorBut.addActionListener(this);
		survivorBut.setActionCommand("Survivor");
		survivorBut.setSelected(true);
		
		zombieBut = new JRadioButton("Zombie");
		zombieBut.addActionListener(this);
		zombieBut.setActionCommand("Zombie");
		//zombieBut.setSelected(false);
		
		group = new ButtonGroup();
		group.add(survivorBut);
		group.add(zombieBut);
		
		JPanel radioPanel = new JPanel(new GridLayout(1,3));
		radioPanel.add(selectButtons);
		radioPanel.add(survivorBut);
		radioPanel.add(zombieBut);
		
		
		BorderLayout b = new BorderLayout();
		this.setLayout(b);
		//this.add(title, BorderLayout.NORTH);
		JPanel menuPanel = new JPanel(new GridLayout(3, 1));
		menuPanel.add(userPanel);
		menuPanel.add(ipPanel);
		menuPanel.add(radioPanel);
		//this.add(radioPanel, BorderLayout.LINE_START);
		this.add(menuPanel);
		
		doneButton = new JButton("Start Game!");
		doneButton.addActionListener(this);
		this.add(doneButton, BorderLayout.SOUTH);
		
		frame.setBounds(0, 0, 400, 200);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		//paintComponents(g);
	}
	
	/*
	public void paintComponents(Graphics g)
	{
		repaint();
	*/
	
	/**
	 * closes the menu
	 */
	public void close()
	{
		username = usernameInput.getText();
		ipAddress = ipInput.getText();
		
		if (username.equals(""))
		{
			JOptionPane.showMessageDialog(frame, "Enter a valid username");
			return;
		}
		
		if (!ipAddress.equals("localhost")) 
		{
			if (!validIP(ipAddress))
			{
				JOptionPane.showMessageDialog(frame, "Enter a valid IP address");
				return;
			}
		}	
		
		frame.setVisible(false);
		frame.dispose();
		
		m.done(username, playerType, ipAddress);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object source = e.getSource();
		if (source == doneButton)
		{
			close();
		}
		else if (e.getActionCommand().equals("Survivor"))
		{
			playerType = 0;
			System.out.println(playerType);
		}
		else if (e.getActionCommand().equals("Zombie"))
		{
			playerType = 1;
			System.out.println(playerType);
		}
	}
	
	/**
	 * @author Mat B from stackoverflow
	 * @param ip ip address
	 * @return a validIP address
	 */
	public static boolean validIP(String ip) {
	    if (ip == null || ip.isEmpty()) return false;
	    ip = ip.trim();
	    if ((ip.length() < 6) & (ip.length() > 15)) return false;

	    try {
	        Pattern pattern = Pattern.compile("^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$");
	        Matcher matcher = pattern.matcher(ip);
	        return matcher.matches();
	    } catch (PatternSyntaxException ex) {
	        return false;
	    }
	}
}

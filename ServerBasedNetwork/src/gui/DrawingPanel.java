package gui;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import networking.frontend.NetworkDataObject;
import networking.frontend.NetworkListener;
import networking.frontend.NetworkMessenger;

import java.util.*;
import java.io.Serializable;


public class DrawingPanel extends JPanel implements ActionListener, MouseMotionListener, MouseListener, NetworkListener, Runnable
{
	private JButton colorButton;
	private JFrame window;

	private ArrayList<Cursor> cursors;
	private ArrayList<Cursor> trails;

	private Cursor me;

	private static final String messageTypeInit = "CREATE_CURSOR";
	private static final String messageTypeMove = "MOUSE_MOVE";
	private static final String messageTypePress = "MOUSE_PRESS";
	private static final String messageTypeColor = "COLOR_SWITCH";
	
	private NetworkMessenger nm;

	public DrawingPanel () {
		setLayout(null);
		setBackground(Color.WHITE);

		colorButton = new JButton("Color");
		colorButton.addActionListener(this);
		colorButton.setBounds(15, 35, 100, 25);
		add(colorButton);

		addMouseMotionListener(this);
		addMouseListener(this);

		cursors = new ArrayList<Cursor>();
		trails = new ArrayList<Cursor>();

		me = new Cursor();
		me.color = Color.BLACK;
		me.host = "me!";
		cursors.add(me);

		new Thread(this).start();

		JFrame window = new JFrame("Peer Draw");
		window.setBounds(300, 300, 600, 480);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.add(this);
		window.setVisible(true);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		synchronized(trails) {
			for (Cursor c : trails) {
				g.setColor(new Color(c.color.getRed(), c.color.getGreen(), c.color.getBlue(), c.timeOut*255/Cursor.TIMEOUT_MAX));
				g.fillOval(c.x-5, c.y-5, 10, 10);
			}
		}

		synchronized(cursors) {
			for (Cursor c : cursors) {
				g.setColor(c.color);
				g.fillOval(c.x-5, c.y-5, 10, 10);
			}
			g.setColor(Color.BLACK);
			g.drawString("Connected users: ", 10, 25);
			int x = g.getFontMetrics().stringWidth("Connected users: ") + 25;

			for (Cursor c : cursors) {
				g.setColor(c.color);
				g.fillRect(x, 15, 10, 10);
				x += 25;
			}
		}


	}

	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == colorButton) {
			Color out = JColorChooser.showDialog(this, "Choose a color!", me.color);
			if (out != null)
				me.color = out;
			if (nm != null)
				nm.sendMessage(NetworkDataObject.MESSAGE, messageTypeColor, me.color );
		}


	}


	@Override
	public void networkMessageReceived(NetworkDataObject ndo) {
		String host = ndo.getSourceIP();

		if (ndo.messageType.equals(NetworkDataObject.MESSAGE)) {
			if (ndo.message[0].equals(messageTypeMove)) {
				synchronized(cursors) {
					for (Cursor c : cursors) {
						if (c.host.equals(host)) {
							c.x = (Integer)ndo.message[1];
							c.y = (Integer)ndo.message[2];
						}
					}
				}
			} else if (ndo.message[0].equals(messageTypePress)) {
				synchronized(cursors) {
					for (Cursor c : cursors) {
						if (c.host.equals(host)) {
							c.x = (Integer)ndo.message[1];
							c.y = (Integer)ndo.message[2];
							c.makeTrailCopy();
						}
					}
				}
			} else if (ndo.message[0].equals(messageTypeInit)) {
				synchronized(cursors) {
					for (Cursor c : cursors) {
						if (c.host.equals(host))
							return;
					}
					Cursor c = new Cursor();
					c.x = (Integer)ndo.message[1];
					c.y = (Integer)ndo.message[2];
					c.color = (Color)ndo.message[3];
					c.host = host;
					cursors.add(c);
				}
			} else if (ndo.message[0].equals(messageTypeColor)) {
				synchronized(cursors) {
					for (Cursor c : cursors) {
						if (c.host.equals(host)) {
							c.color = (Color)ndo.message[1];
						}
					}
				}
			}
		} else if (ndo.messageType.equals(NetworkDataObject.CLIENT_LIST)) {
			nm.sendMessage(NetworkDataObject.MESSAGE, messageTypeInit, me.x, me.y, me.color);
		} else if (ndo.messageType.equals(NetworkDataObject.DISCONNECT)) {
			synchronized(cursors) {
				if (ndo.dataSource.equals(ndo.serverHost)) {
					cursors.clear();
					cursors.add(me);
				} else {
					for (int i = cursors.size()-1; i >= 0; i--)
						if (cursors.get(i).host.equals(host))
							cursors.remove(i);
				}
			}
		}
		repaint();

	}
	
	@Override
	public void connectedToServer(NetworkMessenger nm) {
		this.nm = nm;
	}



	@Override
	public void run() {
		do {
			long startTime = System.currentTimeMillis();

			synchronized(trails) {
				for (int i = trails.size()-1; i >= 0; i--) {
					Cursor c = trails.get(i);
					c.timeOut--;
					if (c.timeOut <= 0)
						trails.remove(i);
				}
			}

			repaint();

			long endTime = System.currentTimeMillis();
			long waitTime = 200-(endTime-startTime);

			if (waitTime > 0) {
				try {
					Thread.sleep(waitTime);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else Thread.yield();

		} while (true);

	}
	
	

	@Override
	public void mouseDragged(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		me.x = x;
		me.y = y;
		me.makeTrailCopy();
		if (nm != null)
			nm.sendMessage(NetworkDataObject.MESSAGE, messageTypePress, x, y);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		me.x = x;
		me.y = y;
		if (nm != null)
			nm.sendMessage(NetworkDataObject.MESSAGE, messageTypeMove, x, y);
	}


	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		me.x = x;
		me.y = y;
		me.makeTrailCopy();
		if (nm != null)
			nm.sendMessage(NetworkDataObject.MESSAGE, messageTypePress,x,y);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}


	class Cursor implements Serializable {
		private static final long serialVersionUID = 1651417152103363037L;

		public static final int TIMEOUT_MAX = 25;

		public int x, y;
		public String host;
		public Color color;
		public int timeOut;

		public void makeTrailCopy() {
			Cursor c = new Cursor();
			c.x = x;
			c.y = y;
			c.host = host;
			c.color = color;
			c.timeOut = TIMEOUT_MAX;
			synchronized(trails) {
				trails.add(c);
			}
		}


	}






}

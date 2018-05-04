import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import jay.jaysound.JayLayer;
import jay.jaysound.JayLayerListener;


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
public class Tester extends JPanel implements ActionListener, JayLayerListener
{
  // TODO Your Instance Variables Here
  private JComboBox<String> effects;
  private JButton play, startstop, next;
  
  private JayLayer sound;

  public Tester () {
	  super();
	  
	  JPanel p0 = new JPanel();
	  p0.setLayout(new BoxLayout(p0,BoxLayout.Y_AXIS));
	  p0.setBackground(Color.WHITE);
	  
	  String[] soundEffects = new String[]{"title1.mp3","title2.mp3","title3.mp3","title4.mp3","title5.mp3"};
	  String[] songs = new String[]{"game1.mp3","game2.mp3","game3.mp3","game4.mp3","game5.mp3"};
	  
	  JPanel p1 = new JPanel();
	  p1.setBackground(Color.WHITE);
	  effects = new JComboBox<String>(soundEffects);
	  p1.add(effects);
	  
	  play = new JButton("Play!");
	  play.addActionListener(this);
	  p1.add(play);

	  p0.add(p1);
	  p0.add(Box.createVerticalStrut(100));

	  JPanel p3 = new JPanel();
	  p3.setBorder(new TitledBorder("Background Music"));
	  p3.setBackground(Color.WHITE);
	  startstop = new JButton("Start");
	  startstop.addActionListener(this);
	  p3.add(startstop);
	  next = new JButton("Next");
	  next.addActionListener(this);
	  next.setEnabled(false);
	  p3.add(next);

	  p0.add(p3);
	  
	  setBackground(Color.WHITE);
	  
	  sound=new JayLayer("audio/","audio/",false);
	  sound.addPlayList();
	  sound.addSongs(0,songs);
	  sound.addSoundEffects(soundEffects);
	  sound.changePlayList(0);
	  sound.addJayLayerListener(this);
	  
	  add(p0);
	  // TODO Add more customizations to the panel

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
    Tester panel = new Tester();
    w.add(panel);
    w.setResizable(true);
    w.setVisible(true);
  }

	@Override
	public void actionPerformed(ActionEvent arg0) {
		String source = arg0.getActionCommand();
		if (source.equals("Play!")) {
			int i = effects.getSelectedIndex();
			if (i >= 0)
				sound.playSoundEffect(i);
		} else if (source.equals("Start") || source.equals("Next")) {
			sound.nextSong();
		} else if (source.equals("Stop")) {
			sound.stopSong();
		}
		
	}


	@Override
	public void songEnded() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void playlistEnded() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void musicStarted() {
		startstop.setText("Stop");
		next.setEnabled(true);
	}


	@Override
	public void musicStopped() {
		startstop.setText("Start");
		next.setEnabled(false);
	}

}

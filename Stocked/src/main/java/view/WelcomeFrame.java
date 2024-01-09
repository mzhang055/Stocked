package view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import model.UserData;

public class WelcomeFrame extends JFrame implements ActionListener{
	
	//fields
	private JButton getStartedBtn;
	private JButton loginBtn;
	
	public static UserData userData = new UserData();
	
	//constructor
	public WelcomeFrame() {
		super("Welcome Frame");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1440, 900);
		
		// set up the background image
		ImageIcon backgroundImg = new ImageIcon("images/welcomeBg.png");
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setPreferredSize(new Dimension(backgroundImg.getIconWidth(), backgroundImg.getIconHeight()));

		JLabel imageLabel = new JLabel(backgroundImg);
		imageLabel.setBounds(0, 0, backgroundImg.getIconWidth(), backgroundImg.getIconHeight());
		Dimension imageSize = new Dimension(backgroundImg.getIconWidth(), backgroundImg.getIconHeight());
		imageLabel.setPreferredSize(imageSize);
		
		// adding components to the layered pane
		layeredPane.add(imageLabel, Integer.valueOf(0));
		
		setVisible(true);
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String[] args) {
		WelcomeFrame welcome = new WelcomeFrame();
		welcome.setVisible(true);
	}
	
	

}

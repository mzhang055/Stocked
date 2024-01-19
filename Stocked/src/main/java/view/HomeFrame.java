package view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

public class HomeFrame extends JFrame implements ActionListener {


	private NavigationBarPanel navPanel;
	private RecommendationPanel recommendPanel;

	// constructor
	public HomeFrame() {
		// set up the frame
		super("Home Frame");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1440, 900);

		// set the background image and add it to a label
		ImageIcon backgroundImg = new ImageIcon("images/homeBg.png");
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setPreferredSize(new Dimension(backgroundImg.getIconWidth(), backgroundImg.getIconHeight()));

		JLabel imageLabel = new JLabel(backgroundImg);
		imageLabel.setBounds(0, 0, backgroundImg.getIconWidth(), backgroundImg.getIconHeight());
		Dimension imageSize = new Dimension(backgroundImg.getIconWidth(), backgroundImg.getIconHeight());
		imageLabel.setPreferredSize(imageSize);
		
		//Creates the navigation JPanel
		navPanel = new NavigationBarPanel();
		navPanel.setBounds(0, 0, 1440, 115);
		imageLabel.add(navPanel);
		

		//create and add survey panel (questions) to frame
		recommendPanel = new RecommendationPanel();
		recommendPanel.setBounds(0, 100, 1440, 700);
		
		recommendPanel.setVisible(true);



		// add background image to the last layer
		layeredPane.add(imageLabel, Integer.valueOf(0));
		layeredPane.add(recommendPanel, Integer.valueOf(2));
		getContentPane().add(layeredPane);

		//set visible
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new HomeFrame();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

}

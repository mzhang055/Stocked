package view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

public class ActiveStocksFrame extends JFrame implements ActionListener {
	
	//fields
	private NavigationBarPanel navPanel;

	public ActiveStocksFrame() {
		// set up the frame
		super("Home Frame");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1440, 900);

		// set the background image and add it to a label
		ImageIcon backgroundImg = new ImageIcon("images/ActiveStocksBg.png");
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setPreferredSize(new Dimension(backgroundImg.getIconWidth(), backgroundImg.getIconHeight()));

		JLabel imageLabel = new JLabel(backgroundImg);
		imageLabel.setBounds(0, 0, backgroundImg.getIconWidth(), backgroundImg.getIconHeight());
		Dimension imageSize = new Dimension(backgroundImg.getIconWidth(), backgroundImg.getIconHeight());
		imageLabel.setPreferredSize(imageSize);

		// Creates the navigation JPanel
		navPanel = new NavigationBarPanel();
		navPanel.setBounds(0, 0, 1440, 115);
		imageLabel.add(navPanel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}

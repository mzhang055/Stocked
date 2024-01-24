package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import org.jfree.chart.ChartPanel;

import controller.ChartController;

public class PredictResultsFrame extends JFrame implements ActionListener {

	// fields
	private NavigationBarPanel navPanel;
	private PredictResultPanel resultsPanel;
	

	// constructor
	public PredictResultsFrame(String symbol) {
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

		// Creates the navigation JPanel
		navPanel = new NavigationBarPanel();
		navPanel.setBounds(0, 0, 1440, 115);
		imageLabel.add(navPanel);


	
//		// Creates the results graph JPanel
//		resultsPanel = new PredictResultPanel();
//		resultsPanel.setBounds(0,100, 1440, 900);
//		imageLabel.add(resultsPanel);

		// add background image to the last layer
		layeredPane.add(imageLabel, Integer.valueOf(0));
		layeredPane.add(resultsPanel, Integer.valueOf(1));

		getContentPane().add(layeredPane);
		//getContentPane().add(new ChartPanel(chart));

		// set visible
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

	public static void main(String[] args) {
		new PredictResultsFrame("TSLA");
	}

}

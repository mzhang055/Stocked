package view;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import controller.ChartController;
import model.UserData;

import org.jdesktop.swingx.JXDatePicker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;

public class HomeFrame extends JFrame implements ActionListener {

	private NavigationBarPanel navPanel;
	private RecommendationPanel recommendPanel;

	private ChartController chartController;
	private UserData userData;

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

		// Creates the navigation JPanel
		navPanel = new NavigationBarPanel();
		navPanel.setBounds(0, 0, 1440, 115);
		imageLabel.add(navPanel);

		// add earnings panel
		EarningsPanel earningsPanel = EarningsPanel.getInstance(chartController, userData);


		earningsPanel.setBounds(900, 100, 400, 700);

		// create and add chart panel to RecommendationPanel
		recommendPanel = new RecommendationPanel();
		recommendPanel.setBounds(0, 100, 900, 700);

		// add background image to the last layer
		layeredPane.add(imageLabel, Integer.valueOf(0));
		layeredPane.add(recommendPanel, Integer.valueOf(1));
		layeredPane.add(earningsPanel, Integer.valueOf(1));

		getContentPane().add(layeredPane);

		// set visible
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
	}
}

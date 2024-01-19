package view;

import javax.swing.*;
import controller.ChartController;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class EarningsPanel extends JPanel {

	private JLabel infoLabel;
	private ChartController chart;
	private ArrayList<String> clickedValues = new ArrayList<>(); // ArrayList to store values
	private String updated;
	private static EarningsPanel instance;
	private JLabel earnings;

	private EarningsPanel(ChartController chart) {
		this.chart = chart; // Store the reference to ChartController

		setLayout(new BorderLayout());

		ImageIcon bg = new ImageIcon("images/widget.png");
		// Create a label to hold the background image
		JLabel backgroundLabel = new JLabel(bg);
		backgroundLabel.setLayout(new BorderLayout());
		add(backgroundLabel, BorderLayout.CENTER);

		// Create a "Refresh" button
		JButton refreshButton = new JButton("Refresh");
		refreshButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			    System.out.println("last clicked: " + getUpdated());

			    // Remove existing earnings label
			    if (earnings != null) {
			        remove(earnings);
			    }

			    // Create a new earnings label and add it to the center of the panel
			    earnings = new JLabel(getUpdated());
			    add(earnings, BorderLayout.CENTER);

			    // Repaint the panel to reflect the changes
			    revalidate();
			    repaint();
			}

		});

		add(refreshButton, BorderLayout.SOUTH);
	}

	public static EarningsPanel getInstance(ChartController chart) {
		if (instance == null) {
			instance = new EarningsPanel(chart);
		}
		return instance;
	}
	public String displayXandY(String xValue, double number) {
	    // Example: Display the clicked point's coordinates
	    // Assuming getxValue() and getyValue() are methods in your ChartController
	    String test = "If you invested "+ number + " during " + xValue;

	    System.out.println(test);

	    // Update the text content of the infoLabel
	    // infoLabel.setText(xValue + " and " + number);

	    JLabel testLabel = new JLabel(test);
	    testLabel.setForeground(Color.BLACK); // Set text color
	    testLabel.setHorizontalAlignment(SwingConstants.CENTER);
	    add(testLabel, BorderLayout.NORTH);

	    // Store the clicked values in the ArrayList
	    clickedValues.add(test);

	    // Print the contents of clickedValues for debugging
	    System.out.println("Clicked values: " + clickedValues);

	    if (!clickedValues.isEmpty()) {
	        updated = clickedValues.get(clickedValues.size() - 1);
	        setUpdated(updated);
	        return updated;
	    } else {
	        return null; // or return some default value if the list is empty
	    }
	}


	public JLabel getInfoLabel() {
		return infoLabel;
	}

	public void setInfoLabel(JLabel infoLabel) {
		this.infoLabel = infoLabel;
	}

	public ChartController getChart() {
		return chart;
	}

	public void setChart(ChartController chart) {
		this.chart = chart;
	}

	public ArrayList<String> getClickedValues() {
		return clickedValues;
	}

	public void setClickedValues(ArrayList<String> clickedValues) {
		this.clickedValues = clickedValues;
	}

	public String getUpdated() {
		return updated;
	}

	public void setUpdated(String updated) {
		this.updated = updated;
	}

}
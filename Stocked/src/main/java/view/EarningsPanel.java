/*
 * this class retrieves and displays the user's potential gains/loss if they were to 
 * invest  one of their recommended stocks at a given time.
 */

package view;

import javax.swing.*;

import com.toedter.calendar.JDateChooser;

import controller.ChartController;
import controller.StockController;
import model.UserData;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class EarningsPanel extends JPanel {

	// fields for components of panel
	private JLabel infoLabel;
	private JLabel recommendLabel;
	private JButton refreshButton;
	private JDateChooser dateChooser; // Date chooser component
	private JComboBox<String> stockComboBox; // Combo box for stocks

	// private ArrayList<String> clickedValues = new ArrayList<>(); // ArrayList to
	// store values

	// variables and data structures
	private String updated;
	private String recommendInfo;
	private HashMap<String, String> selectedStocks = new HashMap<>(); // stores stock symbol and date

	// instance of classes
	private static EarningsPanel instance;
	private ChartController chart;
	private StockController stockController;
	private UserData userData;

	// constructor passes these instances as a parameter to ensure consistency
	private EarningsPanel(ChartController chart, UserData userData) {
		this.chart = chart; // Store the reference to ChartController
		this.userData = userData;

		// Use a BorderLayout for the main panel
		setLayout(new BorderLayout());

		ImageIcon bg = new ImageIcon("images/widget.png");
		// Create a label to hold the background image
		JLabel backgroundLabel = new JLabel(bg);
		backgroundLabel.setLayout(new BorderLayout());
		// add(backgroundLabel, BorderLayout.CENTER);

		// Use a JPanel with FlowLayout for the date chooser and combo box
		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new FlowLayout());

		// Create a "Refresh" button
		refreshButton = new JButton("Search");
		refreshButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("last clicked: " + getUpdated());

				// Retrieve the selected date from the date chooser
				Date selectedDate = dateChooser.getDate();
				// Format the date (this needs to be done for the api)
				String formattedDate = new SimpleDateFormat("yyyy-MM-dd").format(selectedDate);

				// Retrieve the selected stock from the combo box
				String selectedStock = (String) stockComboBox.getSelectedItem();

				// Update the HashMap with the selected stock and date
				selectedStocks.put(selectedStock, formattedDate);

				System.out.println();
				System.out.println("Hashmap:");
				processSelectedStocks(userData.getMoney());

				System.out.println("TESTING FOR SHARE:" + stockController.getNumShares());

				updateRecommendationLabel();
				// Repaint the panel to reflect the changes
				revalidate();
				repaint();
			}
		});

		// Create a date chooser to pick stock dates
		dateChooser = new JDateChooser();
		dateChooser.setDateFormatString("yyyy-MM-dd");
		dateChooser.setMaxSelectableDate(new Date()); // Restrict to the current date and earlier
		dateChooser.setDate(new Date()); // set the default date to the current date

		/// create a combo box for stocks
		stockComboBox = new JComboBox<>();
		stockComboBox.setEditable(false);

		// add the matching stocks to the combo box
		ArrayList<String> matchingStocks = ChartController.getRecommend().determineMatchingStocks("yourRisk");
		for (String stock : matchingStocks) {
			stockComboBox.addItem(stock);
		}

		// add the components to the input panel
		inputPanel.add(dateChooser);
		inputPanel.add(stockComboBox);

		// add the input panel and refresh button to the main panel
		add(inputPanel, BorderLayout.NORTH);
		add(refreshButton, BorderLayout.SOUTH);
	}

	// use a singleton instead of a static class in order to limit the program to
	// exactly one instance of the object
	// (many parts of the program want to use this--shared resource)
	// https://softwareengineering.stackexchange.com/questions/235527/when-to-use-a-singleton-and-when-to-use-a-static-class

	public static EarningsPanel getInstance(ChartController chart, UserData userData) {
		if (instance == null) {
			instance = new EarningsPanel(chart, userData);
		}
		return instance;
	}

	// this method retrieves the stock and date the user selected from the drop down
	// menus
	// this data is used to get the stock's closing price from the api
	public void processSelectedStocks(String money) {
		stockController = new StockController();

		System.out.println("user money: " + userData.getMoney());
		System.out.println("Selected Stocks and Dates:");

		for (String stock : selectedStocks.keySet()) {
			String date = selectedStocks.get(stock);
			System.out.println("Stock: " + stock + ", Date: " + date);
			stockController.processSpecificStock(stock, date, userData.getMoney());
		}
	}

	
	private void updateRecommendationLabel() {

		double profitLoss = stockController.getProfitLoss();
		String formattedProfitLoss = String.format("%.2f", profitLoss);

		// Fill the string with user's potential investment info
		// use html tags to allow for the jlabel to determine line breaks
		// source:
		// https://stackoverflow.com/questions/7861724/is-there-a-word-wrap-property-for-jlabel
		recommendInfo = "<html><div style='text-align: center;'>"
				+ "<div style='font-size: 24px;'>If you invested </div>"
				+ "<div style='color: #F4AA31; font-size: 30px;'>$" + userData.getMoney() + "</div>"
				+ "<div style='color: #000000; font-size: 24px;'> into </div>"
				+ "<div style='color: #F4AA31; font-size: 30px;'>" + stockController.getStockSymbol() + "</div>"
				+ "<div style='font-size: 24px;'> in </div>" + "<div style='font-size: 24px;'>"
				+ stockController.getDate() + "</div>"
				+ "<div style='font-size: 24px;'> you would experience a profit/loss of </div>"
				+ "<div style='color: #F4AA31; font-size: 30px;'>" + formattedProfitLoss + "</div>" + "</div></html>";
		
		
		// Remove the previous recommendLabel if it exists
		if (recommendLabel != null) {
			remove(recommendLabel);
		}

		recommendLabel = new JLabel(recommendInfo);
		recommendLabel.setForeground(Color.BLACK);
		recommendLabel.setPreferredSize(new Dimension(500, 700));
		recommendLabel.setFont(new Font("Arial", Font.BOLD, 18));

		// Set FlowLayout for the recommendLabel
		JPanel labelContainer = new JPanel(new FlowLayout(FlowLayout.CENTER));
		labelContainer.add(recommendLabel);

		// Add the updated recommendLabel to the center of the panel
		add(labelContainer, BorderLayout.CENTER);

		// Repaint the panel to reflect the changes
		revalidate();
		repaint();
	}

	// setters and getters
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

	public String getUpdated() {
		return updated;
	}

	public void setUpdated(String updated) {
		this.updated = updated;
	}

	public HashMap<String, String> getSelectedStocks() {
		return selectedStocks;
	}

}
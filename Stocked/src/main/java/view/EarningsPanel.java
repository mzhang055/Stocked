package view;

import javax.swing.*;

import com.toedter.calendar.JDateChooser;

import controller.ChartController;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class EarningsPanel extends JPanel {

    private JLabel infoLabel;
    private ChartController chart;
    private ArrayList<String> clickedValues = new ArrayList<>(); // ArrayList to store values
    private String updated;
    private static EarningsPanel instance;
    private JLabel earnings;
    private JButton refreshButton;
    private JButton addToPortfolio;
    private JDateChooser dateChooser; // Date chooser component
    private JComboBox<String> stockComboBox; // Combo box for stocks
    private HashMap<String, String> selectedStocks = new HashMap<>(); // HashMap to store selected stocks and dates


    private EarningsPanel(ChartController chart) {
        this.chart = chart; // Store the reference to ChartController

        // Use a BorderLayout for the main panel
        setLayout(new BorderLayout());

        ImageIcon bg = new ImageIcon("images/widget.png");
        // Create a label to hold the background image
        JLabel backgroundLabel = new JLabel(bg);
        backgroundLabel.setLayout(new BorderLayout());
        add(backgroundLabel, BorderLayout.CENTER);

        // Use a JPanel with FlowLayout for the date chooser and combo box
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());

        // Create a "Refresh" button
        refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("last clicked: " + getUpdated());

                // Retrieve the selected date from the date chooser
                Date selectedDate = dateChooser.getDate();
                // Format the date as needed
                String formattedDate = new SimpleDateFormat("yyyy-MM-dd").format(selectedDate);

                // Retrieve the selected stock from the combo box
                String selectedStock = (String) stockComboBox.getSelectedItem();

                // Update the HashMap with the selected stock and date
                selectedStocks.put(selectedStock, formattedDate);
                
                System.out.println();
                System.out.println("Hashmap:");
                printSelectedStocks();

                // Remove existing earnings label
                if (earnings != null) {
                    remove(earnings);
                }

                // Create a new earnings label and add it to the center of the panel
                earnings = new JLabel(displayXandY(formattedDate, 0.0, selectedStock));
                add(earnings, BorderLayout.CENTER);

                // Repaint the panel to reflect the changes
                revalidate();
                repaint();
            }
        });


        // Create a date chooser
        dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("yyyy-MM-dd"); // Set the date format
        dateChooser.setMaxSelectableDate(new Date()); // Restrict to the current date and earlier
        dateChooser.setDate(new Date()); // Set the default date to the current date

        /// Create a combo box for stocks
        stockComboBox = new JComboBox<>();
        stockComboBox.setEditable(false); // Make it non-editable

        // Add the matching stocks to the combo box
        ArrayList<String> matchingStocks = chart.getRecommend().determineMatchingStocks("yourRisk"); // Adjust the risk parameter
        for (String stock : matchingStocks) {
            stockComboBox.addItem(stock);
        }

        // Add the components to the input panel
        inputPanel.add(dateChooser);
        inputPanel.add(stockComboBox);

        // Add the input panel and refresh button to the main panel
        add(inputPanel, BorderLayout.NORTH);
        add(refreshButton, BorderLayout.SOUTH);
    }
    
	public static EarningsPanel getInstance(ChartController chart) {
		if (instance == null) {
			instance = new EarningsPanel(chart);
		}
		return instance;
	}
	
	   // Method to display X and Y values
    public String displayXandY(String date, double money, String stock) {
        // Example: Display the clicked point's coordinates
        // Assuming getxValue() and getyValue() are methods in your ChartController
        String test = "If you invested " + money + " during " + date + " to " + stock;

        //System.out.println(test);

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
    
    public void printSelectedStocks() {
        System.out.println("Selected Stocks and Dates:");
        for (String stock : selectedStocks.keySet()) {
            String date = selectedStocks.get(stock);
            System.out.println("Stock: " + stock + ", Date: " + date);
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
    public HashMap<String, String> getSelectedStocks() {
        return selectedStocks;
    }


}
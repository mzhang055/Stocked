package view;

import com.crazzyghost.alphavantage.AlphaVantage;
import com.crazzyghost.alphavantage.Config;
import com.crazzyghost.alphavantage.parameters.DataType;
import com.crazzyghost.alphavantage.parameters.OutputSize;
import com.crazzyghost.alphavantage.timeseries.response.StockUnit;
import com.crazzyghost.alphavantage.timeseries.response.TimeSeriesResponse;

import controller.PredictionController;
import controller.StockSymbolsController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class PredictFrame extends JFrame {

	//fields
	private Map<String, Double> stockMap; //store sthe stock symbols from yahoo finance
	
	//components of frame
	private NavigationBarPanel navPanel;
	private JButton stockButton;
	
	
	//constructor sets up the frame 
	public PredictFrame() {

		//set up operations
		super("Stock Predictions");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1440, 900);

		//fills hashmap with moupdated stock symbols from yahoo finance
		updateStockMap();

		// set the background image and add it to a label
		ImageIcon backgroundImg = new ImageIcon("images/homeBg.png");
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setPreferredSize(new Dimension(backgroundImg.getIconWidth(), backgroundImg.getIconHeight()));

		JLabel imageLabel = new JLabel(backgroundImg);
		imageLabel.setBounds(0, 0, backgroundImg.getIconWidth(), backgroundImg.getIconHeight());
		Dimension imageSize = new Dimension(backgroundImg.getIconWidth(), backgroundImg.getIconHeight());
		imageLabel.setPreferredSize(imageSize);

		// creates the navigation JPanel
		navPanel = new NavigationBarPanel();
		navPanel.setBounds(0, 0, 1440, 115);
		navPanel.setVisible(true);
		imageLabel.add(navPanel);

		// set iage for all buttons
		ImageIcon icon = new ImageIcon("images/Btn.png");

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 4, 10, 10));

		for (String symbol : stockMap.keySet()) {
			JButton stockButton = createStockButton(symbol, icon);
			panel.add(stockButton);
		}

		// add background image to the last layer
		layeredPane.add(imageLabel, Integer.valueOf(0));
		
		//add scroll pane to frame
		getContentPane().add(new JScrollPane(panel), BorderLayout.CENTER);
		
		setVisible(true);
	}

	private JButton createStockButton(String symbol, ImageIcon icon) {
		stockButton = new JButton();
		stockButton.setLayout(new BorderLayout());

		// Create a JLabel with the image
		JLabel label = new JLabel(icon);
		label.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 5)); // Align to the left with 5 pixels vertical gap

		// Add a text label on top of the image
		JLabel textLabel = new JLabel(
				"<html><div style='font-size:14px;  text-align:left; text-align:left;'>&nbsp;&nbsp;&nbsp;" + symbol
						+ "</div></html>");
		textLabel.setForeground(Color.WHITE);

		// Create another JLabel for closing price
		JLabel closingLabel = new JLabel();
		closingLabel.setForeground(Color.WHITE);

		// Add labels to the panel
		label.add(textLabel);
		label.add(closingLabel);

		// Set an empty border to remove the white border around the label
		label.setBorder(BorderFactory.createEmptyBorder());

		// Add the label to the stockButton
		stockButton.add(label, BorderLayout.WEST);

		stockButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new PredictionController(symbol);
			
			}
		});

		// Add padding to the button
		stockButton.setBorder(new EmptyBorder(10, 10, 10, 10));

		TimeSeriesResponse response = makeAlphaVantageRequest(symbol);
		List<StockUnit> stockUnits = response.getStockUnits();

		// Check if there are stock units before accessing the data
		if (!stockUnits.isEmpty()) {
			double closingPrice = stockUnits.get(0).getClose();
			closingLabel.setText("<html><div style='font-size:16px; text-align:left; font-weight: bold;'><br><br><br>$"
					+ closingPrice + "</br></br></br></div></html>");
			System.out.println(symbol + "\nClosing: " + closingPrice);
		} else {
			closingLabel.setText("<html><div style='font-size:12px; text-align:left;'>Data not available</div></html>");
			System.out.println(symbol + "\nData not available");
		}

		return stockButton;
	}
	
	private TimeSeriesResponse makeAlphaVantageRequest(String symbol) {
		Config cfg = Config.builder().key("DDLQSEH5NHH2H6XE").timeOut(100).build();
		AlphaVantage.api().init(cfg);
		return AlphaVantage.api().timeSeries().daily().forSymbol(symbol).outputSize(OutputSize.FULL)
				.dataType(DataType.JSON).fetchSync();
	}

	private void updateStockMap() {
		new StockSymbolsController();
		try {
			StockSymbolsController.getMostActiveStockSymbols();
			stockMap = StockSymbolsController.getStockMap();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public static void main(String[] args) {
		new PredictFrame();
	}


}
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

public class PredictPanel extends JPanel {

    private Map<String, Double> stockMap; // Store the stock symbols from Yahoo Finance

    public PredictPanel() {
        // Set up operations
        setPreferredSize(new Dimension(1440, 900));
        setBackground(Color.WHITE);

        // Fills hashmap with updated stock symbols from Yahoo Finance
        updateStockMap();

        // Set image for all buttons
        ImageIcon icon = new ImageIcon("images/Btn.png");

        // Create a panel with FlowLayout to add buttons
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panel.setBackground(Color.WHITE);

        for (String symbol : stockMap.keySet()) {
            JButton stockButton = createStockButton(symbol, icon);
            panel.add(stockButton);
        }

        // Add the panel to this JPanel
        add(new JScrollPane(panel), BorderLayout.CENTER);

        // Set this JPanel layout
        setLayout(new BorderLayout());

        // Make it visible
        setVisible(true);
    }

    private JButton createStockButton(String symbol, ImageIcon icon) {
        JButton stockButton = new JButton();
        stockButton.setLayout(new BorderLayout());

        // Create a JLabel with the image
        JLabel label = new JLabel(icon);
        label.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 5)); // Align to the left with 5 pixels vertical gap

        // Add a text label on top of the image
        JLabel textLabel = new JLabel(
                "<html><div style='font-size:14px; text-align:left;'>&nbsp;&nbsp;&nbsp;" + symbol + "</div></html>");
        textLabel.setForeground(Color.BLACK);

        // Create another JLabel for closing price
        JLabel closingLabel = new JLabel();
        closingLabel.setForeground(Color.BLACK);

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
            closingLabel.setText("<html><div style='font-size:16px; text-align:left; font-weight: bold;'>$" + closingPrice
                    + "</div></html>");
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
}

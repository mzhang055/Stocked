package controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.workday.insights.timeseries.arima.Arima;
import com.workday.insights.timeseries.arima.struct.ArimaParams;
import com.workday.insights.timeseries.arima.struct.ForecastResult;

import view.PredictFrame;
import view.PredictResultPanel;
import view.PredictResultsFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;

public class PredictionController {

    // fields
    private static ChartPanel chartPanelPredict;
    private static PredictResultsFrame PredictResultsFramek;

    public PredictionController(String symbol) {
        double[] dataArray = fetchStockData(symbol);

        if (dataArray.length > 0) {
            // Set ARIMA model parameters.
            ArimaParams params = new ArimaParams(3, 1, 3, 1, 0, 1, 1);

            int forecastSize = 50;

            // Obtain forecast result.
            ForecastResult forecastResult = Arima.forecast_arima(dataArray, forecastSize, params);

            // Read forecast values.
            double[] forecastData = forecastResult.getForecast();
            System.out.println("Forecasted Values: " + Arrays.toString(forecastData));

            // Plot the data and forecast on the provided panel
            plotGraph(dataArray, forecastData, symbol);
        }
           
    }
    public static double[] fetchStockData(String symbol) {
        try {
            String apiKey = "DDLQSEH5NHH2H6XE";
            String apiUrl = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=" + symbol + "&apikey="
                    + apiKey + "&outputsize=compact";
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(new URL(apiUrl));

            // Extract closing prices from the API response
            JsonNode timeSeries = rootNode.path("Time Series (Daily)");
            double[] closingPrices = new double[timeSeries.size()];
            int index = 0;
            for (JsonNode dataPoint : timeSeries) {
                closingPrices[index] = Double.parseDouble(dataPoint.path("4. close").asText());
                index++;
            }

            return closingPrices;

        } catch (IOException e) {
            e.printStackTrace();
            return new double[0];
        }
    }

    // plots the graph with the original data and forecasted data on the provided panel
    private static void plotGraph(double[] dataArray, double[] forecastData, String symbol) {

        System.out.println("in plot graph");

        // invokeLater to ensure the Swing components are updated on the event dispatch
        // thread
        SwingUtilities.invokeLater(() -> {
            // create series for original data
            XYSeries series = new XYSeries("original data");
            for (int i = 0; i < dataArray.length; i++) {
                series.add(i, dataArray[i]);
            }

            // create series for forecasted data
            XYSeries forecastSeries = new XYSeries("forecasted data");
            for (int i = 0; i < forecastData.length; i++) {
                forecastSeries.add(dataArray.length + i, forecastData[i]);
            }

            // create dataset with series
            XYSeriesCollection dataset = new XYSeriesCollection();
            dataset.addSeries(series);
            dataset.addSeries(forecastSeries);

            // create the chart
            JFreeChart chart = ChartFactory.createXYLineChart("stock price forecasting", "time", "closing price",
                    dataset);

            // customize plot axes
            XYPlot plot = chart.getXYPlot();
            NumberAxis xAxis = (NumberAxis) plot.getDomainAxis();
            xAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

            NumberAxis yAxis = (NumberAxis) plot.getRangeAxis();
            yAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
            JFrame frame = new JFrame(symbol + " Price Forecasting");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            chartPanelPredict = new ChartPanel(chart);
            
            // Add a return button
            JButton returnButton = new JButton("Return");
            returnButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frame.dispose();  // Close the current frame
                    new PredictFrame();
                }
            });
            
            // Create a panel to hold the chartPanel and the button
            JPanel panel = new JPanel(new BorderLayout());
            panel.add(chartPanelPredict, BorderLayout.CENTER);
            panel.add(returnButton, BorderLayout.SOUTH);
            
            frame.getContentPane().add(panel);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }


    public static ChartPanel getChartPanelPredict() {
        return chartPanelPredict;
    }

    public static void setChartPanelPredict(ChartPanel chartPanelPredict) {
        PredictionController.chartPanelPredict = chartPanelPredict;
    }

    public static void main(String[] args) {
        new PredictionController("AAPL");
    }
}

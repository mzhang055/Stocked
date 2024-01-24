package controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.workday.insights.timeseries.arima.Arima;
import com.workday.insights.timeseries.arima.struct.ArimaParams;
import com.workday.insights.timeseries.arima.struct.ForecastResult;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;

public class PredictionController {
	
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

			// Plot the data and forecast
			plotGraph(dataArray, forecastData);
		} else {
			System.out.println("Error fetching stock data");
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

	private static void plotGraph(double[] dataArray, double[] forecastData) {
		SwingUtilities.invokeLater(() -> {
			XYSeries series = new XYSeries("Original Data");
			for (int i = 0; i < dataArray.length; i++) {
				series.add(i, dataArray[i]);
			}

			XYSeries forecastSeries = new XYSeries("Forecasted Data");
			for (int i = 0; i < forecastData.length; i++) {
				forecastSeries.add(dataArray.length + i, forecastData[i]);
			}

			XYSeriesCollection dataset = new XYSeriesCollection();
			dataset.addSeries(series);
			dataset.addSeries(forecastSeries);

			JFreeChart chart = ChartFactory.createXYLineChart("Stock Price Forecasting", "Time", "Closing Price",
					dataset);

			XYPlot plot = chart.getXYPlot();
			NumberAxis xAxis = (NumberAxis) plot.getDomainAxis();
			xAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

			NumberAxis yAxis = (NumberAxis) plot.getRangeAxis();
			yAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

			JFrame frame = new JFrame("Stock Price Forecasting");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.getContentPane().add(new ChartPanel(chart));
			frame.pack();
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
		});
	}

}

package controller;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.data.category.DefaultCategoryDataset;

import com.crazzyghost.alphavantage.AlphaVantage;
import com.crazzyghost.alphavantage.AlphaVantageException;
import com.crazzyghost.alphavantage.Config;
import com.crazzyghost.alphavantage.parameters.DataType;
import com.crazzyghost.alphavantage.parameters.Interval;
import com.crazzyghost.alphavantage.parameters.OutputSize;
import com.crazzyghost.alphavantage.timeseries.response.StockUnit;
import com.crazzyghost.alphavantage.timeseries.response.TimeSeriesResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Main {

	public static void main(String[] args) {
		Main main = new Main();
		main.init();
	}

	public void init() {
		// initializer
		Config cfg = Config.builder().key("DDLQSEH5NHH2H6XE").timeOut(100).build();

		AlphaVantage.api().init(cfg);

		AlphaVantage.api().timeSeries().daily().forSymbol("TSLA")
				.outputSize(OutputSize.COMPACT).dataType(DataType.JSON)
				.onSuccess(e -> handleSuccess((TimeSeriesResponse) e)).onFailure(e -> handleFailure((e))).fetch();

	}

	public void handleSuccess(TimeSeriesResponse response) {
		try {
			
			
	        // Convert TimeSeriesResponse to JSON string
	        ObjectMapper objectMapper = new ObjectMapper();
	        String jsonResponse = objectMapper.writeValueAsString(response);

	        // Parse the JSON string using Jackson
	        JsonNode jsonNode = objectMapper.readTree(jsonResponse);

	        // Print the parsed JSON data
	        System.out.println("Parsed JSON Data:\n" + objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode));

	     
			// create a dataset for the close and date data
			DefaultCategoryDataset dataset = new DefaultCategoryDataset();

			// 3xtract data from StockUnits and add to the dataset
			for (StockUnit stockUnit : response.getStockUnits()) {
				String date = stockUnit.getDate();
				double close = stockUnit.getClose();
				dataset.addValue(close, "Close", date);
			}

			// create a line chart
			JFreeChart chart = ChartFactory.createLineChart("Close Prices Over Time", // Chart title
					"Date", // X-axis label
					"Close", // Y-axis label
					dataset);

			// use category plot to adjust date labels
			CategoryPlot plot = chart.getCategoryPlot();
			CategoryAxis domainAxis = plot.getDomainAxis();
			domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);

			// display the chart in a JFrame
			ChartPanel chartPanel = new ChartPanel(chart);
			JFrame frame = new JFrame("Close Prices Chart");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.getContentPane().add(chartPanel);
			frame.pack();
			frame.setVisible(true);

		} catch (Exception e) {
			handleFailure(new AlphaVantageException("Failed to create chart."));
		}
	}

	public void handleFailure(AlphaVantageException error) {
		System.out.println("Doesn't function");
		System.out.println(error.getMessage());
	}
}
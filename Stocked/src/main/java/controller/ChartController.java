package controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import javax.swing.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.DateAxis; // Import DateAxis
import org.jfree.chart.axis.DateTickUnit; // Import DateTickUnit
import org.jfree.chart.axis.DateTickUnitType; // Import DateTickUnitType
import org.jfree.chart.axis.DateTickUnitType;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.data.category.DefaultCategoryDataset;

import com.crazzyghost.alphavantage.AlphaVantage;
import com.crazzyghost.alphavantage.AlphaVantageException;
import com.crazzyghost.alphavantage.Config;
import com.crazzyghost.alphavantage.parameters.DataType;
import com.crazzyghost.alphavantage.parameters.OutputSize;
import com.crazzyghost.alphavantage.timeseries.response.StockUnit;
import com.crazzyghost.alphavantage.timeseries.response.TimeSeriesResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import model.UserData;
import view.HomeFrame;

/**
 * 
 */
public class ChartController {

	private static RecommendationController recommend;
	private static UserData userData;

	private static ChartPanel chartPanel;

	public ChartController() {
		recommend = new RecommendationController();
		userData = new UserData();
	}

	public void generateCharts(String userRisk) {
		// Get matching stocks from RecommendationController
		ArrayList<String> matchingStocks = recommend.determineMatchingStocks(userRisk);

		// Create a combined dataset
		DefaultCategoryDataset combinedDataset = new DefaultCategoryDataset();

		// Iterate through matching stocks
		for (String stockSymbol : matchingStocks) {
			System.out.println("Generating chart for stock symbol: " + stockSymbol);

			// Initialize AlphaVantage
			Config cfg = Config.builder().key("DDLQSEH5NHH2H6XE").timeOut(100).build();
			AlphaVantage.api().init(cfg);

			// Fetch time series data for the current stock symbol
			AlphaVantage.api().timeSeries().daily().adjusted().forSymbol(stockSymbol).outputSize(OutputSize.COMPACT)
					.dataType(DataType.JSON).onSuccess(e -> handleSuccess((TimeSeriesResponse) e, combinedDataset, stockSymbol))
					.onFailure(e -> handleFailure((e))).fetch();
		}

	    // Create a line chart with the combined dataset
        JFreeChart chart = ChartFactory.createLineChart("Your Recommended Stocks",
                "Date", // X-axis label
                "Close", // Y-axis label
                combinedDataset);

        // use category plot to adjust date labels
        CategoryPlot plot = chart.getCategoryPlot();
        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);

        // Use DateAxis to allow displaying actual dates
        DateAxis dateAxis = new DateAxis("Date");
        dateAxis.setDateFormatOverride(new SimpleDateFormat("yyyy-MM-dd"));

        // Set the tick unit to two days
        DateTickUnit dateTickUnit = new DateTickUnit(DateTickUnitType.DAY, 2, new SimpleDateFormat("yyyy-MM-dd"));
        dateAxis.setTickUnit(dateTickUnit);



        // display the chart in a JFrame
        chartPanel = new ChartPanel(chart);
        
        
    }

    public void handleSuccess(TimeSeriesResponse response, DefaultCategoryDataset combinedDataset, String stockSymbol) {
        try {
            // Convert TimeSeriesResponse to JSON string
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonResponse = objectMapper.writeValueAsString(response);

            // Parse the JSON string using Jackson
            JsonNode jsonNode = objectMapper.readTree(jsonResponse);

            // Print the parsed JSON data
            System.out.println(
                    "Parsed JSON Data:\n" + objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode));

            // extract data from StockUnits and add to the combined dataset
            ArrayList<StockUnit> sortedStockUnits = new ArrayList<>(response.getStockUnits());
            Collections.sort(sortedStockUnits, (unit1, unit2) -> unit1.getDate().compareTo(unit2.getDate()));

            for (StockUnit stockUnit : sortedStockUnits) {
                String date = stockUnit.getDate();
                double close = stockUnit.getClose();

                // Format date as year and week
                SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");

                Date parsedDate = inputFormat.parse(date);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(parsedDate);
                String formattedDate = outputFormat.format(parsedDate);

                combinedDataset.addValue(close, stockSymbol, formattedDate);
            }
        } catch (Exception e) {
            handleFailure(new AlphaVantageException("Failed to create chart."));
        }
    }

    public void handleFailure(AlphaVantageException error) {
        System.out.println("Doesn't function");
        System.out.println(error.getMessage());
    }

    public static ChartPanel getChartPanel() {
        return chartPanel;
    }


	public static void setChartPanel(ChartPanel chartPanel) {
		ChartController.chartPanel = chartPanel;
	}
	
	
}

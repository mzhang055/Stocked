/*
 * This class retrieves the historical closing data for recommended 
 * stocks and displays them all on one graph
 */

package controller;

import java.awt.BasicStroke;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.axis.DateTickUnitType;

import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
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

public class ChartController {

	// instance of classes
	private RecommendationController recommend;
	private  static ChartPanel chartPanel;

	// storing data
	public String seriesKey;
	public String chartInfo;
	private List<String> clickedDates = new ArrayList<>();
	private ArrayList<String> matchingStocks;

	
	public ChartController(RecommendationController recommend) {
		super();
		this.recommend = recommend;
	}

	//constructor ensures that recommedation controller is ready for the methods in this class
	//since they are dependant on the recoemmended stocks in order to graph it
	
	//this method creates the graph using JFreeChart
	public void generateCharts(String userRisk) {
		// get matching stocks from RecommendationController
		matchingStocks = recommend.determineMatchingStocks(userRisk);

		// create a combined dataset
		DefaultCategoryDataset combinedDataset = new DefaultCategoryDataset();

		// iterate through matching stocks
		for (String stockSymbol : matchingStocks) {
			System.out.println("Generating chart for stock symbol: " + stockSymbol);

			// initialize AlphaVantage
			Config cfg = Config.builder().key("DDLQSEH5NHH2H6XE").timeOut(100).build();
			AlphaVantage.api().init(cfg);

			// fetch time series data for the current stock symbol
			AlphaVantage.api().timeSeries().daily().adjusted().forSymbol(stockSymbol).outputSize(OutputSize.COMPACT)
					.dataType(DataType.JSON)
					.onSuccess(e -> handleSuccess((TimeSeriesResponse) e, combinedDataset, stockSymbol))
					.onFailure(e -> handleFailure((e))).fetch();
		}

		// create a line chart with the combined dataset
		JFreeChart chart = ChartFactory.createLineChart("Your Recommended Stocks", "Date", "Close", combinedDataset);

		// use category plot to adjust date labels
		CategoryPlot plot = chart.getCategoryPlot();
		CategoryAxis domainAxis = plot.getDomainAxis();
		domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);

		// use DateAxis to allow displaying actual dates
		DateAxis dateAxis = new DateAxis("Date");
		dateAxis.setDateFormatOverride(new SimpleDateFormat("yyyy-MM"));

		// set the tick unit to two days
		DateTickUnit dateTickUnit = new DateTickUnit(DateTickUnitType.MONTH, 1, new SimpleDateFormat("yyyy-MM"));
		dateAxis.setTickUnit(dateTickUnit);

		//set line thickness for all series
		LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
		int seriesCount = combinedDataset.getRowCount();
		for (int i = 0; i < seriesCount; i++) {
			renderer.setSeriesStroke(i, new BasicStroke(5.0f)); // set thicknes
		}

		// display the chart
		chartPanel = new ChartPanel(chart);

	}

	// if the api sucessfully retreives historic stock data, then this method will
	// run and plot the stock trends on one graph
	public void handleSuccess(TimeSeriesResponse response, DefaultCategoryDataset combinedDataset, String stockSymbol) {
		try {
			// convert TimeSeriesResponse to JSON string
			ObjectMapper objectMapper = new ObjectMapper();
			String jsonResponse = objectMapper.writeValueAsString(response);

			// parse the JSON string using Jackson
			JsonNode jsonNode = objectMapper.readTree(jsonResponse);

			// print the parsed JSON data (testing)
//			System.out.println(
//					"Parsed JSON Data:\n" + objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode));

			// extract data from StockUnits and add to the combined dataset
			ArrayList<StockUnit> sortedStockUnits = new ArrayList<>(response.getStockUnits());
			Collections.sort(sortedStockUnits, (unit1, unit2) -> unit1.getDate().compareTo(unit2.getDate()));

			// iterate through all the stocks and get their date and closing price. this
			// info is used to graph
			for (StockUnit stockUnit : sortedStockUnits) {
				String date = stockUnit.getDate();
				double close = stockUnit.getClose();

				// format date as year and week
				SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
				SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");

				// parse the date and format it
				Date parsedDate = inputFormat.parse(date);
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(parsedDate);
				String formattedDate = outputFormat.format(parsedDate);

				// add the data point to the combined dataset for graphing
				combinedDataset.addValue(close, stockSymbol, formattedDate);
			}
		} catch (Exception e) {
			handleFailure(new AlphaVantageException("Failed to create chart."));
		}
	}

	// if data is not sucessfully retrieved, this method will run (mainly used for
	// testing)
	public void handleFailure(AlphaVantageException error) {
		System.out.println("Doesn't function");
		System.out.println(error.getMessage());
	}

	// getters and setters
	public static ChartPanel getChartPanel() {
		return chartPanel;
	}

	public static void setChartPanel(ChartPanel chartPanel) {
		ChartController.chartPanel = chartPanel;
	}



	public ArrayList<String> getMatchingStocks() {
		return matchingStocks;
	}

	public void setMatchingStocks(ArrayList<String> matchingStocks) {
		this.matchingStocks = matchingStocks;
	}

	public RecommendationController getRecommend() {
		return recommend;
	}

	public void setRecommend(RecommendationController recommend) {
		this.recommend = recommend;
	}

	public List<String> getClickedDates() {
		return clickedDates;
	}

	public void setClickedDates(List<String> clickedDates) {
		this.clickedDates = clickedDates;
	}

	public void setChartInfo(String chartInfo) {
		this.chartInfo = chartInfo;
	}

	public String getSeriesKey() {
		return seriesKey;
	}

	public void setSeriesKey(String seriesKey) {
		this.seriesKey = seriesKey;
	}

	public String getChartInfo() {
		return chartInfo;
	}

}

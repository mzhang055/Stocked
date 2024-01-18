/*
 * This class retrieves historical stock data from the alpha vantage api,
 * stores it in a TimeSeriesResponse object, and places this information into a CSV file
 * for time series forecasting (trend prediction) -- this trains the model
 * 
 * This class also calculates the standard deviation of the stock so we can find its 
 * riskiness
 * 
 */

package controller;

import com.crazzyghost.alphavantage.AlphaVantage;
import com.crazzyghost.alphavantage.AlphaVantageException;
import com.crazzyghost.alphavantage.Config;
import com.crazzyghost.alphavantage.parameters.DataType;
import com.crazzyghost.alphavantage.parameters.OutputSize;
import com.crazzyghost.alphavantage.timeseries.response.StockUnit;
import com.crazzyghost.alphavantage.timeseries.response.TimeSeriesResponse;

import model.Stock;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class StockController {

	private Map<String, Double> stockMap;

	public static void main(String[] args) {
		StockController processor = new StockController();

		// fetch most active stock symbols and store in the stockMap field
		try {
			StockSymbolsController.getMostActiveStockSymbols();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Populate the stockMap with standard deviations
		processor.populateStockMap(StockSymbolsController.getStockMap(), 500);

		// Print the updated stockMap
		processor.printStockMap(processor.stockMap);
	}

	public void populateStockMap(Map<String, Double> symbols, int dataPoints) {
		stockMap = new HashMap<>();

		for (String symbol : symbols.keySet()) {
			processStockData(symbol, dataPoints);
		}

		// Round the values in the stockMap to 4 decimal places
		// source: https://www.baeldung.com/java-round-decimal-number 
		// and https://stackoverflow.com/questions/53947390/mapstring-integer-getting-rounded-int-percentage
		stockMap.replaceAll((key, value) -> value != null ? Math.round(value * 10000.0) / 10000.0 : null);
	}

	public void printStockMap(Map<String, Double> stockMap) {
		System.out.println("\nPrinting Stock Map:");
		for (Map.Entry<String, Double> entry : stockMap.entrySet()) {
			System.out.println("Stock Symbol: " + entry.getKey() + ", Value: " + entry.getValue());
		}
	}

	public void processStockData(String symbol, int dataPoints) {
		Config cfg = Config.builder().key("DDLQSEH5NHH2H6XE").timeOut(100).build();
		AlphaVantage.api().init(cfg);
		System.out.println("Processing data for symbol: " + symbol);

		try {
			TimeSeriesResponse response = AlphaVantage.api().timeSeries().daily().forSymbol(symbol)
					.outputSize(OutputSize.FULL).dataType(DataType.JSON).fetchSync();

			List<StockUnit> stockUnits = response.getStockUnits();

			// Calculate standard deviation
			double standardDeviation = calculateStandardDeviation(stockUnits, dataPoints);
			stockMap.put(symbol, standardDeviation);

			System.out.println("Standard Deviation for " + symbol + ": " + standardDeviation);

		} catch (AlphaVantageException e) {
			e.printStackTrace();
		}
	}
    /*source : https://www.businessinsider.com/personal-finance/how-to-find-standard-deviation
     * calculates the standard deviation of a stock by 
     * 1. calcualte the average return (the mean) for the time period. the returns
     * are percentage values
     * 2. finding the square of teh different between return and mean
     * 3. add tehe returns to find the numerator
     * 4. divide result by the number of data points minus 1
     * 5. take the squareroot of this to fin the standard deviation
     */
	private double calculateStandardDeviation(List<StockUnit> stockUnits, int dataPoints) {
		double[] returns = new double[dataPoints]; // Array to store daily returns
		double sumReturns = 0.0;

		// Calculate daily returns and sum them
		for (int i = 1; i < dataPoints; i++) {
			StockUnit current = stockUnits.get(i);
			StockUnit previous = stockUnits.get(i - 1);
			double dailyReturn = (current.getClose() - previous.getClose()) / previous.getClose() * 100;
			returns[i] = dailyReturn;
			sumReturns += dailyReturn;
		}

		double mean = sumReturns / dataPoints; // Mean of daily returns
		double sumSquaredDiff = 0.0;

		// Calculate sum of squared differences for standard deviation
		for (double returnVal : returns) {
			double diff = returnVal - mean;
			sumSquaredDiff += Math.pow(diff, 2);
		}

		double result = sumSquaredDiff;
		// (dataPoints -1) to account for errors if there is no previous data point
		double standardDeviation = Math.sqrt(result / (dataPoints - 1));

		// Determine and print the risk level based on ranges
		String riskLevel = determineStockRiskUsingRanges(standardDeviation, mean);

		System.out.println("Risk Level: " + riskLevel);

		return standardDeviation;
	}

	private String determineStockRiskUsingRanges(double standardDeviation, double mean) {

		if (standardDeviation <= 1.0) {
			return "Very Low Risk";
		} else if (standardDeviation <= 1.5) {
			return "Low Risk";
		} else if (standardDeviation <= 2) {
			return "Moderate Risk";
		} else if (standardDeviation <= 4) {
			return "High Risk";
		} else {
			return "Very High Risk";
		}
	}

}
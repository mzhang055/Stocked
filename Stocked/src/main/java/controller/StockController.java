/*
 * This class retrieves historical stock data from the alpha vantage api,
 * calculates the standard deviation of stocks, the user's investment gain/loss
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StockController {

	// this data structure stores the stocks sybmol as the string and its
	// corresponding
	// standard deviation as the double
	public static Map<String, Double> stockMap;

	// variables to store data
	private int numShares;
	private double profitLoss;
	private double currentValue;
	private String stockSymbol;
	private String date;
	private double standardDeviation;
	public String riskLevel;

	// instances
	private StockRiskController risk;

	// this method sets up the requests to the stock api by giving it my api key
	// this is reused through this class to make requests and get data
	private TimeSeriesResponse makeAlphaVantageRequest(String symbol) {
		Config cfg = Config.builder().key("DDLQSEH5NHH2H6XE").timeOut(100).build();
		AlphaVantage.api().init(cfg);
		return AlphaVantage.api().timeSeries().daily().forSymbol(symbol).outputSize(OutputSize.FULL)
				.dataType(DataType.JSON).fetchSync();
	}

	// this method populates the hashmap with each symbols standard deviation
	public void populateStockMap(Map<String, Double> symbols, int dataPoints) {
		stockMap = new HashMap<>();
		for (String symbol : symbols.keySet()) {
			determineStandardDeviation(symbol, dataPoints);
		}
		// this checks if the values asscoaited with the key is not null. if not, it
		// rounds it to 4 decimal places
		// if it is null, it will remain null
		stockMap.replaceAll((key, value) -> value != null ? Math.round(value * 10000.0) / 10000.0 : null);
	}

	// this displays the stock map on the console (demo purposes)
	public void printStockMap(Map<String, Double> stockMap) {
		System.out.println("\nPrinting Stock Map:");
		for (Map.Entry<String, Double> entry : stockMap.entrySet()) {
			System.out.println("Stock Symbol: " + entry.getKey() + ", Value: " + entry.getValue());
		}
	}

	// helper method for calculating the standard deviation by getting historical
	// data from the API
	public void determineStandardDeviation(String symbol, int dataPoints) {

		System.out.println("Processing data for symbol: " + symbol);
		try {
			// requests dat from API for the specified symbol
			TimeSeriesResponse response = makeAlphaVantageRequest(symbol);
			// each stockunit object represents a point in teh time series which contains
			// info such as open price, close price, date, etc
			List<StockUnit> stockUnits = response.getStockUnits();
			// send data for calcuclation
			double standardDeviation = calculateStandardDeviation(stockUnits, dataPoints);

			// updates stockMap when calculated the standard deviation
			stockMap.put(symbol, standardDeviation);
			System.out.println("Standard Deviation for " + symbol + ": " + standardDeviation);
			// handles errors
		} catch (AlphaVantageException e) {
			e.printStackTrace();
		}
	}

	// this method calculates the user's gain/loss if they were to invest at a
	// selected stock on a specified day
	public void processSpecificStock(String symbol, String date, String investment) {
		// parse the amount of money into a double
		double investmentAmount = 0.0;

		// check if investment is not null and not empty
		if (investment != null && !investment.trim().isEmpty()) {
			try {
				investmentAmount = Double.parseDouble(investment);
			} catch (NumberFormatException e) {
				// error checking
				System.err.println("Error parsing investment amount.");

			}
		}
		System.out.println("Processing specific data for symbol: " + symbol + " on date: " + date);
		try {
			//connect to api
			TimeSeriesResponse response = makeAlphaVantageRequest(symbol);
			List<StockUnit> stockUnits = response.getStockUnits(); //fill arraylist with historical data

			// find the close price for the specified date
			double closePrice = findClosePriceOnDate(stockUnits, date);

			//if the close price is a valid number/exists
			if (closePrice != -1) {
				System.out.println("Close Price for " + symbol + " on date " + date + ": " + closePrice);

				setStockSymbol(symbol);
				setDate(date);
				// calculate the number of shares that could be bought with the investment
				// amount
				numShares = (int) (investmentAmount / closePrice);
				setNumShares(numShares);

				// calculate the current value of the investment
				currentValue = numShares * closePrice;
				setCurrentValue(currentValue);

				// calculate profit or loss
				profitLoss = currentValue - investmentAmount;
				setProfitLoss(profitLoss);

				//calculations
//				System.out.println("Number of Shares Purchased: " + numShares);
//				System.out.println("Current Value of Investment: " + currentValue);
//				System.out.println("Profit/Loss: " + profitLoss);
			} else {
				//handle errors
				System.out.println("Close price not found for the specified date.");
			}
		} catch (AlphaVantageException e) {
			e.printStackTrace();
		}
	}

	// this is the helper method for the processSpecificStock method. it makes
	// requests
	// to the api and returns the closing price
	private double findClosePriceOnDate(List<StockUnit> stockUnits, String targetDate) {
		for (StockUnit stockUnit : stockUnits) {
			String unitDate = stockUnit.getDate().toString();
			if (unitDate.startsWith(targetDate)) {
				return stockUnit.getClose();
			}
		}
		return -1; // Return -1 if the date is not found
	}

	/*
	 * source :
	 * https://www.businessinsider.com/personal-finance/how-to-find-standard-
	 * deviation calculates the standard deviation of a stock by 1. calcualte the
	 * average return (the mean) for the time period. the returns are percentage
	 * values 2. finding the square of teh different between return and mean 3. add
	 * tehe returns to find the numerator 4. divide result by the number of data
	 * points minus 1 5. take the squareroot of this to fin the standard deviation
	 */
	private double calculateStandardDeviation(List<StockUnit> stockUnits, int dataPoints) {
		double[] returns = new double[dataPoints]; // Array to store daily returns
		double sumReturns = 0.0;

		// calculate daily returns and sum them
		for (int i = 1; i < dataPoints; i++) {
			StockUnit current = stockUnits.get(i);
			StockUnit previous = stockUnits.get(i - 1);
			double dailyReturn = (current.getClose() - previous.getClose()) / previous.getClose() * 100;
			returns[i] = dailyReturn;
			sumReturns += dailyReturn;
		}

		double mean = sumReturns / dataPoints; // mean of daily returns
		double sumSquaredDiff = 0.0;

		// calculate sum of squared differences for standard deviation
		for (double returnVal : returns) {
			double diff = returnVal - mean;
			sumSquaredDiff += Math.pow(diff, 2);
		}

		double result = sumSquaredDiff;
		// (dataPoints -1) to account for errors if there is no previous data point
		standardDeviation = Math.sqrt(result / (dataPoints - 1));

		risk = new StockRiskController();
		// determine and print the risk level based on ranges
		riskLevel = risk.determineRiskLevel(standardDeviation);

		
		return standardDeviation;
	}

	// getters and setters
	

	public double getStandardDeviation() {
		return standardDeviation;
	}

	public static Map<String, Double> getStockMap() {
		return stockMap;
	}

	public static void setStockMap(Map<String, Double> stockMap) {
		StockController.stockMap = stockMap;
	}

	public void setStandardDeviation(double standardDeviation) {
		this.standardDeviation = standardDeviation;
	}

	public String getRiskLevel() {
		return riskLevel;
	}

	public void setRiskLevel(String riskLevel) {
		this.riskLevel = riskLevel;
	}

	public int getNumShares() {
		return numShares;
	}

	public void setNumShares(int numShares) {
		this.numShares = numShares;
	}

	public double getProfitLoss() {
		return profitLoss;
	}

	public void setProfitLoss(double profitLoss) {
		this.profitLoss = profitLoss;
	}

	public double getCurrentValue() {
		return currentValue;
	}

	public void setCurrentValue(double currentValue) {
		this.currentValue = currentValue;
	}

	public String getStockSymbol() {
		return stockSymbol;
	}

	public void setStockSymbol(String stockSymbol) {
		this.stockSymbol = stockSymbol;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}
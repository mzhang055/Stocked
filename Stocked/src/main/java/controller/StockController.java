/*
 * This class retrieves historical stock data from the alpha vantage api,
 *
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

    private Map<String, Double> stockMap;
    private double standardDeviation;
    public String riskLevel;

    public static void main(String[] args) {
        StockController processor = new StockController();
        try {
            StockSymbolsController.getMostActiveStockSymbols();
        } catch (Exception e) {
            e.printStackTrace();
        }
        processor.populateStockMap(StockSymbolsController.getStockMap(), 500);
        processor.printStockMap(processor.stockMap);
    }

    public void populateStockMap(Map<String, Double> symbols, int dataPoints) {
        stockMap = new HashMap<>();
        for (String symbol : symbols.keySet()) {
            processStockData(symbol, dataPoints);
        }
        stockMap.replaceAll((key, value) -> value != null ? Math.round(value * 10000.0) / 10000.0 : null);
    }

    public void printStockMap(Map<String, Double> stockMap) {
        System.out.println("\nPrinting Stock Map:");
        for (Map.Entry<String, Double> entry : stockMap.entrySet()) {
            System.out.println("Stock Symbol: " + entry.getKey() + ", Value: " + entry.getValue());
        }
    }

    public void processStockData(String symbol, int dataPoints) {
  
        System.out.println("Processing data for symbol: " + symbol);
        try {
            TimeSeriesResponse response = makeAlphaVantageRequest(symbol);
            List<StockUnit> stockUnits = response.getStockUnits();
            double standardDeviation = calculateStandardDeviation(stockUnits, dataPoints);
            stockMap.put(symbol, standardDeviation);
            System.out.println("Standard Deviation for " + symbol + ": " + standardDeviation);
        } catch (AlphaVantageException e) {
            e.printStackTrace();
        }
    }

    private TimeSeriesResponse makeAlphaVantageRequest(String symbol) {
        Config cfg = Config.builder().key("DDLQSEH5NHH2H6XE").timeOut(100).build();
        AlphaVantage.api().init(cfg);
        return AlphaVantage.api().timeSeries()
                .daily()
                .forSymbol(symbol)
                .outputSize(OutputSize.FULL)
                .dataType(DataType.JSON)
                .fetchSync();
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
		standardDeviation = Math.sqrt(result / (dataPoints - 1));

		// Determine and print the risk level based on ranges
		riskLevel = determineStockRisk(standardDeviation);

		// System.out.println("Risk Level: " + riskLevel);

		return standardDeviation;
	}

	private String determineStockRisk(double standardDeviation) {

		if (standardDeviation <= 1.0) {
			return "Very Low Risk";
		} else if (standardDeviation <= 1.5) {
			return "Low Risk";
		} else if (standardDeviation <= 2) {
			return "Moderate Risk";
		} else if (standardDeviation <= 5) {
			return "High Risk";
		} else {
			return "Very High Risk";
		}
	}

	public Map<String, Double> getStockMap() {
		return stockMap;
	}

	public void setStockMap(Map<String, Double> stockMap) {
		this.stockMap = stockMap;
	}

	public double getStandardDeviation() {
		return standardDeviation;
	}

	public void setStandardDeviation(double standardDeviation) {
		this.standardDeviation = standardDeviation;
	}

}
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

public class StockDataProcessor {
	
	//fields
	private String stockRisk;

    public static void main(String[] args) {
        StockDataProcessor processor = new StockDataProcessor();
        // 500 will get us the last 2 years of stock data
        processor.processStockData("JNJ", "historical_data.csv", 500);

        Stock stock = new Stock();
        Map<String, Double> stockMap = stock.getStockMap("listing_status.csv");
        processor.printStockMap(stockMap);
    }
    
    
    public void printStockMap(Map<String, Double> stockMap) {
        System.out.println("Printing Stock Map:");
        for (Map.Entry<String, Double> entry : stockMap.entrySet()) {
            System.out.println("Stock Symbol: " + entry.getKey() + ", Value: " + entry.getValue());
        }
    }

    public void processStockData(String symbol, String outputFile, int dataPoints) {
        Config cfg = Config.builder().key("DDLQSEH5NHH2H6XE").timeOut(100).build();
        AlphaVantage.api().init(cfg);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            TimeSeriesResponse response = AlphaVantage.api()
                    .timeSeries()
                    .daily()
                    .forSymbol(symbol)
                    .outputSize(OutputSize.FULL)
                    .dataType(DataType.JSON)
                    .fetchSync();

            List<StockUnit> stockUnits = response.getStockUnits();

            // Write historical closing prices and calculate daily returns to CSV file
            writeStockDataToCSV(writer, stockUnits, dataPoints);

            // Calculate standard deviation
            double standardDeviation = calculateStandardDeviation(stockUnits, dataPoints);
            System.out.println("Standard Deviation for " + symbol + ": " + standardDeviation);

            // Use the standard deviation for further processing or matching algorithm
        } catch (AlphaVantageException | IOException e) {
            e.printStackTrace();
        }
    }

    private void writeStockDataToCSV(BufferedWriter writer, List<StockUnit> stockUnits, int dataPoints) throws IOException {
        writer.write("Date,Close,DailyReturn\n");

        for (int i = 1; i < dataPoints; i++) {
            StockUnit current = stockUnits.get(i);
            StockUnit previous = stockUnits.get(i - 1);

            double dailyReturn = (current.getClose() - previous.getClose()) / previous.getClose() * 100;

            writer.write(current.getDate() + "," + current.getClose() + "," + dailyReturn + "\n");
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

        double result = sumSquaredDiff; // Corrected this line
        double standardDeviation = Math.sqrt(result / (dataPoints - 1)); // Corrected this line

        // Determine and print the risk level based on ranges
        String riskLevel = determineStockRiskUsingRanges(standardDeviation, mean);

		System.out.println("Risk Level: " + riskLevel);

		return standardDeviation;
	}

	/*
	 * this method determines the riskiness of a stock by using its percentage
	 * deviation (standard deviation/mean) x 100 - i decided not to use regular
	 * standard deviation for this because the ranges can vary greatly, leading to
	 * results that were hard to standardize
	 */
	private String determineStockRiskUsingRanges(double standardDeviation, double mean) {

		if (standardDeviation <= 0.5) {
			return "Very Low Risk";
		} else if (standardDeviation <= 1.0) {
			return "Low Risk";
		} else if (standardDeviation <= 1.5) {
			return "Moderate Risk";
		} else if (standardDeviation <= 2.0) {
			return "High Risk";
		} else {
			return "Very High Risk";
		}
	}

}
package controller;

import com.crazzyghost.alphavantage.AlphaVantage;
import com.crazzyghost.alphavantage.AlphaVantageException;
import com.crazzyghost.alphavantage.Config;
import com.crazzyghost.alphavantage.parameters.DataType;
import com.crazzyghost.alphavantage.parameters.OutputSize;
import com.crazzyghost.alphavantage.timeseries.response.StockUnit;
import com.crazzyghost.alphavantage.timeseries.response.TimeSeriesResponse;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class StockDataProcessor {

    public static void main(String[] args) {
        StockDataProcessor processor = new StockDataProcessor();
        processor.processStockData("JNJ", "historical_data.csv", 1000);
    }

    public void processStockData(String symbol, String outputFile, int dataPoints) {
        Config cfg = Config.builder().key("4O6H83KMNJYW1JZX").timeOut(100).build();
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

            // Write historical closing prices to CSV file
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
        writer.write("Date,Close\n");
        for (int i = 0; i < dataPoints; i++) {
            StockUnit stockUnit = stockUnits.get(i);
            writer.write(stockUnit.getDate() + "," + stockUnit.getClose() + "\n");
        }
    }

    private double calculateStandardDeviation(List<StockUnit> stockUnits, int dataPoints) {
        // Calculate the mean
        double mean = calculateMean(stockUnits, dataPoints);

        // Calculate the sum of squared differences
        double sumSquaredDiff = 0.0;
        for (int i = 0; i < dataPoints; i++) {
            double diff = stockUnits.get(i).getClose() - mean;
            sumSquaredDiff += Math.pow(diff, 2);
        }

        // Calculate the variance
        double variance = sumSquaredDiff / dataPoints;

        // Calculate the standard deviation
        double standardDeviation = Math.sqrt(variance);

        return standardDeviation;
    }

    private double calculateMean(List<StockUnit> stockUnits, int dataPoints) {
        double sum = 0.0;
        for (int i = 0; i < dataPoints; i++) {
            sum += stockUnits.get(i).getClose();
        }
        return sum / dataPoints;
    }
}

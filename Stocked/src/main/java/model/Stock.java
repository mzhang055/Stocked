package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class Stock {

	private String ticker;
	private double closePrice;
	private double percentageDeviation;
	public HashMap<String, Double> stockMap;

	public Stock() {
		super();

	}

    public HashMap<String, Double> getStockMap(String csvFilePath) {
       stockMap = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(csvFilePath))) {
            // Read the first line (header) to skip it
            String header = reader.readLine();

            String line;
            while ((line = reader.readLine()) != null) {
                // Split the line by comma
                String[] parts = line.split(",");
                if (parts.length > 0) {
                    // The first column is assumed to be the key (string), and the value is null
                    stockMap.put(parts[0], null);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stockMap;
    }


	public String getTicker() {
		return ticker;
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

	public double getClosePrice() {
		return closePrice;
	}

	public void setClosePrice(double closePrice) {
		this.closePrice = closePrice;
	}

	public double getPercentageDeviation() {
		return percentageDeviation;
	}

	public void setPercentageDeviation(double percentageDeviation) {
		this.percentageDeviation = percentageDeviation;
	}

}

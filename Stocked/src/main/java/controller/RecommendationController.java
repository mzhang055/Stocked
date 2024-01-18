package controller;

import java.io.IOException;
import java.util.Map;

public class RecommendationController {

	public static StockController stockController;

	public void determineStockRisk() {
		// Retrieve stockMap from StockController
		//Map<String, Double> stockMap = stockController.getStockMap();

		if (stockController.getStockMap() != null) {
			// Print stockMap values
			System.out.println("Printing stockMap values:");
			for (Map.Entry<String, Double> entry : stockController.getStockMap().entrySet()) {
				String stockSymbol = entry.getKey();
				double standardDeviation = entry.getValue();

				// Print the values
				System.out.println("Stock: " + stockSymbol + ", Standard Deviation: " + standardDeviation);
			}

			System.out.println();
			System.out.println();
			// Example: Determine risk based on stockMap values
			for (Map.Entry<String, Double> entry : stockController.getStockMap().entrySet()) {
				String stockSymbol = entry.getKey();
				double standardDeviation = entry.getValue();

				// Your logic to determine risk for each stock
				String riskLevel = determineRiskLevel(standardDeviation);

				// Print risk levels for each stock (testing)
	
				System.out.println("Stock: " + stockSymbol + ", Risk Level: " + riskLevel);
			}
		} else {
			System.out.println("stockMap is null. Data might not be retrieved.");
		}
	}

	public String determineRiskLevel(double standardDeviation) {
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
	public static void main(String[] args) {
	    RecommendationController recommendationController = new RecommendationController();
	    
	    try {
	        StockSymbolsController.getMostActiveStockSymbols();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	    // Assign the created StockController instance to the static variable
	    RecommendationController.stockController = new StockController();
	    RecommendationController.stockController.populateStockMap(StockSymbolsController.getStockMap(), 500);
	    recommendationController.determineStockRisk();
	    
	    RiskController risk = new RiskController();
	    System.out.println("users risk: "+ risk.getUserRisk());
	}


}

// StockRiskCalculator.java
package controller;

import java.util.HashMap;
import java.util.Map;

import model.Risk;
import model.UserData;

public  class StockRiskController  {

    private static final double VERY_LOW_THRESHOLD = 1.0;
    private static final double LOW_THRESHOLD = 1.5;
    private static final double MODERATE_THRESHOLD = 2.0;
    private static final double HIGH_THRESHOLD = 5.0;

    private Map<String, String> stockRiskLevels;
    public static StockController stockController;


	//determine the risk of each stock by calculating the standard deviation
	//this method also returns an updated hashmap filled with these values
	public Map<String, String> determineStockRisk() {
		stockRiskLevels = new HashMap<>();

		//iterate through entire stock hashmap values if there is content
		if (getStockController().getStockMap() != null) {
			for (Map.Entry<String, Double> entry : getStockController().getStockMap().entrySet()) {
				//the key of hashmap represent the stock symbols
				String stockSymbol = entry.getKey();
				//values represent the standard deviation
				double standardDeviation = entry.getValue();

				//determine risk level
				String riskLevel = determineRiskLevel(standardDeviation);
				stockRiskLevels.put(stockSymbol, riskLevel);
	
				//dipslay on console
				System.out.println("Stock: " + stockSymbol + ", Risk Level: " + riskLevel);
			}

			//error checking
		} else {
			System.out.println("stockMap is null. Data might not be retrieved.");
		}

		return stockRiskLevels;
	}

	//this method assigns the stocks a risk level to get a standardized metric 
	//betwen risk level of user and risk level of stock
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

	public Map<String, String> getStockRiskLevels() {
		return stockRiskLevels;
	}

	public void setStockRiskLevels(Map<String, String> stockRiskLevels) {
		this.stockRiskLevels = stockRiskLevels;
	}

	public static StockController getStockController() {
		return stockController;
	}

	public static void setStockController(StockController stockController) {
		StockRiskController.stockController = stockController;
	}
	
	
	

}

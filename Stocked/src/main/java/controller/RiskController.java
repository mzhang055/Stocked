/*
 * this class determines the stock's risk factor and user's risk factor.
 * the stock's risk factor is then stored in a hashmap with its corresponding 
 * stock symbol
 */

package controller;


import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;


public class RiskController {

	private static final int DEFAULT_WEIGHTING = 1;
	public String userRisk;
	public static StockController stockController;
	
	public Map<String, String> stockRiskLevels;

	public String determineUserRisk(Map<Integer, Integer> buttonValues) {

		// Call calculateRisk method with the buttonValues HashMap
		double riskPercentage = calculateRiskPercentage(buttonValues);
		printButtonValues(buttonValues);
		

		// low risk is set to 22 because if user selects '1' (least likely) for all
		// questions,
		// the lowest possible store they can get is 21.111... assuming they answered
		// all questions
		if (riskPercentage < 22) {
			userRisk = "Very Low Risk";
			//set to 49 because this is the percentage if user answers '2' for all questions
		} else if (riskPercentage <= 49) {
			userRisk = "Low Risk";
		//set to 84 because this is the percentage if user answers '3' for all questions
		} else if (riskPercentage <= 84) {
			userRisk = "Moderate Risk";
			//125 is the percentage if user answers '4' for all questions
		} else if (riskPercentage <= 122) {
			userRisk = "High Risk";
		} else {
			userRisk = "Very High Risk";
		}

		return userRisk;
		
		
		//RECOMMENDATINO CONTROLLEr
	}

	private void printButtonValues(Map<Integer, Integer> buttonValues) {
		System.out.println("\n\nButton Values from controller:");
		for (Entry<Integer, Integer> entry : buttonValues.entrySet()) {
			System.out.println("Question " + entry.getKey() + ": ButtonValue=" + entry.getValue());
		}
	}

	public double calculateRiskPercentage(Map<Integer, Integer> buttonValues) {
		int totalWeightedScore = 0;
		int totalWeighting = 0;

		for (Entry<Integer, Integer> entry : buttonValues.entrySet()) {
			int questionIndex = entry.getKey();
			int buttonValue = entry.getValue();

			// Determine the weighting based on the question index
			int weighting = determineWeighting(questionIndex);
			System.out.println(" weighting:" + weighting);

			// Calculate the weighted score for the question
			int weightedScore = buttonValue * weighting;
			System.out.println(" weighted score:" + weightedScore);

			// Add the weighted score to the total
			totalWeightedScore += weightedScore;

			// Add the weighting to the total
			totalWeighting += weighting;
		}

		// Calculate the percentage
		double riskPercentage = (double) totalWeightedScore / 9;
		System.out.println("risk: " + riskPercentage);

		return riskPercentage;
	}

	// Method to determine the weighting based on the question index
	private int determineWeighting(int questionIndex) {
		// Questions 1 to 4 in the survey are more personality-focused and less
		// direct about the user's financial risk
		if (questionIndex >= 0 && questionIndex < 5) {
			return 20; // These questions contribute 20% each
		}
		// Questions 5 to 7 are more direct and focused on the user's
		// financial tendencies, thus will have more weighting
		else if (questionIndex >= 5) {
			return 30; // These questions contribute 30% each
		} else {
			return DEFAULT_WEIGHTING; // Default weighting
		}
	}
	
	 public Map<String, String> determineStockRisk() {
	        stockRiskLevels = new HashMap<>();

	        if (stockController.getStockMap() != null) {
	            for (Map.Entry<String, Double> entry : stockController.getStockMap().entrySet()) {
	                String stockSymbol = entry.getKey();
	                double standardDeviation = entry.getValue();

	                String riskLevel = determineRiskLevel(standardDeviation);
	                stockRiskLevels.put(stockSymbol, riskLevel);
	                System.out.println();
	                System.out.println();
	                System.out.println("Stock: " + stockSymbol + ", Risk Level: " + riskLevel);
	            }
	            
	        } else {
	            System.out.println("stockMap is null. Data might not be retrieved.");
	        }

	        return stockRiskLevels;
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

	public String getUserRisk() {
		return userRisk;
	}

	public void setUserRisk(String userRisk) {
		this.userRisk = userRisk;
	}

	public static StockController getStockController() {
		return stockController;
	}

	public static void setStockController(StockController stockController) {
		RiskController.stockController = stockController;
	}

	public Map<String, String> getStockRiskLevels() {
		return stockRiskLevels;
	}

	public void setStockRiskLevels(Map<String, String> stockRiskLevels) {
		this.stockRiskLevels = stockRiskLevels;
	}
	
	

}
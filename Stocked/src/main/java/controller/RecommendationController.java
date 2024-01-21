/*
 * this class is responsible for retrieving the values (risk levels) from the stock
 * hashmap and matches stocks to the user based on the user's risk level. this is
 * stored in an arraylist
 */

package controller;

import java.util.ArrayList;
import java.util.Map;

public class RecommendationController {

	// instance of classes
	private static RecommendationController instance;

	// store the recommended stocks
	public ArrayList<String> matchingStocks = new ArrayList<String>();

	// singleton design pattern ensures that a class only has one instance (controls
	// instantiation)
	// source:
	// https://softwareengineering.stackexchange.com/questions/235527/when-to-use-a-singleton-and-when-to-use-a-static-class
	public static RecommendationController getInstance() {
		if (instance == null) {
			instance = new RecommendationController();
		}
		return instance;
	}

	//this method compares the risks levels of the stock and the user to get the 10 recommended ones
	//since the web scrapping retreives data in order from most active to least active, that order is 
	//maintained thorugh the mathcing
	public ArrayList<String> determineMatchingStocks(String userRisk) {
		
		//create an instance of risk controller to use the stock risk method
		RiskController riskController = new RiskController();
		riskController.determineStockRisk();

		//if risk controller is properly instantiated
		if (riskController != null) {
			
			//populate hashmap with stock risk levels
			Map<String, String> stockRiskLevels = riskController.getStockRiskLevels();
			if (stockRiskLevels != null) {
				// determine risk based on stockMap values
				int count = 0; // counter for the number of matching stocks added
				for (Map.Entry<String, String> entry : stockRiskLevels.entrySet()) {
					String stockSymbol = entry.getKey();
					String riskLevel = entry.getValue();

					// compare stock risk level with user risk level
					if (userRisk.equalsIgnoreCase(riskLevel)) {
						// store matching stocks in the ArrayList, but limit to the first 10
						if (count < 10) {
							matchingStocks.add(stockSymbol);
							count++;
						}
					}
				}
				//error checking
			} else {
				System.out.println("Stock risk levels map is null.");
			}
		}

		//display matching stocks
		printMatchingStocks();
		return matchingStocks;
	}

	//this method is repsonsible for iterating thorugh the matching stocks and diplaying them all
	public void printMatchingStocks() {
		// Print the matching stocks
		System.out.println("Matching Stocks:");
		for (String stockSymbol : matchingStocks) {
			System.out.println(stockSymbol);
		}
	}

	public ArrayList<String> getMatchingStocks() {
		return matchingStocks;
	}

	public void setMatchingStocks(ArrayList<String> matchingStocks) {
		this.matchingStocks = matchingStocks;
	}

}

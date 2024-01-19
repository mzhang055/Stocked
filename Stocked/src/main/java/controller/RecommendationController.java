/*
 * this class is responsible for retrieving the values (risk levels) from the stock
 * hashmap and matches stocks to the user based on the user's risk level. this is
 * stored in an arraylist
 */

package controller;

import java.util.ArrayList;
import java.util.Map;

public class RecommendationController {
    private static RecommendationController instance;

    
    //ensure it is a singleton
    public RecommendationController() {
        // private constructor to prevent instantiation
    }

    
    public static RecommendationController getInstance() {
        if (instance == null) {
            instance = new RecommendationController();
        }
        return instance;
    }
	public ArrayList<String> matchingStocks = new ArrayList<String>();

	public ArrayList<String> determineMatchingStocks(String userRisk) {
	    RiskController riskController = new RiskController();
	    riskController.determineStockRisk();

	    if (riskController != null) {
	        Map<String, String> stockRiskLevels = riskController.getStockRiskLevels();
	        if (stockRiskLevels != null) {
	            // Determine risk based on stockMap values
	            int count = 0;  // Counter for the number of matching stocks added
	            for (Map.Entry<String, String> entry : stockRiskLevels.entrySet()) {
	                String stockSymbol = entry.getKey();
	                String riskLevel = entry.getValue();

	                // Compare stock risk level with user risk level
	                if (userRisk.equalsIgnoreCase(riskLevel)) {
	                    // Store matching stocks in the ArrayList, but limit to the first 3
	                    if (count < 10) {
	                        matchingStocks.add(stockSymbol);
	                        count++;
	                    }
	                }
	            }
	        } else {
	            System.out.println("Stock risk levels map is null.");
	        }
	    } 

	    printMatchingStocks();
	    return matchingStocks;
	}


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

/*
 * this class is responsible for retrieving the values (risk levels) from the stock
 * hashmap and matches stocks to the user based on the user's risk level. this is
 * stored in an arraylist
 */

package controller;

import java.util.ArrayList;
import java.util.Map;


public class RecommendationController {

    public ArrayList<String> matchingStocks = new ArrayList<String>();

    public void determineMatchingStocks(String userRisk) {
       // stockController = new StockController();
        RiskController riskController = new RiskController(); // Initialize riskController
        riskController.determineStockRisk(); //populate the map

        // Ensure stockController is initialized before using it
        if (riskController != null) {
            //Map<String, String> stockMap = riskController.getStockRiskLevels();

            if (riskController.getStockRiskLevels() != null) {
                // Determine risk based on stockMap values
                for (Map.Entry<String, String> entry : riskController.getStockRiskLevels().entrySet()) {
                    String stockSymbol = entry.getKey();
                    String riskLevel = entry.getValue();

                    // Compare stock risk level with user risk level
                    if (userRisk.equalsIgnoreCase(riskLevel)) {
                        // Store matching stocks in the ArrayList
                        matchingStocks.add(stockSymbol);
                        
                    }
                   
                }
            } else {
                System.out.println("null");
            }
        } 
        
        printMatchingStocks();
    }

    public void printMatchingStocks() {
        // Print the matching stocks
        System.out.println("Matching Stocks:");
        for (String stockSymbol : matchingStocks) {
            System.out.println(stockSymbol);
        }
    }
}

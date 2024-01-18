package controller;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import model.UserData;
import view.SurveyPanel;

public class RiskController {

    private static final int DEFAULT_WEIGHTING = 1;
    public String userRisk;
 

    public  String determineUserRisk(Map<Integer, Integer> buttonValues) {
    	
        // Call calculateRisk method with the buttonValues HashMap
        double riskPercentage = calculateRiskPercentage(buttonValues);
        printButtonValues(buttonValues);
        

        if (riskPercentage < 20) {
            userRisk = "Very Low Risk";
        } else if (riskPercentage <= 40) {
            userRisk = "Low Risk";
        } else if (riskPercentage <= 60) {
            userRisk = "Moderate Risk";
        } else if (riskPercentage <= 80) {
            userRisk = "High Risk";
        } else {
            userRisk = "Very High Risk";
        }
        
        return userRisk;
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
            System.out.println(" weighted score:" +weightedScore);

            // Add the weighted score to the total
            totalWeightedScore += weightedScore;

            // Add the weighting to the total
            totalWeighting += weighting;
        }

        // Calculate the percentage
        double riskPercentage = (double) totalWeighting / 9;
        System.out.println("risk: "+riskPercentage);

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
	public String getUserRisk() {
		return userRisk;
	}
	public void setUserRisk(String userRisk) {
		this.userRisk = userRisk;
	}
    
    
}

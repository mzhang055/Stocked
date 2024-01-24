package controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import model.Risk;
import model.RiskLevel;
import model.UserData;

public class UserRiskController implements Risk {

	// fields
	private String userRisk;
	private static final int DEFAULT_WEIGHTING = 1;

	public String calculateRisk(Map<Integer, Integer> buttonValues) {

		// Call calculateRisk method with the buttonValues HashMap
		double riskPercentage = calculateRiskPercentage(buttonValues);
		// printButtonValues(buttonValues);

		// low risk is set to 22 because if user selects '1' (least likely) for all
		// questions,
		// the lowest possible store they can get is 21.111... assuming they answered
		// all questions
		if (riskPercentage < 22) {
			userRisk = RiskLevel.VERY_LOW.toString();
			// set to 49 because this is the percentage if user answers '2' for all
			// questions
		} else if (riskPercentage <= 49) {
			 userRisk = RiskLevel.LOW.toString();;
			// set to 84 because this is the percentage if user answers '3' for all
			// questions
		} else if (riskPercentage <= 84) {
			userRisk = RiskLevel.MODERATE.toString();;
			// 125 is the percentage if user answers '4' for all questions
		} else if (riskPercentage <= 122) {
			userRisk = RiskLevel.HIGH.toString();;
		} else {
			userRisk = RiskLevel.VERY_HIGH.toString();;
		}

		// return the user's risk
		return userRisk;

	}

	// this method calculates the user's risk by taking into accoun the weighting of
	// each question
	// the last 3 quetsions are weighed more
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

		// calculate the percentage
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

	public String getUserRisk() {
		return userRisk;
	}

	public void setUserRisk(String userRisk) {
		this.userRisk = userRisk;
	}

	public static int getDefaultWeighting() {
		return DEFAULT_WEIGHTING;
	}

	

}

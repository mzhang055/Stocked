package controller;

import java.util.HashMap;
import java.util.Map.Entry;

import model.Risk;
import view.SurveyPanel;

public class RiskController implements Risk {

	private static final int DEFAULT_WEIGHTING = 1;
	public String userRisk;

	public String determineUserRisk() {
		// Call calculateRisk method with the buttonValues HashMap
		int userScore = calculateRisk(SurveyPanel.buttonValues);

		if (userScore <= 25) {
			System.out.println("low risk");
			userRisk = "Low Risk";
		} else if (userScore > 25 && userScore <= 45) {
			System.out.println("moderate risk");
			userRisk = "Moderate Risk";
		} else if (userScore > 45) {
			System.out.println("high risk");
			userRisk = "High Risk";
		}
		return userRisk;

	}

	public int calculateRisk(HashMap<Integer, Integer> buttonValues) {
		int totalRisk = 0;

		for (Entry<Integer, Integer> entry : buttonValues.entrySet()) {
			int questionIndex = entry.getKey();
			int buttonValue = entry.getValue();

			// Determine the weighting based on the question index
			int weighting = determineWeighting(questionIndex);

			// Calculate risk by multiplying button value with weighting
			int questionRisk = buttonValue * weighting;

			// Add the calculated risk to the total
			totalRisk += questionRisk;
		}

		return totalRisk;
	}

	// Method to determine the weighting based on the question index
	private int determineWeighting(int questionIndex) {
		
		//questions 1 to 4 in the survey are more personality focused and less
		//direct about the user's financial risk
		if (questionIndex >= 0 && questionIndex < 5) {
			return 1; //these questions have weight one 1
		} 
		//questions 5 to 7 are more direct and focused on the user's 
		//financial tendencies, thus will have more weighting 
		else if (questionIndex >= 5) {
			return 2; //these questions have a weight of 1.5
		} else {
			return DEFAULT_WEIGHTING; // Default weighting
		}
	}

}

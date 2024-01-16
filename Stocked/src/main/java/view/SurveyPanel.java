package view;

import javax.swing.*;

import model.QuestionData;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class SurveyPanel extends JPanel {
	private JLabel questionLabel;
	private JPanel choicesPanel;
	private JButton[] choiceButtons;

	private List<String> choices;
	private JButton selectedButton;

	public SurveyPanel(String questionText, List<String> choices) {
		this.choices = choices;

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		

        // Add space above the question label
        add(Box.createRigidArea(new Dimension(0, 20)));


		// Customize the font and size for the question label
		Font questionFont = new Font("Arial", Font.BOLD, 22);
		questionLabel = new JLabel(questionText);
		questionLabel.setFont(questionFont);
		add(questionLabel);

		choicesPanel = new JPanel();
		choicesPanel.setLayout(new BoxLayout(choicesPanel, BoxLayout.Y_AXIS));
		add(choicesPanel);

		choiceButtons = new JButton[choices.size()];
		for (int i = 0; i < choices.size(); i++) {
			String choiceText = choices.get(i);
			JButton choiceButton = new JButton(choiceText);

			// Set a fixed size for each button
			choiceButton.setMaximumSize(new Dimension(1300, 200));

			// Customize the font and size for the choice buttons
			Font choiceFont = new Font("Arial", Font.PLAIN, 18);
			choiceButton.setFont(choiceFont);

			choiceButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					handleChoiceSelection(choiceButton);
				}
			});
			choicesPanel.add(choiceButton);
	

			choiceButtons[i] = choiceButton;
		}
	}

	public static List<QuestionData> getQuestions() {
		List<QuestionData> questionDataList = new ArrayList<>();

		questionDataList.add(new QuestionData("How old are you?", List.of("16-18", "18-20", "20-25", "25+")));
		questionDataList.add(new QuestionData("How much money have you allocated for investing?",
				List.of("Less than $100", "$100-$300", "$300-$500", "$500+")));
		questionDataList.add(new QuestionData("How much investing experience do you have?",
				List.of("This is my first time", "Less than 1 year", "1-3 years", "More than 4 years")));
		questionDataList.add(new QuestionData("What is your primary reason for investing?",
				List.of("Saving for retirement", "Home deposit", "Pay off student loans", "Not sure yet")));
		questionDataList.add(new QuestionData("When do you expect to pull money from your investments?",
				List.of("Less than 1 year", "1-4 years", "5-10 years", "10+ years")));
		questionDataList.add(new QuestionData("Which scenario best describes you?",
				List.of("Pursue modest increases in my investments, with low risk of loss",
						"Aim for investment growth, " + "accepting moderate risk of loss",
						"Seek above-average growth in investments, accepting above-average risk of loss",
						"Reach for maximum returns, accepting significant risk of loss ")));
		questionDataList.add(new QuestionData(
				"How would you react if your investment loses 20% in the first year?",
				List.of("I would sell my investment because of my concerns",
						"I would consider selling a part of my investment",
						"I would wait to see how it continues to perform",
						"I would buy more of the investment because of the discount")));
		questionDataList.add(new QuestionData(
				"Imagine that an investment you own lost 30% of its value in 3 days. What would you do?",
				List.of("Sell all my shares", "Sell a portion of my shares", "Do nothing", "Buy more shares")));

		return questionDataList;
	}

	private void handleChoiceSelection(JButton selectedButton) {
		// If a button was previously selected, reset its color
		if (this.selectedButton != null) {
			this.selectedButton.setForeground(null); // Set to default color
		}

		// Set the text color of the selected button
		selectedButton.setForeground(Color.RED);

		// Store the selected choice
		String selectedChoice = selectedButton.getText();
		System.out.println("Selected Choice: " + selectedChoice);

		// Update the selected button
		this.selectedButton = selectedButton;
	}

}

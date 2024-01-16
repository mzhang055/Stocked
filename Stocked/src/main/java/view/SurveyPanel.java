package view;

import javax.swing.*;

import model.QuestionData;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SurveyPanel extends JPanel {
    private JLabel questionLabel;
    private JPanel choicesPanel;
    private JButton[] choiceButtons;
    private List<String> choices;
    private List<Integer> finalSelectedChoices; // List to store the final selected choices

    private Map<String, Integer> choiceValues; // Map to associate choice text with integer values
    private JButton selectedButton; // Store the currently selected button

    private int currentQuestionIndex = 0; // Index of the current question

    public SurveyPanel(String questionText, List<String> choices) {
        this.choices = choices;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        finalSelectedChoices = new ArrayList<>();

        add(Box.createRigidArea(new Dimension(0, 20)));

        Font questionFont = new Font("Arial", Font.BOLD, 22);
        questionLabel = new JLabel(questionText);
        questionLabel.setFont(questionFont);
        add(questionLabel);

        choicesPanel = new JPanel();
        choicesPanel.setLayout(new BoxLayout(choicesPanel, BoxLayout.Y_AXIS));
        add(choicesPanel);

        choiceValues = new HashMap<>();
        choiceButtons = new JButton[choices.size()];

        // Initialize the finalSelectedChoices list with initial values
        for (int i = 0; i < getQuestions().size(); i++) {
            finalSelectedChoices.add(-1); // -1 indicates no selection for each question initially
        }

        for (int i = 0; i < choices.size(); i++) {
            String choiceText = choices.get(i);
            JButton choiceButton = new JButton(choiceText);
            choiceButton.setMaximumSize(new Dimension(1300, 200));
            Font choiceFont = new Font("Arial", Font.PLAIN, 18);
            choiceButton.setFont(choiceFont);
            choiceValues.put(choiceText, i + 1);

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
    private void handleChoiceSelection(JButton selectedButton) {
        if (this.selectedButton != null) {
            this.selectedButton.setForeground(null);
        }

        selectedButton.setForeground(Color.RED);
        this.selectedButton = selectedButton;

        // Update the array with the new index of the selected button based on the current question index
        int selectedIndex = choicesPanel.getComponentZOrder(selectedButton);

        // Check if there is already a response for the current question
        if (finalSelectedChoices.get(currentQuestionIndex) != -1) {
            // Replace the previous response with the new index
            finalSelectedChoices.set(currentQuestionIndex, selectedIndex);
        } else {
            // Add a new response for the current question
            finalSelectedChoices.add(currentQuestionIndex, selectedIndex);
        }

        // Print the updated array
        printSelectedChoices();

        // Move to the next question (if available)
        if (currentQuestionIndex < finalSelectedChoices.size() - 1) {
            currentQuestionIndex++;
            updateQuestion();
        }
    }


    private void updateQuestion() {
        // Clear the choicesPanel before adding new buttons
        choicesPanel.removeAll();

        // Update the question label for the next question
        String nextQuestionText = getQuestions().get(currentQuestionIndex).getQuestionText();
        questionLabel.setText(nextQuestionText);

        // Create buttons for the choices of the current question
        List<String> nextChoices = getQuestions().get(currentQuestionIndex).getChoices();
        choiceButtons = new JButton[nextChoices.size()];

        for (int i = 0; i < nextChoices.size(); i++) {
            String choiceText = nextChoices.get(i);
            JButton choiceButton = new JButton(choiceText);
            choiceButton.setMaximumSize(new Dimension(1300, 200));
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

        // Repaint the panel to reflect the changes
        choicesPanel.revalidate();
        choicesPanel.repaint();
    }

    private void printSelectedChoices() {
        System.out.println("Selected Choices Index: " + finalSelectedChoices);
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



}

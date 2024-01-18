
package view;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import controller.LoginController;
import controller.RiskController;
import model.QuestionData;
import model.UserData;

public class SurveyPanel extends JPanel implements MouseListener {

	public static final int QUESTION_SIZE_X = 1440;
	public static final int QUESTION_SIZE_Y = 900 - 115;

	private JLabel questionPrompt;
	private JButton[] buttons;
	private JButton skipButton, backButton;
	private JPanel buttonPanel;

	// HashMap to store button values
	public static HashMap<Integer, Integer> buttonValues;

	public static UserData userData;

	// Index to keep track of the current question
	private int currentQuestionIndex;

	public SurveyPanel() {
		setLayout(null);
		setOpaque(false);
		setVisible(true);

		// Initialize the HashMap
		buttonValues = new HashMap<>();

		// Initialize the current question index
		currentQuestionIndex = 0;

		createQuestionPrompt();
		createButtonPanel();
		createSkipButton();
		createBackButton();
	}

	private void createQuestionPrompt() {

		String questionText = "<html>" + QuestionData.getQuestions().get(currentQuestionIndex).getQuestionPrompt()
				+ "</html>";

		// Create the JLabel with padding
		questionPrompt = new JLabel(questionText, SwingConstants.CENTER);
		questionPrompt.setBounds(0, 40, QUESTION_SIZE_X, 400);
		questionPrompt.setFont(new Font("Arial", Font.BOLD, 30));

		// Set padding around the text
		int padding = 100;
		questionPrompt.setBorder(new EmptyBorder(padding, padding, padding, padding));

		questionPrompt.setForeground(Color.decode("#666666"));
		questionPrompt.setHorizontalAlignment(SwingConstants.CENTER);
		questionPrompt.setVerticalAlignment(SwingConstants.CENTER);
		add(questionPrompt);
	}

	private void createButtonPanel() {
		buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 0)); // Increase horizontal gap
		buttonPanel.setBounds(0, 300, QUESTION_SIZE_X, 200);
		buttonPanel.setOpaque(false);

		buttons = new JButton[6];
		for (int i = 0; i < buttons.length; i++) {
			buttons[i] = createButton(String.valueOf(i + 1));
			buttonPanel.add(buttons[i]);
		}

		add(buttonPanel);
	}

	private JButton createButton(String text) {
		JButton button = new JButton(text);
		button.setForeground(Color.decode("#FFC566"));
		button.setFont(new Font("Arial", Font.BOLD, 30));
		button.setPreferredSize(new Dimension(200, 200));
		button.addMouseListener(this);
		return button;
	}

	private void createSkipButton() {
		ImageIcon skipIcon = new ImageIcon("images/fwdBtn.png");
		skipButton = new JButton(skipIcon);
		skipButton.setContentAreaFilled(false);
		skipButton.setBorderPainted(false);
		skipButton.setPreferredSize(new Dimension(200, 200));

		skipButton.addMouseListener(this);
		skipButton.setBounds(300, 600, 300, 110);
		add(skipButton);
	}

	private void createBackButton() {
		ImageIcon backIcon = new ImageIcon("images/backBtn.png");
		backButton = new JButton(backIcon);
		backButton.setContentAreaFilled(false);
		backButton.setBorderPainted(false);
		backButton.setPreferredSize(new Dimension(200, 200));
		backButton.addMouseListener(this);
		backButton.setBounds(50, 600, 300, 110);
		add(backButton);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		JButton clickedButton = (JButton) e.getSource();
		String buttonText = clickedButton.getText();

		// If Skip button is clicked
		if (clickedButton == skipButton) {
			// Move on to the next question
			currentQuestionIndex++;
		} else if (clickedButton == backButton) {
			// If Back button is clicked, go back to the previous question and remove the
			// answer from HashMap
			if (currentQuestionIndex > 0) {
				currentQuestionIndex--;
				buttonValues.remove(currentQuestionIndex);
			}
		} else {
			// Store the button value in the HashMap using the current question index as a
			// key
			buttonValues.put(currentQuestionIndex, Integer.parseInt(buttonText));

			// Move on to the next question
			currentQuestionIndex++;
		}

		if (currentQuestionIndex < QuestionData.getQuestions().size()) {
			// If there are more questions, update the question prompt
			questionPrompt.setText(String.format("<html><p body style='text-align:center'>%s</p></html>\\\"",
					QuestionData.getQuestions().get(currentQuestionIndex).getQuestionPrompt()));
		} else {
			// Handle the end of the survey (no more questions)
			System.out.println("End of survey");

			// Print the entire HashMap
			System.out.println("Survey Results: " + buttonValues);

			// Iterate through the entire HashMap and print all contents
			for (Entry<Integer, Integer> entry : buttonValues.entrySet()) {
				System.out.println("Question " + entry.getKey() + ": ButtonValue=" + entry.getValue());
			}

			// Check if it's the last question
			if (currentQuestionIndex == QuestionData.getQuestions().size()) {
				// Disable all question buttons
				for (JButton button : buttons) {
					button.setEnabled(false);
					button.setForeground(Color.decode("#FAFAFA"));
				}
			}

		}

		// Enable or disable Back button based on the current question index
		backButton.setEnabled(currentQuestionIndex > 0);

		// Print the current values in the HashMap
		System.out.println("Button Values: " + buttonValues);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO: Handle mouse press
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO: Handle mouse release
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// No enlargement on mouse enter
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// No reset on mouse exit
	}

	public static int getQuestionSizeX() {
		return QUESTION_SIZE_X;
	}

	public static int getQuestionSizeY() {
		return QUESTION_SIZE_Y;
	}
}

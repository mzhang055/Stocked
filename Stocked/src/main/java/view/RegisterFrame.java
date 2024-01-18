package view;

import javax.swing.*;

import controller.*;
import model.QuestionData;
import model.UserData;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.List;

public class RegisterFrame extends JFrame implements ActionListener {

	private JLayeredPane layeredPane;
	private List<JTextField> textFields;
	private JButton fwdBtn;

	public static UserData userData;
	public static SurveyPanel surveyPanel;

	public RegisterFrame() {
		super("Register Frame");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1440, 900);

		// set the background color for all text fields.
		Color color = new Color(233, 233, 233);
		Font font = new Font("Arial", Font.PLAIN, 23);

		ImageIcon backgroundImg = new ImageIcon("images/profileBg.png");
		layeredPane = new JLayeredPane();
		layeredPane.setPreferredSize(new Dimension(backgroundImg.getIconWidth(), backgroundImg.getIconHeight()));

		JLabel imageLabel = new JLabel(backgroundImg);
		imageLabel.setBounds(0, 0, backgroundImg.getIconWidth(), backgroundImg.getIconHeight());
		Dimension imageSize = new Dimension(backgroundImg.getIconWidth(), backgroundImg.getIconHeight());
		imageLabel.setPreferredSize(imageSize);

		textFields = new ArrayList<>();
		textFields.add(createPlaceholderTextField("Enter your first name", 100, 200, 500, 80));
		textFields.add(createPlaceholderTextField("Enter your last name", 745, 200, 500, 80));
		textFields.add(createPlaceholderTextField("Enter your username", 100, 300, 500, 80));
		textFields.add(createPlaceholderTextField("Enter your password", 745, 300, 500, 80));
		textFields.add(createPlaceholderTextField("Enter your age", 100, 400, 500, 80));
		textFields.add(createPlaceholderTextField("Enter the amount of money you want to invest", 745, 400, 500, 80));

		surveyPanel = new SurveyPanel();
		surveyPanel.setBounds(0, 350, SurveyPanel.getQuestionSizeX(), SurveyPanel.getQuestionSizeY());
		layeredPane.add(surveyPanel, Integer.valueOf(5));
		surveyPanel.setVisible(true);

		ImageIcon confirmIcon = new ImageIcon("images/fwdBtn.png");
		fwdBtn = new JButton(confirmIcon);
		fwdBtn.setOpaque(false);
		fwdBtn.setContentAreaFilled(false);
		fwdBtn.setBorderPainted(false);
		fwdBtn.setBounds(1300, 2300, confirmIcon.getIconWidth(), confirmIcon.getIconHeight());
		fwdBtn.addActionListener(this);

		layeredPane.add(imageLabel, Integer.valueOf(0));
		for (JTextField textField : textFields) {
			layeredPane.add(textField, Integer.valueOf(1));
		}
		layeredPane.add(fwdBtn, Integer.valueOf(2));

		JScrollPane jsp = new JScrollPane(layeredPane);
		jsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		jsp.getVerticalScrollBar().setUnitIncrement(12);

		getContentPane().add(jsp);
		setVisible(true);
	}

	// method to create a JTextField with placeholder text
	private JTextField createPlaceholderTextField(String placeholder, int x, int y, int width, int height) {
		JTextField textField = new JTextField(placeholder);
		textField.setBounds(x, y, width, height);
		textField.addActionListener(this);
		textField.setBackground(new Color(233, 233, 233));
		textField.setFont(new Font("Arial", Font.PLAIN, 15));

		// Set placeholder text color
		textField.setForeground(Color.GRAY);

		// Add focus listener to handle placeholder behavior
		textField.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				if (textField.getText().equals(placeholder)) {
					textField.setText("");
					textField.setForeground(Color.BLACK); // Change text color on focus
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (textField.getText().isEmpty()) {
					textField.setText(placeholder);
					textField.setForeground(Color.GRAY); // Change text color on focus lost
				}
			}
		});

		return textField;
	}

	public static List<UserData> userDataList = new ArrayList<>();

	private void collectUserData() {
		userData = new UserData();
		
		userData.setFirstName(textFields.get(0).getText());
		userData.setLastName(textFields.get(1).getText());
		userData.setUsername(textFields.get(2).getText());
		userData.setPassword(textFields.get(3).getText());
		userData.setAge(Integer.parseInt(textFields.get(4).getText()));
		userData.setMoney(Double.parseDouble(textFields.get(5).getText()));
		
		userDataList.add(userData);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == fwdBtn) {
			collectUserData();
			RiskController riskController = new RiskController();
			riskController.determineUserRisk();
			// .parseCSV("TickerSymbol.csv");
			// calculateRiskAndPrintResults(); // Call the method to calculate risk

			System.out.println(userData.getFirstName());
			System.out.println("clicked");
			LoginController.addUserToDatabase(userData);
		}
	}

//	// Method to calculate risk using RiskController and print results
//	private void calculateRiskAndPrintResults() {
//	    // Assuming riskController is an instance of RiskController
//	    RiskController riskController = new RiskController();
//
//	    // Call calculateRisk method with the buttonValues HashMap
//	    int userRisk = riskController.calculateRisk(SurveyPanel.buttonValues);
//
//	    // testing
//	    System.out.println("User's Risk: " + userRisk);
//	}

	public static void main(String[] args) {
		new RegisterFrame();
	}
}

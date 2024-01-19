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
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RegisterFrame extends JFrame implements ActionListener {

	private JLayeredPane layeredPane;

	private JTextField firstNameField;
	private JTextField lastNameField;
	private JTextField usernameField;
	private JTextField passwordField, ageField, moneyField;
	private JButton fwdBtn;
	private String firstName;

	public static UserData userData;
	public static SurveyPanel surveyPanel;
	public static RiskController risk;

	public HomeFrame home;

	public RegisterFrame() {
		super("Register Frame");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1440, 900);

		// set the background color for all text fields.
		Color color = new Color(233, 233, 233);
		Font font = new Font("Arial", Font.PLAIN, 23);
		Font placeholderfont = new Font("Arial", Font.PLAIN, 15);

		ImageIcon backgroundImg = new ImageIcon("images/profileBg.png");
		layeredPane = new JLayeredPane();
		layeredPane.setPreferredSize(new Dimension(backgroundImg.getIconWidth(), backgroundImg.getIconHeight()));

		JLabel imageLabel = new JLabel(backgroundImg);
		imageLabel.setBounds(0, 0, backgroundImg.getIconWidth(), backgroundImg.getIconHeight());
		Dimension imageSize = new Dimension(backgroundImg.getIconWidth(), backgroundImg.getIconHeight());
		imageLabel.setPreferredSize(imageSize);

		// --- User Info
		// add first name text field
		firstNameField = new JTextField();
		firstNameField.setBounds(100, 200, 500, 80);
		firstNameField.addActionListener(this);
		firstNameField.setBackground(color);
		firstNameField.setFont(font);

		lastNameField = new JTextField();
		lastNameField.setBounds(745, 200, 500, 80);
		lastNameField.addActionListener(this);
		lastNameField.setBackground(color);
		lastNameField.setFont(font);

		usernameField = new JTextField();
		usernameField.setBounds(100, 350, 500, 80);
		usernameField.addActionListener(this);
		usernameField.setBackground(color);
		usernameField.setFont(font);

		passwordField = new JTextField();
		passwordField.setBounds(745, 350, 500, 80);
		passwordField.addActionListener(this);
		passwordField.setBackground(color);
		passwordField.setFont(font);

		ageField = new JTextField();
		ageField.setBounds(100, 400, 500, 80);
		ageField.addActionListener(this);
		ageField.setBackground(color);
		ageField.setFont(font);

		moneyField = new JTextField();
		moneyField.setBounds(745, 400, 500, 80);
		moneyField.addActionListener(this);
		moneyField.setBackground(color);
		moneyField.setFont(font);

		// add styling for text field
		firstNameField = createPlaceholderTextField("Enter your first name", 100, 200, 500, 80);
		lastNameField = createPlaceholderTextField("Enter your last name", 745, 200, 500, 80);
		usernameField = createPlaceholderTextField("Enter your username", 100, 300, 500, 80);
		passwordField = createPlaceholderTextField("Enter your password", 745, 300, 500, 80);
		ageField = createPlaceholderTextField("Enter your age", 100, 400, 500, 80);
		moneyField = createPlaceholderTextField("Enter amount of money to invest", 745, 400, 500, 80);

		// add components to the layered pane
		layeredPane.add(firstNameField, Integer.valueOf(1));
		layeredPane.add(lastNameField, Integer.valueOf(1));
		layeredPane.add(usernameField, Integer.valueOf(1));
		layeredPane.add(passwordField, Integer.valueOf(1));
		layeredPane.add(ageField, Integer.valueOf(1));
		layeredPane.add(moneyField, Integer.valueOf(1));

		// create and add survey panel (questions) to frame
		surveyPanel = new SurveyPanel();
		surveyPanel.setBounds(0, 350, SurveyPanel.getQuestionSizeX(), SurveyPanel.getQuestionSizeY());
		layeredPane.add(surveyPanel, Integer.valueOf(2));
		surveyPanel.setVisible(true);

		// add the forwards button
		ImageIcon confirmIcon = new ImageIcon("images/finishBtn.png");
		fwdBtn = new JButton(confirmIcon);
		fwdBtn.setOpaque(false);
		fwdBtn.setContentAreaFilled(false);
		fwdBtn.setBorderPainted(false);
		fwdBtn.setBounds(1200, 1000, confirmIcon.getIconWidth(), confirmIcon.getIconHeight());
		fwdBtn.addActionListener(this);

		layeredPane.add(imageLabel, Integer.valueOf(0));

		layeredPane.add(fwdBtn, Integer.valueOf(2));

		JScrollPane jsp = new JScrollPane(layeredPane);
		jsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		jsp.getVerticalScrollBar().setUnitIncrement(12);

		getContentPane().add(jsp);
		setVisible(true);
	}

//    // method to create a JTextField
//    private JTextField createTextField(int x, int y, int width, int height) {
//        JTextField textField = new JTextField();
//        textField.setBounds(x, y, width, height);
//        textField.addActionListener(this);
//        textField.setBackground(new Color(233, 233, 233));
//        textField.setFont(new Font("Arial", Font.PLAIN, 15));
//        textField.setForeground(Color.GRAY);
//
//        // Add focus listener to handle placeholder behavior
//        textField.addFocusListener(new FocusListener() {
//            @Override
//            public void focusGained(FocusEvent e) {
//                if (textField.getText().isEmpty()) {
//                    textField.setForeground(Color.BLACK); // Change text color on focus
//                }
//            }
//
//            @Override
//            public void focusLost(FocusEvent e) {
//                if (textField.getText().isEmpty()) {
//                    textField.setForeground(Color.GRAY); // Change text color on focus lost
//                }
//            }
//        });
//
//        return textField;
//    }

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

	// create a list of userData objects to store the entered information
	// (composition)
	public static List<UserData> userDataList = new ArrayList<>();

	private void collectUserData() {
		System.out.println("testing" + firstName);
		userData = new UserData();
		userData.setUsername(usernameField.getText());
		userData.setPassword(passwordField.getText());
		userData.setFirstName(firstNameField.getText());
		userData.setLastName(lastNameField.getText());

		userData.setAge(ageField.getText());
		userData.setMoney(moneyField.getText());

		// collect risk information
		RiskController riskController = new RiskController();
		riskController.determineUserRisk(SurveyPanel.buttonValues);

		userData.setRisk(riskController.getUserRisk());

		userDataList.add(userData);

		// Testing: Print collected data
		System.out.println("First Name: " + userData.getFirstName());
		System.out.println("Last Name: " + userData.getLastName());
		System.out.println("Username: " + userData.getUsername());
		System.out.println("Password: " + userData.getPassword());
		System.out.println("Age: " + userData.getAge());
		System.out.println("Money: " + userData.getMoney());
		System.out.println("Risk: " + userData.getRisk());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == fwdBtn) {
			collectUserData();

			RiskController risk = new RiskController();
			RecommendationController recommend = new RecommendationController();

			try {
				StockSymbolsController.getMostActiveStockSymbols();
			} catch (IOException i) {
				i.printStackTrace();
			}

			RiskController.stockController = new StockController();
			RiskController.stockController.populateStockMap(StockSymbolsController.getStockMap(), 500);
			risk.determineStockRisk();

			// RiskController risk = new RiskController();
			// Ensure userData is not null
			if (userData != null) {
				// Check if risk determination is synchronous
				System.out.println("User's risk: " + userData.getRisk());
			}

			// determine the user's matching stocks
			recommend.determineMatchingStocks(userData.getRisk());

			// display charts of recommended stocks
			ChartController chartController = new ChartController();
			chartController.generateCharts(userData.getRisk());

			LoginController.addUserToDatabase(userData);

			SwingUtilities.invokeLater(() -> {
				dispose();
			});
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

//	public static void main(String[] args) {
//		new RegisterFrame();
//	}
}

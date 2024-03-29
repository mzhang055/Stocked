/*
 * this class is for the user's registration frame. it collects all data and stores it in sql
 */

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

	//fields for each component
	private JLayeredPane layeredPane;
	private JTextField firstNameField;
	private JTextField lastNameField;
	private JTextField usernameField;
	private JTextField passwordField, ageField, moneyField;
	private JButton fwdBtn;
	private JButton backBtn;
	private String firstName;

	//instance of classes
	public static UserData userData;
	public static SurveyPanel surveyPanel;
	//public static RiskController risk;
	public ChartController chart;
	public RecommendationController recommend;

	public HomeFrame home;
	

	//constructor
	public RegisterFrame() {
		super("Register Frame");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1440, 900);

		//set background image
		ImageIcon backgroundImg = new ImageIcon("images/profileBg.png");
		layeredPane = new JLayeredPane();
		layeredPane.setPreferredSize(new Dimension(backgroundImg.getIconWidth(), backgroundImg.getIconHeight()));

		JLabel imageLabel = new JLabel(backgroundImg);
		imageLabel.setBounds(0, 0, backgroundImg.getIconWidth(), backgroundImg.getIconHeight());
		Dimension imageSize = new Dimension(backgroundImg.getIconWidth(), backgroundImg.getIconHeight());
		imageLabel.setPreferredSize(imageSize);
		
		//create a back button to return to welcome frame
		// get started button that leads user to the sign up
		ImageIcon backIcon = new ImageIcon("images/backWelcomeBtn.png");
		backBtn = new JButton(backIcon);
		backBtn.setContentAreaFilled(false);
		backBtn.setBorderPainted(false);
		backBtn.setBounds(30, 70, backIcon.getIconWidth(), backIcon.getIconHeight());
		backBtn.addActionListener(this);
		
		// add the forwards button
		ImageIcon confirmIcon = new ImageIcon("images/finishBtn.png");
		fwdBtn = new JButton(confirmIcon);
		fwdBtn.setOpaque(false);
		fwdBtn.setContentAreaFilled(false);
		fwdBtn.setBorderPainted(false);
		fwdBtn.setBounds(1200, 1000, confirmIcon.getIconWidth(), confirmIcon.getIconHeight());
		fwdBtn.addActionListener(this);

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
		layeredPane.add(imageLabel, Integer.valueOf(0));
		layeredPane.add(backBtn, Integer.valueOf(2));
		layeredPane.add(fwdBtn, Integer.valueOf(2));

		// create and add survey panel (questions) to frame
		surveyPanel = new SurveyPanel();
		surveyPanel.setBounds(0, 350, SurveyPanel.getQuestionSizeX(), SurveyPanel.getQuestionSizeY());
		layeredPane.add(surveyPanel, Integer.valueOf(2));
		surveyPanel.setVisible(true);

		//add scroll
		JScrollPane jsp = new JScrollPane(layeredPane);
		jsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		jsp.getVerticalScrollBar().setUnitIncrement(12);

		getContentPane().add(jsp);
		setVisible(true);
		
		chart = new ChartController(recommend);
	}


	// method to create a JTextField with placeholder text
	private JTextField createPlaceholderTextField(String placeholder, int x, int y, int width, int height) {
		JTextField textField = new JTextField(placeholder);
		textField.setBounds(x, y, width, height);
		textField.addActionListener(this);
		textField.setBackground(new Color(233, 233, 233));
		textField.setFont(new Font("Arial", Font.PLAIN, 15));
		// set placeholder text color
		textField.setForeground(Color.GRAY);
		// add focus listener to handle placeholder behavior
		textField.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				if (textField.getText().equals(placeholder)) {
					textField.setText("");
					textField.setForeground(Color.BLACK); // change text color on focus
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (textField.getText().isEmpty()) {
					textField.setText(placeholder);
					textField.setForeground(Color.GRAY); // change text color on focus lost
				}
			}
		});
		return textField;
	}

	// create a list of userData objects to store the entered information
	// (composition)
	public static List<UserData> userDataList = new ArrayList<>();

	public void collectUserData() {
		System.out.println("testing" + firstName);
		userData = new UserData();
		recommend = new RecommendationController();
		
		userData.setUsername(usernameField.getText());
		userData.setPassword(passwordField.getText());
		userData.setFirstName(firstNameField.getText());
		userData.setLastName(lastNameField.getText());
	    userData.setAge(ageField.getText());
	    userData.setMoney(moneyField.getText());
	    userData.setMatchingStocks(chart.getMatchingStocks());

		// collect risk information
		UserRiskController riskController = new UserRiskController();
		riskController.calculateRisk(SurveyPanel.buttonValues);

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
	
	//this method holds all of the data that needs to be processed for the application
	// to run
	public void processData() {

		//analyze risk of user
		UserRiskController userRisk = new UserRiskController();
		StockRiskController stockRisk = new StockRiskController();
		RecommendationController recommend = new RecommendationController();

		//start webscrapping
		try {
			StockSymbolsController.getMostActiveStockSymbols();
		} catch (IOException i) {
			i.printStackTrace();
		}

		//analyze risk of stock
		StockRiskController.setStockController(new StockController());
		StockRiskController.stockController.populateStockMap(StockSymbolsController.getStockMap(), 500);
		stockRisk.determineStockRisk();

		// RiskController risk = new RiskController();
		// Ensure userData is not null
		if (userData != null) {
			// Check if risk determination is synchronous
			System.out.println("User's risk: " + userData.getRisk());
		}

		// determine the user's matching stocks
		recommend.determineMatchingStocks(userData.getRisk());

		// display charts of recommended stocks
		ChartController chartController = new ChartController(recommend);
		chartController.generateCharts(userData.getRisk());
		
		//dissplay results 
		 EarningsPanel earningsPanel = EarningsPanel.getInstance(chartController, userData, userData.getMatchingStocks());
		earningsPanel.processSelectedStocks(userData.getMoney());
		
		//update database
		LoginController.addUserToDatabase(userData, chartController);

	}
	
	//this method provides validation checks for each field to ensure all data is valid
	public boolean accountSecurity() {
	    // check username length
	    if (usernameField.getText().length() < 8) {
	        JOptionPane.showMessageDialog(null, "Username must be at least 8 characters long.");
	        return false;
	    }

	    // check password length and requirements
	    String password = passwordField.getText();
	    if (password.length() < 8 || !password.matches(".*\\d.*\\d.*") || !password.matches(".*[.,!].*[.,!].*")) {
	        JOptionPane.showMessageDialog(null, "Password must be at least 8 characters long, include at least 2 numbers, and have at least 2 special characters (.,!).");
	        return false;
	    }
	    
	    //check if a username already exists in the database
		if (LoginController.checkUsername(userData.getUsername())) {
			JOptionPane.showMessageDialog(null, "This username already exists.");
			return false;
		}

	    // check if age is a valid integer
	    try {
	        Integer.parseInt(ageField.getText());
	    } catch (NumberFormatException e) {
	        JOptionPane.showMessageDialog(null, "Age must be a valid integer.");
	        return false;
	    }

	    // check if money is a valid double
	    try {
	        Double.parseDouble(moneyField.getText());
	    } catch (NumberFormatException e) {
	        JOptionPane.showMessageDialog(null, "Money must be a valid number.");
	        return false;
	    }

	    // all checks passed, return true
	    return true;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	    // when forward button is clicked
	    if (e.getSource() == fwdBtn) {
	    	// populate/collect user data
            collectUserData();

	        if (accountSecurity()) {
	            
	            // send all data to necessary classes
	            processData();

	            // close this frame
	            SwingUtilities.invokeLater(() -> {
	                dispose();
	            });
	        }
	    }

	    // returns to welcome frame
	    else if (e.getSource() == backBtn) {
	        new WelcomeFrame();
	        dispose();
	    }
	}


}
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

	// fields for components
	private JLayeredPane layeredPane;
	private JTextField firstNameField;
	private JTextField lastNameField;
	private JTextField usernameField;
	private JTextField passwordField;
	private JButton fwdBtn;

	// instance of classes
	public static UserData userData;
	public static SurveyPanel surveyPanel;
	// private static SurveyController surveyController;

	// constructor
	public RegisterFrame() {
		super("Register Frame");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1440, 900);

		// set the background colour for all text fields.
		Color color = new Color(233, 233, 233);
		Font font = new Font("Arial", Font.PLAIN, 23);

		// set the background image
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

		// add first name text field
		firstNameField = createPlaceholderTextField("Enter your first name", 100, 200, 500, 80);
		lastNameField = createPlaceholderTextField("Enter your last name", 745, 200, 500, 80);
		usernameField = createPlaceholderTextField("Enter your username", 100, 350, 500, 80);
		passwordField = createPlaceholderTextField("Enter your password", 745, 350, 500, 80);

		// Creates the survey JPanel

		surveyPanel = new SurveyPanel();
		surveyPanel.setBounds(0, 500, SurveyPanel.getQuestionSizeX(), SurveyPanel.getQuestionSizeY());
		layeredPane.add(surveyPanel, Integer.valueOf(5)); // Change the layer value to 5 or higher
		setSurveyPanelVisibility(true); // Ensure the panel is initially visible

		ImageIcon confirmIcon = new ImageIcon("images/fwdBtn.png");
		fwdBtn = new JButton(confirmIcon);
		fwdBtn.setOpaque(false);
		fwdBtn.setContentAreaFilled(false);
		fwdBtn.setBorderPainted(false);
		fwdBtn.setBounds(1300, 2300, confirmIcon.getIconWidth(), confirmIcon.getIconHeight());
		fwdBtn.addActionListener(this);

		// add components to the layered pane
		layeredPane.add(imageLabel, Integer.valueOf(0));
		layeredPane.add(firstNameField, Integer.valueOf(1));
		layeredPane.add(lastNameField, Integer.valueOf(1));
		layeredPane.add(usernameField, Integer.valueOf(1));
		layeredPane.add(passwordField, Integer.valueOf(1));

		layeredPane.add(fwdBtn, Integer.valueOf(2));

		// add layered pane to scroll pane
		JScrollPane jsp = new JScrollPane(layeredPane);
		jsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		jsp.getVerticalScrollBar().setUnitIncrement(12);

		// add the scroll pane to the content pane
		getContentPane().add(jsp);
		// set frame visible
		setVisible(true);

	}

	public void setSurveyPanelVisibility(boolean visible) {
		surveyPanel.setVisible(visible);
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

	// set the setters with the user's input
	private void collectUserData() {
		userData = new UserData();
		userData.setUsername(usernameField.getText());
		userData.setPassword(passwordField.getText());
		userData.setFirstName(firstNameField.getText());
		userData.setLastName(lastNameField.getText());
		userDataList.add(userData);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == fwdBtn) {
			collectUserData();
			RiskController riskController = new RiskController();
			riskController.determineUserRisk();
			//.parseCSV("TickerSymbol.csv");
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

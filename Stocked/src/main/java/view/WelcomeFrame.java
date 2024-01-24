/*
 * this is the welcome frame which prompts returning users to login and allows new users
 * to navigate to the sign up frame
 */

package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import controller.LoginController;
import model.UserData;

public class WelcomeFrame extends JFrame implements ActionListener {

	// fields
	private JButton loginBtn;
	private JButton signUpBtn;
	private JLabel titleLabel;
	private JTextArea descLabel;
	private JTextField usernameField;
	private JPasswordField passwordField;

	// store data
	public static String dataUsername;

	//instance of classes
	public static UserData userData;
	public static HomeFrame homeFrame;

	// constructor
	public WelcomeFrame() {
		// frame set up
		super("Welcome Frame");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1440, 900);
		
		//initialization so data can be accessed
		userData = new UserData();

		//this colour is used for text fields
		Color lightgrey = Color.decode("#D3D3D3");
		
		
		// set up the background image
		ImageIcon backgroundImg = new ImageIcon("images/welcomeBg.png");
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setPreferredSize(new Dimension(backgroundImg.getIconWidth(), backgroundImg.getIconHeight()));

		JLabel imageLabel = new JLabel(backgroundImg);
		imageLabel.setBounds(0, 0, backgroundImg.getIconWidth(), backgroundImg.getIconHeight());
		Dimension imageSize = new Dimension(backgroundImg.getIconWidth(), backgroundImg.getIconHeight());
		imageLabel.setPreferredSize(imageSize);

		// text label/title
		titleLabel = new JLabel("Grow Your Money");
		titleLabel.setBounds(490, 250, 470, 60);
		// set the font
		Font fontTitle = new Font("Arial", Font.BOLD, 50);
		titleLabel.setFont(fontTitle);

		// description of application
		descLabel = new JTextArea("View future stock market price projections to ease your investing\n experience. "
				+ "Built for high school and university students. ");
		descLabel.setBounds(500, 310, 470, 55);
		descLabel.setEditable(false);
		// set the font
		Font fontDesc = new Font("Arial", Font.PLAIN, 13);
		descLabel.setFont(fontDesc);

		// get started button that leads user to the sign up
		ImageIcon getStartedIcon = new ImageIcon("images/loginBtn.png");
		loginBtn = new JButton(getStartedIcon);
		loginBtn.setContentAreaFilled(false);
		loginBtn.setBorderPainted(false);
		loginBtn.setBounds(620, 600, getStartedIcon.getIconWidth(), getStartedIcon.getIconHeight());
		loginBtn.addActionListener(this);

		// add a login button for returning users
		signUpBtn = new JButton("Sign Up");
		signUpBtn.setContentAreaFilled(false);
		signUpBtn.setBorderPainted(false);
		signUpBtn.setBounds(1250, 30, 150, 80);
		signUpBtn.addActionListener(this);
		// set the font
		Font fontlogin = new Font("Arial", Font.PLAIN, 17);
		signUpBtn.setFont(fontlogin);

		// add login text fields
		usernameField = new JTextField(); // instantiate the JTextField
		usernameField.setBounds(420, 370, 600, 80);
		usernameField.addActionListener(this);
		usernameField.setBackground(lightgrey);
		usernameField.setFont(new Font("Arial", Font.PLAIN, 23));

		passwordField = new JPasswordField(); // instantiate the JTextField
		passwordField.setBounds(420, 480, 600, 80);
		passwordField.addActionListener(this);
		passwordField.setBackground(lightgrey);
		passwordField.setFont(new Font("Arial", Font.PLAIN, 23));

		// adding components to the layered pane
		layeredPane.add(imageLabel, Integer.valueOf(0));
		layeredPane.add(titleLabel, Integer.valueOf(1));
		layeredPane.add(descLabel, Integer.valueOf(1));
		layeredPane.add(loginBtn, Integer.valueOf(1));
		layeredPane.add(signUpBtn, Integer.valueOf(1));
		layeredPane.add(usernameField, Integer.valueOf(1));
		layeredPane.add(passwordField, Integer.valueOf(1));

		// add the layeredPane to the content pane
		setContentPane(layeredPane);

		setVisible(true);
	}

	// handle user's actions
	@Override
	public void actionPerformed(ActionEvent e) {

		// open register frame when get started btn clicked
		if (e.getSource() == signUpBtn) {
			new RegisterFrame();
			dispose();
		}

		//when user clicks login, determine if this is sucessful
		else if (e.getSource() == loginBtn) {
			if (LoginController.checkLoginCredentials(usernameField, passwordField)) {
		
				// get the data the user entered in the text fields
				dataUsername = usernameField.getText().trim();

				// close current frame
				String username = WelcomeFrame.getDataUsername();
				UserData userData = LoginController.getUserData(username);
				dispose();
				new HomeFrame(userData.getMatchingStocks());
			
				//display error message to user 
			} else {
				JOptionPane.showMessageDialog(null, "Invalid Login.");
				 // clear text fields and enable them so the user can re-enter their login
	            usernameField.setText("");
	            passwordField.setText("");
	            usernameField.setEditable(true);
	            passwordField.setEditable(true);
			}

		}

	}


	//setters and getters
	public JButton getLoginBtn() {
		return loginBtn;
	}

	public void setLoginBtn(JButton loginBtn) {
		this.loginBtn = loginBtn;
	}

	public JButton getSignUpBtn() {
		return signUpBtn;
	}

	public void setSignUpBtn(JButton signUpBtn) {
		this.signUpBtn = signUpBtn;
	}

	public JLabel getTitleLabel() {
		return titleLabel;
	}

	public void setTitleLabel(JLabel titleLabel) {
		this.titleLabel = titleLabel;
	}

	public JTextArea getDescLabel() {
		return descLabel;
	}

	public void setDescLabel(JTextArea descLabel) {
		this.descLabel = descLabel;
	}

	public JTextField getUsernameField() {
		return usernameField;
	}

	public void setUsernameField(JTextField usernameField) {
		this.usernameField = usernameField;
	}

	public JPasswordField getPasswordField() {
		return passwordField;
	}

	public void setPasswordField(JPasswordField passwordField) {
		this.passwordField = passwordField;
	}

	public static String getDataUsername() {
		return dataUsername;
	}

	public static void setDataUsername(String dataUsername) {
		WelcomeFrame.dataUsername = dataUsername;
	}

	public static UserData getUserData() {
		return userData;
	}

	public static void setUserData(UserData userData) {
		WelcomeFrame.userData = userData;
	}

}
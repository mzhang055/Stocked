/*
 * this class creates the welcome frame where users can sign up
 * or login to their account. it provides a brief description to what 
 * the program does
 */

package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

import controller.ChartController;
import controller.ConnectionController;
import controller.LoginController;
import controller.RecommendationController;
import model.UserData;

public class WelcomeFrame extends JFrame implements ActionListener {

	// fields
	private JButton loginBtn;
	private JButton signUpBtn;
	private JLabel titleLabel;
	private JTextArea descLabel;
	private JTextField usernameField;
	private JPasswordField passwordField;
	
	public static String dataUsername;
	public static HomeFrame homeFrame;
	

	public static UserData userData = new UserData();
	public static RecommendationController recommendationController = RecommendationController.getInstance();
	public static ChartController chartController = new ChartController(recommendationController);


	// constructor
	public WelcomeFrame() {
		super("Welcome Frame");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1440, 900);
		
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
		descLabel = new JTextArea(
				"View future stock market price projections to ease your investing\n experience. "
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
		
		//add a login button for returning users
		signUpBtn = new JButton("Sign Up");
		signUpBtn.setContentAreaFilled(false);
		signUpBtn.setBorderPainted(false);
		signUpBtn.setBounds(1250, 30, 150, 80);
		signUpBtn.addActionListener(this);
		//set the font
		Font fontlogin = new Font("Arial", Font.PLAIN, 17);
		signUpBtn.setFont(fontlogin);
		
		// add login text fields
		usernameField = new JTextField(); // instantiate the JTextField
		usernameField.setBounds(420,370, 600, 80);
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

	//handle user's actions
	@Override
	public void actionPerformed(ActionEvent e) {
		
		//open register frame when get started btn clicked
		if (e.getSource() == signUpBtn) {
			new RegisterFrame();
			dispose();
		}
		
		else if (e.getSource() == loginBtn) {
			// get the data the user entered in the text fields
			dataUsername = usernameField.getText().trim();
			
			//convert the character array to a string
			//they were chracters initially in order to hide what the user was entering
			//in that field
			char[] passwordChars = passwordField.getPassword();
			String dataPassword = new String(passwordChars);

			PreparedStatement ps; //execute query
			ResultSet rs; //store query

			//SQL query to select user data based on the username and password
			String query = "SELECT * FROM `users` WHERE `username` =? AND `password` =?";

			try {
				//get prepared statement by connecting with database
				ps = ConnectionController.getConnection().prepareStatement(query);
				
				//set data
				ps.setString(1, dataUsername);
				ps.setString(2, dataPassword);

				//execute the query
				rs = ps.executeQuery();

				//if result set has data, display message to user
				if (rs.next()) {
					String firstName = rs.getString("firstName");
					JOptionPane.showMessageDialog(null, "Successful Login");
					
					// close current frame
					// swing utilities is needed here to ensure current frame is disposed
					// since this occurs after the joptionpane message
					SwingUtilities.invokeLater(() -> {
						String username = WelcomeFrame.getDataUsername();
						UserData userData = LoginController.getUserData(username);
						dispose();
						homeFrame = new HomeFrame();
					});

				}

				//display error message to user
				else {
					JOptionPane.showMessageDialog(null, "Invalid Login.");
					SwingUtilities.invokeLater(() -> {
						dispose();
						new WelcomeFrame();
					});
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
		

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

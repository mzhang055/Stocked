package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import model.UserData;
import view.RegisterFrame;

public class LoginController {
	
	private static RegisterFrame register;

	// checks if a username already exists
	public static boolean checkUsername(String username) {
		PreparedStatement ps; //for executing SQL queries
		ResultSet rs; //for storing query results
		boolean checkUser = false; //boolean to indicate if a user exists of not

		//SQL query to check if the username exists in the database called app_users
		String query = "SELECT * FROM `users` WHERE `username` =?";

		try {
			// create a prepared statement and set the username parameter
			ps = ConnectionController.getConnection().prepareStatement(query);
			ps.setString(1, username);

			//execute to check if the result set has any entries
			rs = ps.executeQuery();

			//if there are entries, the username exists
			if (rs.next()) {
				checkUser = true;

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return checkUser;

	}
	// add user to the mysql database
		public static void addUserToDatabase(UserData user) {

			PreparedStatement ps; //for execution
			
			//this string inserts a new user into the database
			String query = "INSERT INTO `users`(`username`, `password`, `firstName`, `lastName`, `stock1`, `stock2`, `stock3`, `stock4`, `stock5`) "
					+ "VALUES (?,?,?,?,?,?,?,?,?)";

			//set the user data in database with the data the user entered
			try {
				ps = ConnectionController.getConnection().prepareStatement(query);

				ps.setString(1, user.getUsername());
				ps.setString(2, user.getPassword());
				ps.setString(3, user.getFirstName());
				ps.setString(4, user.getLastName());
				ps.setString(5, user.getStock1());
				ps.setString(6, user.getStock2());
				ps.setString(7, user.getStock3());
				ps.setString(8, user.getStock4());
				ps.setString(9, user.getStock5());

				//if execution of query is successful, display message to user
				if (ps.executeUpdate() > 0) {
				    // Display a success message
				    JOptionPane.showMessageDialog(null, "New User Added");

				    // create a new instance of registerFrame
				    register = new RegisterFrame();

				    //dispose set up frame
				    SwingUtilities.invokeLater(() -> {
				        if (register != null) {
				        	register.dispose();
				        }
				    });
				}


			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
		
		//get user data from database using the username
		public static UserData getUserData(String username) {
			PreparedStatement ps; //execution
			ResultSet rs; //storing

			
			//query to select user data based on the username
			String query = "SELECT * FROM `users` WHERE `username` =?";

			try {
				//set and execute the query
				ps = ConnectionController.getConnection().prepareStatement(query);
				ps.setString(1, username);
				rs = ps.executeQuery();

				//if the result set has a least one row, create a StudentData object and
				//set its properties
				if (rs.next()) {
					UserData studentData = new UserData();
					studentData.setUsername(rs.getString("username"));
					studentData.setPassword(rs.getString("password"));
					studentData.setFirstName(rs.getString("firstName"));
					studentData.setLastName(rs.getString("lastName"));
					studentData.setStock1(rs.getString("stock1"));
					studentData.setStock2(rs.getString("stock2"));
					studentData.setStock3(rs.getString("stock3"));
					studentData.setStock4(rs.getString("stock4"));
					studentData.setStock5(rs.getString("stock5"));

					return studentData;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

			//reutrn null if no user data is found
			return null;
		}
		
		// this method checks if the user already exists in the database
		public static boolean doesUserExist(String username) {
			//execute and store query
			PreparedStatement ps;
			ResultSet rs;

			//query to select user data based on username
			String query = "SELECT * FROM `users` WHERE `username` =?";

			//check if the user exists
			try {
				ps = ConnectionController.getConnection().prepareStatement(query);
				ps.setString(1, username);

				rs = ps.executeQuery();

				// if this is true, user exists. if not, they dont exist
				return rs.next();
			} catch (SQLException e) {
				e.printStackTrace();
			}

			//reutrn false is no user found
			return false;
		}

	
}

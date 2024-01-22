/*
 * this class is responsible for handling data retrieval from the sql database,
 * verfying the user login, and adding to the database
 */

package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import model.UserData;
import view.HomeFrame;
import view.RegisterFrame;

public class LoginController {

	// instances
	private static RegisterFrame register;

	// checks if a username already exists
	public static boolean checkUsername(String username) {
		PreparedStatement ps; // for executing SQL queries
		ResultSet rs; // for storing query results
		boolean checkUser = false; // boolean to indicate if a user exists of not

		// SQL query to check if the username exists in the database called app_users
		String query = "SELECT * FROM `users` WHERE `username` =?";

		try {
			// create a prepared statement and set the username parameter
			ps = ConnectionController.getConnection().prepareStatement(query);
			ps.setString(1, username);

			// execute to check if the result set has any entries
			rs = ps.executeQuery();

			// if there are entries, the username exists
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

		PreparedStatement ps; // for execution

		// this string inserts a new user into the database
		String query = "INSERT INTO `users`(`username`, `password`, `firstName`, `lastName`, `age`, `money`, "
				+ "`risk`, `rec1`, `rec2`, `rec3`, `rec4`, `rec5`, `rec6`, `rec7`, `rec8`, `rec9`, `rec10`)"
				+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		// set the user data in database with the data the user entered
		try {
			ps = ConnectionController.getConnection().prepareStatement(query);

			ps.setString(1, user.getUsername());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getFirstName());
			ps.setString(4, user.getLastName());
			ps.setString(5, user.getAge());
			ps.setString(6, user.getMoney());
			ps.setString(7, user.getRisk());
			
		      // iterate over the ArrayList and set values for rec1 to rec10
	        ArrayList<String> recommendations = user.getMatchingStocks();
	        for (int i = 0; i < 10; i++) {
	            // check if there are enough recommendations in the ArrayList
	            if (i < recommendations.size()) {
	                ps.setString(8 + i, recommendations.get(i));
	            } else {
	                // if not enough recommendations, set to an empty string or null as needed
	                ps.setString(8 + i, "");
	            }
	        }
			

			// if execution of query is successful, display message to user
			if (ps.executeUpdate() > 0) {
				// Display a success message
				JOptionPane.showMessageDialog(null, "New User Added");

				// create a new instance of registerFrame
				register = new RegisterFrame();
				// home.setVisible(true);

				// dispose set up frame
				SwingUtilities.invokeLater(() -> {
					if (register != null) {
						System.out.println("not null");
						new HomeFrame();

						register.dispose();
					}
				});
			}

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	// get user data from database using the username
	public static UserData getUserData(String username) {
		PreparedStatement ps; // execution
		ResultSet rs; // storing

		// query to select user data based on the username
		String query = "SELECT * FROM `users` WHERE `username` =?";

		try {
			// set and execute the query
			ps = ConnectionController.getConnection().prepareStatement(query);
			ps.setString(1, username);
			rs = ps.executeQuery();

			// if the result set has a least one row, create a user data object and
			// set its properties
			if (rs.next()) {
				UserData userData = new UserData();
				// UserPortfolio portfolioData = new UserPortfolio();
				userData.setUsername(rs.getString("username"));
				userData.setPassword(rs.getString("password"));
				userData.setFirstName(rs.getString("firstName"));
				userData.setLastName(rs.getString("lastName"));
				userData.setAge(rs.getString("age"));
				userData.setMoney(rs.getString("money"));
				userData.setRisk(rs.getString("risk"));
				
				
				  // Use a loop to add rec1 to rec10 based on column names
	            for (int i = 1; i <= 10; i++) {
	                String recColumnName = "rec" + i;
	                userData.getMatchingStocks().add(rs.getString(recColumnName));
	            }

	            // Set matching stocks in the UserData object
	            userData.setMatchingStocks(userData.getMatchingStocks());

				return userData;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// return null if no user data is found
		return null;
	}

	// this method checks if the user already exists in the database
	public static boolean doesUserExist(String username) {
		// execute and store query
		PreparedStatement ps;
		ResultSet rs;

		// query to select user data based on username
		String query = "SELECT * FROM `users` WHERE `username` =?";

		// check if the user exists
		try {
			ps = ConnectionController.getConnection().prepareStatement(query);
			ps.setString(1, username);

			rs = ps.executeQuery();

			// if this is true, user exists. if not, they dont exist
			return rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// reutrn false is no user found
		return false;
	}

}

/* This class is responsible for managing database connections between
 * the MySQL database and the java application.
 */

package controller;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionController {

	
	public static Connection getConnection() {
		//the connection object to be returned
		Connection con = null;
		
		try {
			//dynamically load the MySQL JDBC driver class
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			//establish a connection to the MySQL database
			con = DriverManager.getConnection("jdbc:mysql://localhost/stock users", "root", "");
			
			//handle errors
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		
		//return the connection object
		return con;
	
	}
}

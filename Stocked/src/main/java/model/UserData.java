/*
 * 
 */

package model;

public class UserData {

	// fields for user data
	private String username;
	private String password;
	private String firstName;
	private String lastName;

	// fields for the stocks the user invested in
	// this data is used in the portfolio
	private String stock1;
	private String stock2;
	private String stock3;
	private String stock4;
	private String stock5;
	
	//constructor
	
	
	//getters and setters
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getStock1() {
		return stock1;
	}

	public void setStock1(String stock1) {
		this.stock1 = stock1;
	}

	public String getStock2() {
		return stock2;
	}

	public void setStock2(String stock2) {
		this.stock2 = stock2;
	}

	public String getStock3() {
		return stock3;
	}

	public void setStock3(String stock3) {
		this.stock3 = stock3;
	}

	public String getStock4() {
		return stock4;
	}

	public void setStock4(String stock4) {
		this.stock4 = stock4;
	}

	public String getStock5() {
		return stock5;
	}

	public void setStock5(String stock5) {
		this.stock5 = stock5;
	}

}

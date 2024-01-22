/*
 * this class stores all information on the user 
 */

package model;

import java.util.ArrayList;

public class UserData {

	// fields for user data
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String age;
	private String money;
	private String risk;
	public ArrayList<String> matchingStocks; 
	
	
	//constructor to initialize matching stocks list
    public UserData() {
        matchingStocks = new ArrayList<>();
    }

	
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

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getRisk() {
		return risk;
	}

	public void setRisk(String risk) {
		this.risk = risk;
	}

	public ArrayList<String> getMatchingStocks() {
		return matchingStocks;
	}

    public void setMatchingStocks(ArrayList<String> matchingStocks) {
        this.matchingStocks = matchingStocks;
    }


}

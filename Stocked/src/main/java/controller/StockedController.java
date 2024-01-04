/*
 * This is the main controller for the application. it opens the login
 * frame when run
 */

package controller;

import view.*;

public class StockedController {
	
	//frames
	private LoginFrame login;
	private RegisterFrame register;
	private WelcomeFrame welcome;
	
	//constructor
	public StockedController(LoginFrame login, RegisterFrame register, WelcomeFrame welcome) {
		super();
		this.login = login;
		this.register = register;
		this.welcome = welcome;
	}

	//setters and getters
	public LoginFrame getLogin() {
		return login;
	}

	public void setLogin(LoginFrame login) {
		this.login = login;
	}

	public RegisterFrame getRegister() {
		return register;
	}

	public void setRegister(RegisterFrame register) {
		this.register = register;
	}

	public WelcomeFrame getWelcome() {
		return welcome;
	}

	public void setWelcome(WelcomeFrame welcome) {
		this.welcome = welcome;
	}

}

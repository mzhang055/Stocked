/*
 * Michelle Zhang
 * Friday Jan 19 2024
 * ICS4U1
 * Stocked - Final Summative Project
 * 
 * DESCRIPTION:
 * - this is a tool that helps students and young adults get into investing by 
 * analyzing their financial risk tolerance through a survey and recommending them
 * up to 10 stocks that fit this description. Users also have the ability to
 * view projected market trends (calculated based on historical stock data), and 
 * if they were to invest in a stock at a certain time, how much money they would
 * earn/lose
 * 
 * FEATURES:
 * - sophisticated recommendation system algorithm with questions that carry different weightings.
 * the questions toward the end are more direct and finance/money-focused so they have more weighting.
 * this is taken into calculation when determining the user's risk
 * - determines the riskiness of a stock by analyzing a stock's historical performance through data
 * retrieved from the Alpha Vantage API. I calculated the standard deviation of each stock for this
 * - due to limitations on API requested, I web scrapped on Yahoo Finance to get each day's top 25 
 * most active stocks. this is live so every time the user logins, updated data is collected. these 25
 * stocks act as the pool of stocks the user can be recommended
 * - stock price projections using time series forecasting 
 * - calculates the amount of money the user can lose/gain if they invested a certain amount of money at a certain 
 * time. this calculation takes into account the current price, historical price, and the amount of shares they can buy
 * - MySQL connection for the login system. All data is stored there
 * - there is also a validation check on the passwords
 * 
 * MAJOR SKILLS:
 * - Object Oriented Programming principles (interfaces, composition), HashMaps, ArrayLists, basic control structures,
 * methods, JFreeChart, JSoup for web scrapping on Yahoo Finance website, MySQL, XAMP for local hosting, Alpha Vantage API, 
 * all dependencies were implemented with Maven
 * 
 * CONCERNS:
 * - trend projection may not be very accurate due to ARMIA parameters 
 * - SQL database can only be accessed locally
 * 
 */

package application;

import view.WelcomeFrame;

public class StockedApplication {
	
	public static void main(String[] args) {
		WelcomeFrame welcome = new WelcomeFrame();
	}

}

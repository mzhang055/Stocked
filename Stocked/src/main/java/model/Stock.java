/*
 * this class contains all needed attributes of a stock
 */

package model;

import java.util.HashMap;

public class Stock {

	// fields
	private String ticker;
	private double closePrice;
	private double percentageDeviation;
	public HashMap<String, Double> stockMap;

	// constructor
	public Stock() {
		super();

	}

	// setters and getters
	public String getTicker() {
		return ticker;
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

	public double getClosePrice() {
		return closePrice;
	}

	public void setClosePrice(double closePrice) {
		this.closePrice = closePrice;
	}

	public double getPercentageDeviation() {
		return percentageDeviation;
	}

	public void setPercentageDeviation(double percentageDeviation) {
		this.percentageDeviation = percentageDeviation;
	}

}

package model;

public class Stock {

	private String timestamp;
	private String open;
	private String high;
	private String low;
	private String close;
	private String volume;

	public Stock(String timestamp, String open, String high, String low, String close, String volume) {
		super();
		this.timestamp = timestamp;
		this.open = open;
		this.high = high;
		this.low = low;
		this.close = close;
		this.volume = volume;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getOpen() {
		return open;
	}

	public void setOpen(String open) {
		this.open = open;
	}

	public String getHigh() {
		return high;
	}

	public void setHigh(String high) {
		this.high = high;
	}

	public String getLow() {
		return low;
	}

	public void setLow(String low) {
		this.low = low;
	}

	public String getClose() {
		return close;
	}

	public void setClose(String close) {
		this.close = close;
	}

	public String getVolume() {
		return volume;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}
	
	

}

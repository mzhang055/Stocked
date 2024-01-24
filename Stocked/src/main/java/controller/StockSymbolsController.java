/*
 * this class is repsonsible for web scrapping the top 25 most active stocks from 
 * the yahoo finance website. This will be web scrapped everytime the user logs in
 * so it is the most up to date
 * source; https://medium.com/javarevisited/web-scraping-using-java-why-not-it-is-very-easy-b9934229269d
 */
package controller;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class StockSymbolsController {

	// establishing connection / set up for web scrapping
	static Logger logger = Logger.getLogger(StockSymbolsController.class.getName());
	private static final String BASE_URI = "https://finance.yahoo.com";
	private static final String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36";
	// hashmap to store stock symbols and null values
	public static Map<String, Double> stockMap = new HashMap<>();

	// get the symbols from the link
	public static void getMostActiveStockSymbols() throws IOException {
		// connection to link
		Document doc = Jsoup.connect(BASE_URI.concat("/most-active/?count=25&offset=0")) // rest of link
				.userAgent(USER_AGENT).get();

		// log title of document
		logger.info(doc.title());
		//extract and store stock symbols in stockMap
		stockMap = extractStockMap(doc);
	}

	//helper method for extraction. handles HTML
	private static Map<String, Double> extractStockMap(Document document) {
		Map<String, Double> stockMap = new HashMap<>();

		//selecting the elements containing stock symbols
		Elements symbolElements = document.select("a[data-test=quoteLink]");

		//iterate thorugh symbol elements and add to stockmap
		for (Element symbolElement : symbolElements) {
			String stockSymbol = symbolElement.text();
			// initialize the value as null
			stockMap.put(stockSymbol, null);
		}
		//return populated map
		return stockMap;
	}

	// print stock symbols and null values
	public static void printStockMap() {
		System.out.println("Stock Map:");
		for (Map.Entry<String, Double> entry : stockMap.entrySet()) {
			System.out.println("Stock Symbol: " + entry.getKey() + ", Value: " + entry.getValue());
		}
	}

	//getters and setters
	public static Map<String, Double> getStockMap() {
		return stockMap;
	}

	public static void setStockMap(Map<String, Double> stockMap) {
		StockSymbolsController.stockMap = stockMap;
	}

}
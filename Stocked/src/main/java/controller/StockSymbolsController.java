/*
 * this class is repsonsible for web scrapping the top 25 most active stocks from 
 * the yahoo finance website. This will be web scrapped everytime the user logs in
 * so it is the most up to date
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

    //fields
    static Logger logger = Logger.getLogger(StockSymbolsController.class.getName());
    private static final String BASE_URI = "https://finance.yahoo.com";
    private static final String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36";
    // hashmap to store stock symbols and null values
    public static Map<String, Double> stockMap = new HashMap<>();

    public static void main(String[] args) {
        try {
            // Fetch most active stock symbols and store in the stockMap field
            getMostActiveStockSymbols();
            printStockMap();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void getMostActiveStockSymbols() throws IOException {
        Document doc = Jsoup
                .connect(BASE_URI.concat("/most-active/?count=25&offset=0"))
                .userAgent(USER_AGENT)
                .get();

        logger.info(doc.title());

        stockMap = extractStockMap(doc);
    }

    private static Map<String, Double> extractStockMap(Document document) {
        Map<String, Double> stockMap = new HashMap<>();

        // Selecting the elements containing stock symbols
        Elements symbolElements = document.select("a[data-test=quoteLink]");

        for (Element symbolElement : symbolElements) {
            String stockSymbol = symbolElement.text();
            // Initialize the value as null
            stockMap.put(stockSymbol, null);
        }

        return stockMap;
    }

    // Public static method to print stock symbols and null values
    public static void printStockMap() {
        System.out.println("Stock Map:");
        for (Map.Entry<String, Double> entry : stockMap.entrySet()) {
            System.out.println("Stock Symbol: " + entry.getKey() + ", Value: " + entry.getValue());
        }
    }

	public static Map<String, Double> getStockMap() {
		return stockMap;
	}

	public static void setStockMap(Map<String, Double> stockMap) {
		StockSymbolsController.stockMap = stockMap;
	}
    
    
}

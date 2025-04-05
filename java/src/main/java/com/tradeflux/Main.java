package com.tradeflux;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        logger.info("Starting TradeFlux app...");
        try {
            BinanceConnector connector = new BinanceConnector();
            logger.info("Making API request to Binance");

            HashMap<String, String> params = new HashMap<>();
            params.put("symbols", "[\"BTCUSDT\",\"BNBUSDT\"]");

            connector.makeApiRequest("/ticker/price", params);
            logger.info("API request completed");
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error occurred during API request", e);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Unexpected error occurred", e);
        } finally {
            logger.info("TradeFlux application finished");
        }
    }

}
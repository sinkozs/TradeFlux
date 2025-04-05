package com.tradeflux;

import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import okhttp3.*;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        logger.info("Starting TradeFlux app...");
        try {
            BinanceConnector connector = new BinanceConnector();
            logger.info("Making API request to Binance");

            HashMap<String, String> params = new HashMap<>();
            params.put("symbols", "[\"BTCUSDT\",\"BNBUSDT\"]");

            connector.makeRESTApiRequest("/exchangeInfo",  Optional.of(params));
            logger.info("API request completed");

            WebSocket bookTickerSocket = connector.connectToWSStream("btcusdt", "bookTicker");

        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error occurred during API request", e);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Unexpected error occurred", e);
        } finally {
            logger.info("TradeFlux application finished");
        }
    }

}
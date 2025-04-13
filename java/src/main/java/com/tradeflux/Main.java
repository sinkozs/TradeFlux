package com.tradeflux;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());
    private static final MarketDataService service = new MarketDataService();

    public static void main(String[] args) {
        logger.info("Starting TradeFlux app...");
        try {
            MarketDataProcessorGrpc binanceService = new MarketDataProcessorGrpc(service);
            MarketDataServer server = new MarketDataServerImpl(8080, binanceService);

            server.start();
            server.blockUntilShutdown();

        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error occurred during API request", e);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Unexpected error occurred", e);
        } finally {
            logger.info("TradeFlux application finished");
        }
    }

}
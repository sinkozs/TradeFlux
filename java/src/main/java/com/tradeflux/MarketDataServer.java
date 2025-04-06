package com.tradeflux;

import java.io.IOException;

public interface MarketDataServer {
    void start() throws IOException;

    void stop() throws InterruptedException;

    void blockUntilShutdown() throws InterruptedException;
}
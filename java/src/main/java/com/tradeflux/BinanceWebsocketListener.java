package com.tradeflux;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class BinanceWebsocketListener extends WebSocketListener {
    private static final Logger logger = Logger.getLogger(BinanceWebsocketListener.class.getName());
    private final ObjectMapper objectMapper;
    private final String[] streamParams;

    public BinanceWebsocketListener(String... streamParams) {
        this.objectMapper = new ObjectMapper();
        this.streamParams = streamParams;
    }

    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        logger.info("WebSocket connection established");

        try {
            Map<String, Object> subscribeMessage = new HashMap<>();
            subscribeMessage.put("method", "SUBSCRIBE");
            subscribeMessage.put("params", streamParams);
            subscribeMessage.put("id", 1);

            String subscribeJson = objectMapper.writeValueAsString(subscribeMessage);
            webSocket.send(subscribeJson);
            logger.info("Subscription request sent: " + subscribeJson);
        } catch (Exception e) {
            logger.severe("Error creating subscription message: " + e.getMessage());
        }
    }
    @Override
    public void onMessage(WebSocket webSocket, String text) {
        try {
            JsonNode data = objectMapper.readTree(text);
            logger.info("Received: " + data);
        } catch (Exception e) {
            logger.warning("Error processing message: " + e.getMessage());
        }
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        logger.severe("WebSocket failure: " + (t != null ? t.getMessage() : "Unknown error"));
    }

    @Override
    public void onClosing(WebSocket webSocket, int code, String reason) {
        logger.info("WebSocket closing: " + code + " " + reason);
        webSocket.close(1000, null);
    }

    @Override
    public void onClosed(WebSocket webSocket, int code, String reason) {
        logger.info("WebSocket closed: " + code + " " + reason);
    }
}
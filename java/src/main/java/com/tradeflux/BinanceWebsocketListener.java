package com.tradeflux;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tradeflux.grpc.CoinPrice;
import com.tradeflux.grpc.PriceResponse;
import io.grpc.stub.StreamObserver;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class BinanceWebsocketListener<T> extends WebSocketListener {
    private static final Logger logger = Logger.getLogger(BinanceWebsocketListener.class.getName());
    private final ObjectMapper objectMapper;
    private final String[] streamParams;
    private final StreamObserver<T> responseObserver;
    private final Class<T> responseType;

    // constructor for non-gRPC usage
    public BinanceWebsocketListener(String... streamParams) {
        this.objectMapper = new ObjectMapper();
        this.streamParams = streamParams;
        this.responseObserver = null;
        this.responseType = null;
    }

    // constructor for gRPC streaming
    public BinanceWebsocketListener(StreamObserver<T> responseObserver, Class<T> responseType, String... streamParams) {
        this.objectMapper = new ObjectMapper();
        this.streamParams = streamParams;
        this.responseObserver = responseObserver;
        this.responseType = responseType;
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
            if(responseObserver != null){
                logger.info("Calling processMessageForGrpc" );
                processMessageForGrpc(data);
            }
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

    protected void processMessageForGrpc(JsonNode data) {
        if (responseType.equals(PriceResponse.class)) {
            try {
                if (!data.has("result") && data.has("s")) {
                    String symbol = data.get("s").asText();
                    // Assuming 'b' is the best bid price
                    double price = data.get("b").asDouble();

                    CoinPrice coinPrice = CoinPrice.newBuilder()
                            .setSymbol(symbol)
                            .setPrice(price)
                            .build();

                    PriceResponse response = PriceResponse.newBuilder()
                            .addCoinPrices(coinPrice)
                            .build();

                    ((StreamObserver<PriceResponse>)responseObserver).onNext(response);
                }
            } catch (Exception e) {
                logger.warning("Error converting WebSocket message to gRPC: " + e.getMessage());
            }
        }
    }
}
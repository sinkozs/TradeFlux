package com.tradeflux;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tradeflux.grpc.CoinPrice;
import com.tradeflux.grpc.NBBO;
import com.tradeflux.grpc.PriceResponse;
import com.tradeflux.grpc.StreamNBBOResponse;
import io.grpc.stub.StreamObserver;
import io.grpc.stub.ServerCallStreamObserver;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.concurrent.atomic.AtomicBoolean;

public class BinanceWebsocketListener<T> extends WebSocketListener {
    private static final Logger logger = Logger.getLogger(BinanceWebsocketListener.class.getName());
    private final ObjectMapper objectMapper;
    private final String[] streamParams;
    private final StreamObserver<T> responseObserver;
    private final Class<T> responseType;
    private final String streamType;
    private final AtomicBoolean cancelled = new AtomicBoolean(false);
    private WebSocket webSocket;

    // constructor for non-gRPC usage
    public BinanceWebsocketListener(String streamType, String... streamParams) {
        this.objectMapper = new ObjectMapper();
        this.streamParams = streamParams;
        this.streamType = streamType;
        this.responseObserver = null;
        this.responseType = null;
    }

    // constructor for gRPC streaming
    public BinanceWebsocketListener(String streamType, StreamObserver<T> responseObserver, Class<T> responseType, String... streamParams) {
        this.objectMapper = new ObjectMapper();
        this.streamParams = streamParams;
        this.streamType = streamType;
        this.responseObserver = responseObserver;
        this.responseType = responseType;

        if (responseObserver instanceof ServerCallStreamObserver) {
            ServerCallStreamObserver<T> serverCallStreamObserver =
                    (ServerCallStreamObserver<T>) responseObserver;

            serverCallStreamObserver.setOnCancelHandler(() -> {
                logger.info("Client cancelled the request");
                cancelled.set(true);
                closeWebSocketGracefully();
            });

            serverCallStreamObserver.setOnReadyHandler(() -> {
                logger.info("Stream is ready");
            });
        }
    }

    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        this.webSocket = webSocket;
        logger.info("WebSocket connection established");

        try {
            // For each symbol, create a parameter in the format "symbol@streamType"
            List<String> formattedParams = new ArrayList<>();
            for (String symbol : streamParams) {
                logger.info("onOpen formattedParams add " + symbol.toLowerCase() + "@" + streamType);
                formattedParams.add(symbol.toLowerCase() + "@" + streamType);
            }

            Map<String, Object> subscribeMessage = new HashMap<>();
            subscribeMessage.put("method", "SUBSCRIBE");
            subscribeMessage.put("params", formattedParams);
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
        if (cancelled.get()) {
            logger.info("Ignoring message as client has cancelled");
            return;
        }
        try {
            JsonNode data = objectMapper.readTree(text);

            // Check if this is a subscription response
            if (data.has("result") && data.has("id")) {
                logger.info("Received subscription response: " + text);
                return;
            }

            if (responseObserver != null) {
                logger.info("Processing message for gRPC");
                if (responseType.equals(PriceResponse.class)) {
                    processAvgPriceUpdateGrpc(data);
                }
                if (responseType.equals(StreamNBBOResponse.class)) {
                    processNBBOMessageGrpc(data);
                } else {
                    logger.info("Unrecognized responseType: " + responseType);
                    return;
                }
            }
            logger.info("Received: " + data);
        } catch (Exception e) {
            logger.warning("Error processing message: " + e.getMessage());
        }
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        logger.severe("WebSocket failure: " + (t != null ? t.getMessage() : "Unknown error"));
        if (responseObserver != null) {
            responseObserver.onError(t);
        }
    }

    @Override
    public void onClosing(WebSocket webSocket, int code, String reason) {
        logger.info("WebSocket closing: " + code + " " + reason);
        webSocket.close(1000, null);
    }

    @Override
    public void onClosed(WebSocket webSocket, int code, String reason) {
        logger.info("WebSocket closed: " + code + " " + reason);
        if (responseObserver != null) {
            responseObserver.onCompleted();
        }
    }

    protected void processNBBOMessageGrpc(JsonNode data) {
        if (responseType.equals(StreamNBBOResponse.class)) {
            try {
                // Skip subscription confirmation messages
                if (!data.has("result") && data.has("s")) {
                    String symbol = data.get("s").asText();

                    double bidPrice = data.has("b") ? data.get("b").asDouble() : 0.0;
                    double askPrice = data.has("a") ? data.get("a").asDouble() : 0.0;
                    double bidQty = data.has("B") ? data.get("B").asDouble() : 0.0;
                    double askQty = data.has("A") ? data.get("A").asDouble() : 0.0;

                    NBBO nbbo = NBBO.newBuilder()
                            .setSymbol(symbol)
                            .setBidPrice(bidPrice)
                            .setBidQuantity(bidQty)
                            .setAskPrice(askPrice)
                            .setAskQuantity(askQty)
                            .build();

                    StreamNBBOResponse response = StreamNBBOResponse.newBuilder()
                            .addNbboList(nbbo)
                            .build();

                    ((StreamObserver<StreamNBBOResponse>) responseObserver).onNext(response);
                }
            } catch (Exception e) {
                logger.warning("Error converting WebSocket message to gRPC: " + e.getMessage());
            }
        }
    }

    protected void processAvgPriceUpdateGrpc(JsonNode data) {
        if (responseType.equals(PriceResponse.class)) {
            try {
                // Skip subscription confirmation messages
                if (!data.has("result") && data.has("s")) {
                    String symbol = data.get("s").asText();

                    long eventTime = data.has("E") ? data.get("E").asLong() : 0;
                    long lastTradeTime = data.has("T") ? data.get("T").asLong() : 0;
                    double avgPrice = data.has("w") ? data.get("w").asDouble() : 0.0;
                    int averagePriceIntervalMins = 0;
                    if (data.has("i")) {
                        String intervalString = data.get("i").asText();
                        if (intervalString.endsWith("m")) {
                            try {
                                averagePriceIntervalMins = Integer.parseInt(intervalString.substring(0, intervalString.length() - 1));
                            } catch (NumberFormatException e) {
                                logger.warning("Failed to parse interval: " + intervalString);
                            }
                        }
                    }

                    CoinPrice coinPrice = CoinPrice.newBuilder()
                            .setSymbol(symbol)
                            .setPrice(avgPrice)
                            .setLastTradeTime(lastTradeTime)
                            .setEventTime(eventTime)
                            .setAveragePriceIntervalMins(averagePriceIntervalMins)
                            .build();

                    PriceResponse response = PriceResponse.newBuilder()
                            .addCoinPrices(coinPrice)
                            .build();

                    ((StreamObserver<PriceResponse>) responseObserver).onNext(response);
                }
            } catch (Exception e) {
                logger.warning("Error converting WebSocket message to gRPC: " + e.getMessage());
            }
        }
    }

    private void closeWebSocketGracefully() {
        if (webSocket != null) {
            logger.info("Closing WebSocket connection due to client cancellation");

            try {
                List<String> formattedParams = new ArrayList<>();
                for (String symbol : streamParams) {
                    formattedParams.add(symbol.toLowerCase() + "@" + streamType);
                }

                Map<String, Object> unsubscribeMessage = new HashMap<>();
                unsubscribeMessage.put("method", "UNSUBSCRIBE");
                unsubscribeMessage.put("params", formattedParams);
                unsubscribeMessage.put("id", 2);

                String unsubscribeJson = objectMapper.writeValueAsString(unsubscribeMessage);
                webSocket.send(unsubscribeJson);
                logger.info("Unsubscription request sent: " + unsubscribeJson);

                webSocket.close(1000, "Client cancelled request");
            } catch (Exception e) {
                logger.warning("Error during WebSocket cleanup: " + e.getMessage());
                webSocket.cancel();
            }
        }
    }
}
package com.tradeflux;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tradeflux.grpc.PriceResponse;
import io.grpc.stub.StreamObserver;
import okhttp3.*;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;
import java.util.logging.Logger;

public class BinanceConnector {
    private static final Logger logger = Logger.getLogger(BinanceConnector.class.getName());

    private static final String API_BASE_URL = "https://api.binance.com/api/v3";
    private static final String WS_BASE_URL = "wss://stream.binance.com:9443";

    private final OkHttpClient httpClient;
    private final ObjectMapper objectMapper;

    public BinanceConnector() {
        this.httpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();

        this.objectMapper = new ObjectMapper();
    }

    private String getRestApiUrl(String endpoint, Optional<HashMap<String, String>> params) {
        return buildUrl(API_BASE_URL, endpoint, params, Optional.empty());
    }

    private String getWebSocketUrl(String endpoint, Optional<HashMap<String, String>> params) {
        return buildUrl(WS_BASE_URL, endpoint, params, Optional.empty());
    }

    private String getDirectWebSocketUrl(String endpoint, String streamName) {
        return buildUrl(WS_BASE_URL, endpoint, Optional.empty(), Optional.of(streamName));
    }

    private String buildUrl(String baseUrl, String endpoint, Optional<HashMap<String, String>> params,
                            Optional<String> streamParams) {
        StringBuilder urlBuilder = new StringBuilder(baseUrl);
        urlBuilder.append(endpoint);

        // Add stream parameter for WebSocket
        if (streamParams.isPresent() && !streamParams.get().isEmpty()) {
            if (endpoint.endsWith("/")) {
                // Remove trailing slash if present
                urlBuilder.deleteCharAt(urlBuilder.length() - 1);
            }
            urlBuilder.append("/").append(streamParams.get());
        }

        // Add query parameters
        if (params.isPresent() && !params.get().isEmpty()) {
            boolean hasQuestionMark = urlBuilder.toString().contains("?");
            urlBuilder.append(hasQuestionMark ? "&" : "?");

            int count = 0;
            for (Map.Entry<String, String> entry : params.get().entrySet()) {
                if (count > 0) {
                    urlBuilder.append("&");
                }
                urlBuilder.append(entry.getKey())
                        .append("=")
                        .append(entry.getValue());
                count++;
            }
        }

        return urlBuilder.toString();
    }

    public JsonNode makeRESTApiRequest(String endpoint, Optional<HashMap<String, String>> params) throws IOException {

        String url = getRestApiUrl(endpoint, params);
        logger.info("Making request to: " + url);

        Request request = new Request.Builder()
                .url(url)
                .build();

        try (
                Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                logger.warning("API request failed with code: " + response.code() + " - " + url);
                return null;
            }

            String responseBody = response.body().string();
            return objectMapper.readTree(responseBody);
        }
    }

    public WebSocket connectToWSStream(String symbol, String streamType) throws IOException {
        String url = getDirectWebSocketUrl("/ws", symbol + "@" + streamType);
        logger.info("Establishing WebSocket connection to: " + url);

        Request request = new Request.Builder()
                .url(url)
                .build();

        WebSocketListener listener = new BinanceWebsocketListener(symbol + "@" + streamType);
        WebSocket webSocket = httpClient.newWebSocket(request, listener);
        return webSocket;
    }

    public <T> WebSocket connectToWSStreamGRPC(String symbol, String streamType, StreamObserver<T> responseObserver, Class<T> responseType) throws IOException {
        String url = getDirectWebSocketUrl("/ws", symbol + "@" + streamType);
        logger.info("Establishing WebSocket connection to: " + url);

        Request request = new Request.Builder()
                .url(url)
                .build();

        WebSocketListener listener = new BinanceWebsocketListener<>(responseObserver, responseType, symbol + "@" + streamType);
        WebSocket webSocket = httpClient.newWebSocket(request, listener);
        return webSocket;
    }
}
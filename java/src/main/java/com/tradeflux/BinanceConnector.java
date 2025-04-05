package com.tradeflux;

import com.tradeflux.grpc.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import okio.ByteString;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BinanceConnector {
    private static final Logger logger = Logger.getLogger(BinanceConnector.class.getName());

    private static final String API_BASE_URL = "https://api.binance.com/api/v3";
    private static final String WS_BASE_URL = "wss://stream.binance.com:9443/ws";

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

    public JsonNode makeApiRequest(String endpoint, HashMap<String, String> params) throws IOException {
        StringBuilder urlBuilder = new StringBuilder(API_BASE_URL);
        urlBuilder.append(endpoint);

        if (params != null && !params.isEmpty()) {
            boolean hasQuestionMark = urlBuilder.toString().contains("?");
            urlBuilder.append(hasQuestionMark ? "&" : "?");

            int count = 0;
            for (Map.Entry<String, String> entry : params.entrySet()) {
                if (count > 0) {
                    urlBuilder.append("&");
                }
                urlBuilder.append(entry.getKey())
                        .append("=")
                        .append(entry.getValue());
                count++;
            }
        }

        String url = urlBuilder.toString();
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
            System.out.println(responseBody);
            return objectMapper.readTree(responseBody);
        }
    }
}
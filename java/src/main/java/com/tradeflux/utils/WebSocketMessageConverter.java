package com.tradeflux.utils;

import com.fasterxml.jackson.databind.JsonNode;

public interface WebSocketMessageConverter<T> {
    T convert(JsonNode data);

    boolean shouldProcess(JsonNode data);
}


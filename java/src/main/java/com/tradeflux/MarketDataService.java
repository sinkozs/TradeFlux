package com.tradeflux;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import okhttp3.WebSocket;
import java.util.*;

public class MarketDataService {
    private BinanceConnector connector = new BinanceConnector();

    private Optional getSymbolsRequestParams(List<String> symbols) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, String> params = new HashMap<>();

        params.put("symbols", mapper.writeValueAsString(symbols));
        return Optional.of(params);
    }

    private List<Map<String, Object>> parseCoinPricesFromJson(JsonNode apiResponse) {
        List<Map<String, Object>> response = new ArrayList<>();
        for (JsonNode symbolNode : apiResponse) {
            Map<String, Object> coinInfo = new HashMap<>();
            if (symbolNode.has("symbol")) {
                String symbol = symbolNode.get("symbol").asText();
                coinInfo.put("symbol", symbol);
            }
            if (symbolNode.has("price")) {
                Double price = symbolNode.get("price").asDouble();
                coinInfo.put("price", price);
            }
            response.add(coinInfo);
        }
        return response;
    }

    public List<Map<String, Object>> getAvailableCoinsAPI() throws IOException {
        /**
         * Example response: [{symbol=ETHBTC, orderTypes=[LIMIT, LIMIT_MAKER, MARKET, STOP_LOSS, STOP_LOSS_LIMIT, TAKE_PROFIT, TAKE_PROFIT_LIMIT],
         * baseAsset=ETH, isMarginTradingAllowed=true, quoteAsset=BTC},
         * {symbol=LTCBTC, orderTypes=[LIMIT, LIMIT_MAKER, MARKET, STOP_LOSS, STOP_LOSS_LIMIT, TAKE_PROFIT, TAKE_PROFIT_LIMIT],
         * baseAsset=LTC, isMarginTradingAllowed=true, quoteAsset=BTC}]
         * **/

        List<Map<String, Object>> response = new ArrayList<>();
        JsonNode apiResponse = connector.makeRESTApiRequest("/exchangeInfo", Optional.empty());
        JsonNode symbolsNode = apiResponse.get("symbols");

        if (symbolsNode != null && symbolsNode.isArray()) {
            for (JsonNode symbolNode : symbolsNode) {
                Map<String, Object> coinInfo = new HashMap<>();
                if (symbolNode.has("symbol")) {
                    String symbol = symbolNode.get("symbol").asText();
                    coinInfo.put("symbol", symbol);
                }
                if (symbolNode.has("baseAsset")) {
                    String baseAsset = symbolNode.get("baseAsset").asText();
                    coinInfo.put("baseAsset", baseAsset);
                }
                if (symbolNode.has("quoteAsset")) {
                    String quoteAsset = symbolNode.get("quoteAsset").asText();
                    coinInfo.put("quoteAsset", quoteAsset);
                }
                if (symbolNode.has("orderTypes")) {
                    JsonNode orderTypesNode = symbolNode.get("orderTypes");
                    List<String> orderTypes = new ArrayList<>();
                    if (orderTypesNode.isArray()) {
                        for (JsonNode orderTypeNode : orderTypesNode) {
                            orderTypes.add(orderTypeNode.asText());
                        }
                    }
                    if (symbolNode.has("isMarginTradingAllowed")) {
                        boolean isMarginTradingAllowed = symbolNode.get("isMarginTradingAllowed").asBoolean();
                        coinInfo.put("isMarginTradingAllowed", isMarginTradingAllowed);
                    }
                    coinInfo.put("orderTypes", orderTypes);
                }
                response.add(coinInfo);
            }
        }
        return response;
    }

    public List<Map<String, Object>> getCurrentPriceAPI(CoinRequestBuilder requestBuilder) throws IOException {
        HashMap<String, String> params = requestBuilder.buildParams();
        JsonNode apiResponse = connector.makeRESTApiRequest("/ticker/price", Optional.of(params));

        return parseCoinPricesFromJson(apiResponse);
    }

    public List<Map<String, Object>> getHistoricalOHLCDataAPI(OHLCRequestBuilder requestBuilder) throws IOException {
        List<Map<String, Object>> response = new ArrayList<>();
        HashMap<String, String> params = requestBuilder.buildParams();

        JsonNode apiResponse = connector.makeRESTApiRequest("/klines", Optional.of(params));

        for (JsonNode candleNode : apiResponse) {
            if (candleNode.isArray()) {
                Map<String, Object> ohlcData = new HashMap<>();

                ohlcData.put("klineOpenTime", candleNode.get(0).asLong());
                ohlcData.put("openPrice", candleNode.get(1).asText());
                ohlcData.put("highPrice", candleNode.get(2).asText());
                ohlcData.put("lowPrice", candleNode.get(3).asText());
                ohlcData.put("closePrice", candleNode.get(4).asText());
                ohlcData.put("volume", candleNode.get(5).asText());
                ohlcData.put("klineCloseTime", candleNode.get(6).asLong());
                ohlcData.put("quoteAssetVolume", candleNode.get(7).asText());
                ohlcData.put("numberOfTrades", candleNode.get(8).asInt());
                ohlcData.put("takerBuyBaseAssetVolume", candleNode.get(9).asText());
                ohlcData.put("takerBuyQuoteAssetVolume", candleNode.get(10).asText());

                response.add(ohlcData);
            }
        }
        return response;
    }

//    public List<Map<String, Object>> streamPriceUpdatesWS(String symbol) throws IOException {
//        WebSocket ws = connector.connectToWSStream(symbol, "bookTicker");
//
//    }


    ///  Builders
    public static class CoinRequestBuilder {
        private final List<String> symbols;

        public CoinRequestBuilder(List<String> symbols) {
            this.symbols = symbols;
        }

        public HashMap<String, String> buildParams() throws JsonProcessingException {
            ObjectMapper mapper = new ObjectMapper();
            HashMap<String, String> params = new HashMap<>();

            params.put("symbols", mapper.writeValueAsString(symbols));
            return params;
        }

    }

    public static class OHLCRequestBuilder {
        private final String symbol;
        private final String interval;

        private Integer limit = 100;
        private Integer timezone = null;
        private Long startTime = null;
        private Long endTime = null;

        public OHLCRequestBuilder(String symbol, String interval) {
            this.symbol = symbol;
            this.interval = interval;
        }

        public OHLCRequestBuilder limit(Integer limit) {
            this.limit = limit;
            return this;
        }

        public OHLCRequestBuilder timezone(Integer timezone) {
            this.timezone = timezone;
            return this;
        }

        public OHLCRequestBuilder startTime(Long startTime) {
            this.startTime = startTime;
            return this;
        }

        public OHLCRequestBuilder endTime(Long endTime) {
            this.endTime = endTime;
            return this;
        }

        public HashMap<String, String> buildParams() {
            HashMap<String, String> params = new HashMap<>();
            params.put("symbol", symbol);
            params.put("interval", interval);

            if (limit != null) {
                params.put("limit", String.valueOf(limit));
            }

            if (timezone != null) {
                params.put("timeZone", String.valueOf(timezone));
            }

            if (startTime != null) {
                params.put("startTime", String.valueOf(startTime));
            }

            if (endTime != null) {
                params.put("endTime", String.valueOf(endTime));
            }

            return params;
        }
    }
}

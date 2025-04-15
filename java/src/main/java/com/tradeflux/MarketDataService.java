package com.tradeflux;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import com.tradeflux.grpc.CoinRequest;
import com.tradeflux.grpc.HistoricalOHLCRequest;
import com.tradeflux.grpc.PriceResponse;
import com.tradeflux.grpc.StreamNBBOResponse;
import io.grpc.stub.StreamObserver;
import okhttp3.WebSocket;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

import static com.tradeflux.util.IntervalConverter.intervalToString;

public class MarketDataService {
    private BinanceConnector connector = new BinanceConnector();
    private static final Logger logger = Logger.getLogger(MarketDataService.class.getName());
    private static final Map<StreamObserver<?>, List<WebSocket>> symbolConnections = new ConcurrentHashMap<>();


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

    public List<Map<String, Object>> getCurrentPriceAPI(CoinRequestToAPIParamsBuilder requestBuilder) throws IOException {
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

    public <T> void connectToWSStreams(List<String> symbols, StreamObserver<T> responseObserver, Class<T> responseType) {
        if (symbols == null || symbols.isEmpty()) {
            logger.warning("No symbols provided for price updates");
            return;
        }

        try {
            if (responseType.equals(PriceResponse.class)) {
                logger.info("Subscribing for price updates, symbols: " + symbols);
                connector.connectToMultipleWSStreams(symbols, "avgPrice", responseObserver, responseType);
            } else if (responseType.equals(StreamNBBOResponse.class)) {
                logger.info("Subscribing for NBBO stream, symbols: " + symbols);
                connector.connectToMultipleWSStreams(symbols, "bookTicker", responseObserver, responseType);
            } else {
                logger.warning("Invalid response type: " + responseType);
            }
        } catch (IOException e) {
            logger.warning("Failed to connect for symbols: " + symbols);
        }
    }


    ///  Builders
    public static class CoinRequestToAPIParamsBuilder {
        private final CoinRequest request;

        public CoinRequestToAPIParamsBuilder(CoinRequest request) {
            this.request = request;
        }

        public HashMap<String, String> buildParams() throws JsonProcessingException {
            ObjectMapper mapper = new ObjectMapper();
            HashMap<String, String> params = new HashMap<>();

            params.put("symbols", mapper.writeValueAsString(request.getSymbolsList()));
            return params;
        }

    }

    public static class OHLCRequestBuilder {
        private final HistoricalOHLCRequest request;

        public OHLCRequestBuilder(HistoricalOHLCRequest request) {
            this.request = request;
        }

        public HashMap<String, String> buildParams() {
            HashMap<String, String> params = new HashMap<>();
            params.put("symbol", request.getSymbol());
            params.put("interval", intervalToString(request.getInterval()));

            if (request.hasLimit()) {
                params.put("limit", String.valueOf(request.getLimit()));
            }

            if (request.hasTimezone()) {
                params.put("timeZone", request.getTimezone());
            }

            if (request.hasStartTime()) {
                params.put("startTime", String.valueOf(request.getStartTime()));
            }

            if (request.hasEndTime()) {
                params.put("endTime", String.valueOf(request.getEndTime()));
            }

            return params;
        }
    }
}

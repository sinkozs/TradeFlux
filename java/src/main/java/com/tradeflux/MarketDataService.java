package com.tradeflux;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.*;

public class MarketDataService {
    private BinanceConnector connector = new BinanceConnector();

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

    public List<Map<String, Object>> getCurrentPriceAPI(List<String> symbols) throws IOException {
        List<Map<String, Object>> response = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, String> params = new HashMap<>();

        params.put("symbols", mapper.writeValueAsString(symbols));

        JsonNode apiResponse = connector.makeRESTApiRequest("/ticker/price", Optional.of(params));

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
}

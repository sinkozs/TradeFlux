package com.tradeflux;

import com.tradeflux.grpc.*;
import com.tradeflux.grpc.MarketDataServiceGrpc.MarketDataServiceImplBase;
import io.grpc.stub.StreamObserver;

import com.tradeflux.util.OrderTypeConverter;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class MarketDataProcessorGrpc extends MarketDataServiceImplBase {

    private static final Logger logger = Logger.getLogger(MarketDataProcessorGrpc.class.getName());
    private final MarketDataService marketDataService;

    public MarketDataProcessorGrpc(MarketDataService marketDataService) {
        this.marketDataService = marketDataService;
    }

    @Override
    public void getAvailableCoins(EmptyRequest request, StreamObserver<CoinListResponse> responseObserver) {
        try {
            logger.info("Received request for available coins");
            List<Map<String, Object>> coinData = marketDataService.getAvailableCoinsAPI();
            CoinListResponse.Builder responseBuilder = CoinListResponse.newBuilder();

            for (Map<String, Object> coin : coinData) {
                CoinInfo.Builder coinInfoBuilder = CoinInfo.newBuilder();

                if (coin.containsKey("symbol")) {
                    coinInfoBuilder.setSymbol(String.valueOf(coin.get("symbol")));
                }

                if (coin.containsKey("baseAsset")) {
                    coinInfoBuilder.setBaseAsset(String.valueOf(coin.get("baseAsset")));
                }

                if (coin.containsKey("quoteAsset")) {
                    coinInfoBuilder.setQuoteAsset(String.valueOf(coin.get("quoteAsset")));
                }

                if (coin.containsKey("isMarginTradingAllowed")) {
                    boolean isMarginAllowed = Boolean.parseBoolean(String.valueOf(coin.get("isMarginTradingAllowed")));
                    coinInfoBuilder.setIsMarginTradingAllowed(isMarginAllowed);
                }

                if (coin.containsKey("orderTypes") && coin.get("orderTypes") instanceof List) {
                    @SuppressWarnings("unchecked")
                    List<String> orderTypesList = (List<String>) coin.get("orderTypes");

                    List<OrderTypes> protoOrderTypes = OrderTypeConverter.convertToProtoOrderTypes(orderTypesList);
                    coinInfoBuilder.addAllOrderTypes(protoOrderTypes);
                }
                logger.info("COINS" + coinInfoBuilder.build());
                responseBuilder.addCoins(coinInfoBuilder.build());
            }

            responseObserver.onNext(responseBuilder.build());
            responseObserver.onCompleted();

            logger.info("Sent response with " + coinData.size() + " coins");
        } catch (Exception e) {
            logger.severe("Error in getAvailableCoins: " + e.getMessage());
            responseObserver.onError(
                    io.grpc.Status.INTERNAL
                            .withDescription("Internal server error: " + e.getMessage())
                            .asRuntimeException()
            );
        }
    }

    @Override
    public void getCurrentPrice(CoinRequest request, StreamObserver<PriceResponse> responseObserver) {
        try {
            logger.info("Received request for current price");

            MarketDataService.CoinRequestToAPIParamsBuilder builder = new MarketDataService.CoinRequestToAPIParamsBuilder(request);

            List<Map<String, Object>> priceData = marketDataService.getCurrentPriceAPI(builder);

            PriceResponse.Builder responseBuilder = PriceResponse.newBuilder();
            for (Map<String, Object> coin : priceData) {
                CoinPrice.Builder coinPriceBuilder = CoinPrice.newBuilder();

                // mandatory fields
                coinPriceBuilder.setSymbol(String.valueOf(coin.get("symbol")));
                coinPriceBuilder.setPrice(Double.parseDouble(String.valueOf(coin.get("price"))));

                // optional fields
                if (coin.containsKey("AveragePriceIntervalMins")) {
                    coinPriceBuilder.setAveragePriceIntervalMins(Integer.valueOf(String.valueOf(coin.get("AveragePriceIntervalMins"))));
                }

                if (coin.containsKey("LastTradeTime")) {
                    coinPriceBuilder.setLastTradeTime(Integer.valueOf(String.valueOf(coin.get("LastTradeTime"))));
                }

                responseBuilder.addCoinPrices(coinPriceBuilder.build());
            }

            responseObserver.onNext(responseBuilder.build());
            responseObserver.onCompleted();

        } catch (Exception e) {
            logger.severe("Error in getCurrentPrice: " + e.getMessage());
            responseObserver.onError(
                    io.grpc.Status.INTERNAL
                            .withDescription("Internal server error: " + e.getMessage())
                            .asRuntimeException()
            );
        }
    }

    @Override
    public void getHistoricalOHLCData(HistoricalOHLCRequest request, StreamObserver<HistoricalOHLCResponse> responseObserver) {
        try {
            logger.info("Received request for historical OHLC data");

            MarketDataService.OHLCRequestBuilder builder = new MarketDataService.OHLCRequestBuilder(request);
            List<Map<String, Object>> historicalOHLCData = marketDataService.getHistoricalOHLCDataAPI(builder);

            HistoricalOHLCResponse.Builder responseBuilder = HistoricalOHLCResponse.newBuilder();

            for (Map<String, Object> data : historicalOHLCData) {
                OHLCData.Builder ohlcDataBuilder = OHLCData.newBuilder();

                ohlcDataBuilder.setKlineOpenTime(Long.parseLong(String.valueOf(data.get("klineOpenTime"))));
                ohlcDataBuilder.setOpenPrice(Double.parseDouble(String.valueOf(data.get("openPrice"))));
                ohlcDataBuilder.setHighPrice(Double.parseDouble(String.valueOf(data.get("highPrice"))));
                ohlcDataBuilder.setLowPrice(Double.parseDouble(String.valueOf(data.get("lowPrice"))));
                ohlcDataBuilder.setVolume(Double.parseDouble(String.valueOf(data.get("volume"))));
                ohlcDataBuilder.setKlineCloseTime(Long.parseLong(String.valueOf(data.get("klineCloseTime"))));
                ohlcDataBuilder.setQuoteAssetVolume(Double.parseDouble(String.valueOf(data.get("quoteAssetVolume"))));
                ohlcDataBuilder.setNumberOfTrades(Long.parseLong(String.valueOf(data.get("numberOfTrades"))));
                ohlcDataBuilder.setTakerBuyAssetVol(Double.parseDouble(String.valueOf(data.get("takerBuyBaseAssetVolume"))));
                ohlcDataBuilder.setTakerBuyQuoteAssetVol(Double.parseDouble(String.valueOf(data.get("takerBuyQuoteAssetVolume"))));

                responseBuilder.addOHLCData(ohlcDataBuilder.build());
            }

            responseObserver.onNext(responseBuilder.build());
            responseObserver.onCompleted();

        } catch (Exception e) {
            logger.severe("Error in getHistoricalOHLCData: " + e.getMessage());
            responseObserver.onError(
                    io.grpc.Status.INTERNAL
                            .withDescription("Internal server error: " + e.getMessage())
                            .asRuntimeException()
            );
        }
    }
}
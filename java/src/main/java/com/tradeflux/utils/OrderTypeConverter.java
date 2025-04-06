package com.tradeflux.util;

import com.tradeflux.grpc.OrderTypes;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class OrderTypeConverter {
    private static final Logger logger = Logger.getLogger(OrderTypeConverter.class.getName());

    public static List<OrderTypes> convertToProtoOrderTypes(List<String> orderTypeStrings) {
        List<OrderTypes> protoOrderTypes = new ArrayList<>();

        if (orderTypeStrings == null || orderTypeStrings.isEmpty()) {
            return protoOrderTypes;
        }

        for (String orderType : orderTypeStrings) {
            OrderTypes protoOrderType = stringToOrderType(orderType);
            protoOrderTypes.add(protoOrderType);
        }

        return protoOrderTypes;
    }


    public static OrderTypes stringToOrderType(String orderTypeString) {
        if (orderTypeString == null || orderTypeString.isEmpty()) {
            return OrderTypes.ORDER_TYPE_UNKNOWN;
        }

        try {
            switch (orderTypeString.toUpperCase()) {
                case "LIMIT":
                    return OrderTypes.LIMIT;
                case "MARKET":
                    return OrderTypes.MARKET;
                case "STOP_LOSS":
                    return OrderTypes.STOP_LOSS;
                case "STOP_LOSS_LIMIT":
                    return OrderTypes.STOP_LOSS_LIMIT;
                case "TAKE_PROFIT":
                    return OrderTypes.TAKE_PROFIT;
                case "TAKE_PROFIT_LIMIT":
                    return OrderTypes.TAKE_PROFIT_LIMIT;
                case "LIMIT_MAKER":
                    return OrderTypes.LIMIT_MAKER;
                default:
                    logger.warning("Unknown order type: " + orderTypeString);
                    return OrderTypes.ORDER_TYPE_UNKNOWN;
            }
        } catch (Exception e) {
            logger.warning("Error processing order type: " + orderTypeString + ", error: " + e.getMessage());
            return OrderTypes.ORDER_TYPE_UNKNOWN;
        }
    }


    public static String orderTypeToString(OrderTypes orderType) {
        switch (orderType) {
            case LIMIT:
                return "LIMIT";
            case MARKET:
                return "MARKET";
            case STOP_LOSS:
                return "STOP_LOSS";
            case STOP_LOSS_LIMIT:
                return "STOP_LOSS_LIMIT";
            case TAKE_PROFIT:
                return "TAKE_PROFIT";
            case TAKE_PROFIT_LIMIT:
                return "TAKE_PROFIT_LIMIT";
            case LIMIT_MAKER:
                return "LIMIT_MAKER";
            case ORDER_TYPE_UNKNOWN:
            default:
                return "UNKNOWN";
        }
    }
}
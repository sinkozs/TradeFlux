package com.tradeflux.util;

import com.tradeflux.grpc.Interval;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class IntervalConverter {
    private static final Logger logger = Logger.getLogger(IntervalConverter.class.getName());

    public static List<Interval> convertToProtoIntervals(List<String> intervalStrings) {
        List<Interval> protoIntervals = new ArrayList<>();

        if (intervalStrings == null || intervalStrings.isEmpty()) {
            return protoIntervals;
        }

        for (String interval : intervalStrings) {
            Interval protoInterval = stringToInterval(interval);
            protoIntervals.add(protoInterval);
        }

        return protoIntervals;
    }

    public static Interval stringToInterval(String intervalString) {
        if (intervalString == null || intervalString.isEmpty()) {
            return Interval.INTERVAL_UNKNOWN;
        }

        try {
            switch (intervalString.toLowerCase()) {
                case "1s":
                    return Interval.SEC_1;
                case "1m":
                    return Interval.MINUTE_1;
                case "3m":
                    return Interval.MINUTE_3;
                case "5m":
                    return Interval.MINUTE_5;
                case "15m":
                    return Interval.MINUTE_15;
                case "30m":
                    return Interval.MINUTE_30;
                case "1h":
                    return Interval.HOUR_1;
                case "2h":
                    return Interval.HOUR_2;
                case "4h":
                    return Interval.HOUR_4;
                case "6h":
                    return Interval.HOUR_6;
                case "8h":
                    return Interval.HOUR_8;
                case "12h":
                    return Interval.HOUR_12;
                case "1d":
                    return Interval.DAY_1;
                case "3d":
                    return Interval.DAY_3;
                case "1w":
                    return Interval.WEEK_1;
                case "1M":
                    return Interval.MONTH_1;
                default:
                    logger.warning("Unknown interval: " + intervalString);
                    return Interval.INTERVAL_UNKNOWN;
            }
        } catch (Exception e) {
            logger.warning("Error processing interval: " + intervalString + ", error: " + e.getMessage());
            return Interval.INTERVAL_UNKNOWN;
        }
    }

    public static String intervalToString(Interval interval) {
        switch (interval) {
            case SEC_1:
                return "1s";
            case MINUTE_1:
                return "1m";
            case MINUTE_3:
                return "3m";
            case MINUTE_5:
                return "5m";
            case MINUTE_15:
                return "15m";
            case MINUTE_30:
                return "30m";
            case HOUR_1:
                return "1h";
            case HOUR_2:
                return "2h";
            case HOUR_4:
                return "4h";
            case HOUR_6:
                return "6h";
            case HOUR_8:
                return "8h";
            case HOUR_12:
                return "12h";
            case DAY_1:
                return "1d";
            case DAY_3:
                return "3d";
            case WEEK_1:
                return "1w";
            case MONTH_1:
                return "1M";
            case INTERVAL_UNKNOWN:
            default:
                return "UNKNOWN";
        }
    }
}
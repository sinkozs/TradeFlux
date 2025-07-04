// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: grpc/tradeflux.proto

package com.tradeflux.grpc;

/**
 * Protobuf enum {@code tradeflux.OrderTypes}
 */
public enum OrderTypes
    implements com.google.protobuf.ProtocolMessageEnum {
  /**
   * <code>ORDER_TYPE_UNKNOWN = 0;</code>
   */
  ORDER_TYPE_UNKNOWN(0),
  /**
   * <code>LIMIT = 1;</code>
   */
  LIMIT(1),
  /**
   * <code>MARKET = 2;</code>
   */
  MARKET(2),
  /**
   * <code>STOP_LOSS = 3;</code>
   */
  STOP_LOSS(3),
  /**
   * <code>STOP_LOSS_LIMIT = 4;</code>
   */
  STOP_LOSS_LIMIT(4),
  /**
   * <code>TAKE_PROFIT = 5;</code>
   */
  TAKE_PROFIT(5),
  /**
   * <code>TAKE_PROFIT_LIMIT = 6;</code>
   */
  TAKE_PROFIT_LIMIT(6),
  /**
   * <code>LIMIT_MAKER = 7;</code>
   */
  LIMIT_MAKER(7),
  UNRECOGNIZED(-1),
  ;

  /**
   * <code>ORDER_TYPE_UNKNOWN = 0;</code>
   */
  public static final int ORDER_TYPE_UNKNOWN_VALUE = 0;
  /**
   * <code>LIMIT = 1;</code>
   */
  public static final int LIMIT_VALUE = 1;
  /**
   * <code>MARKET = 2;</code>
   */
  public static final int MARKET_VALUE = 2;
  /**
   * <code>STOP_LOSS = 3;</code>
   */
  public static final int STOP_LOSS_VALUE = 3;
  /**
   * <code>STOP_LOSS_LIMIT = 4;</code>
   */
  public static final int STOP_LOSS_LIMIT_VALUE = 4;
  /**
   * <code>TAKE_PROFIT = 5;</code>
   */
  public static final int TAKE_PROFIT_VALUE = 5;
  /**
   * <code>TAKE_PROFIT_LIMIT = 6;</code>
   */
  public static final int TAKE_PROFIT_LIMIT_VALUE = 6;
  /**
   * <code>LIMIT_MAKER = 7;</code>
   */
  public static final int LIMIT_MAKER_VALUE = 7;


  public final int getNumber() {
    if (this == UNRECOGNIZED) {
      throw new java.lang.IllegalArgumentException(
          "Can't get the number of an unknown enum value.");
    }
    return value;
  }

  /**
   * @param value The numeric wire value of the corresponding enum entry.
   * @return The enum associated with the given numeric wire value.
   * @deprecated Use {@link #forNumber(int)} instead.
   */
  @java.lang.Deprecated
  public static OrderTypes valueOf(int value) {
    return forNumber(value);
  }

  /**
   * @param value The numeric wire value of the corresponding enum entry.
   * @return The enum associated with the given numeric wire value.
   */
  public static OrderTypes forNumber(int value) {
    switch (value) {
      case 0: return ORDER_TYPE_UNKNOWN;
      case 1: return LIMIT;
      case 2: return MARKET;
      case 3: return STOP_LOSS;
      case 4: return STOP_LOSS_LIMIT;
      case 5: return TAKE_PROFIT;
      case 6: return TAKE_PROFIT_LIMIT;
      case 7: return LIMIT_MAKER;
      default: return null;
    }
  }

  public static com.google.protobuf.Internal.EnumLiteMap<OrderTypes>
      internalGetValueMap() {
    return internalValueMap;
  }
  private static final com.google.protobuf.Internal.EnumLiteMap<
      OrderTypes> internalValueMap =
        new com.google.protobuf.Internal.EnumLiteMap<OrderTypes>() {
          public OrderTypes findValueByNumber(int number) {
            return OrderTypes.forNumber(number);
          }
        };

  public final com.google.protobuf.Descriptors.EnumValueDescriptor
      getValueDescriptor() {
    if (this == UNRECOGNIZED) {
      throw new java.lang.IllegalStateException(
          "Can't get the descriptor of an unrecognized enum value.");
    }
    return getDescriptor().getValues().get(ordinal());
  }
  public final com.google.protobuf.Descriptors.EnumDescriptor
      getDescriptorForType() {
    return getDescriptor();
  }
  public static final com.google.protobuf.Descriptors.EnumDescriptor
      getDescriptor() {
    return com.tradeflux.grpc.Tradeflux.getDescriptor().getEnumTypes().get(2);
  }

  private static final OrderTypes[] VALUES = values();

  public static OrderTypes valueOf(
      com.google.protobuf.Descriptors.EnumValueDescriptor desc) {
    if (desc.getType() != getDescriptor()) {
      throw new java.lang.IllegalArgumentException(
        "EnumValueDescriptor is not for this type.");
    }
    if (desc.getIndex() == -1) {
      return UNRECOGNIZED;
    }
    return VALUES[desc.getIndex()];
  }

  private final int value;

  private OrderTypes(int value) {
    this.value = value;
  }

  // @@protoc_insertion_point(enum_scope:tradeflux.OrderTypes)
}


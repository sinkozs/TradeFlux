package com.tradeflux.grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.60.0)",
    comments = "Source: grpc/tradeflux.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class MarketDataServiceGrpc {

  private MarketDataServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "tradeflux.MarketDataService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.tradeflux.grpc.EmptyRequest,
      com.tradeflux.grpc.CoinListResponse> getGetAvailableCoinsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetAvailableCoins",
      requestType = com.tradeflux.grpc.EmptyRequest.class,
      responseType = com.tradeflux.grpc.CoinListResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.tradeflux.grpc.EmptyRequest,
      com.tradeflux.grpc.CoinListResponse> getGetAvailableCoinsMethod() {
    io.grpc.MethodDescriptor<com.tradeflux.grpc.EmptyRequest, com.tradeflux.grpc.CoinListResponse> getGetAvailableCoinsMethod;
    if ((getGetAvailableCoinsMethod = MarketDataServiceGrpc.getGetAvailableCoinsMethod) == null) {
      synchronized (MarketDataServiceGrpc.class) {
        if ((getGetAvailableCoinsMethod = MarketDataServiceGrpc.getGetAvailableCoinsMethod) == null) {
          MarketDataServiceGrpc.getGetAvailableCoinsMethod = getGetAvailableCoinsMethod =
              io.grpc.MethodDescriptor.<com.tradeflux.grpc.EmptyRequest, com.tradeflux.grpc.CoinListResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetAvailableCoins"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.tradeflux.grpc.EmptyRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.tradeflux.grpc.CoinListResponse.getDefaultInstance()))
              .setSchemaDescriptor(new MarketDataServiceMethodDescriptorSupplier("GetAvailableCoins"))
              .build();
        }
      }
    }
    return getGetAvailableCoinsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.tradeflux.grpc.CoinRequest,
      com.tradeflux.grpc.PriceResponse> getGetCurrentPriceMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetCurrentPrice",
      requestType = com.tradeflux.grpc.CoinRequest.class,
      responseType = com.tradeflux.grpc.PriceResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.tradeflux.grpc.CoinRequest,
      com.tradeflux.grpc.PriceResponse> getGetCurrentPriceMethod() {
    io.grpc.MethodDescriptor<com.tradeflux.grpc.CoinRequest, com.tradeflux.grpc.PriceResponse> getGetCurrentPriceMethod;
    if ((getGetCurrentPriceMethod = MarketDataServiceGrpc.getGetCurrentPriceMethod) == null) {
      synchronized (MarketDataServiceGrpc.class) {
        if ((getGetCurrentPriceMethod = MarketDataServiceGrpc.getGetCurrentPriceMethod) == null) {
          MarketDataServiceGrpc.getGetCurrentPriceMethod = getGetCurrentPriceMethod =
              io.grpc.MethodDescriptor.<com.tradeflux.grpc.CoinRequest, com.tradeflux.grpc.PriceResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetCurrentPrice"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.tradeflux.grpc.CoinRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.tradeflux.grpc.PriceResponse.getDefaultInstance()))
              .setSchemaDescriptor(new MarketDataServiceMethodDescriptorSupplier("GetCurrentPrice"))
              .build();
        }
      }
    }
    return getGetCurrentPriceMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.tradeflux.grpc.HistoricalOHLCRequest,
      com.tradeflux.grpc.HistoricalOHLCResponse> getGetHistoricalOHLCDataMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetHistoricalOHLCData",
      requestType = com.tradeflux.grpc.HistoricalOHLCRequest.class,
      responseType = com.tradeflux.grpc.HistoricalOHLCResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.tradeflux.grpc.HistoricalOHLCRequest,
      com.tradeflux.grpc.HistoricalOHLCResponse> getGetHistoricalOHLCDataMethod() {
    io.grpc.MethodDescriptor<com.tradeflux.grpc.HistoricalOHLCRequest, com.tradeflux.grpc.HistoricalOHLCResponse> getGetHistoricalOHLCDataMethod;
    if ((getGetHistoricalOHLCDataMethod = MarketDataServiceGrpc.getGetHistoricalOHLCDataMethod) == null) {
      synchronized (MarketDataServiceGrpc.class) {
        if ((getGetHistoricalOHLCDataMethod = MarketDataServiceGrpc.getGetHistoricalOHLCDataMethod) == null) {
          MarketDataServiceGrpc.getGetHistoricalOHLCDataMethod = getGetHistoricalOHLCDataMethod =
              io.grpc.MethodDescriptor.<com.tradeflux.grpc.HistoricalOHLCRequest, com.tradeflux.grpc.HistoricalOHLCResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetHistoricalOHLCData"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.tradeflux.grpc.HistoricalOHLCRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.tradeflux.grpc.HistoricalOHLCResponse.getDefaultInstance()))
              .setSchemaDescriptor(new MarketDataServiceMethodDescriptorSupplier("GetHistoricalOHLCData"))
              .build();
        }
      }
    }
    return getGetHistoricalOHLCDataMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.tradeflux.grpc.CoinRequest,
      com.tradeflux.grpc.PriceResponse> getStreamAvgPriceMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "StreamAvgPrice",
      requestType = com.tradeflux.grpc.CoinRequest.class,
      responseType = com.tradeflux.grpc.PriceResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<com.tradeflux.grpc.CoinRequest,
      com.tradeflux.grpc.PriceResponse> getStreamAvgPriceMethod() {
    io.grpc.MethodDescriptor<com.tradeflux.grpc.CoinRequest, com.tradeflux.grpc.PriceResponse> getStreamAvgPriceMethod;
    if ((getStreamAvgPriceMethod = MarketDataServiceGrpc.getStreamAvgPriceMethod) == null) {
      synchronized (MarketDataServiceGrpc.class) {
        if ((getStreamAvgPriceMethod = MarketDataServiceGrpc.getStreamAvgPriceMethod) == null) {
          MarketDataServiceGrpc.getStreamAvgPriceMethod = getStreamAvgPriceMethod =
              io.grpc.MethodDescriptor.<com.tradeflux.grpc.CoinRequest, com.tradeflux.grpc.PriceResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "StreamAvgPrice"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.tradeflux.grpc.CoinRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.tradeflux.grpc.PriceResponse.getDefaultInstance()))
              .setSchemaDescriptor(new MarketDataServiceMethodDescriptorSupplier("StreamAvgPrice"))
              .build();
        }
      }
    }
    return getStreamAvgPriceMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.tradeflux.grpc.CoinRequest,
      com.tradeflux.grpc.StreamNBBOResponse> getStreamNBBOMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "StreamNBBO",
      requestType = com.tradeflux.grpc.CoinRequest.class,
      responseType = com.tradeflux.grpc.StreamNBBOResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<com.tradeflux.grpc.CoinRequest,
      com.tradeflux.grpc.StreamNBBOResponse> getStreamNBBOMethod() {
    io.grpc.MethodDescriptor<com.tradeflux.grpc.CoinRequest, com.tradeflux.grpc.StreamNBBOResponse> getStreamNBBOMethod;
    if ((getStreamNBBOMethod = MarketDataServiceGrpc.getStreamNBBOMethod) == null) {
      synchronized (MarketDataServiceGrpc.class) {
        if ((getStreamNBBOMethod = MarketDataServiceGrpc.getStreamNBBOMethod) == null) {
          MarketDataServiceGrpc.getStreamNBBOMethod = getStreamNBBOMethod =
              io.grpc.MethodDescriptor.<com.tradeflux.grpc.CoinRequest, com.tradeflux.grpc.StreamNBBOResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "StreamNBBO"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.tradeflux.grpc.CoinRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.tradeflux.grpc.StreamNBBOResponse.getDefaultInstance()))
              .setSchemaDescriptor(new MarketDataServiceMethodDescriptorSupplier("StreamNBBO"))
              .build();
        }
      }
    }
    return getStreamNBBOMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static MarketDataServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<MarketDataServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<MarketDataServiceStub>() {
        @java.lang.Override
        public MarketDataServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new MarketDataServiceStub(channel, callOptions);
        }
      };
    return MarketDataServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static MarketDataServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<MarketDataServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<MarketDataServiceBlockingStub>() {
        @java.lang.Override
        public MarketDataServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new MarketDataServiceBlockingStub(channel, callOptions);
        }
      };
    return MarketDataServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static MarketDataServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<MarketDataServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<MarketDataServiceFutureStub>() {
        @java.lang.Override
        public MarketDataServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new MarketDataServiceFutureStub(channel, callOptions);
        }
      };
    return MarketDataServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void getAvailableCoins(com.tradeflux.grpc.EmptyRequest request,
        io.grpc.stub.StreamObserver<com.tradeflux.grpc.CoinListResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetAvailableCoinsMethod(), responseObserver);
    }

    /**
     */
    default void getCurrentPrice(com.tradeflux.grpc.CoinRequest request,
        io.grpc.stub.StreamObserver<com.tradeflux.grpc.PriceResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetCurrentPriceMethod(), responseObserver);
    }

    /**
     */
    default void getHistoricalOHLCData(com.tradeflux.grpc.HistoricalOHLCRequest request,
        io.grpc.stub.StreamObserver<com.tradeflux.grpc.HistoricalOHLCResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetHistoricalOHLCDataMethod(), responseObserver);
    }

    /**
     */
    default void streamAvgPrice(com.tradeflux.grpc.CoinRequest request,
        io.grpc.stub.StreamObserver<com.tradeflux.grpc.PriceResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getStreamAvgPriceMethod(), responseObserver);
    }

    /**
     */
    default void streamNBBO(com.tradeflux.grpc.CoinRequest request,
        io.grpc.stub.StreamObserver<com.tradeflux.grpc.StreamNBBOResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getStreamNBBOMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service MarketDataService.
   */
  public static abstract class MarketDataServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return MarketDataServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service MarketDataService.
   */
  public static final class MarketDataServiceStub
      extends io.grpc.stub.AbstractAsyncStub<MarketDataServiceStub> {
    private MarketDataServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected MarketDataServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new MarketDataServiceStub(channel, callOptions);
    }

    /**
     */
    public void getAvailableCoins(com.tradeflux.grpc.EmptyRequest request,
        io.grpc.stub.StreamObserver<com.tradeflux.grpc.CoinListResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetAvailableCoinsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getCurrentPrice(com.tradeflux.grpc.CoinRequest request,
        io.grpc.stub.StreamObserver<com.tradeflux.grpc.PriceResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetCurrentPriceMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getHistoricalOHLCData(com.tradeflux.grpc.HistoricalOHLCRequest request,
        io.grpc.stub.StreamObserver<com.tradeflux.grpc.HistoricalOHLCResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetHistoricalOHLCDataMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void streamAvgPrice(com.tradeflux.grpc.CoinRequest request,
        io.grpc.stub.StreamObserver<com.tradeflux.grpc.PriceResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getStreamAvgPriceMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void streamNBBO(com.tradeflux.grpc.CoinRequest request,
        io.grpc.stub.StreamObserver<com.tradeflux.grpc.StreamNBBOResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getStreamNBBOMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service MarketDataService.
   */
  public static final class MarketDataServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<MarketDataServiceBlockingStub> {
    private MarketDataServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected MarketDataServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new MarketDataServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.tradeflux.grpc.CoinListResponse getAvailableCoins(com.tradeflux.grpc.EmptyRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetAvailableCoinsMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.tradeflux.grpc.PriceResponse getCurrentPrice(com.tradeflux.grpc.CoinRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetCurrentPriceMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.tradeflux.grpc.HistoricalOHLCResponse getHistoricalOHLCData(com.tradeflux.grpc.HistoricalOHLCRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetHistoricalOHLCDataMethod(), getCallOptions(), request);
    }

    /**
     */
    public java.util.Iterator<com.tradeflux.grpc.PriceResponse> streamAvgPrice(
        com.tradeflux.grpc.CoinRequest request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getStreamAvgPriceMethod(), getCallOptions(), request);
    }

    /**
     */
    public java.util.Iterator<com.tradeflux.grpc.StreamNBBOResponse> streamNBBO(
        com.tradeflux.grpc.CoinRequest request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getStreamNBBOMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service MarketDataService.
   */
  public static final class MarketDataServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<MarketDataServiceFutureStub> {
    private MarketDataServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected MarketDataServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new MarketDataServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.tradeflux.grpc.CoinListResponse> getAvailableCoins(
        com.tradeflux.grpc.EmptyRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetAvailableCoinsMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.tradeflux.grpc.PriceResponse> getCurrentPrice(
        com.tradeflux.grpc.CoinRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetCurrentPriceMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.tradeflux.grpc.HistoricalOHLCResponse> getHistoricalOHLCData(
        com.tradeflux.grpc.HistoricalOHLCRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetHistoricalOHLCDataMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_AVAILABLE_COINS = 0;
  private static final int METHODID_GET_CURRENT_PRICE = 1;
  private static final int METHODID_GET_HISTORICAL_OHLCDATA = 2;
  private static final int METHODID_STREAM_AVG_PRICE = 3;
  private static final int METHODID_STREAM_NBBO = 4;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_AVAILABLE_COINS:
          serviceImpl.getAvailableCoins((com.tradeflux.grpc.EmptyRequest) request,
              (io.grpc.stub.StreamObserver<com.tradeflux.grpc.CoinListResponse>) responseObserver);
          break;
        case METHODID_GET_CURRENT_PRICE:
          serviceImpl.getCurrentPrice((com.tradeflux.grpc.CoinRequest) request,
              (io.grpc.stub.StreamObserver<com.tradeflux.grpc.PriceResponse>) responseObserver);
          break;
        case METHODID_GET_HISTORICAL_OHLCDATA:
          serviceImpl.getHistoricalOHLCData((com.tradeflux.grpc.HistoricalOHLCRequest) request,
              (io.grpc.stub.StreamObserver<com.tradeflux.grpc.HistoricalOHLCResponse>) responseObserver);
          break;
        case METHODID_STREAM_AVG_PRICE:
          serviceImpl.streamAvgPrice((com.tradeflux.grpc.CoinRequest) request,
              (io.grpc.stub.StreamObserver<com.tradeflux.grpc.PriceResponse>) responseObserver);
          break;
        case METHODID_STREAM_NBBO:
          serviceImpl.streamNBBO((com.tradeflux.grpc.CoinRequest) request,
              (io.grpc.stub.StreamObserver<com.tradeflux.grpc.StreamNBBOResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getGetAvailableCoinsMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.tradeflux.grpc.EmptyRequest,
              com.tradeflux.grpc.CoinListResponse>(
                service, METHODID_GET_AVAILABLE_COINS)))
        .addMethod(
          getGetCurrentPriceMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.tradeflux.grpc.CoinRequest,
              com.tradeflux.grpc.PriceResponse>(
                service, METHODID_GET_CURRENT_PRICE)))
        .addMethod(
          getGetHistoricalOHLCDataMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.tradeflux.grpc.HistoricalOHLCRequest,
              com.tradeflux.grpc.HistoricalOHLCResponse>(
                service, METHODID_GET_HISTORICAL_OHLCDATA)))
        .addMethod(
          getStreamAvgPriceMethod(),
          io.grpc.stub.ServerCalls.asyncServerStreamingCall(
            new MethodHandlers<
              com.tradeflux.grpc.CoinRequest,
              com.tradeflux.grpc.PriceResponse>(
                service, METHODID_STREAM_AVG_PRICE)))
        .addMethod(
          getStreamNBBOMethod(),
          io.grpc.stub.ServerCalls.asyncServerStreamingCall(
            new MethodHandlers<
              com.tradeflux.grpc.CoinRequest,
              com.tradeflux.grpc.StreamNBBOResponse>(
                service, METHODID_STREAM_NBBO)))
        .build();
  }

  private static abstract class MarketDataServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    MarketDataServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.tradeflux.grpc.Tradeflux.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("MarketDataService");
    }
  }

  private static final class MarketDataServiceFileDescriptorSupplier
      extends MarketDataServiceBaseDescriptorSupplier {
    MarketDataServiceFileDescriptorSupplier() {}
  }

  private static final class MarketDataServiceMethodDescriptorSupplier
      extends MarketDataServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    MarketDataServiceMethodDescriptorSupplier(java.lang.String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (MarketDataServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new MarketDataServiceFileDescriptorSupplier())
              .addMethod(getGetAvailableCoinsMethod())
              .addMethod(getGetCurrentPriceMethod())
              .addMethod(getGetHistoricalOHLCDataMethod())
              .addMethod(getStreamAvgPriceMethod())
              .addMethod(getStreamNBBOMethod())
              .build();
        }
      }
    }
    return result;
  }
}

package com.tradeflux;

import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class MarketDataServerImpl implements MarketDataServer {
    private static final Logger logger = Logger.getLogger(MarketDataServerImpl.class.getName());
    private static final int DEFAULT_PORT = 50051;

    private Server server;
    private final int port;
    private final BindableService service;

    public MarketDataServerImpl(int port, BindableService service) {
        this.port = port;
        this.service = service;
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        int port = DEFAULT_PORT;
        if (args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                logger.warning("Invalid port number: " + args[0] + ". Using default port " + DEFAULT_PORT);
            }
        }

        MarketDataService marketDataService = new MarketDataService();
        final MarketDataServer server = new MarketDataServerImpl(port, new MarketDataProcessorGrpc(marketDataService));

        server.start();
        server.blockUntilShutdown();
    }

    public void start() throws IOException {
        server = ServerBuilder.forPort(port)
                .addService(service)
                .build()
                .start();
        logger.info("Server started, listening on " + port);

        // This JVM hook gets executed when the JVM is shutting down.
        // This ensures that the grpc server shuts down gracefully
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.err.println("Shutting down gRPC server since JVM is shutting down");
            try {
                MarketDataServerImpl.this.stop();
            } catch (InterruptedException e) {
                e.printStackTrace(System.err);
            }
            System.err.println("Server shut down");
        }));
    }

    public void stop() throws InterruptedException {
        logger.info("Closing all connections");
        if (service instanceof MarketDataService) {
            ((MarketDataService) service).closeAllConnections();
        }

        // Then shutdown the gRPC server
        if (server != null) {
            server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
        }
    }

    // Await termination on the main thread since the grpc library uses daemon threads
    public void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

}

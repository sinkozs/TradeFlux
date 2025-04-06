package com.tradeflux;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class MarketDataServer {
    private static final Logger logger = Logger.getLogger(MarketDataServer.class.getName());
    private static final int DEFAULT_PORT = 50051;

    private Server server;
    private final int port;

    public MarketDataServer(int port) {
        this.port = port;
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
        final MarketDataServer server = new MarketDataServer(port);
        server.start();
        server.blockUntilShutdown();
    }

    private void start() throws IOException {
        server = ServerBuilder.forPort(port)
                .build()
                .start();
        logger.info("Server started, listening on " + port);

        // This JVM hook gets executed when the JVM is shutting down.
        // This ensures that the gRPC server shuts down gracefully
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                System.err.println("Shutting down gRPC server since JVM is shutting down");
                try {
                    MarketDataServer.this.stop();
                } catch (InterruptedException e) {
                    e.printStackTrace(System.err);
                }
                System.err.println("Server shut down");
            }
        });
    }

    private void stop() throws InterruptedException {
        if (server != null) {
            server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
        }
    }

    // Await termination on the main thread since the grpc library uses daemon threads
    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

}

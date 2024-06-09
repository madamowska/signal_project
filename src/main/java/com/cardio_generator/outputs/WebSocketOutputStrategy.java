package com.cardio_generator.outputs;

import org.java_websocket.WebSocket;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

public class WebSocketOutputStrategy implements OutputStrategy {

    private WebSocketServer server;
    private List<WebSocket> connections;

    public WebSocketOutputStrategy(int port) {
        connections = new ArrayList<>();
        server = new SimpleWebSocketServer(new InetSocketAddress(port));
        System.out.println("WebSocket server created on port: " + port + ", listening for connections...");
        server.start();
    }

    @Override
    public void output(int patientId, long timestamp, String label, String data) {
        String message = String.format("%d,%d,%s,%s", patientId, timestamp, label, data);
        // Broadcast the message to all connected clients
        for (WebSocket conn : connections) {
            if (conn.isOpen()) {
                conn.send(message);
            } else {
                System.err.println("Connection is closed: " + conn.getRemoteSocketAddress());
                connections.remove(conn);
            }
        }
    }

    private class SimpleWebSocketServer extends WebSocketServer {

        public SimpleWebSocketServer(InetSocketAddress address) {
            super(address);
        }

        @Override
        public void onOpen(WebSocket conn, org.java_websocket.handshake.ClientHandshake handshake) {
            System.out.println("New connection: " + conn.getRemoteSocketAddress());
            ((WebSocketOutputStrategy) getOutputStrategy()).addConnection(conn);
        }

        @Override
        public void onClose(WebSocket conn, int code, String reason, boolean remote) {
            System.out.println("Closed connection: " + conn.getRemoteSocketAddress());
            ((WebSocketOutputStrategy) getOutputStrategy()).removeConnection(conn);
        }

        @Override
        public void onMessage(WebSocket conn, String message) {
            // Not used in this context
        }

        @Override
        public void onError(WebSocket conn, Exception ex) {
            ex.printStackTrace();
            System.err.println("Error on connection " + conn.getRemoteSocketAddress() + ": " + ex.getMessage());
            conn.close();
        }

        @Override
        public void onStart() {
            System.out.println("Server started successfully");
        }

    }

    public void addConnection(WebSocket conn) {
        connections.add(conn);
    }

    public void removeConnection(WebSocket conn) {
        connections.remove(conn);
    }

    public OutputStrategy getOutputStrategy() {
        return this;
    }
}

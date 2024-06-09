package com.cardio_generator.outputs;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

public class SignalWebSocketClient extends WebSocketClient {

    public SignalWebSocketClient(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        System.out.println("Connected to server");
    }

    @Override
    public void onMessage(String message) {
        System.out.println("Received: " + message);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("Disconnected from server: " + reason);
    }

    @Override
    public void onError(Exception ex) {
        ex.printStackTrace();
    }
    public static void main(String[] args) {
        try {
            URI serverUri = new URI("ws://localhost:8080");
            SignalWebSocketClient client = new SignalWebSocketClient(serverUri);
            client.connectBlocking(); // Synchronous connect
            System.out.println("WebSocket Client connected");

            // Keep the client running to receive messages
            Thread.sleep(60000); // Keep the client open for 60 seconds to receive messages
            client.close(); // Close the connection after usage

        } catch (URISyntaxException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

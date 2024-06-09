package com.data_management;


import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.io.IOException;

    public class WebSocketDataReader implements DataReader {
        private WebSocketClient client;

        @Override
        public void readData(DataStorage dataStorage) throws IOException {

        }

        @Override
        public void readData_websocket(DataStorage dataStorage, int port) throws IOException {
            try {
                URI uri = new URI("ws://localhost:" + port);
                client = new WebSocketClient(uri) {
                    @Override
                    public void onOpen(ServerHandshake handshake) {
                        System.out.println("Connected to server");
                    }

                    @Override
                    public void onMessage(String message) {
                        System.out.println("Received: " + message);
                        // Parse and store the data in dataStorage
                        String[] parts = message.split(",");
                        int patientId = Integer.parseInt(parts[0]);
                        long timestamp = Long.parseLong(parts[1]);
                        String label = parts[2];
                        long data = Long.parseLong(parts[3]);
                        dataStorage.addPatientData(patientId, timestamp, label, data);
                    }

                    @Override
                    public void onClose(int code, String reason, boolean remote) {
                        System.out.println("Disconnected from server: " + reason);
                    }

                    @Override
                    public void onError(Exception ex) {
                        ex.printStackTrace();
                    }
                };
                client.connectBlocking(); // Synchronous connect
                System.out.println("WebSocket Client connected");
            } catch (URISyntaxException | InterruptedException e) {
                throw new IOException("Error connecting to WebSocket server", e);
            }
        }

        public void stop() {
            if (client != null) {
                client.close();
            }
        }
    }




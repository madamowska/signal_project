package com.cardio_generator.outputs;

import com.data_management.DataReader;
import com.data_management.DataStorage;
import com.data_management.PatientRecord;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

public class SignalWebSocketClient extends WebSocketClient  {

    private DataStorage dataStorage;

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
        try {
            // Parse the message and update DataStorage
            String[] parts = message.split(",");
            if (parts.length != 4) {
                throw new IllegalArgumentException("Invalid message format");
            }
            int patientId = Integer.parseInt(parts[0]);
            double measurementValue = Double.parseDouble(parts[1]);
            String recordType = parts[2];
            long timestamp = Long.parseLong(parts[3]);
            //PatientRecord record = new PatientRecord(patientId, measurementValue, recordType, timestamp);
            dataStorage.addPatientData(patientId, measurementValue,recordType, timestamp);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to parse message: " + message);
        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("Disconnected from server: " + reason);
    }

    @Override
    public void onError(Exception ex) {
        //specific cases to be implemented
        ex.printStackTrace();
    }


    public void startReading(DataStorage dataStorage) {
        this.dataStorage = dataStorage;
        try {
            this.connectBlocking(); // Synchronous connect
            System.out.println("WebSocket Client connected");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void stopReading() {
        this.close(); // Close the connection
    }

    /*public static void main(String[] args) {
        try {
            URI serverUri = new URI("ws://localhost:12345");
            SignalWebSocketClient client = new SignalWebSocketClient(serverUri);
            DataStorage dataStorage = new DataStorage();
            client.startReading(dataStorage);

            // Keep the client running to receive messages
            Thread.sleep(60000); // Keep the client open for 60 seconds to receive messages
            client.stopReading(); // Close the connection after usage

        } catch (URISyntaxException | InterruptedException e) {
            e.printStackTrace();
        }
    }*/
}



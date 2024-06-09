package websocket;
import static org.junit.Assert.*;

import com.cardio_generator.outputs.SignalWebSocketClient;
import org.junit.Test;
import org.java_websocket.handshake.ServerHandshake;


import java.net.URI;
import java.net.URISyntaxException;


public class WebsocketClientTest {
    @Test
    public void testWebSocketClient() throws URISyntaxException, InterruptedException {
        final StringBuilder onOpenOutput = new StringBuilder();
        final StringBuilder onMessageOutput = new StringBuilder();
        final StringBuilder onCloseOutput = new StringBuilder();
        final StringBuilder onErrorOutput = new StringBuilder();

        SignalWebSocketClient client = new SignalWebSocketClient(new URI("ws://localhost:8080")) {
            @Override
            public void onOpen(ServerHandshake handshakedata) {
                onOpenOutput.append("Connected to server");
            }

            @Override
            public void onMessage(String message) {
                onMessageOutput.append("Received: ").append(message);
            }

            @Override
            public void onClose(int code, String reason, boolean remote) {
                onCloseOutput.append("Disconnected from server: ").append(reason);
            }

            @Override
            public void onError(Exception ex) {
                onErrorOutput.append("Error occurred: ").append(ex.getMessage());
            }
        };

        // Test connect and receive events
        client.connectBlocking();
        Thread.sleep(1000); // Wait for 1 second

        assertEquals("Connected to server", onOpenOutput.toString()); // Assert connection open event

        client.onMessage("Test message");
        assertEquals("Received: Test message", onMessageOutput.toString()); // Assert message received event

        client.onClose(1000, "Connection closed", true);
        assertEquals("Disconnected from server: Connection closed", onCloseOutput.toString()); // Assert connection closed event

        client.onError(new Exception("Test error"));
        assertEquals("Error occurred: Test error", onErrorOutput.toString()); // Assert error event

        client.close();
    }

}

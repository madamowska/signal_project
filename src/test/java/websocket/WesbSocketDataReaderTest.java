package websocket;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import com.cardio_generator.outputs.SignalWebSocketClient;
import com.data_management.DataStorage;
import com.data_management.PatientRecord;
import com.data_management.WebSocketDataReader;
import org.junit.Test;
import org.java_websocket.client.WebSocketClient;
public class WesbSocketDataReaderTest {
    @Test
    public void testReadData() throws URISyntaxException {
        DataStorage dataStorage = new DataStorage();
        SignalWebSocketClient client = new SignalWebSocketClient(new URI("ws://localhost:8080"));
        WebSocketDataReader webSocketDataReader = new WebSocketDataReader(8080,client);

        webSocketDataReader.setClient(client);

        String message = "1,1700000000000,HeartRate,70";
        try {
            webSocketDataReader.readData_websocket(dataStorage, 8080);

            // Verify data added to DataStorage
            PatientRecord record = dataStorage.getRecords(1, 1700000000000L, 1800000000000L).get(0);
            assertEquals(1, record.getPatientId());
            assertEquals(1700000000000L, record.getTimestamp());
            assertEquals("HeartRate", record.getRecordType());
            assertEquals(70L, record.getMeasurementValue());
        } catch (IOException e) {
            fail("IOException not expected");
        }
    }

    @Test
    public void testReadData_withException() throws URISyntaxException {
        DataStorage dataStorage = new DataStorage();
        SignalWebSocketClient client = new SignalWebSocketClient(new URI("ws://localhost:8080"));
        WebSocketDataReader webSocketDataReader = new WebSocketDataReader(8080, client);

        webSocketDataReader.setClient(client);
        try {
            webSocketDataReader.readData_websocket(dataStorage, 8080);
            fail("Expected IOException not thrown");
        } catch (IOException e) {
            // Should throw IOException
            System.out.println(e.getMessage());
        }
    }
}

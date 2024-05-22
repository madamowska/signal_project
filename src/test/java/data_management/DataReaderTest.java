package data_management;

import com.data_management.DataReader;
import com.data_management.DataStorage;
import com.data_management.OutputFileDataReader;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DataReaderTest {

    @Test
    void testReadOutputFile(){
        String diretory = "/Users/martaadamowska/signal_project/test";
        OutputFileDataReader outputFileDataReader = new OutputFileDataReader(diretory);
        DataStorage storage = new DataStorage();

        try {
            outputFileDataReader.readData(storage);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Check if the label is read correctly
        assertEquals("Cholesterol", storage.getRecords(1,1700000000000L, 1800000000000L).get(0).getRecordType());
        assertEquals("Alert", storage.getRecords(1,1700000000000L, 1800000000000L).get(1).getRecordType());
        assertEquals("Alert", storage.getRecords(2,1700000000000L, 1800000000000L).get(1).getRecordType());
        assertEquals("Saturation", storage.getRecords(2,1700000000000L, 1800000000000L).get(0).getRecordType());

        // Check if the triggered measurement is read correctly
        assertEquals(1, storage.getAllPatients().get(1).getRecords(1700000000000L, 1800000000000L).get(1).getMeasurementValue());

        // Check if the percentage measurement is read correctly
        assertEquals(0.391, storage.getAllPatients().get(1).getRecords(1700000000000L, 1800000000000L).get(0).getMeasurementValue());
    }

    @Test
    void testReadData(){
        DataReader reader = new MockDataReader();
        DataStorage storage = new DataStorage();

        try {
            reader.readData(storage);
        } catch (IOException e){
            e.printStackTrace();
        }

        assertEquals(2, storage.getAllPatients().size()); // Check if two patient data are read
        assertEquals(1, storage.getAllPatients().get(0).getId()); // Validate first patient
    }
}

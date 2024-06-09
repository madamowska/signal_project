package data_management;

import com.data_management.DataReader;
import com.data_management.DataStorage;

import java.io.IOException;

public class MockDataReader implements DataReader {

    @Override
    public void readData(DataStorage storage) throws IOException {
        storage.addPatientData(1, 100.0, "WhiteBloodCells", 1714376789050L);
        storage.addPatientData(2, 200.0, "WhiteBloodCells", 1714376789051L);


    }

    @Override
    public void readData_websocket(DataStorage dataStorage, int port) throws IOException {

    }

    public void stop(){}
}

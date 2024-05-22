package alerts;

import com.alerts.AlertGenerator;
import com.data_management.DataStorage;
import com.data_management.Patient;
import com.data_management.PatientRecord;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AlertGeneratorTest {
    @Test
    void testDataEvaluation(){
        DataStorage dataStorage = new DataStorage();
        long currentTime = System.currentTimeMillis();
        Patient patient = new Patient(1);
        patient.addRecord(0.90,"Saturation",currentTime);
        AlertGenerator alertGenerator = new AlertGenerator(dataStorage);
        alertGenerator.evaluateData(patient);
        List<PatientRecord> records = patient.getRecords(currentTime-86400000, currentTime+86400000);
        PatientRecord alert = records.get(records.size()-1);
        double value = alert.getMeasurementValue();
        int id = alert.getPatientId();
        String type = alert.getRecordType();
        assertEquals(1,value);
        assertEquals(1,id);
        assertEquals("Alert",type);
    }
}

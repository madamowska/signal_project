package data_management;

import com.data_management.Patient;
import com.data_management.PatientRecord;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class PatientTest {
    @Test
    void testGetRecords(){
        Patient patient = new Patient(1);

        patient.addRecord(10,"Cholesterol",1713620522826L);
        patient.addRecord(0.2,"Saturation",1713620522826L);
        patient.addRecord(1,"Alert",1713620522826L);

        List<PatientRecord> records_ = patient.getRecords(1700000000000L, 1800000000000L);

        // Check if 3 records are retrieved
        assertEquals(3, records_.size());
        // Check if measurement value is retrieved correctly
        assertEquals(0.2, records_.get(1).getMeasurementValue());

    }
}

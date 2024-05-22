package alerts;

import com.alerts.AlertGenerator;
import com.alerts.AlertService;
import com.alerts.BloodOxygenAlertService;
import com.data_management.DataStorage;
import com.data_management.Patient;
import com.data_management.PatientRecord;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BloodOxygenAlertServiceTest {
    @Test
    void testTooLowSaturation (){
        Patient patient = new Patient(1);
        DataStorage dataStorage = new DataStorage();

        // correct blood saturation
        patient.addRecord(0.95,"Saturation",1713620522833L);
        // incorrect blood saturation (< 92%)
        patient.addRecord(0.91,"Saturation",1713620522833L);

        BloodOxygenAlertService alertService = new BloodOxygenAlertService();
        alertService.checkAndTriggerAlerts(patient,patient.getRecords(1700000000000L, 1800000000000L),new AlertGenerator(dataStorage));

        List<PatientRecord> records = patient.getRecords(1700000000000L, 1800000000000L);

        // check size of patient record list
        assertEquals(3, records.size());

        // check alert in patient records list
        PatientRecord alert = records.get(records.size()-1);
        double alertValue = alert.getMeasurementValue();
        int alertID = alert.getPatientId();
        String alertType = alert.getRecordType();

        assertEquals(1,alertValue);
        assertEquals(1,alertID);
        assertEquals("Alert",alertType);

        // check record in patient records list
        PatientRecord record = records.get(records.size()-2);
        double recordValue = record.getMeasurementValue();
        int recordID = record.getPatientId();
        String recordType = record.getRecordType();

        assertEquals(0.91,recordValue);
        assertEquals(1,recordID);
        assertEquals("Saturation",recordType);
    }

    @Test
    void testRapidDrop (){
        Patient patient = new Patient(2);
        DataStorage dataStorage = new DataStorage();
        long tenMinutes = 600000;

        // correct records
        patient.addRecord(0.99,"Saturation",1713620522833L);
        patient.addRecord(0.96,"Saturation",1713620522833L+tenMinutes); // drop < 0.05
        patient.addRecord(0.93,"Saturation",1713620522833L+2*tenMinutes); // time > ten minutes
        patient.addRecord(0.99,"Saturation",1713620522833L);

        // incorrect records (rapid drop)
        patient.addRecord(0.93,"Saturation",1713620522833L+tenMinutes);

        BloodOxygenAlertService alertService = new BloodOxygenAlertService();
        alertService.checkAndTriggerAlerts(patient,patient.getRecords(1700000000000L, 1800000000000L),new AlertGenerator(dataStorage));

        List<PatientRecord> records = patient.getRecords(1700000000000L, 1800000000000L);

        // check size of patient record list after alert triggering
        assertEquals(6, records.size());

        // check alert in patient records list
        PatientRecord alert = records.get(records.size()-1);
        double alertValue = alert.getMeasurementValue();
        int alertID = alert.getPatientId();
        String alertType = alert.getRecordType();

        assertEquals(1,alertValue);
        assertEquals(2,alertID);
        assertEquals("Alert",alertType);

        // check record in patient records list
        PatientRecord record = records.get(records.size()-2);
        double recordValue = record.getMeasurementValue();
        int recordID = record.getPatientId();
        String recordType = record.getRecordType();

        assertEquals(0.93,recordValue);
        assertEquals(2,recordID);
        assertEquals("Saturation",recordType);



    }
}

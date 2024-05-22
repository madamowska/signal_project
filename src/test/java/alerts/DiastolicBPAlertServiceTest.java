package alerts;

import com.alerts.AlertGenerator;
import com.alerts.DiastolicBPAlertService;
import com.data_management.DataStorage;
import com.data_management.Patient;
import com.data_management.PatientRecord;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DiastolicBPAlertServiceTest {

    @Test
    void testTooLowSystolicBP (){
        Patient patient = new Patient(1);
        DataStorage dataStorage = new DataStorage();

        // correct blood pressure
        patient.addRecord(100,"SystolicPressure",1713620522833L);
        // low blood pressure (<90)
        patient.addRecord(89,"SystolicPressure",1713620522833L);

        DiastolicBPAlertService  alertService = new DiastolicBPAlertService();
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

        assertEquals(89,recordValue);
        assertEquals(1,recordID);
        assertEquals("SystolicPressure",recordType);
    }


    @Test
    void testTooHighSystolicBP (){
        Patient patient = new Patient(1);
        DataStorage dataStorage = new DataStorage();

        // correct blood pressure
        patient.addRecord(100,"DiastolicPressure",1713620522833L);
        // low blood pressure (>180)
        patient.addRecord(181,"DiastolicPressure",1713620522833L);

        DiastolicBPAlertService alertService = new DiastolicBPAlertService();
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

        assertEquals(181,recordValue);
        assertEquals(1,recordID);
        assertEquals("DiastolicPressure",recordType);
    }

    @Test
    void testTrendDetectionInSlidingWindow() {
        // Arrange
        Patient patient = new Patient(1);
        DataStorage dataStorage = new DataStorage();

        // Add records to patient
        patient.addRecord(100, "DiastolicPressure", 1713620522833L);
        patient.addRecord(120, "DiastolicPressure", 1713620522834L);
        patient.addRecord(135, "DiastolicPressure", 1713620522835L); // This should trigger the trend alert

        List<PatientRecord> records = patient.getRecords(1700000000000L, 1800000000000L);
        AlertGenerator alertGenerator =new AlertGenerator(new DataStorage());
        DiastolicBPAlertService alertService = new DiastolicBPAlertService();
        alertService.checkAndTriggerAlerts(patient, records, alertGenerator);
        records = patient.getRecords(1700000000000L, 1800000000000L);

        // check size of patient record list
        assertEquals(4, records.size());

        // check alert in patient records list
        PatientRecord alert = records.get(records.size()-1);
        double alertValue = alert.getMeasurementValue();
        int alertID = alert.getPatientId();
        String alertType = alert.getRecordType();

        assertEquals(1,alertValue);
        assertEquals(1,alertID);
        assertEquals("Alert",alertType);
    }
}



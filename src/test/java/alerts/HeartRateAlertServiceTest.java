package alerts;

import com.alerts.AlertGenerator;
import com.alerts.alertServices.HeartRateAlertService;
import com.data_management.DataStorage;
import com.data_management.Patient;
import com.data_management.PatientRecord;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HeartRateAlertServiceTest {

        @Test
        void testTooLowHeartRate (){
            Patient patient = new Patient(1);
            DataStorage dataStorage = new DataStorage();

            // correct heart rate
            patient.addRecord(55,"HeartRate",1713620522833L);
            // low heart rate (<50)
            patient.addRecord(49,"HeartRate",1713620522833L);

            HeartRateAlertService alertService = new HeartRateAlertService();
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

            assertEquals(49,recordValue);
            assertEquals(1,recordID);
            assertEquals("HeartRate",recordType);
        }


        @Test
        void testTooHighHeartRate (){
            Patient patient = new Patient(1);
            DataStorage dataStorage = new DataStorage();

            // correct heart rate
            patient.addRecord(90,"HeartRate",1713620522833L);
            // high heart rate (>100)
            patient.addRecord(101,"HeartRate",1713620522833L);

            HeartRateAlertService  alertService = new HeartRateAlertService();
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

            assertEquals(101,recordValue);
            assertEquals(1,recordID);
            assertEquals("HeartRate",recordType);
        }

        @Test
        void testTrendDetectionInSlidingWindow() {
            // Arrange
            Patient patient = new Patient(1);
            DataStorage dataStorage = new DataStorage();

            // Add records to patient
            patient.addRecord(60, "HeartRate", 1713620522833L);
            patient.addRecord(73, "HeartRate", 1713620522834L); // This should trigger the trend alert

            List<PatientRecord> records = patient.getRecords(1700000000000L, 1800000000000L);
            AlertGenerator alertGenerator =new AlertGenerator(new DataStorage());
            HeartRateAlertService alertService = new HeartRateAlertService();
            alertService.checkAndTriggerAlerts(patient, records, alertGenerator);
            records = patient.getRecords(1700000000000L, 1800000000000L);

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
        }
    }





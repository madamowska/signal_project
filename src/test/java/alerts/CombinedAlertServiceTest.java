package alerts;

import com.alerts.AlertGenerator;
import com.alerts.alertStrategies.CombinedAlertService;
import com.data_management.DataStorage;
import com.data_management.Patient;
import com.data_management.PatientRecord;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
public class CombinedAlertServiceTest {
    @Test
    void testCombinedCondition(){
        Patient patient = new Patient(1);
        DataStorage dataStorage = new DataStorage();

        // correct combined condition
        // correct systolic pressure incorrect saturation
        patient.addRecord(91.0,"SystolicPressure",1713620522832L);
        patient.addRecord(0.91,"Saturation",1713620522836L);

        // correct combined condition
        // incorrect systolic pressure correct saturation
        patient.addRecord(88.0,"SystolicPressure",1713620522842L);
        patient.addRecord(0.99,"Saturation",1713620522846L);

        // incorrect combined condition
        // incorrect systolic pressure incorrect saturation
        patient.addRecord(89.0,"SystolicPressure",1713620522832L);
        patient.addRecord(0.91,"Saturation",1713620522836L);

        CombinedAlertService alertService = new CombinedAlertService();
        alertService.checkAndTriggerAlerts(patient,patient.getRecords(1700000000000L, 1800000000000L),new AlertGenerator(dataStorage));

        List<PatientRecord> records = patient.getRecords(1700000000000L, 1800000000000L);

        // check size of patient record list
        assertEquals(7, records.size());

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

}

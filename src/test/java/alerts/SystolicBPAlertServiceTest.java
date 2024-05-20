package alerts;

import com.alerts.Alert;
import com.alerts.SystolicBPAlertService;
import com.data_management.DataStorage;
import com.data_management.PatientRecord;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

public class SystolicBPAlertServiceTest {

    private SystolicBPAlertService service;
    private FakeAlertGenerator fakeAlertGenerator;
    private List<PatientRecord> records_1, records_2, records_3;

    @Before
    public void setUp() {
        fakeAlertGenerator = new FakeAlertGenerator(new DataStorage());
        service = new SystolicBPAlertService();

        records_1 = List.of(
                new PatientRecord(1, 190, "Systolic BP", 1714376789052L)
        );

        records_2 = List.of(
                new PatientRecord(1, 70, "Systolic BP", 1714376789052L)
        );

        records_3 = List.of(
                new PatientRecord(1, 170, "Systolic BP", 1714376789052L),
                new PatientRecord(1, 155, "Systolic BP", 1714376789055L),
                new PatientRecord(1, 140, "Systolic BP", 1714376789057L)
        );
    }

    @Test
    public void testCriticalThresholds() {
        service.checkAndTriggerAlerts(records_1, fakeAlertGenerator);
        service.checkAndTriggerAlerts(records_2, fakeAlertGenerator);
        List<Alert> alerts = fakeAlertGenerator.getTriggeredAlerts();

        assertEquals(2, alerts.size());
        assertTrue(alerts.get(1).getCondition().contains("Critical systolic blood pressure level"));
        assertTrue(alerts.get(0).getCondition().contains("Critical systolic blood pressure level"));

    }
    @Test
        public void testBPInconsistencies() {
            service.checkAndTriggerAlerts(records_3, fakeAlertGenerator);
            List<Alert> alerts = fakeAlertGenerator.getTriggeredAlerts();
            assertEquals(3, alerts.size());
            assertTrue(alerts.stream().anyMatch(a -> a.getCondition().contains("Inconsistent systolic blood pressure trend detected")));
        }
}


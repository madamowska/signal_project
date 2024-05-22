//package alerts;
//
//import com.alerts.Alert;
//import com.alerts.HeartRateAlertService;
//import com.data_management.DataStorage;
//import com.data_management.PatientRecord;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;
//
//import java.util.List;
//
//public class HeartRateAlertServiceTest {
//
//    private HeartRateAlertService service;
//    private FakeAlertGenerator fakeAlertGenerator;
//    private List<PatientRecord> records_1;
//    private List<PatientRecord> records_2;
//    private List<PatientRecord> records_3;
//
//    @Before
//    public void setUp() {
//        fakeAlertGenerator = new FakeAlertGenerator(new DataStorage());
//        service = new HeartRateAlertService();
//
//        records_1 = List.of(
//                new PatientRecord(1, 49, "Heart Rate", 1714376789052L)
//
//        );
//        records_2 = List.of(
//                new PatientRecord(1, 101, "Heart Rate", 1714376789052L)
//
//        );
//        records_3 = List.of(
//                new PatientRecord(1, 80, "Heart Rate", 1714376789052L),
//                new PatientRecord(1, 63, "Heart Rate", 1714376789055L)
//        );
//    }
//        @Test
//        public void testAbnormalLowHeartRate() {
//            service.checkAndTriggerAlerts(records_1, fakeAlertGenerator);
//            List<Alert> alerts = fakeAlertGenerator.getTriggeredAlerts();
//
//            assertEquals(1, alerts.size());
//            assertTrue(alerts.get(0).getCondition().contains("Abnormal heart rate"));
//
//        }
//
//    @Test
//    public void testAbnormalHighHeartRate() {
//
//        service.checkAndTriggerAlerts(records_2, fakeAlertGenerator);
//        List<Alert> alerts = fakeAlertGenerator.getTriggeredAlerts();
//
//        assertEquals(2, alerts.size());
//        assertTrue(alerts.get(1).getCondition().contains("Abnormal heart rate"));
//
//    }
//        @Test
//        public void testHeartRateVariability() {
//            service.checkAndTriggerAlerts(records_3, fakeAlertGenerator);
//            List<Alert> alerts = fakeAlertGenerator.getTriggeredAlerts();
//            assertEquals(3, alerts.size());
//            assertTrue(alerts.stream().anyMatch(a -> a.getCondition().contains("Significant heart rate variability")));
//        }
//
//
//}

//package alerts;
//
//import com.alerts.*;
//import com.data_management.DataStorage;
//import com.data_management.Patient;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class FakeAlertGenerator extends AlertGenerator {
//    private List<Alert> triggeredAlerts;
//
//
//    /**
//     * Constructs an {@code AlertGenerator} with a specified {@code DataStorage}.
//     * The {@code DataStorage} is used to retrieve patient data that this class
//     * will monitor and evaluate.
//     *
//     * @param dataStorage the data storage system that provides access to patient
//     *                    data
//     */
//
//
//    public FakeAlertGenerator(DataStorage dataStorage) {
//        super(dataStorage);
//        this.triggeredAlerts = new ArrayList<>();
//
//    }
//
//
//    public void triggerAlert(Alert alert) {
//        triggeredAlerts.add(alert);
//    }
//
//    public List<Alert> getTriggeredAlerts() {
//        return triggeredAlerts;
//    }
//
//}
//

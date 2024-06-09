package alertFactories;

import com.alerts.BloodPressureAlert;
import com.alerts.HeartRateAlert;
import com.alerts.alertDecorators.Alert;
import com.alerts.alertFactories.AlertFactory;
import com.alerts.alertFactories.BloodPressureAlertFactory;
import com.alerts.alertFactories.ECGAlertFactory;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ECGAlertFactoryTest  {

    @Test
    void testCreateAlert() {
        AlertFactory factory = new ECGAlertFactory();
        String patientId = "12345";
        String condition = "Abnormal heart rate";
        long timestamp = System.currentTimeMillis();

        Alert alert = factory.createAlert(patientId, condition, timestamp);
        assertTrue(alert instanceof HeartRateAlert);

    }
}

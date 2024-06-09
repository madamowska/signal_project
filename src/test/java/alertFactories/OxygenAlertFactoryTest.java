package alertFactories;

import com.alerts.BloodOxygenAlert;
import com.alerts.BloodPressureAlert;
import com.alerts.alertDecorators.Alert;
import com.alerts.alertFactories.AlertFactory;
import com.alerts.alertFactories.BloodPressureAlertFactory;
import com.alerts.alertFactories.OxygenAlertFactory;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OxygenAlertFactoryTest  {

    @Test
    void testCreateAlert() {
        AlertFactory factory = new OxygenAlertFactory();
        String patientId = "12345";
        String condition = "High Blood Oxygen";
        long timestamp = System.currentTimeMillis();

        Alert alert = factory.createAlert(patientId, condition, timestamp);
        assertTrue(alert instanceof BloodOxygenAlert);

    }
}

package alertFactories;

import com.alerts.BloodPressureAlert;
import com.alerts.alertDecorators.Alert;
import com.alerts.alertFactories.BloodPressureAlertFactory;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BloodPressureAlertFactoryTest  {

    @Test
    void testCreateAlert() {
        BloodPressureAlertFactory factory = new BloodPressureAlertFactory();
        String patientId = "12345";
        String condition = "High Blood Pressure";
        long timestamp = System.currentTimeMillis();

        Alert alert = factory.createAlert(patientId, condition, timestamp);
        assertTrue(alert instanceof BloodPressureAlert, "The alert should be an instance of BloodPressureAlert");

    }
}

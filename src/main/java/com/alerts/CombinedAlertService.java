package com.alerts;

import com.data_management.PatientRecord;

import java.util.List;

public class CombinedAlertService implements AlertService{

    public void checkAndTriggerAlerts (List<PatientRecord> records, AlertGenerator alertGenerator) {
        // Assuming records are sorted by timestamp
        double lastSystolicBP = -1;
        double lastOxygenSaturation = -1;

        for (PatientRecord record : records) {
            if ("Systolic Blood Pressure".equals(record.getRecordType())) {
                lastSystolicBP = record.getMeasurementValue();
            } else if ("Blood Oxygen".equals(record.getRecordType())) {
                lastOxygenSaturation = record.getMeasurementValue();
            }

            // Check combined condition
            if (lastSystolicBP != -1 && lastOxygenSaturation != -1) {
                if (lastSystolicBP < 90 && lastOxygenSaturation < 92) {
                    alertGenerator.triggerAlert(new Alert(Integer.toString(record.getPatientId()), "Hypotensive hypoxemia detected.", record.getTimestamp()));
                    break;
                }
            }
        }
    }
}

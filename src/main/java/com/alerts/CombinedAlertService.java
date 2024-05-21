package com.alerts;

import com.data_management.PatientRecord;

import java.util.List;

public class CombinedAlertService implements AlertService{
    public static final double  MINIMAL_SYSTOLIC = 90.0;
    public static final double MINIMAL_SATURATION = 0.92;

    public void checkAndTriggerAlerts (List<PatientRecord> records, AlertGenerator alertGenerator) {
        // Assuming records are sorted by timestamp
        double lastSystolicBP = -1;
        double lastOxygenSaturation = -1;

        for (PatientRecord record : records) {
            if ("SystolicPressure".equals(record.getRecordType())) {
                lastSystolicBP = record.getMeasurementValue();
            } else if ("Saturation".equals(record.getRecordType())) {
                lastOxygenSaturation = record.getMeasurementValue();
            }

            // Check combined condition
            if (lastSystolicBP != -1 && lastOxygenSaturation != -1) {
                if (lastSystolicBP < MINIMAL_SYSTOLIC && lastOxygenSaturation < MINIMAL_SATURATION) {
                    alertGenerator.triggerAlert(new Alert(Integer.toString(record.getPatientId()), "Hypotensive hypoxemia detected.", record.getTimestamp()));
                    break;
                }
            }
        }
    }
}

package com.alerts;

import com.data_management.PatientRecord;

import java.util.List;

public class BloodOxygenAlertService implements AlertService{

    public void checkAndTriggerAlerts (List<PatientRecord> records, AlertGenerator alertGenerator) {
        for (int i = 0; i < records.size() - 1; i++) {
            PatientRecord current = records.get(i);
            if ("Blood Oxygen".equals(current.getRecordType())) {
                double currentSaturation = current.getMeasurementValue();
                double nextSaturation = records.get(i + 1).getMeasurementValue();
                long timeDiff = records.get(i).getTimestamp() - records.get(i - 1).getTimestamp();

                // Check for low saturation
                if (currentSaturation < 92) {
                    alertGenerator.triggerAlert(new Alert(Integer.toString(current.getPatientId()), "Low blood oxygen saturation detected: " + currentSaturation + "%", current.getTimestamp()));
                }

                // Check for rapid drop within 10 minutes (600000 milliseconds)
                if (timeDiff <= 600000 && (currentSaturation - nextSaturation) >= 5) {
                    alertGenerator.triggerAlert(new Alert(Integer.toString(current.getPatientId()), "Rapid drop in blood oxygen saturation detected.", current.getTimestamp()));
                }
            }
        }
    }
}

package com.alerts;

import com.data_management.PatientRecord;

import java.util.List;

public class HeartRateAlertService implements AlertService{

    public void checkAndTriggerAlerts (List<PatientRecord> records, AlertGenerator alertGenerator) {
        PatientRecord previousRecord = null;
        final double VARIABILITY_THRESHOLD = 0.2;

        for (PatientRecord record : records) {
            if ("Heart Rate".equals(record.getRecordType())) {
                double heartRate = record.getMeasurementValue();
                if (heartRate < 50 || heartRate > 100) {
                    alertGenerator.triggerAlert(new Alert(Integer.toString(record.getPatientId()), "Abnormal heart rate detected: " + heartRate, record.getTimestamp()));
                }

                // Check for irregular heartbeat if there is a previous record to compare
                if (previousRecord != null) {
                    double previousHeartRate = previousRecord.getMeasurementValue();
                    double variation = Math.abs(heartRate - previousHeartRate) / previousHeartRate;

                    // Trigger alert if the variation is greater than the threshold
                    if (variation > VARIABILITY_THRESHOLD) {
                        alertGenerator.triggerAlert(new Alert(Integer.toString(record.getPatientId()),
                                "Significant heart rate variability detected: Current rate = " + heartRate
                                        + ", Previous rate = " + previousHeartRate, record.getTimestamp()));
                    }
                }
                previousRecord = record;
            }
        }
    }
}

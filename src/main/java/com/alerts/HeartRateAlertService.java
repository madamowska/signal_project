package com.alerts;

import com.data_management.Patient;
import com.data_management.PatientRecord;

import java.util.List;

public class HeartRateAlertService implements AlertService{
    private static final double VARIABILITY_THRESHOLD = 0.2;
    private static final double MIN_HEART_RATE = 50;
    private static final double MAX_HEART_RATE = 100;

    public void checkAndTriggerAlerts (Patient patient, List<PatientRecord> records, AlertGenerator alertGenerator) {
        PatientRecord previousRecord = null;

        for (PatientRecord record : records) {
            if ("HeartRate".equals(record.getRecordType())) {
                double heartRate = record.getMeasurementValue();
                if (heartRate < MIN_HEART_RATE || heartRate > MAX_HEART_RATE) {
                    alertGenerator.triggerAlert(new Alert(Integer.toString(record.getPatientId()), "Abnormal heart rate detected: " + heartRate, record.getTimestamp()),patient);
                }

                // Check for irregular heartbeat if there is a previous record to compare
                if (previousRecord != null) {
                    double previousHeartRate = previousRecord.getMeasurementValue();
                    double variation = Math.abs(heartRate - previousHeartRate) / previousHeartRate;

                    // Trigger alert if the variation is greater than the threshold
                    if (variation > VARIABILITY_THRESHOLD) {
                        alertGenerator.triggerAlert(new Alert(Integer.toString(record.getPatientId()),
                                "Significant heart rate variability detected: Current rate = " + heartRate
                                        + ", Previous rate = " + previousHeartRate, record.getTimestamp()),patient);
                    }
                }
                previousRecord = record;
            }
        }
    }
}

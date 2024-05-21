package com.alerts;

import com.data_management.PatientRecord;

import java.util.List;

public class SystolicBPAlertService implements AlertService{
    private static final double CRITICAL_SYSTOLIC_UPPER = 180.0;
    private static final double CRITICAL_SYSTOLIC_LOWER = 90.0;
    private static final double TREND_THRESHOLD = 10.0;

    private SlidingWindow window = new SlidingWindow(3);

    public void checkAndTriggerAlerts(List<PatientRecord> records, AlertGenerator alertGenerator) {
        for (PatientRecord record : records) {
            if (record.getRecordType().equals("SystolicPressure")) {

                double value = record.getMeasurementValue();
                // Check for critical thresholds
                if (value > CRITICAL_SYSTOLIC_UPPER || value < CRITICAL_SYSTOLIC_LOWER) {
                    String patientID = record.getPatientId() + "";
                    alertGenerator.triggerAlert(new Alert(record.getPatientId() + "",
                            "Critical systolic blood pressure level detected", record.getTimestamp()));
                }
                window.addData(value);
                // Check for trends
                if (window.isFull()) {
                    double first = window.getWindow().peek();
                    double second = window.getWindow().toArray(new Double[0])[1];
                    double third = window.getWindow().toArray(new Double[0])[2];
                    if (Math.abs(first - second) > TREND_THRESHOLD && Math.abs(second - third) > TREND_THRESHOLD) {
                        alertGenerator.triggerAlert(new Alert(record.getPatientId() + "",
                                "Inconsistent systolic blood pressure trend detected.",
                                record.getTimestamp()));
                    }
                }
            }
        }
    }
}

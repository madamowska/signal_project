package com.alerts.alertServices;

import com.alerts.Alert;
import com.alerts.AlertGenerator;
import com.alerts.SlidingWindow;
import com.alerts.alertServices.AlertService;
import com.data_management.Patient;
import com.data_management.PatientRecord;

import java.util.List;

public class DiastolicBPAlertService implements AlertService {
    private static final double CRITICAL_DIASTOLIC_UPPER = 120.0;
    private static final double CRITICAL_DIASTOLIC_LOWER = 60.0;
    private static final double TREND_THRESHOLD = 10.0;

    private SlidingWindow window = new SlidingWindow(3);

    public void checkAndTriggerAlerts(Patient patient, List<PatientRecord> records, AlertGenerator alertGenerator) {
        for (PatientRecord record : records) {
            if (record.getRecordType().equals("DiastolicPressure")) {

                double value = record.getMeasurementValue();
                // Check for critical thresholds
                if (value > CRITICAL_DIASTOLIC_UPPER || value < CRITICAL_DIASTOLIC_LOWER) {
                    String patientID = record.getPatientId() + "";
                    alertGenerator.triggerAlert(new Alert(record.getPatientId() + "",
                            "Critical systolic blood pressure level detected", record.getTimestamp()), patient);
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
                                record.getTimestamp()), patient);
                    }
                }
            }
        }
    }
}

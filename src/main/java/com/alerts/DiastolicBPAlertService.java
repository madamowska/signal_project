package com.alerts;

import com.data_management.PatientRecord;

import java.util.List;

public class DiastolicBPAlertService implements AlertService{

    public void checkAndTriggerAlerts(List<PatientRecord> records, AlertGenerator alertGenerator) {
        for (int i = 2; i < records.size(); i++) {
            PatientRecord current = records.get(i);
            PatientRecord prev = records.get(i - 1);
            PatientRecord prevPrev = records.get(i - 2);

            if ("Diastolic BP".equals(current.getRecordType())) {
                double diastolicCurrent = current.getMeasurementValue();
                double diastolicPrev = prev.getMeasurementValue();
                double diastolicPrevPrev = prevPrev.getMeasurementValue();

                // Check for critical thresholds
                if (diastolicCurrent > 120 || diastolicCurrent < 60) {
                    alertGenerator.triggerAlert(new Alert(Integer.toString(current.getPatientId()), "Critical diastolic blood pressure level detected: " + diastolicCurrent + " mmHg", current.getTimestamp()));
                }

                // Check for trends
                if (Math.abs(diastolicCurrent - diastolicPrev) > 10 && Math.abs(diastolicPrev - diastolicPrevPrev) > 10) {
                    alertGenerator.triggerAlert(new Alert(Integer.toString(current.getPatientId()), "Consistent diastolic blood pressure trend detected.", current.getTimestamp()));
                }
            }
        }
    }
}

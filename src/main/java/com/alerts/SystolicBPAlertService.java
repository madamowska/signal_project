package com.alerts;

import com.data_management.PatientRecord;

import java.util.List;

public class SystolicBPAlertService implements AlertService{

    public void checkAndTriggerAlerts(List<PatientRecord> records, AlertGenerator alertGenerator) {
        for (int i = 2; i < records.size(); i++) {
            PatientRecord current = records.get(i);
            PatientRecord prev = records.get(i - 1);
            PatientRecord prevPrev = records.get(i - 2);

            if ("Systolic BP".equals(current.getRecordType())) {
                double systolicCurrent = current.getMeasurementValue();
                double systolicPrev = prev.getMeasurementValue();
                double systolicPrevPrev = prevPrev.getMeasurementValue();

                // Check for critical thresholds
                if (systolicCurrent > 180 || systolicCurrent < 90) {
                    alertGenerator.triggerAlert(new Alert(Integer.toString(current.getPatientId()), "Critical systolic blood pressure level detected: " + systolicCurrent + " mmHg", current.getTimestamp()));
                }

                // Check for trends
                if (Math.abs(systolicCurrent - systolicPrev) > 10 && Math.abs(systolicPrev - systolicPrevPrev) > 10) {
                    alertGenerator.triggerAlert(new Alert(Integer.toString(current.getPatientId()), "Consistent systolic blood pressure trend detected.", current.getTimestamp()));
                }
            }
        }
    }

}

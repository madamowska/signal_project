package com.alerts.alertStrategies;

import com.alerts.SimpleAlert;
import com.alerts.AlertGenerator;
import com.data_management.Patient;
import com.data_management.PatientRecord;

import java.util.List;

public class BloodOxygenAlertService implements AlertService {
    private static final double MINIMAL_SATURATION = 0.92;
    private static final double MAXIMUM_SATURATION_DROP = 0.05;
    private static final long TEN_MINUTES = 600000;

    public void checkAndTriggerAlerts (Patient patient, List<PatientRecord> records, AlertGenerator alertGenerator) {
        for (int i = 0; i < records.size(); i++) {
            PatientRecord current = records.get(i);
            if ("Saturation".equals(current.getRecordType())) {
                double currentSaturation = current.getMeasurementValue();

                // Check for low saturation
                if (currentSaturation < MINIMAL_SATURATION) {
                    alertGenerator.triggerAlert(new Alert(Integer.toString(current.getPatientId()), "Low blood oxygen saturation detected: " + currentSaturation*100 + "%", current.getTimestamp()),patient);
                }
                if(records.size()>1 && i<records.size()-1){
                    double nextSaturation = records.get(i+1).getMeasurementValue();
                    long timeDiff = records.get(i).getTimestamp() - records.get(i+1).getTimestamp();
                    // Check for rapid drop within 10 minutes (600000 milliseconds)
                    if (timeDiff <= TEN_MINUTES && (currentSaturation - nextSaturation) >= MAXIMUM_SATURATION_DROP) {
                        alertGenerator.triggerAlert(new Alert(Integer.toString(current.getPatientId()), "Rapid drop in blood oxygen saturation detected.", current.getTimestamp()),patient);
                    }
                }

            }
        }
    }
}

package com.alerts.alertStrategies;

import com.alerts.AlertGenerator;
import com.data_management.Patient;
import com.data_management.PatientRecord;

import java.util.List;
// AlertFactory
public interface AlertService {
    void checkAndTriggerAlerts(Patient patient, List<PatientRecord> records, AlertGenerator alertGenerator);
}

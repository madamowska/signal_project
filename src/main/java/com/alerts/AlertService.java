package com.alerts;

import com.data_management.Patient;
import com.data_management.PatientRecord;

import java.util.List;
// AlertFactory
public interface AlertService {
    Alert checkAndTriggerAlerts(Patient patient, List<PatientRecord> records, AlertGenerator alertGenerator);
}

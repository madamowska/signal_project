package com.alerts;

import com.data_management.Patient;
import com.data_management.PatientRecord;

import java.util.List;

public interface AlertService {
    void checkAndTriggerAlerts(Patient patient, List<PatientRecord> records, AlertGenerator alertGenerator);
}

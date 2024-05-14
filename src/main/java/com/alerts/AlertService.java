package com.alerts;

import com.data_management.PatientRecord;

import java.util.List;

public interface AlertService {
    void checkAndTriggerAlerts(List<PatientRecord> records, AlertGenerator alertGenerator);
}

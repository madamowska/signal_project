package com.alerts;

import com.alerts.alertDecorators.Alert;

// Represents an alert
public class SimpleAlert implements Alert {
    private String patientId;
    private String condition;
    private long timestamp;

    public SimpleAlert(String patientId, String condition, long timestamp) {
        this.patientId = patientId;
        this.condition = condition;
        this.timestamp = timestamp;
    }

    public SimpleAlert() {

    }

    public String getPatientId() {
        return patientId;
    }

    public String getCondition() {
        return condition;
    }

    public long getTimestamp() {
        return timestamp;
    }
    @Override
    public String toString() {
        return "Alert{patientId='" + patientId + '\'' +
                ", condition='" + condition + '\'' +
                ", timestamp=" + timestamp + '}';
    }

}

package com.alerts;

public class BloodOxygenAlert extends SimpleAlert {

    public BloodOxygenAlert(String patientId, String condition, long timestamp) {
        super(patientId, condition, timestamp);
    }

    @Override
    public String toString() {
        return "OxygenSaturationAlert: " + super.toString();
    }

}

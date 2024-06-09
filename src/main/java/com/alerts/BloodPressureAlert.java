package com.alerts;

public class BloodPressureAlert extends SimpleAlert {

    public BloodPressureAlert(String patientId, String condition, long timestamp){
        super();
    }
    @Override
    public String toString() {
        return "BloodPressureAlert: " + super.toString();
    }
}

package com.alerts;

public class HeartRateAlert extends SimpleAlert {


    public HeartRateAlert(String patientId, String condition, long timestamp) {
        super(patientId, condition, timestamp);
    }

    @Override
        public String toString() {
            return "HeartRateAlert: " + super.toString();
        }
    }


package com.alerts.alertFactories;

import com.alerts.BloodOxygenAlert;

public class OxygenAlertFactory extends AlertFactory{

    @Override
    public BloodOxygenAlert createAlert(String patientId, String condition, long timeStamp){
        return new BloodOxygenAlert(patientId, condition, timeStamp);
    }
}

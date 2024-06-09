package com.alerts.alertFactories;

import com.alerts.SimpleAlert;
import com.alerts.BloodPressureAlert;

public class BloodPressureAlertFactory extends AlertFactory{

    @Override
    public SimpleAlert createAlert(String patientId, String condition, long timeStamp){
        return new BloodPressureAlert(patientId, condition, timeStamp);
    }
}

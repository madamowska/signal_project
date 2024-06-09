package com.alerts.alertFactories;

import com.alerts.SimpleAlert;
import com.alerts.HeartRateAlert;

public class ECGAlertFactory extends AlertFactory{

    @Override
    public SimpleAlert createAlert (String patientID, String condition, long timeStamp) {
        return new HeartRateAlert(patientID, condition, timeStamp);
    }
}

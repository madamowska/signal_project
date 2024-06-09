package com.alerts.alertFactories;

import com.alerts.Alert;
import com.alerts.HeartRateAlert;

public class ECGAlertFactory extends AlertFactory{

    @Override
    public Alert createAlert (String patientID, String condition, long timeStamp) {
        return new HeartRateAlert(patientID, condition, timeStamp);
    }
}

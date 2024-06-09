package com.alerts.alertFactories;

import com.alerts.SimpleAlert;

public abstract class AlertFactory {

    public abstract SimpleAlert createAlert(String patientID, String condition, long timeStamp);
}

package com.alerts.alertFactories;

import com.alerts.Alert;

public abstract class AlertFactory {

    public abstract Alert createAlert(String patientID, String condition, long timeStamp);
}

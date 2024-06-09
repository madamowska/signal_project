package com.alerts.alertDecorators;

public abstract class AlertDecorator implements Alert{

    protected Alert decoratedAlert;

    public AlertDecorator(Alert decoratedAlert) {
        this.decoratedAlert = decoratedAlert;
    }

    @Override
    public String getPatientId() {
        return decoratedAlert.getPatientId();
    }

    @Override
    public String getCondition() {
        return decoratedAlert.getCondition();
    }

    @Override
    public long getTimestamp() {
        return decoratedAlert.getTimestamp();
    }

    @Override
    public String toString() {
        return "Alert{patientId='" + decoratedAlert.getPatientId() + '\'' +
                ", condition='" + decoratedAlert.getCondition() + '\'' +
                ", timestamp=" + decoratedAlert.getTimestamp() + '}';
    }

}

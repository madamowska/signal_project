package com.alerts.alertDecorators;

public interface Alert {

    public String getPatientId();

    public String getCondition();

    public long getTimestamp();

    public String toString();
}

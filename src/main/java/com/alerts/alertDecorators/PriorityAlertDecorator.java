package com.alerts.alertDecorators;

public class PriorityAlertDecorator extends AlertDecorator {

    private String priority;

    public PriorityAlertDecorator(Alert decoratedAlert, String priority) {
        super(decoratedAlert);
        this.priority = priority;
    }

    public PriorityAlertDecorator(Alert decoratedAlert) {
        super(decoratedAlert);
    }

    public String toString() {
        return super.toString() + " [Priority + " + priority + "]";
    }
}

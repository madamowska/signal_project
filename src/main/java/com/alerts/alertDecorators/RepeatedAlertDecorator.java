package com.alerts.alertDecorators;
import com.alerts.AlertGenerator;
import com.alerts.alertStrategies.AlertService;
import com.data_management.Patient;
import com.data_management.PatientRecord;

        import java.util.List;

public class RepeatedAlertDecorator extends AlertDecorator {

    private int repeatInterval;
    private int repeatCount;
    private AlertService alertService;
    private Patient patient;
    private List<PatientRecord> records;
    private AlertGenerator alertGenerator;

    public RepeatedAlertDecorator(Alert decoratedAlert, int repeatInterval,
                                  AlertService alertService, Patient patient,
                                  List<PatientRecord> records, AlertGenerator alertGenerator) {
        super(decoratedAlert);
        this.repeatInterval = repeatInterval;
        this.repeatCount = 0;
        this.alertService = alertService;
        this.patient = patient;
        this.records = records;
        this.alertGenerator = alertGenerator;
    }

    @Override
    public String toString() {
        return super.toString() + " [Repeats every " + repeatInterval + " seconds, " + repeatCount + " times]";
    }

   public void repeatAlert() {
        for (int i = 0; i < repeatCount; i++) {
            try {
                alertService.checkAndTriggerAlerts(patient, records, alertGenerator);
                Thread.sleep(repeatInterval * 1000);
            } catch (InterruptedException e) {
                System.err.println("Alert repetition interrupted");
                Thread.currentThread().interrupt();
            }
        }
    }
}


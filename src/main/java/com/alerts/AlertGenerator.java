package com.alerts;

import com.data_management.DataStorage;
import com.data_management.Patient;
import com.data_management.PatientRecord;


import java.util.List;



/**
 * The {@code AlertGenerator} class is responsible for monitoring patient data
 * and generating alerts when certain predefined conditions are met. This class
 * relies on a {@link DataStorage} instance to access patient data and evaluate
 * it against specific health criteria.
 */
public class AlertGenerator {
    private DataStorage dataStorage;
    private List<AlertService> alertServices;

    /**
     * Constructs an {@code AlertGenerator} with a specified {@code DataStorage}.
     * The {@code DataStorage} is used to retrieve patient data that this class
     * will monitor and evaluate.
     *
     * @param dataStorage the data storage system that provides access to patient
     *                    data
     */
    public AlertGenerator(DataStorage dataStorage) {
        this.dataStorage = dataStorage;
        alertServices.add(new BloodOxygenAlertService());
        alertServices.add(new CombinedAlertService());
        alertServices.add(new DiastolicBPAlertService());
        alertServices.add(new SystolicBPAlertService());
        alertServices.add(new HeartRateAlertService());

    }

    /**
     * Evaluates the specified patient's data to determine if any alert conditions
     * are met. If a condition is met, an alert is triggered via the
     * {@link #triggerAlert}
     * method. This method should define the specific conditions under which an
     * alert
     * will be triggered.
     *
     * @param patient the patient data to evaluate for alert conditions
     */
    public void evaluateData(Patient patient) {
        // Implementation goes here
        long startTime = System.currentTimeMillis() - 86400000; // 24 hours ago
        long endTime = System.currentTimeMillis(); // current time
        List<PatientRecord> records = patient.getRecords(startTime, endTime);

        if (records == null || records.isEmpty()) {
            System.out.println("No records available to evaluate for patient ID: " + patient.getId());
            return;
        }

        for (AlertService service : alertServices) {
            service.checkAndTriggerAlerts(records, this);
        }

    }

    /**
     * Triggers an alert for the monitoring system. This method can be extended to
     * notify medical staff, log the alert, or perform other actions. The method
     * currently assumes that the alert information is fully formed when passed as
     * an argument.
     *
     * @param alert the alert object containing details about the alert condition
     */
    void triggerAlert(Alert alert) {
        // Implementation might involve logging the alert or notifying staff
        Patient patient = new Patient(Integer.parseInt(alert.getPatientId()));
        //measurment value 1 - boolean - alert was triggerer
        patient.addRecord(1, "Alert", alert.getTimestamp());
        System.out.println(alert.toString());
    }

}


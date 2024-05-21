package com.alerts;

import com.data_management.PatientRecord;

import java.util.List;

public class BloodPressureAlertService implements AlertService{
    private static final double CRITICAL_SYSTOLIC_UPPER = 180.0;
    private static final double CRITICAL_DIASTOLIC_UPPER = 120.0;
    private static final double CRITICAL_SYSTOLIC_LOWER = 90.0;
    private static final double CRITICAL_DIASTOLIC_LOWER = 60.0;

    private SlidingWindow systolicWindow;
    private SlidingWindow diastolicWindow;

    public BloodPressureAlertService(){
        this.systolicWindow = new SlidingWindow(3);
        this.diastolicWindow = new SlidingWindow(3);
    }
    @Override
    public void checkAndTriggerAlerts(List<PatientRecord> records, AlertGenerator alertGenerator) {
        for(PatientRecord record : records){
            if(record.getRecordType().equals("SystolicPressure")){
                double value = record.getMeasurementValue();
                systolicWindow.addData(value);
                // Check for critical thresholds
                if(value > CRITICAL_SYSTOLIC_UPPER || value < CRITICAL_SYSTOLIC_LOWER){
                    String patientID = record.getPatientId() + "";
                    //alertGenerator.triggerAlert(new Alert(record.getPatientId() + "", "Critical systolic blood pressure level detected"));
                }
            }
            if(record.getRecordType().equals("DiastolicPressure")){

            }
        }
    }
}

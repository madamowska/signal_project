package com.data_management;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class OutputFileDataReader implements DataReader{
    private String outputDir;
    public OutputFileDataReader(String outputDir){
        this.outputDir = outputDir;
    }

    /**
     * Reads data from a specified source directory,
     * processes it to remove white spaces, commas, colons
     * and stores the data correspondingly in the data storage object.
     *
     * @param dataStorage the storage where data will be stored
     * @throws IOException
     */
    @Override
    public void readData (DataStorage dataStorage) throws IOException {
        File directory = new File(outputDir);
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    System.out.println(file);
                    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            String[] line_ = line.split(",");
                            int patientId = 0;
                            long timestamp = 0;
                            double measurementValue = 0.0;
                            String recordType = "";

                            for (String word : line_) {
                                // remove whitespaces
                                word = word.trim();
                                String[] keyValue = word.split(":");
                                if (keyValue.length == 2) {
                                    String key = keyValue[0].trim();
                                    String value = keyValue[1].trim();
                                    switch (key) {
                                        case "Patient ID":
                                            patientId = Integer.parseInt(value);
                                            break;
                                        case "Timestamp":
                                            timestamp = Long.parseLong(value);
                                            break;
                                        case "Label":
                                            recordType = value;
                                            break;
                                        case "Data":
                                            // %
                                            // for percentage values we convert the value to a double representing it
                                            if (value.charAt(value.length() - 1) == '%') {
                                                value = value.replace("%", "");
                                                measurementValue = Double.parseDouble(value) / 100;
                                            }

                                            // triggered
                                            // for triggered we set measurementValue to 1, and to 0 other ways
                                            else if (value.equals("triggered")) {
                                                measurementValue = 1;
                                            }

                                            else if (value.equals("not triggered")){
                                                measurementValue = 0;
                                            }

                                            else measurementValue = Double.parseDouble(value);
                                            break;
                                        default:
                                            break;
                                    }
                                }
                            }
                            dataStorage.addPatientData(patientId,measurementValue,recordType,timestamp);
                            //System.out.println(patientId + " " + measurementValue + " " + recordType + " " + timestamp);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void readData_websocket(DataStorage dataStorage, int port) throws IOException {

    }


    public static void main(String[] args) throws IOException {
        String diretory = "/Users/martaadamowska/signal_project/test";
        OutputFileDataReader outputFileDataReader = new OutputFileDataReader(diretory);
        DataStorage dataStorage = new DataStorage();
        outputFileDataReader.readData(dataStorage);
        List<PatientRecord> records = dataStorage.getRecords(2, 1700000000000L, 1800000000000L);
        for (PatientRecord record : records) {
            System.out.println("Record for Patient ID: " + record.getPatientId() +
                    ", Type: " + record.getRecordType() +
                    ", Data: " + record.getMeasurementValue() +
                    ", Timestamp: " + record.getTimestamp());
        }
    }

    public void stop(){}

}

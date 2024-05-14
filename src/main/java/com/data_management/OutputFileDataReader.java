package com.data_management;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;

public class OutputFileDataReader implements DataReader{
    private String outputDir;
    public OutputFileDataReader(String outputDir){
        this.outputDir = outputDir;
    }
    @Override
    public void readData (DataStorage dataStorage) throws IOException{
        File directory = new File(outputDir);
        File[] files = directory.listFiles();
        if(files != null){
            for(File file : files){
                if(file.isFile()){
                    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            String[] line_ = line.split(",");
                            int patientId = 0;
                            long timestamp = 0;
                            double measurementValue = 0.0;
                            String recordType = "";

                            for(String word : line_){
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
                                            try {
                                                measurementValue = Double.parseDouble(value);
                                            }
                                            catch (NumberFormatException e) {
                                                // %
                                                // for percentage values we convert the value to a double representing it
                                                if (value.charAt(value.length() - 1) == '%') {
                                                    value = value.replace("%", "");
                                                    measurementValue = Double.parseDouble(value) / 100;
                                                }

                                                // triggered
                                                // for triggered we set measurementValue to 1, and to 0 other ways
                                                if (value.equals("triggered")) {
                                                    measurementValue = 1;
                                                } else {
                                                    measurementValue = 0;
                                                }
                                                //System.err.println("Input is not a valid number: " + value);                                            }
                                            }
                                            break;
                                        default:
                                            break;
                                    }
                                }
                            }
                            //System.out.println(patientId + measurementValue + recordType + timestamp);
                            dataStorage.addPatientData(patientId,measurementValue,recordType,timestamp);
                        }
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        String diretory = "/Users/martaadamowska/signal_project/output";
        OutputFileDataReader outputFileDataReader = new OutputFileDataReader(diretory);
        DataStorage dataStorage = new DataStorage();
        outputFileDataReader.readData(dataStorage);
    }
}

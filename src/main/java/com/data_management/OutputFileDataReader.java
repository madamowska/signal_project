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
    public void readData (DataStorage dataStorage){
        File directory = new File(outputDir);
        File[] files = directory.listFiles();
        if(files != null){
            for(File file : files){
                if(file.isFile()){
                    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            //dataStorage.addPatientData(line);
                        }
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}

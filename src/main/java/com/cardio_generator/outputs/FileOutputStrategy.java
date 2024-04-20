package com.cardio_generator.outputs;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ConcurrentHashMap;
//class name changed to UpperCamelCase
public class FileOutputStrategy implements OutputStrategy {
    // variable name changed to lowerCamelCase
    private String baseDirectory;

    public final ConcurrentHashMap<String, String> file_map = new ConcurrentHashMap<>();

    public FileOutputStrategy(String baseDirectory) {

        this.baseDirectory = baseDirectory;
    }

    @Override
    // variable name changed to lowerCamelCase
    public void output(int patientId, long timeStamp, String label, String data) {
        try {
            // Create the directory
            Files.createDirectories(Paths.get(baseDirectory));
        } catch (IOException e) {
            System.err.println("Error creating base directory: " + e.getMessage());
            return;
        }
        // Set the FilePath variable
        String filePath = file_map.computeIfAbsent(label, k -> Paths.get(baseDirectory, label + ".txt").toString());

        // Write the data to the file
        // change variable name to lowerCamelCase
        try (PrintWriter out = new PrintWriter(
                Files.newBufferedWriter(Paths.get(filePath), StandardOpenOption.CREATE, StandardOpenOption.APPEND))) {
            out.printf("Patient ID: %d, Timestamp: %d, Label: %s, Data: %s%n", patientId, timeStamp, label, data);
        } catch (Exception e) {
            System.err.println("Error writing to file " + filePath + ": " + e.getMessage());
        }
    }
}
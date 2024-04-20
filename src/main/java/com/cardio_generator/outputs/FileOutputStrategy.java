package com.cardio_generator.outputs;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ConcurrentHashMap;
/**
 * Implements the OutputStrategy interface for writing data to files.
 * This class manages file output operations based on specified labels,
 * creating and appending to text files within a given base directory.
 */
//class name changed to UpperCamelCase
public class FileOutputStrategy implements OutputStrategy {
    // variable name changed to lowerCamelCase
    private String baseDirectory;

    public final ConcurrentHashMap<String, String> file_map = new ConcurrentHashMap<>();
/**
 * Constructs a new FileOutputStrategy with a specified base directory.
 *
 * @param baseDirectory the String name of the base directory where data files will
 * be created and stored.
 * */
    public FileOutputStrategy(String baseDirectory) {

        this.baseDirectory = baseDirectory;
    }

    /**
     * Outputs data to a file determined by the label. Data is formatted and appended to the respective file.
     *
     * @param patientId Identifier of the patient as an integer.
     * @param timeStamp  Time stamp of the data record as a long.
     * @param label Determines the file to which data is written as a String.
     * @param data The actual data string to be written.
     */
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
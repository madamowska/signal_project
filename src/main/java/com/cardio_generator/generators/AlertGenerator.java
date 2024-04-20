package com.cardio_generator.generators;

import java.util.Random;
import com.cardio_generator.outputs.OutputStrategy;

/**
 * Implementation of PatientDataGenerator for Alert Generator
 */
public class AlertGenerator implements PatientDataGenerator {
    // Changed variable name to UPPER_SNAKE_CASE
    public static final Random RANDOM_GENERATOR = new Random();
    // Changed variable name to lowerCamelCase
    private boolean[] alertStates; // false = resolved, true = pressed

    /**
     * Constructs an Allert Generator with a specific number of patients.
     * @param patientCount The number of patients for which alert states are measured.
     */
    public AlertGenerator(int patientCount) {
        alertStates = new boolean[patientCount + 1];
    }

    /**
     * Generates alert data for a particular patient and outputs it using a provided output strategy.
     * @param patientId      An integer representation of a patient's ID.
     * @param outputStrategy An object of type OutputStrategy that specifies the strategy used in generation.
     */
    @Override
    public void generate(int patientId, OutputStrategy outputStrategy) {
        try {
            if (alertStates[patientId]) {
                if (RANDOM_GENERATOR.nextDouble() < 0.9) { // 90% chance to resolve
                    alertStates[patientId] = false;
                    // Output the alert
                    outputStrategy.output(patientId, System.currentTimeMillis(), "Alert", "resolved");
                }
            } else {
                // Changed variable name to lowerCamelCase
                double lambda = 0.1; // Average rate (alerts per period), adjust based on desired frequency
                double p = -Math.expm1(-lambda); // Probability of at least one alert in the period
                boolean alertTriggered = RANDOM_GENERATOR.nextDouble() < p;

                if (alertTriggered) {
                    alertStates[patientId] = true;
                    // Output the alert
                    outputStrategy.output(patientId, System.currentTimeMillis(), "Alert", "triggered");
                }
            }
        } catch (Exception e) {
            System.err.println("An error occurred while generating alert data for patient " + patientId);
            e.printStackTrace();
        }
    }
}
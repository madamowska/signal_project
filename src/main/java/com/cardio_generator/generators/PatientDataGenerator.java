package com.cardio_generator.generators;

import com.cardio_generator.outputs.OutputStrategy;
/**
* Interface for generation of patient data.
 */
public interface PatientDataGenerator {
    /**
     * Generates patient data.
     * @param patientId      An integer representation of a patient's ID.
     * @param outputStrategy An object of type OutputStrategy that specifies the strategy used in generation.
     */
    void generate(int patientId, OutputStrategy outputStrategy);
}

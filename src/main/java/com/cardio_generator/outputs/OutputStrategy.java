package com.cardio_generator.outputs;
/**
* Interface for specifying the output strategy.
 */
public interface OutputStrategy {
    /**
     * Outputs patient data.
     * @param patientId     An integer representation of a patient's ID.
     * @param timestamp     A long representation of the timestamp
     * @param label         A string representation of a label of the data.
     * @param data          A string representation of the data to be outputted.
     */
    void output(int patientId, long timestamp, String label, String data);
}

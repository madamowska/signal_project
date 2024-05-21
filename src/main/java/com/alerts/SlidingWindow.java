package com.alerts;

import java.util.LinkedList;
import java.util.Queue;

public class SlidingWindow {
    private Queue<Double> window;
    private int size;
    private double sum;

    /**
     * Constructor for creating a sliding window
     */
    public SlidingWindow(int size){
        this.window = new LinkedList<>();
        this.size = size;
        this.sum = 0.0;
    }

    /**
     * Method adds value to the window, removes the oldest value if
     * the window size is equal to the predefined size
     * @param value
     */
    public void addData(double value){
        if(window.size() == size){
            sum -= window.poll();
        }
        window.add(value);
        sum += value;
    }

    /**
     * Method calculates average value of the window
     * @return 0 if the window is empty, average value of the window otherwise
     */
    public double getAverage(){
        return this.window.isEmpty() ? 0.0 : sum/this.window.size();
    }

    public boolean isFull(){
        return this.size == this.window.size();
    }

    // Getters

    public int getSize() {
        return size;
    }

    public double getSum() {
        return sum;
    }

    public Queue<Double> getWindow() {
        return window;
    }
}

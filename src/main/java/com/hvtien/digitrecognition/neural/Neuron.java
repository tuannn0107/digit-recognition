package com.hvtien.digitrecognition.neural;

import com.hvtien.utils.MathUtils;

import java.util.ArrayList;

public class Neuron {

    private static final int BIAS = 1;
    private static final double LEARNING_RATIO = 0.3;

    private ArrayList<Integer> inputs;
    private ArrayList<Double> weights;
    private double biasWeight;
    private double output;


    /**
     * constructor
     */
    public Neuron() {
        this.inputs = new ArrayList<>();
        this.weights = new ArrayList<>();
        this.biasWeight = Math.random();
    }

    public void setInputs(ArrayList<Integer> inputs) {
        if (this.inputs.size() == 0) {
            this.inputs = new ArrayList<>(inputs);
            generateWeights();
        }

        this.inputs = new ArrayList<>(inputs);
    }

    private void generateWeights() {
        for (int i = 0; i < inputs.size(); i++) {
            weights.add(Math.random());
        }
    }

    private void calOutput() {
        double sum = 0;

        for (int i = 0; i < inputs.size(); i++) {
            sum += inputs.get(i) * weights.get(i);
        }
        sum += BIAS * biasWeight;

        output = MathUtils.sigmoidValue(sum);
    }

    public void adjustWeights(double delta) {
        for (int i = 0; i < inputs.size(); i++) {
            double d = weights.get(i);
            d += LEARNING_RATIO * delta * inputs.get(i);
            weights.set(i, d);
        }

        biasWeight += LEARNING_RATIO * delta * BIAS;
    }

    public double calculateOutput() {
        this.calOutput();
        return output;
    }

    public static int getBIAS() {
        return BIAS;
    }

    public static double getLearningRatio() {
        return LEARNING_RATIO;
    }

    public ArrayList<Integer> getInputs() {
        return inputs;
    }

    public ArrayList<Double> getWeights() {
        return weights;
    }

    public void setWeights(ArrayList<Double> weights) {
        this.weights = weights;
    }

    public double getBiasWeight() {
        return biasWeight;
    }

    public void setBiasWeight(double biasWeight) {
        this.biasWeight = biasWeight;
    }

    public double getOutput() {
        return output;
    }

    public void setOutput(double output) {
        this.output = output;
    }
}

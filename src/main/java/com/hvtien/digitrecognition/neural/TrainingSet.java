package com.hvtien.digitrecognition.neural;

import java.util.ArrayList;

public class TrainingSet {

    private ArrayList<Integer> inputs;
    private ArrayList<Double> goodOutput;

    public TrainingSet()
    {
        this.inputs = new ArrayList<>();
        this.goodOutput = new ArrayList<>();
    }

    public TrainingSet(ArrayList<Integer> inputs, ArrayList<Double> goodOutput) {
        this.inputs = inputs;
        this.goodOutput = goodOutput;
    }

    public ArrayList<Integer> getInputs() {
        return inputs;
    }

    public ArrayList<Double> getGoodOutput() {
        return goodOutput;
    }

    public void setInputs(ArrayList<Integer> inputs) {
        this.inputs = inputs;
    }

    public void setGoodOutput(ArrayList<Double> goodOutput) {
        this.goodOutput = goodOutput;
    }
}

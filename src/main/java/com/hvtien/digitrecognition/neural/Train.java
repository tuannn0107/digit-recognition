package com.hvtien.digitrecognition.neural;

import java.util.ArrayList;

public class Train {

    private static final int NEURON_COUNT = 2;

    private Network network;
    private ArrayList<TrainingSet> trainingSets;

    public Train() {
        this.network = new Network();
        this.network.addNeurons(NEURON_COUNT);
        trainingSets = new ArrayList<>();
    }

    public void train(TrainingSet trainingSet) {
        network.setInputs(trainingSet.getInputs());
        network.adjustWages(trainingSet.getGoodOutput());
        this.addTrainingSet(trainingSet);
    }

    public void setInputs(ArrayList<Integer> inputs) {
        network.setInputs(inputs);
    }

    public void addTrainingSet(TrainingSet newSet) {
        trainingSets.add(newSet);
    }

    public ArrayList<Double> getOutputs() {
        return network.getOutputs();
    }

}

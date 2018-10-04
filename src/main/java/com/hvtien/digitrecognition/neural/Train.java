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

    public void train() {
        for (long i = 0; i < 100; i++) {
            int index = ((int) (Math.random() * trainingSets.size()));
            TrainingSet set = trainingSets.get(index);
            network.setInputs(set.getInputs());
            network.adjustWages(set.getGoodOutput());
        }
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

    public Network getNetwork() {
        return network;
    }

    public void setNetwork(Network network) {
        this.network = network;
    }

    public ArrayList<TrainingSet> getTrainingSets() {
        return trainingSets;
    }

    public void setTrainingSets(ArrayList<TrainingSet> trainingSets) {
        this.trainingSets = trainingSets;
    }
}

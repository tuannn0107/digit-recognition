package com.hvtien.digitrecognition.neural;

import java.util.ArrayList;

public class Network {

    private ArrayList<Neuron> neurons;

    public Network() {
        neurons = new ArrayList<>();
    }


    /**
     * add neurons
     *
     * @param count
     */
    public void addNeurons(int count) {
        for (int i = 0; i < count; i++)
            neurons.add(new Neuron());
    }


    /**
     * set inputs
     *
     * @param inputs
     */
    public void setInputs(ArrayList<Integer> inputs) {
        for (Neuron n : neurons)
            n.setInputs(inputs);
    }


    /**
     * get outputs
     *
     * @return
     */
    public ArrayList<Double> getOutputs() {
        ArrayList<Double> outputs = new ArrayList<>();
        for (Neuron n : neurons)
            outputs.add(n.calculateOutput());

        return outputs;
    }


    /**
     * adjust wages
     *
     * @param goodOutput
     */
    public void adjustWages(ArrayList<Double> goodOutput) {
        for (int i = 0; i < neurons.size(); i++) {
            double delta = goodOutput.get(i) - neurons.get(i).calculateOutput();
            neurons.get(i).adjustWeights(delta);
        }
    }

    public ArrayList<Neuron> getNeurons() {
        return neurons;
    }

    public void setNeurons(ArrayList<Neuron> neurons) {
        this.neurons = neurons;
    }
}

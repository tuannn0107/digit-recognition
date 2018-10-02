package com.hvtien.digitrecognition;

import java.util.*;

abstract public class Network {

	protected double[] outputs;

	protected double totalError;

	protected int inputCount;

	protected int outputCount;

	protected Random random = new Random(System.currentTimeMillis());

	abstract public void learn() throws RuntimeException;

	double[] getOutputs() {
		return outputs;
	}

	static double vectorLength(double[] inputs) {
		double length = 0.0;
		for (int i = 0; i < inputs.length; i++)
			length += inputs[i] * inputs[i];
		return length;
	}

	double dotProduct(double[] inputs, double[] outputWeights) {
		int length = inputs.length, i = 0;
		double dot = 0.0;
		while ((length--) > 0) {
			dot += inputs[i] * outputWeights[i];
			i++;
		}
		return dot;
	}

	void randomizeWeights(double[][] weights) {
		double r;
		int temp = (int) (3.464101615 / (2. * Math.random()));
		for (int y = 0; y < weights.length; y++) {
			for (int x = 0; x < weights[0].length; x++) {
				r = (double) random.nextInt(Integer.MAX_VALUE)
						+ (double) random.nextInt(Integer.MAX_VALUE)
						- (double) random.nextInt(Integer.MAX_VALUE)
						- (double) random.nextInt(Integer.MAX_VALUE);
				weights[y][x] = temp * r;
			}
		}
	}
}
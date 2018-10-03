package com.hvtien.digitrecognition.neural;

import com.hvtien.digitrecognition.gui.PictureClassification;

public class KohonenNetwork extends Network {
	double[][] weights;
	protected double learnRate = 0.3;
	protected double quitError = 0.1;
	protected int retries = 10000;
	protected double reduction = .99;
	protected PictureClassification owner;
	public boolean stop = false;
	protected TrainingSet trainingSet;

	public KohonenNetwork(int inputCount, int outputCount, PictureClassification owner) {
		totalError = 1.0;
		this.inputCount = inputCount;
		this.outputCount = outputCount;
		this.weights = new double[outputCount][inputCount + 1];
		this.outputs = new double[outputCount];
		this.owner = owner;
	}

	public void setTrainingSet(TrainingSet trainingSet) {
		this.trainingSet = trainingSet;
	}

	public static void copyWeights(KohonenNetwork dest, KohonenNetwork source) {
		for (int i = 0; i < source.weights.length; i++) {
			System.arraycopy(source.weights[i], 0, dest.weights[i], 0,
					source.weights[i].length);
		}
	}

	public void clearWeights() {
		totalError = 1.0;
		for (int y = 0; y < weights.length; y++)
			for (int x = 0; x < weights[0].length; x++)
				weights[y][x] = 0;
	}

	void normalizeInput(final double[] inputs, double[] normalizationFactor) {
		double length = vectorLength(inputs);
		if (length < 1.E-30)
			length = 1.E-30;
		normalizationFactor[0] = 1.0 / Math.sqrt(length);
	}

	void normalizeWeight(double[] outputWeights) {
		double length = vectorLength(outputWeights);
		if (length < 1.E-30)
			length = 1.E-30;
		double normalizationFactor = 1.0 / Math.sqrt(length);
		for (int i = 0; i < inputCount; i++)
			outputWeights[i] *= normalizationFactor;
		outputWeights[inputCount] = 0;
	}

	public int findWinner(double[] inputs, double[] normalizationFactor) {
		int win = 0;
		double biggestOutput = -1.E30;
		double[] outputWeights;

		normalizeInput(inputs, normalizationFactor);

		for (int i = 0; i < outputCount; i++) {
			outputWeights = weights[i];
			outputs[i] = dotProduct(inputs, outputWeights)
					* normalizationFactor[0];

			// Remap to bipolar (-1,1 to 0,1)
			outputs[i] = 0.5 * (outputs[i] + 1.0);
			if (outputs[i] > biggestOutput) {
				biggestOutput = outputs[i];
				win = i;
			}

			// Account for rounding
			if (outputs[i] > 1.0)
				outputs[i] = 1.0;
			if (outputs[i] < 0.0)
				outputs[i] = 0.0;
		}
		return win;
	}

	void evaluateErrors(int[] wins, double[] biggestError,
			double[][] corrections) throws RuntimeException {
		int win;
		double[] inputs;
		double[] normalizationFactor = new double[1];
		double[] outputWeights;
		double[] outputCorrections;
		double length, diff;

		for (int y = 0; y < corrections.length; y++) {
			for (int x = 0; x < corrections[0].length; x++) {
				corrections[y][x] = 0;
			}
		}

		for (int i = 0; i < wins.length; i++)
			wins[i] = 0;

		biggestError[0] = 0.0;

		for (int tset = 0; tset < trainingSet.getTrainingSetCount(); tset++) {
			inputs = trainingSet.getInputSet(tset);
			win = findWinner(inputs, normalizationFactor);
			wins[win]++;
			outputWeights = weights[win];
			outputCorrections = corrections[win];
			length = 0.0;

			for (int i = 0; i < inputCount; i++) {
				diff = inputs[i] * normalizationFactor[0] - outputWeights[i];
				length += diff * diff;
				outputCorrections[i] += diff;
			}

			diff = -outputWeights[inputCount];
			length += diff * diff;

			if (length > biggestError[0])
				biggestError[0] = length;
		}

		biggestError[0] = Math.sqrt(biggestError[0]);
	}

	void adjustWeights(double learningRate, int[] wins,
			double[] biggestCorrection, double[][] corrections) {
		double correction, length, f;
		double[] outputCorrections;
		double[] outputWeights;

		biggestCorrection[0] = 0.0;

		for (int i = 0; i < outputCount; i++) {
			if (wins[i] == 0) {
				continue;
			}

			outputWeights = weights[i];
			outputCorrections = corrections[i];
			
			f = 1.0 / (double) wins[i] * learningRate;

			length = 0.0;

			for (int j = 0; j <= inputCount; j++) {
				correction = f * outputCorrections[j];
				outputWeights[j] += correction;
				length += correction * correction;
			}

			if (length > biggestCorrection[0])
				biggestCorrection[0] = length;
		}
		// scale the correction
		biggestCorrection[0] = Math.sqrt(biggestCorrection[0]) / learningRate;
	}

	void forceWin(int[] wins) throws RuntimeException {
		int win, which = 0;
		double dist;
		double[] inputs;
		double[] normalizationFactor = new double[1];
		double[] outputWeights;

		// (1) Calculate all of outputs of each trainingSet and save tset with
		// smallest output
		dist = 1.E30;
		for (int tset = 0; tset < trainingSet.getTrainingSetCount(); tset++) {
			inputs = trainingSet.getInputSet(tset);
			win = findWinner(inputs, normalizationFactor);
			if (outputs[win] < dist) {
				dist = outputs[win];
				which = tset;
			}
		}

		inputs = trainingSet.getInputSet(which);
		win = findWinner(inputs, normalizationFactor);

		dist = -1.E30;
		int i = outputCount;

		// Find biggest Output in (1)
		while ((i--) > 0) {
			if (wins[i] != 0) {
				continue;
			}
			if (outputs[i] > dist) {
				dist = outputs[i];
				which = i;
			}
		}

		outputWeights = weights[which];

		System.arraycopy(inputs, 0, outputWeights, 0, inputs.length);

		outputWeights[inputCount] = 0.0;
		normalizeWeight(outputWeights);
	}

	/**
	 * This method is called to train the network. It can run for a very long
	 * time and will report progress back to the owner object.
	 *
	 * @exception RuntimeException
	 */
	public void learn() throws RuntimeException {
		int retry, win;
		int[] wins;
		double learningRate, bestError;
		double[][] corrections;
		double[] inputs;
		double[] biggestError = new double[1];
		double[] biggestCorrection = new double[1];
		KohonenNetwork bestNetwork;

		totalError = 1.0;
		for (int i = 0; i < trainingSet.getTrainingSetCount(); i++) {
			inputs = trainingSet.getInputSet(i);
			if (vectorLength(inputs) < 1.E-30) {
				throw (new RuntimeException("Vector Length < 1.E-30!"));
			}
		}

		bestNetwork = new KohonenNetwork(inputCount, outputCount, owner);

		wins = new int[outputCount];
		corrections = new double[outputCount][inputCount + 1];
		learningRate = learnRate;

		initialize();
		bestError = 1.E30;

		retry = 0;
		while (true) {
			evaluateErrors(wins, biggestError, corrections);
			totalError = biggestError[0];
			if (totalError < bestError) {
				bestError = totalError;
				copyWeights(bestNetwork, this);
			}
			win = 0;
			for (int i = 0; i < wins.length; i++)
				if (wins[i] != 0)
					win++;
			if (biggestError[0] < quitError) {
				break;
			}

			if ((win < outputCount)
					&& (win < trainingSet.getTrainingSetCount())) {
				forceWin(wins);
				// continue;
			}

			if (biggestCorrection[0] < 1E-5) {
				if (++retry > retries)
					break;
				initialize();
				learningRate = learnRate;
				continue;
			}

			if (learningRate > 0.01) {
				learningRate *= reduction;
			}

		}

		// done

		copyWeights(this, bestNetwork);

		for (int i = 0; i < outputCount; i++)
			normalizeWeight(weights[i]);

		stop = true;
		retry++;
	}

	/**
	 * Called to initialize the Kononen network.
	 */
	public void initialize() {
		int i;
		double[] outputWeights;

		clearWeights();
		randomizeWeights(weights);
		for (i = 0; i < outputCount; i++) {
			outputWeights = weights[i];
			normalizeWeight(outputWeights);
		}
	}
}
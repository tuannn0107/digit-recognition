package com.hvtien.digitrecognition;

public class TrainingSet {

	protected int inputCount;
	protected int outputCount;
	protected double[][] inputs;
	protected double[][] outputs;
	protected double[] classifies;
	protected int trainingSetCount;

	TrainingSet(int inputCount, int outputCount) {
		this.inputCount = inputCount;
		this.outputCount = outputCount;
		trainingSetCount = 0;
	}

	public int getInputCount() {
		return inputCount;
	}

	public int getOutputCount() {
		return outputCount;
	}

	public void setTrainingSetCount(int trainingSetCount) {
		this.trainingSetCount = trainingSetCount;
		inputs = new double[trainingSetCount][inputCount];
		outputs = new double[trainingSetCount][outputCount];
		classifies = new double[trainingSetCount];
	}

	public int getTrainingSetCount() {
		return trainingSetCount;
	}

	void setInputs(int set, int index, double value) throws RuntimeException {
		if ((set < 0) || (set >= trainingSetCount))
			throw (new RuntimeException("Training set out of range:" + set));
		if ((index < 0) || (index >= inputCount))
			throw (new RuntimeException("Training input index out of range:"
					+ index));
		inputs[set][index] = value;
	}

	void setOutputs(int set, int index, double value) throws RuntimeException {
		if ((set < 0) || (set >= trainingSetCount))
			throw (new RuntimeException("Training set out of range:" + set));
		if ((index < 0) || (set >= outputCount))
			throw (new RuntimeException("Training input index out of range:"
					+ index));
		outputs[set][index] = value;
	}

	void setClassifies(int set, double value) throws RuntimeException {
		if ((set < 0) || (set >= trainingSetCount))
			throw (new RuntimeException("Training set out of range:" + set));
		classifies[set] = value;
	}

	double getInputs(int set, int index) throws RuntimeException {
		if ((set < 0) || (set >= trainingSetCount))
			throw (new RuntimeException("Training set out of range:" + set));
		if ((index < 0) || (index >= inputCount))
			throw (new RuntimeException("Training input index out of range:"
					+ index));
		return inputs[set][index];
	}

	double getOutputs(int set, int index) throws RuntimeException {
		if ((set < 0) || (set >= trainingSetCount))
			throw (new RuntimeException("Training set out of range:" + set));
		if ((index < 0) || (set >= outputCount))
			throw (new RuntimeException("Training input index out of range:"
					+ index));
		return outputs[set][index];
	}

	double getClassifies(int set) throws RuntimeException {
		if ((set < 0) || (set >= trainingSetCount))
			throw (new RuntimeException("Training set out of range:" + set));
		return classifies[set];
	}

	void CalculateClass(int c) {
		for (int i = 0; i <= trainingSetCount; i++) {
			classifies[i] = c + 0.1;
		}
	}

	double[] getOutputSet(int set) throws RuntimeException {
		if ((set < 0) || (set >= trainingSetCount))
			throw (new RuntimeException("Training set out of range:" + set));
		return outputs[set];
	}

	double[] getInputSet(int set) throws RuntimeException {
		if ((set < 0) || (set >= trainingSetCount))
			throw (new RuntimeException("Training set out of range:" + set));
		return inputs[set];
	}
}

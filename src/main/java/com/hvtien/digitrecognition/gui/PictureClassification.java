package com.hvtien.digitrecognition.gui;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hvtien.digitrecognition.data.GoodOutputs;
import com.hvtien.digitrecognition.neural.Train;
import com.hvtien.digitrecognition.neural.TrainingSet;
import com.hvtien.utils.Constants;
import com.hvtien.utils.Converter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class PictureClassification {
	private static Logger logger = LoggerFactory.getLogger(PictureClassification.class);

	public JFrame frame;
	public Paint paint;
	private Train networkTrainer;


	/**
	 * constructor
	 */
	public PictureClassification()
	{	
		initGUI();
		networkTrainer = new Train();
		loadDataTrain();
	}

	/**
	 * init gui
	 */
	private void initGUI() {
		this.frame = new JFrame("Neural Network Learning!");
		this.frame.setSize(600 ,400);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setLayout(null);

		paint = new Paint();
		paint.setBounds(10, 10, Constants.DEFAULT_SIZE.width, Constants.DEFAULT_SIZE.height);
		this.frame.add(paint);

		BinaryLayer binaryLayer = new BinaryLayer((int) Constants.BINARY_LAYER.getWidth(), (int) Constants.BINARY_LAYER.getHeight());
		binaryLayer.setBounds(320, 10, ((int) Constants.BINARY_LAYER.getWidth()) * Constants.BINARY_LAYER_SCALE, ((int) Constants.BINARY_LAYER.getHeight()) * Constants.BINARY_LAYER_SCALE);
		paint.setBinaryLayer(binaryLayer);

		this.frame.add(binaryLayer);

		JButton Learn=new JButton("Digit 6");
		Learn.setBounds(320, 255, 100, 25);
		Learn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				BinaryData binaryData = paint.convertBinaryLayer();
				paint.binaryLayer.setData(binaryData);
				paint.binaryLayer.repaint();
				paint.repaint();
				networkTrainer.addTrainingSet(new TrainingSet(Converter.convertToSingleDimension(binaryData.grid),
						GoodOutputs.getInstance().getGoodOutput(Constants.DIGIT_LIST[0])));
				networkTrainer.train();
			}
		});
		this.frame.add(Learn);

		JProgressBar progressBar6;
		//Where the GUI is constructed:
		progressBar6 = new JProgressBar(0, 100);
		progressBar6.setValue(0);
		progressBar6.setStringPainted(true);
		progressBar6.setBounds(430, 260, 130, 15);
		this.frame.add(progressBar6);

		JButton Learn2=new JButton("Digit 9");
		Learn2.setBounds(320, 285, 100, 25);
		Learn2.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				BinaryData binaryData = paint.convertBinaryLayer();
				paint.binaryLayer.setData(binaryData);
				paint.binaryLayer.repaint();
				paint.repaint();
				networkTrainer.addTrainingSet(new TrainingSet(Converter.convertToSingleDimension(binaryData.grid),
						GoodOutputs.getInstance().getGoodOutput(Constants.DIGIT_LIST[1])));
				networkTrainer.train();
			}
		});
		this.frame.add(Learn2);

		JProgressBar progressBar9;
		//Where the GUI is constructed:
		progressBar9 = new JProgressBar(0, 100);
		progressBar9.setValue(0);
		progressBar9.setStringPainted(true);
		progressBar9.setBounds(430, 290, 130, 15);
		this.frame.add(progressBar9);

		JButton classification = new JButton("Classification");
		classification.setBounds(320, 320, 100, 25);
		classification.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				BinaryData binaryData = paint.convertBinaryLayer();
				paint.binaryLayer.setData(binaryData);
				paint.binaryLayer.repaint();
				paint.repaint();

				networkTrainer.setInputs(Converter.convertToSingleDimension(binaryData.grid));

				ArrayList<Double> outputs = networkTrainer.getOutputs();
				progressBar6.setValue((int)((outputs.get(0) / (outputs.get(0) + outputs.get(1))) * 100));
				progressBar9.setValue((int)((outputs.get(1) / (outputs.get(0) + outputs.get(1))) * 100));
			}
		});
		this.frame.add(classification);

		JButton export = new JButton("Export");
		export.setBounds(430, 320, 100, 25);
		export.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				exportDataTrain();
			}
		});
		this.frame.add(export);

		JButton clear = new JButton("Clear");
		clear.setBounds(240, 320, 70, 25);
		clear.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				paint.clear();
				binaryLayer.getData().clear();
				binaryLayer.repaint();
				progressBar6.setValue(0);
				progressBar9.setValue(0);
			}
		});
		this.frame.add(clear);

		this.frame.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				// Export before close
				//exportDataTrain();
			}
		});

		frame.setVisible(true);
	}



	private void loadDataTrain() {
		logger.info("Start import data train for json file.");
		ObjectMapper mapper = new ObjectMapper();
		try {
			networkTrainer = mapper.readValue(new File(Constants.RESOURCES_PATH + Constants.FILE_DATA_TRAIN), Train.class);
            logger.info("Import data train sucessfully.");
		} catch (IOException e) {
			logger.error("Could not parse object from json file." + e);
		}
	}


	/**
	 * Export data train
	 */
	private void exportDataTrain() {
		logger.info("Start export data train for json file.");
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writerWithDefaultPrettyPrinter().writeValue(new File(Constants.RESOURCES_PATH + Constants.FILE_DATA_TRAIN), networkTrainer);
			logger.info("Create json file for data train successfully.");
		} catch (IOException e) {
			logger.error("Could not write object to json file.");
		}
	}
}

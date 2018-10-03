package com.hvtien.digitrecognition.original;

import com.hvtien.utils.Constants;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class Perceptron {
	int w,h;
	double[] weight;
	double alpha=0.5;
	double loop=100;
	
	public int class1=0;
	public int class2=0;
	
	public Perceptron(int w,int h){
		this.w=w;
		this.h=h;
		this.weight=new double[w*h+1];
		Random rand=new Random();
		for (int i=0;i<weight.length;i++) {
			weight[i]=rand.nextDouble()-0.5;
		}
	}
	
	public double output(BufferedImage image)
	{
		double sum=weight[w*h];
		for (int i=0;i<w;i++) {
			for (int j=0;j<h;j++) {
				if (image.getRGB(i, j) == Constants.COLOR_FOR_PAINT_RGB) {
					sum+=weight[i*h+j];
				}
			}
		}
		return 1.0/(1.0+Math.exp(-sum/(1000)));
	}
	
	
	public void learning(BufferedImage image, int y){
		if (y == 0) {
			class1++;
		} else {
			class2++;
		}
		for (int l = 0; l < loop; l++) {
			double o = output(image);
			double d = alpha * o * (1 - o) * (y - o);
			for (int i = 0; i < w; i++) {
				for (int j = 0; j < h; j++) {
					if (image.getRGB(i, j) == Constants.COLOR_FOR_PAINT_RGB) {
						weight[i * h + j] = weight[i * h + j] + d;
					}
				}
				weight[w * h] = weight[w * h] + d;
			}
		}
	}

	
	public void export() {
		FileWriter tmpFileOut;
		try {
			tmpFileOut = new FileWriter(Constants.RESOURCES_PATH + Constants.FILE_DATA_TRAIN);
			for (int i = 0; i < weight.length ; i++)
			{
				tmpFileOut.write(String.valueOf(weight[i]));
				if (i < weight.length - 1) {
					tmpFileOut.write("\n");
				}
			}
			tmpFileOut.close();		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void loadDataTrain() {
		try {
			Scanner scanner = new Scanner(new File(Constants.RESOURCES_PATH + Constants.FILE_DATA_TRAIN));
			int i = 0;
			while (scanner.hasNext()) {
				weight[i++] = scanner.nextDouble();
			}
		} catch (Exception ex) {

		}


	}
}

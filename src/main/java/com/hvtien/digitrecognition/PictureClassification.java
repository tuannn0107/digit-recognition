package com.hvtien.digitrecognition;

import com.hvtien.utils.Constants;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JProgressBar;


public class PictureClassification {
	public JFrame frame;
	public Paint paint;
	Perceptron pct;
	public PictureClassification()
	{	
		pct=new Perceptron(Constants.DEFAULT_SIZE.width, Constants.DEFAULT_SIZE.height);
		createGUI();
		pct.loadDataTrain();
	}

	private void createGUI() {
		this.frame = new JFrame("Neural Network Learning!");
		this.frame.setSize(600 ,400);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setLayout(null);

		/*paintOriginal = new PaintOriginal();
		paintOriginal.setBounds(10, 10, Constants.DEFAULT_SIZE.width, Constants.DEFAULT_SIZE.height);
		this.frame.getContentPane().add(paintOriginal);*/

		paint = new Paint();
		paint.setBounds(10, 10, Constants.DEFAULT_SIZE.width, Constants.DEFAULT_SIZE.height);
		// // getContentPane().add(entry);
		//
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
				//pct.learning(paint.image, 0);
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
				//pct.learning(paintOriginal.image, 1);
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
				paint.convertBinaryLayer();
				//double o = pct.output(paintOriginal.image);
				//l3.setText(""+o);
				//progressBar6.setValue((new Double((1 - o) * 100)).intValue());
				//progressBar9.setValue((new Double(o * 100)).intValue());
			}
		});
		this.frame.add(classification);

		JButton export = new JButton("Export");
		export.setBounds(430, 320, 100, 25);
		export.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				pct.export();
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
//				l3.setText("");
				progressBar6.setValue(0);
				progressBar9.setValue(0);
			}
		});
		this.frame.add(clear);

		this.frame.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				// Export before close
				pct.export();
			}
		});

		frame.setVisible(true);
	}
}

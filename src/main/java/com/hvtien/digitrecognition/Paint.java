package com.hvtien.digitrecognition;


import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Paint extends JPanel {

	protected Image entryImage;

	protected Graphics entryGraphics;

	protected int lastX = -1;

	protected int lastY = -1;

	protected BinaryLayer binaryLayer;

	protected int boundLeft;

	protected int boundRight;

	protected int boundTop;

	protected int boundBottom;

	protected double ratioX;

	protected double ratioY;

	Paint() {
		enableEvents(AWTEvent.MOUSE_MOTION_EVENT_MASK
				| AWTEvent.MOUSE_EVENT_MASK | AWTEvent.COMPONENT_EVENT_MASK);
	}

	protected void initImage() {
		entryImage = createImage(getWidth(), getHeight());
		entryGraphics = entryImage.getGraphics();
		entryGraphics.setColor(Color.white);
		entryGraphics.fillRect(0, 0, getWidth(), getHeight());
	}

	@Override
	public void paint(Graphics g) {
		if (entryImage == null)
			initImage();
		g.drawImage(entryImage, 0, 0, this);
		g.setColor(Color.black);
		g.drawRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.red);
		g.drawRect(boundLeft - 1, boundTop - 1, boundRight
				- boundLeft + 2, boundBottom - boundTop + 2);

	}

	@Override
	protected void processMouseEvent(MouseEvent e) {
		if (e.getID() != MouseEvent.MOUSE_PRESSED)
			return;
		lastX = e.getX();
		lastY = e.getY();
	}

	@Override
	protected void processMouseMotionEvent(MouseEvent e) {
		if (e.getID() != MouseEvent.MOUSE_DRAGGED)
			return;

		entryGraphics.setColor(Color.black);
		entryGraphics.drawLine(lastX, lastY, e.getX(), e.getY());
		getGraphics().drawImage(entryImage, 0, 0, this);
		lastX = e.getX();
		lastY = e.getY();
	}

	public void setBinaryLayer(BinaryLayer s) {
		binaryLayer = s;
	}

	public BinaryLayer getBinaryLayer() {
		return binaryLayer;
	}


	/**
	 * Check if the horizontal line contain paint of digit
	 *
	 * @param y height line of image
	 * @return
	 */
	protected boolean hLineClear(int y) {
		int w = entryImage.getWidth(this);
		BufferedImage bufferedImage = (BufferedImage) entryImage;
		for (int i = 0; i < w; i++) {
			if (bufferedImage.getRGB(i, y) == Color.BLACK.getRGB())
				return true;
		}
		return false;
	}


	/**
	 * Check if the vertical line contain paint of digit
	 *
	 * @param x width line of image
	 * @return
	 */
	protected boolean vLineClear(int x) {
		int h = entryImage.getHeight(this);

		BufferedImage bufferedImage = (BufferedImage) entryImage;
		for (int i = 0; i < h; i++) {
			if (bufferedImage.getRGB(x, i) == Color.BLACK.getRGB())
				return true;
		}
		return false;
	}


	/**
	 * find bound of image
	 *
	 * @param w
	 * @param h
	 */
	protected void findBounds(int w, int h) {
		// top line
		for (int y = 0; y < h; y++) {
			if (hLineClear(y)) {
				boundTop = y;
				break;
			}

		}

		for (int y = h - 1; y >= 0; y--) {
			if (hLineClear(y)) {
				boundBottom = y;
				break;
			}
		}

		for (int x = 0; x < w; x++) {
			if (vLineClear(x)) {
				boundLeft = x;
				break;
			}
		}

		for (int x = w - 1; x >= 0; x--) {
			if (vLineClear(x)) {
				boundRight = x;
				break;
			}
		}
	}


	/**
	 * Check if it is able to convert to binary layer at coordinate x,y
	 *
	 * @param xCoor
	 * @param yCoor
	 * @return
	 */
	protected boolean isAbleConverted(int xCoor, int yCoor) {
		int w = entryImage.getWidth(this);
		BufferedImage bufferedImage = (BufferedImage) entryImage;

		int startX = (int) (boundLeft + (xCoor * ratioX));
		int endX = (int) (startX + ratioX);

		int startY = (int) (boundTop + (yCoor * ratioY));
		int endY = (int) (startY + ratioY);

		for (int y = startY; y <= endY; y++) {
			for (int x = startX; x <= endX; x++) {
				if (bufferedImage.getRGB(x, y) == Color.BLACK.getRGB())
					return true;
			}
		}
		return false;
	}


	/**
	 * convert digit from paint to binary Layer
	 *
	 */
	public BinaryData convertBinaryLayer() {
		int w = entryImage.getWidth(this);
		int h = entryImage.getHeight(this);

		findBounds(w, h);

		BinaryData data = binaryLayer.getData();

		ratioX = (double) (boundRight - boundLeft + 1)
				/ (double) data.getWidth();
		ratioY = (double) (boundBottom - boundTop + 1)
				/ (double) data.getHeight();

		for (int y = 0; y < data.getHeight(); y++) {
			for (int x = 0; x < data.getWidth(); x++) {
				if (isAbleConverted(x, y))
					data.setData(x, y, true);
				else
					data.setData(x, y, false);
			}
		}

		return data;
	}


	public void clear() {
		this.entryGraphics.setColor(Color.white);
		this.entryGraphics.fillRect(0, 0, getWidth(), getHeight());
		this.boundBottom = this.boundTop = this.boundLeft = this.boundRight = 0;
		repaint();
	}
}
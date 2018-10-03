package com.hvtien.digitrecognition.original;

import com.hvtien.utils.Constants;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

public class PaintOriginal extends JPanel implements MouseListener,
                                             MouseMotionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public BufferedImage image;
	private Graphics graphics;
	private Point startPoint;
	
	public PaintOriginal()
	{
		setPreferredSize(Constants.DEFAULT_SIZE);
		addMouseListener(this);
		addMouseMotionListener(this);
		
		image = new BufferedImage(
				Constants.DEFAULT_SIZE.width, Constants.DEFAULT_SIZE.height, BufferedImage.TYPE_3BYTE_BGR);
		graphics = image.getGraphics();
		clear();
	}
	
	public void clear(){
		graphics.setColor(Constants.COLOR_FOR_BACKGROUND);
		graphics.fillRect(0, 0, Constants.DEFAULT_SIZE.width, Constants.DEFAULT_SIZE.height);
		graphics.setColor(Constants.COLOR_FOR_PAINT);
		repaint();
	
	}
	@Override public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		g2.drawImage(image, 0, 0, this);
	}
	
	@Override public void mousePressed(MouseEvent e)
	{
		Point p = e.getPoint();
		graphics.fillOval(p.x-10, p.y-10, 20, 20);
		repaint();
		startPoint = p;
	}
	
	@Override public void mouseClicked(MouseEvent e) {}
	@Override public void mouseReleased(MouseEvent e) {}
	@Override public void mouseEntered(MouseEvent e) {}
	@Override public void mouseExited(MouseEvent e) {}
	
	@Override public void mouseDragged(MouseEvent e)
	{
		Point p = e.getPoint();
		graphics.fillOval(p.x-10, p.y-10, 20, 20);
		//graphics.drawLine(startPoint.x, startPoint.y, p.x, p.y);
		repaint();
		startPoint = p;
	}
	
	@Override public void mouseMoved(MouseEvent e) {}
	
	public void saveImage()
	{
		JFileChooser fileDialog = new JFileChooser();
		int state = fileDialog.showSaveDialog(this);
		if (state != JFileChooser.APPROVE_OPTION)
			return;
		
		File file = fileDialog.getSelectedFile();
		String fileName = file.getName();
		if (!fileName.endsWith(".png"))
			file = new File(file.getParent(), fileName + ".png");
		
		try { ImageIO.write(image, "png", file); }
		catch (Exception e) { e.printStackTrace(); }
	}

	
}

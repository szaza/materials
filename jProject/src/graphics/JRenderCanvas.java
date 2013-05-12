package graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.util.Random;

import javax.swing.JPanel;

import collect.TransformList;
import collect.Triangle;

public class JRenderCanvas extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private int width;
	private int height;
	private int scale;
	private Color color;
	private Triangle defTriang;
	private TransformList trList;
	private AffineTransform transform;
	
	public JRenderCanvas() {
		this (200,150);
	}
	
	public JRenderCanvas(int width, int height) {
		this.width = width;
		this.height = height;
		this.color = Color.black;
		this.scale = width/10;
		this.setBackground(color);
		this.setPreferredSize(new Dimension(width,height));
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Triangle getDefTriang() {
		return defTriang;
	}

	public void setDefTriang(Triangle defTriang) {
		this.defTriang = defTriang;
	}

	public TransformList getTransform() {
		return trList;
	}

	public void setTransformList(TransformList trList) {
		this.trList = trList;
		repaint();
	}
	
	@Override
	public void paint(Graphics g) {
		Random rnb = new Random();
		Shape shape;
		Graphics2D gr = (Graphics2D) g;
		int index;
		
		gr.setColor(color);
		gr.fillRect(0, 0, width, height);
		gr.translate(width/2,height/2);
		
		shape = defTriang.getPolygon(0,0,scale);
		gr.setColor(Color.red);
		
		for (int i=0; i<100; i++) {		
			index = rnb.nextInt(trList.getLength());
			transform = trList.getValue(index);
			shape = transform.createTransformedShape(shape);
			gr.draw(shape);
		}
	}
}

package graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.JPanel;

import collect.FractalComponent;
import collect.Triangle;

public class JRenderCanvas extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private int width;
	private int height;
	private int scale;
	private int iterationNumber;
	private Color bgColor;
	private Triangle defTriang;
	private LinkedList <FractalComponent> componentList;
	private BufferedImage img;
	
	public JRenderCanvas() {
		this (200,150);
	}
	
	public JRenderCanvas(int width, int height) {
		this.width = width;
		this.height = height;
		this.bgColor = Color.black;
		this.scale = width/6;
		this.iterationNumber = 30000;
		this.setBackground(bgColor);
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

	public LinkedList <FractalComponent> getTransform() {
		return componentList;
	}

	public void setComponentList(LinkedList <FractalComponent> componentList) {
		this.componentList = componentList;
		repaint();
	}
	
	public void drawEllipse(Point2D.Double pont,Graphics2D gr2D,double scale){
		Shape shape;		
		shape = new Ellipse2D.Float((float)(pont.x * scale),(float)(pont.y*scale), 0.1f, 0.0f);
		gr2D.draw(shape);
	}
	
	@Override
	public void paint(Graphics g) {
		int index;
		Random rnb = new Random();
		Graphics2D  gr2D = null;
		FractalComponent component;
		Point2D.Double pont;
		Color color;
		AffineTransform transform;
		
		img = new BufferedImage(getWidth(),getHeight(),BufferedImage.TYPE_INT_ARGB);	
		
		gr2D = (Graphics2D) img.getGraphics();
		
		g.setColor(bgColor);
		g.fillRect(0, 0, width, height);
		
		transform = gr2D.getTransform();
		transform.scale(1,-1);
		transform.translate(0,-height);
		gr2D.setTransform(transform);
		
		if (componentList != null) {			
			gr2D.translate(width/2,height/2);		
			
			pont = new Point2D.Double(1,0);
			color = componentList.get(0).getColor();
			
			for (int i=0; i<iterationNumber; i++) {
				index = rnb.nextInt(componentList.size());				
				component = componentList.get(index);
				color = new Color((color.getRed() + component.getColor().getRed()) / 2,(color.getGreen() + component.getColor().getGreen()) / 2,(color.getBlue() + component.getColor().getBlue()) / 2);
				gr2D.setColor(color);
				pont = component.transform.transform(pont);
				drawEllipse(pont,gr2D,scale);								
			}
			
			g.drawImage(img,0,0,null);
		}
	}
}

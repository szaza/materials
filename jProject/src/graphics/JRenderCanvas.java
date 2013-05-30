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
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;

import javax.swing.JPanel;

import collect.FractalComponent;
import collect.Triangle;

public class JRenderCanvas extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private int width;
	private int height;
	private int scale;
	private int itNumber;
	private double boxDimension;
	private Color bgColor;
	private Triangle defTriang;
	private volatile LinkedList <FractalComponent> componentList;
	private BufferedImage img;
	
	public JRenderCanvas() {
		this (200,150);
	}
	
	public JRenderCanvas(int width, int height) {
		this.width = width;
		this.height = height;
		this.boxDimension = 0;
		this.bgColor = Color.black;
		this.scale = width/6;
		this.itNumber = 30000;
		this.setBackground(bgColor);
		this.setPreferredSize(new Dimension(width,height));
	}
	
	public void drawEllipse(Point2D.Double pont,Graphics2D gr2D){
		Shape shape;		
		shape = new Ellipse2D.Float((float)(pont.x * scale),(float)(pont.y*scale), 0.1f, 0.0f);
		gr2D.draw(shape);
	}
	
	@Override
	public void paint(Graphics g) {
		int index;
		int mapKey;
		int[][] boxMargin = new int[2][2];
		int xKoord;
		int yKoord;
		
		Color color;
		Point2D.Double pont;
		Graphics2D  gr2D = null;
		Random rnd = new Random();
		FractalComponent component;
		AffineTransform transform;
		Map<Object,Integer> map = new HashMap<Object,Integer>();	//Rogzitem a kirajzolt pontokat a koordinatajuk szerint. (A boxdimenzio szamolasahoz kell)
		
		img = new BufferedImage(getWidth(),getHeight(),BufferedImage.TYPE_INT_ARGB);	
		
		gr2D = (Graphics2D) img.getGraphics();
		
		g.setColor(bgColor);
		g.fillRect(0, 0, width, height);
		
		transform = gr2D.getTransform();
		transform.scale(1,-1);
		transform.translate(0,-height);
		gr2D.setTransform(transform);
		
		if ((componentList != null) && (!componentList.isEmpty())) {	
			
			gr2D.translate(width/2,height/2);		
			
			pont = new Point2D.Double(1,0);
			color = componentList.get(0).getColor();
			
			boxMargin[0][0] = width;
			boxMargin[1][0] = height;
			
			for (int i=0; i<itNumber; i++) {
				try {
					index = rnd.nextInt(componentList.size());
					
					try {
						component = componentList.get(index);
						color = new Color((color.getRed() + component.getColor().getRed()) / 2,(color.getGreen() + component.getColor().getGreen()) / 2,(color.getBlue() + component.getColor().getBlue()) / 2);
						gr2D.setColor(color);
						pont = component.transform.transform(pont);
						drawEllipse(pont,gr2D);
						
						xKoord = (int)(pont.x * scale);
						yKoord = (int)(pont.y*scale);
						
						mapKey = width * xKoord + yKoord;
						if (!map.containsKey(mapKey)) map.put(mapKey, 1);
						
						if (boxMargin[0][0] > xKoord) boxMargin[0][0] = xKoord; //Bal szele
						if (boxMargin[0][1] < xKoord) boxMargin[0][1] = xKoord; //Jobb szele
						
						if (boxMargin[1][0] > yKoord) boxMargin[1][0] = yKoord; //Teteje
						if (boxMargin[1][1] < yKoord) boxMargin[1][1] = yKoord; //Alja
							
					} catch (NullPointerException e) {
						System.out.println("NullPointer exception: " + index + " - " + componentList.size());
					}
				} catch (IllegalArgumentException e) {
					System.out.println("Illegal Argument EXception: " + componentList.size());
				}						
			}
			
			g.drawImage(img,0,0,null);
		}
		
		//Box-dimenzio szamolasa
		int shapeWidth = boxMargin[0][1] - boxMargin[0][0] + 1;
		int shapeHeight = boxMargin[1][1] - boxMargin[1][0] + 1;
		double r = Math.max(shapeWidth,shapeHeight);
		
		if (r == 0) boxDimension = 0;
		else {
			boxDimension = Math.log10(map.size()) / Math.log10(r);
		}
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

	public int getItNumber() {
		return itNumber;
	}

	public void setItNumber(int itNumber) {
		this.itNumber = itNumber;
	}

	public double getBoxDimension() {
		return boxDimension;
	}	
}

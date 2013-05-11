package graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

import ui.UIFrame;

import collect.Item;
import collect.Polygon2D;
import collect.Triangle;
import collect.TriangleList;

public class JCanvas extends JPanel {

	private static final long serialVersionUID = 1L;
	private int width;
	private int height;
	private double offsetX;
	private double offsetY;
	private double scale;
	private BufferedImage img;
	private AffineTransform transform;
	private TriangleList tList;
	public Triangle defTriang;
	
	public JCanvas() {
		width=700;
		height=600;
		scale = 1.0f;
		transform = new AffineTransform();
		defTriang = UIFrame.defTriangle;
		
		setPreferredSize(new Dimension(width,height));
		
		this.addMouseWheelListener(new MouseWheelListener() {
			//Scroll eseten skalazast vegez el
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				float mod = e.getUnitsToScroll() / 3;
				mod = mod / 10;
				
				if (((scale + mod) <= 12.0f) && ((scale + mod) >= 1.0f)) {
					scale += mod;
				    offsetX = e.getX() * (1.0 - scale) / scale;
				    offsetY = e.getY() * (1.0 - scale) / scale;					
					offsetX = (offsetX < 0) ? offsetX : 0;
					offsetY = (offsetY < 0) ? offsetY : 0;
					
					repaint();
				}
			}
		});
	}
	
	public void init() {
		Graphics gr;
		//Egy image canvasra rajzol
		img = new BufferedImage(getWidth(),getHeight(),BufferedImage.TYPE_INT_ARGB);
		gr = img.getGraphics();
		
		//Hatterszin beallitasa
		gr.setColor(Color.DARK_GRAY);
		gr.fillRect(0, 0,width,height);
		
		//Fuggoleges vonalak
		gr.setColor(Color.gray);
		for (int i=0; i<width; i=i+10) {
			gr.drawLine(i,0,i,height);
		}
		
		//Vizszintes vonalak
		for (int j=0; j<width; j=j+10) {
			gr.drawLine(0,j,width,j);
		}		
		
		//Fuggoleges kozepvonal
		gr.setColor(Color.BLUE);
		gr.drawLine(width/2, 0, width/2, height);
		
		//Vizszintes kozepvonal
		gr.setColor(Color.RED);
		gr.drawLine(0, height/2, width, height/2);		
	}
	
	public void drawTriangles(Graphics2D g) {
		Item current = tList.first;
		Polygon2D p;
		
		//Alap haromszog kirajzolasa
		defTriang = UIFrame.defTriangle;
		p = defTriang.getPolygon(width/2,height/2,50);
		g.setColor(Color.WHITE);
		g.draw(p);
		
		//A megadott haromszogek kirajzolasa
		while (current != null) {
			p = current.triang.getPolygon(width/2,height/2,50);
			g.setColor(Color.CYAN);
			g.draw(p);
			current = current.next;
		}
	}
	
	//Frissiti a canvas listajat
	public void setTList(TriangleList tList) {
		this.tList = tList;
		repaint();
	}
	
	@Override 
	public void paint(Graphics g) {				
		if (img == null) {
			init();
		}	
		
		Graphics2D  gr2D = (Graphics2D) g;
		transform = gr2D.getTransform();
		transform.scale(scale,scale);
		transform.translate(offsetX,offsetY);
		gr2D.setTransform(transform);
		
		gr2D.drawImage(img,0,0,null);
		drawTriangles(gr2D);
		
	}	
}

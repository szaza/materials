package graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import javax.swing.JPanel;

import ui.UIFrame;
import collect.FractalComponent;
import collect.Polygon2D;
import collect.Triangle;

public class JCanvas extends JPanel {

	private static final long serialVersionUID = 1L;
	private int width;
	private int height;
	private double offsetX;
	private double offsetY;
	private double scale;
	private boolean fVisible;
	private boolean gVisible;
	private BufferedImage img;
	private AffineTransform transform;
	private LinkedList <FractalComponent> fComponentList;
	private LinkedList <FractalComponent> gComponentList;
	private Triangle defTriang;	
	
	public JCanvas() {
		width=700;
		height=600;
		scale = 1.0f;
		defTriang = UIFrame.defTriangle;
		fVisible = true;
		gVisible = false;
		
		transform = new AffineTransform();
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
	
	public void drawTexts(Graphics2D g,Triangle triang,String label,float scalex,float scaley) {
		float x3;
		float y3;
		
		g.drawString("A",(float)(triang.A.x *scalex -10),(float)(triang.A.y *scaley +10));
		g.drawString("B",(float)(triang.B.x *scalex +10),(float)(triang.B.y *scaley +10));
		g.drawString("C",(float)(triang.C.x *scalex -10),(float)(triang.C.y *scaley -5));
		
		//Haromszog sulypontja
		x3 = ((triang.A.x * scalex + triang.B.x * scalex + triang.C.x * scalex) / 3);
		y3 = ((triang.A.y * scaley + triang.B.y * scaley + triang.C.y * scaley) / 3);
		
		g.drawString(label,x3,y3);
	}
	
	public void drawTriangles(Graphics2D g) {
		Polygon2D p;
		float scalex = width / 10;
		float scaley = -scalex;
		
		g.translate(width/2,height/2);
		
		//Alap haromszog kirajzolasa
		defTriang = UIFrame.defTriangle;
		p = defTriang.getPolygon(scalex,scaley);
		g.setColor(Color.WHITE);
		g.draw(p);
		
		if (!fComponentList.isEmpty() && fVisible) {			
			//A megadott haromszogek kirajzolasa
			for (FractalComponent component: fComponentList) {
				g.setColor(component.getColor());
				drawTexts(g,component.getTriang(),"F",scalex,scaley);
				p = component.getTriang().getPolygon(scalex,scaley);
				g.draw(p);
			}		
		}

		
		if (!gComponentList.isEmpty() && gVisible) {
			g.setColor(Color.orange);
			
			//A megadott haromszogek kirajzolasa
			for (FractalComponent component: gComponentList) {
				g.setColor(component.getColor());
				drawTexts(g,component.getTriang(),"G",scalex,scaley);
				p = component.getTriang().getPolygon(scalex,scaley);
				g.draw(p);			
			}		
		}		
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
	
	//Frissiti a canvas listajat
	public void setFComponentList(LinkedList <FractalComponent> fComponentList) {
		this.fComponentList = fComponentList;
		repaint();
	}

	public void setGComponentList(LinkedList <FractalComponent> gComponentList) {
		this.gComponentList = gComponentList;
		repaint();
	}

	public void setfVisible(boolean fVisible) {
		this.fVisible = fVisible;
		repaint();
	}

	public void setgVisible(boolean gVisible) {
		this.gVisible = gVisible;
		repaint();
	}		
}

package graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import collect.FractalComponent;
import collect.Triangle;

//A fraktálok képét kirajzoló és megjelenítő felület
public class JRenderCanvas extends JPanel implements ChangeListener {

	private static final long serialVersionUID = 1L;
	
	private int width;
	private int height;
	private int scale;
	private int itNumber;
	private int mouseX;
	private int mouseY;
	private double tmpOffsetX;
	private double tmpOffsetY;	
	private double offsetX;
	private double offsetY;	
	private double boxDimension;
	private Color bgColor;
	private Triangle defTriang;
	private BufferedImage img;
	private volatile LinkedList <FractalComponent> componentList;
	private ArrayList<ChangeListener> listenerList;
	
	public JRenderCanvas() {
		this (200,150);
	}
	
	public JRenderCanvas(int width, int height) {
		this.width = width;
		this.height = height;
		
		boxDimension = 0;
		bgColor = Color.black;
		scale = width/6;
		itNumber = 30000;
		offsetX = 0;
		offsetY = 0;
		tmpOffsetX = 0;
		tmpOffsetY = 0;
		mouseX = 0;
		mouseY = 0;
		
		listenerList = new ArrayList<ChangeListener>();
		this.setBackground(bgColor);
		this.setPreferredSize(new Dimension(width,height));
		
		this.addMouseListener(new MouseAdapter(){
			@Override
			public void mousePressed(MouseEvent e) {
				mouseX = e.getX();
				mouseY = e.getY();
				tmpOffsetX = offsetX;
				tmpOffsetY = offsetY;
			}
			
			@Override
			public void mouseReleased(MouseEvent e) {
				offsetX = tmpOffsetX + e.getX() - mouseX;
				offsetY = tmpOffsetY + mouseY - e.getY();
				repaint();
			}			
		});
		
		this.addMouseMotionListener(new MouseMotionAdapter(){
			public void mouseDragged(MouseEvent e) {
				offsetX = tmpOffsetX + e.getX() - mouseX;
				offsetY = tmpOffsetY + mouseY - e.getY();	
				repaint();
			}
		});
		
		this.addMouseWheelListener(new MouseWheelListener() {
			// Scroll esetén skálázást végez el			
			
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				float mod = e.getUnitsToScroll();
				
				if (((scale + mod) <= 1200.0f) && ((scale + mod) >= 60.0f)) {
					scale += mod;
					offsetX -= (e.getX() - JRenderCanvas.this.width / 2) * 30 / scale;
					offsetY += (e.getY() - JRenderCanvas.this.height / 2) * 30 / scale;
					repaint();
				}
			}
		});		
	}
	
	//Kirajzolja a pixeleket
	public void drawEllipse(Point2D.Double pont,Graphics2D gr2D){
		Shape shape;		
		shape = new Ellipse2D.Float((float)(pont.x * scale),(float)(pont.y*scale), 0.1f, 0.0f);
		gr2D.draw(shape);
	}
	
	//A box-dimenziót kiszámoló függvény
	public void calculateBoxDimension(int mapSize,int[][] boxMargin) {
		int shapeWidth = Math.abs(boxMargin[0][1] - boxMargin[0][0]) + 1;
		int shapeHeight = Math.abs(boxMargin[1][1] - boxMargin[1][0]) + 1;
		double r = Math.max(shapeWidth,shapeHeight);
		
		if (r == 1) boxDimension = 0;
		else {
			boxDimension = Math.log10(mapSize) / Math.log10(r);
		}
		
		ChangeEvent e = new ChangeEvent(this);
		stateChanged(e);		
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
		Map<Object,Integer> map = new HashMap<Object,Integer>();	//Rögzítem a kirajzolt pontokat a koordinatajuk szerint. (A box-dimenzió számolásához kell)
		
		img = new BufferedImage(getWidth(),getHeight(),BufferedImage.TYPE_INT_ARGB);	
		
		gr2D = (Graphics2D) img.getGraphics();
		
		g.setColor(bgColor);
		g.fillRect(0, 0, width, height);
		
		//Megfordítom a canvast, hogy ne fejjel lefele jelenjenek meg az ábrák
		transform = gr2D.getTransform();
		transform.scale(1,-1);
		transform.translate(0,-height);
		gr2D.setTransform(transform);
		
		if ((componentList != null) && (!componentList.isEmpty())) {	
			
			gr2D.translate(width/2+offsetX,height/2+offsetY);		
			
			component = componentList.get(0);
			
			pont = new Point2D.Double(component.triang.A.x,component.triang.A.y);
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
						
						//Megkeresi a kirajzolt ábrát
						if ((boxMargin[0][0] > xKoord) && (xKoord > 0)) boxMargin[0][0] = xKoord; //Bal széle
						if (boxMargin[0][1] < xKoord) boxMargin[0][1] = xKoord; //Jobb széle
						
						if ((boxMargin[1][0] > yKoord) && (yKoord > 0)) boxMargin[1][0] = yKoord; //Teteje
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
		
		calculateBoxDimension(map.size(),boxMargin);
	}
	
	public void reset() {
		offsetX = 0;
		offsetY = 0;
		tmpOffsetX = 0;
		tmpOffsetY = 0;
		mouseX = 0;
		mouseY = 0;		
	}
	
	public synchronized void addChangeListener(ChangeListener listener){
		listenerList.add(listener);
	}	

	public synchronized void removeChangeListener(ChangeListener listener){
		listenerList.remove(listener);
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
	
	@Override
	public void stateChanged(ChangeEvent e) {
		synchronized (this) {
			for (ChangeListener i : listenerList) {
				i.stateChanged(e);
			}
		}
	}
}

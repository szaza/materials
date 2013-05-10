package graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.renderable.RenderableImage;

import javax.swing.JPanel;

public class JCanvas extends JPanel {

	private static final long serialVersionUID = 1L;
	private int width;
	private int height;
	private double offsetX;
	private double offsetY;
	private double scale;
	private BufferedImage img;
	private Graphics gr;
	private Graphics2D gr2D;
	private AffineTransform transform;
	
	public JCanvas() {
		width=700;
		height=600;
		scale = 1.0f;
		transform = new AffineTransform();
		
		setPreferredSize(new Dimension(width,height));
		
		this.addMouseWheelListener(new MouseWheelListener() {
			
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
		img = new BufferedImage(getWidth(),getHeight(),BufferedImage.TYPE_INT_ARGB);
		gr = img.getGraphics();
		
		gr.setColor(Color.DARK_GRAY);
		gr.fillRect(0, 0,width,height);
		
		gr.setColor(Color.gray);
		for (int i=0; i<width; i=i+10) {
			gr.drawLine(i,0,i,height);
		}
		
		for (int j=0; j<width; j=j+10) {
			gr.drawLine(0,j,width,j);
		}		
		
		gr.setColor(Color.BLUE);
		gr.drawLine(width/2, 0, width/2, height);
		
		gr.setColor(Color.RED);
		gr.drawLine(0, height/2, width, height/2);		
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
		
		gr2D.drawRenderedImage(img, transform);
	}	
}

package graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import javax.swing.JPanel;

public class JCanvas extends JPanel {

	private static final long serialVersionUID = 1L;
	private int width;
	private int height;
	private float scale;
	
	public JCanvas() {
		width=700;
		height=600;
		scale = 6;
		this.setPreferredSize(new Dimension(width,height));
		this.setBackground(Color.DARK_GRAY);
		
		this.addMouseWheelListener(new MouseWheelListener() {
			
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				float mod = e.getUnitsToScroll() / 3;
				if ((scale + mod < 12) && (scale + mod > 1)) {
					scale += mod;
					//scale(universe,scale);
				}
			}
		});
	}
	
	@Override 
	public void paint(Graphics g) {
		super.paintComponent(g);
		
		g.setColor(Color.WHITE);
		g.drawLine(0,0,40,40);
	}	
}

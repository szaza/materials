package graphics;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

public class JCanvas extends JPanel {

	private static final long serialVersionUID = 1L;
	private int width;
	private int height;
	
	public JCanvas() {
		width=700;
		height=600;
		this.setPreferredSize(new Dimension(width,height));
		this.setBackground(Color.BLACK);
	}
}

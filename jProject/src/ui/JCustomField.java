package ui;

import java.awt.Dimension;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener; 
import javax.swing.JTextField;

public class JCustomField extends JTextField {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int width = 150;
	private int height = 26;

	public JCustomField() {
		this.setPreferredSize(new Dimension(width, height));
		this.setMinimumSize(new Dimension(width, height));
		this.addFocusListener(new focusListener()); 
	}

	public JCustomField(String s) {
		this.setPreferredSize(new Dimension(width, height));
		this.setMinimumSize(new Dimension(width, height));
		this.setText(s);
		this.addFocusListener(new focusListener()); 
	}

	public JCustomField(int width,int height,String s) {
		this.width = width;
		this.height = height;
		this.setPreferredSize(new Dimension(width, height));
		this.setMinimumSize(new Dimension(width, height));
		this.setText(s);
		this.addFocusListener(new focusListener()); 
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
	
	class focusListener implements FocusListener {
	
	    @Override
	    public void focusGained(FocusEvent e) {
	      ((JCustomField) e.getSource()).selectAll();      
	    }
	
	    @Override
	    public void focusLost(FocusEvent e) {
	      // TODO Auto-generated method stub
	      
	    }
	    
	  }
}

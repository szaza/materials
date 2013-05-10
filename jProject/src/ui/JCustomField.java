package ui;

import java.awt.Dimension;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
		this.addKeyListener(new keyListener());
	}

	public JCustomField(String s) {
		this.setPreferredSize(new Dimension(width, height));
		this.setMinimumSize(new Dimension(width, height));
		this.setText(s);
		this.addFocusListener(new focusListener());
		this.addKeyListener(new keyListener());
	}

	public JCustomField(int width, int height, String s) {
		this.width = width;
		this.height = height;
		this.setPreferredSize(new Dimension(width, height));
		this.setMinimumSize(new Dimension(width, height));
		this.setText(s);
		this.addFocusListener(new focusListener());
		this.addKeyListener(new keyListener());
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
			UIFrame.canvas.repaint();
		}

	}

	class keyListener implements KeyListener {

		@Override
		public void keyPressed(KeyEvent e) {

		}

		@Override
		public void keyReleased(KeyEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void keyTyped(KeyEvent e) {
			char c = e.getKeyChar();
			if (c != '-') {
				if (((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)
						&& (c != '.')) {
					e.consume(); // ignore event
				} else {
					try {
						String str = ((JTextField) (e.getSource())).getText();
						str += c;
						Float.parseFloat(str);
					} catch (Exception e2) {
						e.consume();
					}
				}
			}
		}

	}
}

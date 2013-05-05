package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class JAddTriangFrame {

	private int x;
	private int y;
	private int width;
	private int height;
	String title;

	JFrame frame;
	JPanel buttonContainer;
	JButton ok;
	JButton cancel;
	JTriangPanel triangSettings;

	public JAddTriangFrame() {
		this("Háromszög hozzáadása", 30, 30, 300, 300);
	}

	public JAddTriangFrame(String title, int x, int y, int width, int height)
			throws HeadlessException {
		super();
		this.title = title;
		this.setX(x);
		this.setY(y);
		this.setWidth(width);
		this.setHeight(height);
		
		frame = new JFrame();
		frame.setBounds(x, y, width, height);
		frame.setTitle(title);
		frame.setLayout(new BorderLayout());

		
		ok = new JButton("Ok");
		cancel = new JButton("Mégse");
		buttonContainer = new JPanel();
		triangSettings = new JTriangPanel();
		triangSettings.setSize(new Dimension(frame.getWidth(), frame.getHeight()));
		buttonContainer.add(ok);
		buttonContainer.add(cancel);
		frame.add(buttonContainer, BorderLayout.SOUTH);
		frame.add(triangSettings, BorderLayout.NORTH);
		frame.setResizable(false);
		frame.setVisible(true);
		
		cancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});	
		
		ok.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
				frame.dispose();			
			}
			
		});
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

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
}

package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import collect.TriangleList;

public class JAddTriangFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private int x;
	private int y;
	private int width;
	private int height;
	String title;
	TriangleList tList;

	JPanel buttonContainer;
	JButton ok;
	JButton cancel;
	JTriangPanel triangSettings;

	public JAddTriangFrame() {
		this("Háromszög hozzáadása", 30, 30, 300, 300,null);
	}
	
	public JAddTriangFrame(TriangleList tList) {
		this("Háromszög hozzáadása", 30, 30, 300, 300,tList);
	}

	public JAddTriangFrame(String title, int x, int y, int width, int height,TriangleList tList)
			throws HeadlessException {
		super();
		this.title = title;
		this.setX(x);
		this.setY(y);
		this.setWidth(width);
		this.setHeight(height);
		this.tList = tList;
		
		setBounds(x, y, width, height);
		setTitle(title);
		setLayout(new BorderLayout());

		
		ok = new JButton("Ok");
		cancel = new JButton("Mégse");
		buttonContainer = new JPanel();
		triangSettings = new JTriangPanel();
		triangSettings.setSize(new Dimension(getWidth(), getHeight()));
		buttonContainer.add(ok);
		buttonContainer.add(cancel);
		add(buttonContainer, BorderLayout.SOUTH);
		add(triangSettings, BorderLayout.NORTH);
		setResizable(false);
		setVisible(true);
		
		cancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});	
		
		ok.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				saveSettings();
				dispose();	
			}
			
		});
	}

	public void saveSettings() {
		tList.insertItem(triangSettings.getTriangSettings());
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
	
	public TriangleList gettList() {
		return tList;
	}
	
}

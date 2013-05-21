package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import collect.FractalComponent;
import collect.Transform;
import collect.Triangle;

public class JAddTriangFrame extends JDialog {

	private static final long serialVersionUID = 1L;
	private int x;
	private int y;
	private int width;
	private int height;
	private String title;
	
	private LinkedList <FractalComponent> componentList;
	public JButton ok;
	public JButton cancel;	
	private JLabel colorLabel;
	private JPanel colorContainer;
	private JPanel buttonContainer;
	private JTriangPanel triangSettings;
	
	private ColorSelector colorChooser;

	public JAddTriangFrame() {
		this("Háromszög hozzáadása", 30, 30, 300, 300,null);
	}
	
	public JAddTriangFrame(LinkedList <FractalComponent> componentList) {
		this("Háromszög hozzáadása", 30, 30, 300, 300,componentList);
	}

	public JAddTriangFrame(String title, int x, int y, int width, int height,LinkedList <FractalComponent> componentList)
		throws HeadlessException {
		super();
		this.title = title;
		this.setX(x);
		this.setY(y);
		this.setWidth(width);
		this.setHeight(height);
		this.componentList = componentList;
		
		setBounds(x, y, width, height);
		setTitle(title);
		
		setLayout(new BorderLayout());
		ok = new JButton("Ok");
		cancel = new JButton("Mégse");		
		buttonContainer = new JPanel();
		colorLabel = new JLabel("Válasszon színt: ");
		colorContainer = new JPanel();
		triangSettings = new JTriangPanel();
		triangSettings.setSize(new Dimension(getWidth(), getHeight()));
		colorChooser = new ColorSelector();
		
		buttonContainer.add(ok);	
		buttonContainer.add(cancel);
		
		colorContainer.add(colorLabel);
		colorContainer.add(colorChooser);

		add(buttonContainer,BorderLayout.SOUTH);
		add(triangSettings, BorderLayout.CENTER);
		add(colorContainer,BorderLayout.NORTH);
		setResizable(false);
		setVisible(true);
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
	
	public LinkedList <FractalComponent> getList() {
		return componentList;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void saveSettings() {
		Triangle triang = triangSettings.getTriangSettings();
		Transform transform = triang.toTransform();
		FractalComponent component = new FractalComponent(triang,transform,colorChooser.getColor());	
		componentList.add(component);
	}
}

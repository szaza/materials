package ui;

import collect.Triangle;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.FocusListener;
import java.awt.geom.Point2D;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import layout.SpringUtilities;
//A háromszög csúcspontjait beállító panel
public class JTriangPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	private String text;
	private int width;
	private int height;	
	
	private JPanel contentPanel;
	private JLabel axEdit;
	private JLabel ayEdit;
	private JLabel bxEdit;
	private JLabel byEdit;
	private JLabel cxEdit;
	private JLabel cyEdit;
	private JCustomField axField;
	private JCustomField ayField;
	private JCustomField bxField;
	private JCustomField byField;
	private JCustomField cxField;
	private JCustomField cyField;
	
	public JTriangPanel() {
		width = 200;
		height = 200;
		
		contentPanel = new JPanel();
		
		axEdit = new JLabel("Ax:");
		axField = new JCustomField("0");
		ayEdit = new JLabel("Ay:");
		ayField = new JCustomField("0");
		
		bxEdit = new JLabel("Bx:");
		bxField = new JCustomField("1");
		byEdit = new JLabel("By:");
		byField = new JCustomField("0");
		
		cxEdit = new JLabel("Cx:");
		cxField = new JCustomField("0");
		cyEdit = new JLabel("Cy:");
		cyField = new JCustomField("1");
		
		contentPanel.setLayout(new SpringLayout());
		contentPanel.add(axEdit);		
		contentPanel.add(axField);			
		contentPanel.add(ayEdit);		
		contentPanel.add(ayField);	
		contentPanel.add(bxEdit);		
		contentPanel.add(bxField);
		contentPanel.add(byEdit);		
		contentPanel.add(byField);			
		contentPanel.add(cxEdit);		
		contentPanel.add(cxField);
		contentPanel.add(cyEdit);		
		contentPanel.add(cyField);
		
		setPreferredSize(new Dimension(width,height));
        SpringUtilities.makeCompactGrid(contentPanel,6,2,3,3,3,3);		
        
        this.add(contentPanel,BorderLayout.CENTER);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
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
	
	public Triangle getTriangSettings() {
		Point2D.Float a = new Point2D.Float(Float.parseFloat(axField.getText()),Float.parseFloat(ayField.getText()));
		Point2D.Float b = new Point2D.Float(Float.parseFloat(bxField.getText()),Float.parseFloat(byField.getText()));
		Point2D.Float c = new Point2D.Float(Float.parseFloat(cxField.getText()),Float.parseFloat(cyField.getText()));
		Triangle triang = new Triangle(a,b,c);
		return triang;
	}
	
	public void setFields(Point2D.Float A,Point2D.Float B,Point2D.Float C) {
		axField.setText(String.format("%.6f",A.x));
		ayField.setText(String.format("%.6f",A.y));
		bxField.setText(String.format("%.6f",B.x));
		byField.setText(String.format("%.6f",B.y));
		cxField.setText(String.format("%.6f",C.x));
		cyField.setText(String.format("%.6f",C.y));		
	}	
	
	public void setFields(double ax,double ay,double bx,double by, double cx, double cy) {
		axField.setText(String.format("%.6f",ax));
		ayField.setText(String.format("%.6f",ay));
		bxField.setText(String.format("%.6f",bx));
		byField.setText(String.format("%.6f",by));
		cxField.setText(String.format("%.6f",cx));
		cyField.setText(String.format("%.6f",cy));		
	}		
	
	@Override
	public void addFocusListener(FocusListener action) {
	axField.addFocusListener(action);
	ayField.addFocusListener(action);
	bxField.addFocusListener(action);
	byField.addFocusListener(action);
	cxField.addFocusListener(action);
	cyField.addFocusListener(action);
	}	
	
	@Override
	public void setSize(Dimension d) {
		this.width = d.width;
		this.height = d.height;
	}	
}

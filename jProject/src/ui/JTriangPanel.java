package ui;

import collect.Point;
import collect.Triangle;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.FocusListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import layout.SpringUtilities;

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
		bxField = new JCustomField("0");
		byEdit = new JLabel("By:");
		byField = new JCustomField("0");
		
		cxEdit = new JLabel("Cx:");
		cxField = new JCustomField("0");
		cyEdit = new JLabel("Cy:");
		cyField = new JCustomField("0");
		
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
		Point a = new Point(Double.parseDouble(axField.getText()),Double.parseDouble(ayField.getText()));
		Point b = new Point(Double.parseDouble(bxField.getText()),Double.parseDouble(byField.getText()));
		Point c = new Point(Double.parseDouble(cxField.getText()),Double.parseDouble(cyField.getText()));
		Triangle triang = new Triangle(a,b,c);
		return triang;
	}
	
	public void setFields(Point A,Point B,Point C) {
		axField.setText(Double.toString(A.getX()));
		ayField.setText(Double.toString(A.getY()));
		bxField.setText(Double.toString(B.getX()));
		byField.setText(Double.toString(B.getY()));
		cxField.setText(Double.toString(C.getX()));
		cyField.setText(Double.toString(C.getY()));		
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

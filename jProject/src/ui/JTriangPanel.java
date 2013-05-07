package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import collect.Triangle;

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
		Point a = new Point(Integer.parseInt(axField.getText()),Integer.parseInt(ayField.getText()));
		Point b = new Point(Integer.parseInt(bxField.getText()),Integer.parseInt(byField.getText()));
		Point c = new Point(Integer.parseInt(cxField.getText()),Integer.parseInt(cyField.getText()));
		Triangle triang = new Triangle(a,b,c);
		return triang;
	}
	
	@Override
	public void setSize(Dimension d) {
		this.width = d.width;
		this.height = d.height;
	}
	
}

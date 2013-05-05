package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;

import layout.SpringUtilities;

public class JTriangPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	JPanel contentPanel;
	JLabel labelEdit1;
	JLabel axEdit;
	JLabel ayEdit;
	JLabel bxEdit;
	JLabel byEdit;
	JLabel cxEdit;
	JLabel cyEdit;
	JCustomField axField;
	JCustomField ayField;
	JCustomField bxField;
	JCustomField byField;
	JCustomField cxField;
	JCustomField cyField;
	private String text;
	private int width;
	private int height;
	
	public JTriangPanel() {
		text = "A háromszög beállításai:";
		width = 200;
		height = 200;
		
		contentPanel = new JPanel();
		labelEdit1 = new JLabel(text);
		labelEdit1.setHorizontalAlignment(SwingConstants.CENTER);		
		
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
        
        this.add(labelEdit1,BorderLayout.NORTH);
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
	
	@Override
	public void setSize(Dimension d) {
		this.width = d.width;
		this.height = d.height;
	}
	
}

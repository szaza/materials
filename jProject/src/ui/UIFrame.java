package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import collect.Point;

import javax.swing.*;

import collect.Triangle;
import collect.TriangleList;
import graphics.JCanvas;

public class UIFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel controlPanel;
	private JPanel contentPanel;
	private JPanel editPanel;
	private JTriangPanel innerPanel;
	private JMenuBar menuBar;
	private JMenuItem fileMenu;
	private JCanvas canvas;
	private JButton addTriang;
	private JComboBox<String> selectTriangle;
	private JLabel triangSettings;
	private JAddTriangFrame addFrame;
	
	TriangleList tList = new TriangleList();
	
	public UIFrame() {
		this.setTitle("Fractal");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		
		contentPanel = new JPanel();
		controlPanel = new JPanel();
		editPanel = new JPanel();
		innerPanel = new JTriangPanel();
		menuBar = new JMenuBar();
		fileMenu = new JMenuItem();
		canvas = new JCanvas();				
		selectTriangle = new JComboBox<String>();
		triangSettings = new JLabel("A Háromszög adatai:");
		addTriang = new JButton("Új háromszög");
		
		fileMenu.setText("File");
		menuBar.add(fileMenu);
		
		selectTriangle.setPreferredSize(new Dimension(150,26));
		
		controlPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		controlPanel.add(menuBar);
		
		editPanel.add(triangSettings);
		editPanel.add(selectTriangle);
		editPanel.add(innerPanel);
		editPanel.add(addTriang);
		editPanel.setPreferredSize(new Dimension(300,400));	
		
		contentPanel.setLayout(new BorderLayout());
		contentPanel.add(controlPanel,BorderLayout.NORTH);
		contentPanel.add(canvas,BorderLayout.CENTER);
		contentPanel.add(editPanel,BorderLayout.EAST);
		
		init();
		
		this.setContentPane(contentPanel);
		
       addTriang.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				addFrame = new JAddTriangFrame(tList);
				addFrame.ok.addActionListener(new okPressed());
				addFrame.ok.addActionListener(new exitDialog());
				addFrame.cancel.addActionListener(new exitDialog());
			}
        });	
       
       selectTriangle.addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				int index = selectTriangle.getSelectedIndex();	
				Triangle triang;
				triang = tList.getValue(index);
				innerPanel.setFields(triang.getA(),triang.getB(),triang.getC());
			}   
       });
	}
	
	class okPressed implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			addFrame.saveSettings();
			tList = addFrame.gettList();
			if (selectTriangle.getItemCount() < tList.getLength()) {
				for (int i=selectTriangle.getItemCount()+1; i<=tList.getLength(); i++) {
					selectTriangle.addItem(Integer.toString(i));
				}
			}
		}
		
	}
	
	class exitDialog implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			addFrame.dispose();			
		}
		
	}
	
	public void init() {
		Point A = new Point(1,1);
		Point B = new Point(-1,1);
		Point C = new Point(1,-1);
		
		tList.insertItem(new Triangle(A,B,C));
		selectTriangle.addItem("1");
		innerPanel.setFields(A,B,C);
	}
}

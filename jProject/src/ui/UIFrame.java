package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import collect.Item;
import collect.TriangleList;
import graphics.JCanvas;

public class UIFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	JPanel controlPanel;
	JPanel contentPanel;
	JPanel editPanel;
	JTriangPanel innerPanel;

	JMenuBar menuBar;
	JMenuItem fileMenu;
	JCanvas canvas;
	JButton addTriang;
	JAddTriangFrame addFrame;
	
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
		addTriang = new JButton("Új háromszög");
		
		
		fileMenu.setText("File");
		menuBar.add(fileMenu);
		
		controlPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		controlPanel.add(menuBar);
		
		editPanel.add(innerPanel,BorderLayout.NORTH);
		editPanel.add(addTriang,BorderLayout.SOUTH);
		editPanel.setPreferredSize(new Dimension(300,400));	
		
		contentPanel.setLayout(new BorderLayout());
		contentPanel.add(controlPanel,BorderLayout.NORTH);
		contentPanel.add(canvas,BorderLayout.CENTER);
		contentPanel.add(editPanel,BorderLayout.EAST);
		
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
	}
	
	class okPressed implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			addFrame.saveSettings();
			tList = addFrame.gettList();
			
			for (Item it = tList.first; it!=null; it = it.getNext()) {
				System.out.println(it);
			}				
		}
		
	}
	
	class exitDialog implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			addFrame.dispose();			
		}
		
	}
}

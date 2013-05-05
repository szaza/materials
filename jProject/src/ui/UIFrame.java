package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;

import collect.TriangleList;
import collect.TriangleListIterator;
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
		
        addTriang.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				final JAddTriangFrame addFrame = new JAddTriangFrame(tList);
				addFrame.addWindowListener(new WindowAdapter(){
					@Override
					public void windowClosing(WindowEvent e) {
						tList = addFrame.gettList();
						
						System.out.println("Ebbe pedig belep!");
						
						for (TriangleListIterator it = tList.getIterator(); it.hasMoreElements();) {
							System.out.println(it.nextElement());
						}	
					}
				});
			}
        });
        
		editPanel.add(innerPanel,BorderLayout.NORTH);
		editPanel.add(addTriang,BorderLayout.SOUTH);
		editPanel.setPreferredSize(new Dimension(300,400));	
		
		contentPanel.setLayout(new BorderLayout());
		contentPanel.add(controlPanel,BorderLayout.NORTH);
		contentPanel.add(canvas,BorderLayout.CENTER);
		contentPanel.add(editPanel,BorderLayout.EAST);
		
		this.setContentPane(contentPanel);
	}
}

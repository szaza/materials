package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
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
		
		selectTriangle.addItem("1");
		
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
}

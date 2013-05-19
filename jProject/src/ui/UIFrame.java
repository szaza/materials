package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import collect.Triangle;
import collect.FractalComponentList;
import graphics.JCanvas;
import graphics.JRenderCanvas;

public class UIFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel controlPanel;
	private JPanel contentPanel;
	private JPanel editPanel;
	
	private static FractalPanel fFractal;
	private static FractalPanel gFractal;
	
	private JTabbedPane fractalTab;
	
	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JMenuItem viewMenu;
	private JMenuItem saveMenu;
	private JMenuItem exitMenu;
	
	private FractalComponentList fList;
	private FractalComponentList gList;
	
	public static JCanvas canvas;
	public static JRenderCanvas rCanvas;
	public static Triangle defTriangle;	
	
	
	public UIFrame() {
		this.setTitle("Fractal");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		
		contentPanel = new JPanel();
		controlPanel = new JPanel();
		editPanel = new JPanel();
		
		fList = new FractalComponentList();
				
		menuBar = new JMenuBar();
		fileMenu = new JMenu("File");
		saveMenu = new JMenuItem("Save");
		viewMenu = new JMenuItem("View");
		exitMenu = new JMenuItem("Exit");
		canvas = new JCanvas();
		rCanvas = new JRenderCanvas();

		defTriangle = new Triangle(0.0f,0.0f,1.0f,0.0f,0.0f,1.0f);

		fFractal = new FractalPanel(this);
		gFractal = new FractalPanel(this);
		fractalTab = new JTabbedPane();
		
		fileMenu.add(saveMenu);
		fileMenu.add(viewMenu);
		fileMenu.add(exitMenu);

		menuBar.add(fileMenu);

		controlPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		controlPanel.add(menuBar);
		
		fractalTab.addTab("F-fractal", fFractal);
		fractalTab.addTab("G-fractal", gFractal);
		
		editPanel.add(fractalTab);
		editPanel.add(rCanvas);
		editPanel.setPreferredSize(new Dimension(300, 500));
		
		canvas.setFComponentList(fList);
		canvas.setGComponentList(gList);
		
		contentPanel.setLayout(new BorderLayout());
		contentPanel.add(controlPanel, BorderLayout.NORTH);
		contentPanel.add(canvas, BorderLayout.CENTER);
		contentPanel.add(editPanel, BorderLayout.EAST);

		this.setContentPane(contentPanel);
		
		refreshCanvas();

		viewMenu.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JDialog frame = new JDialog();
				JRenderCanvas rCanv = new JRenderCanvas(600,600);
				
				fList = fFractal.getFractalComponentList();
				//gList = gFractal.getFractalComponentList();
				rCanv.setComponentList(fList);				
				
				frame.setTitle("Rendered frame");
				frame.setSize(new Dimension(600,600));
				frame.add(rCanv);
				frame.setVisible(true);
				frame.setResizable(false);
			}
		});
	}
	
	public void refreshCanvas() {
		fList = fFractal.getFractalComponentList();
		gList = gFractal.getFractalComponentList();
		canvas.setFComponentList(fList);
		canvas.setGComponentList(gList);
		rCanvas.setComponentList(fList);
	}
}


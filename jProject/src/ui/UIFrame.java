package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;

import javax.swing.*;

import collect.Curves;
import collect.FractalComponent;
import collect.HFractal;
import collect.Transform;
import collect.Triangle;
import collect.WrappingObj;
import graphics.JCanvas;
import graphics.JRenderCanvas;

public class UIFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel controlPanel;
	private JPanel contentPanel;
	private JPanel editPanel;
	
	private JTabbedPane fractalTab;
	
	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JMenu editMenu;
	private JMenu viewMenu;
	private JMenu deformMenu;
	
	private JMenuItem newMenuItem;
	private JMenuItem viewMenuItem;
	private JMenuItem saveMenuItem;
	private JMenuItem loadMenuItem;
	private JMenuItem exitMenuItem;
	private JMenuItem viewControls;
	private JMenuItem deformItem;
	
	private JMenuItem curveMenu;
	private JMenuItem settingsMenu;
	
	private	 boolean fVisible;
	private boolean gVisible;
	
	private static FractalPanel fFractal;
	private static FractalPanel gFractal;	
	
	private LinkedList<FractalComponent> fList;
	private LinkedList<FractalComponent> gList;
	private LinkedList<Curves> cList;
	
	public static JCanvas canvas;
	public static Triangle defTriangle;
	
	private int itNumber;
	
	
	public UIFrame() {
		this.setTitle("Fraktál deformáció");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		
		this.fVisible = true;
		this.gVisible = true;
		
		this.itNumber = 30000;
		
		contentPanel = new JPanel();
		controlPanel = new JPanel();
		editPanel = new JPanel();
		
		fList = new LinkedList<FractalComponent>();
		gList = new LinkedList<FractalComponent>();
		cList = new LinkedList<Curves>();
		
		menuBar = new JMenuBar();
		fileMenu = new JMenu("Fájl");
		editMenu = new JMenu("Szerkesztés");
		viewMenu = new JMenu("Nézet");
		deformMenu = new JMenu("Deformáció");
		
		newMenuItem = new JMenuItem("Új");
		loadMenuItem = new JMenuItem("Betöltés");
		saveMenuItem = new JMenuItem("Mentés");
		viewMenuItem = new JMenuItem("Előnézet");
		exitMenuItem = new JMenuItem("Kilépés");
		viewControls = new JMenuItem("Kontrol pont");
		deformItem = new JMenuItem("Deformál");
		
		curveMenu = new JMenuItem("Görbék");
		settingsMenu = new JMenuItem("Beállítás");
		
		canvas = new JCanvas();

		defTriangle = new Triangle(0.0f,0.0f,1.0f,0.0f,0.0f,1.0f);
		Triangle gTriangle = new Triangle(2.0f,2.0f,3.0f,2.0f,2.0f,3.0f);
		
		
		fFractal = new FractalPanel(this,defTriangle);
		gFractal = new FractalPanel(this,gTriangle);
		fractalTab = new JTabbedPane();
		
		fileMenu.add(newMenuItem);
		fileMenu.add(loadMenuItem);
		fileMenu.add(saveMenuItem);
		fileMenu.add(exitMenuItem);
		
		editMenu.add(curveMenu);
		editMenu.add(settingsMenu);

		viewMenu.add(viewMenuItem);
		viewMenu.add(viewControls);
		
		deformMenu.add(deformItem);
		
		menuBar.add(fileMenu);
		menuBar.add(editMenu);
		menuBar.add(viewMenu);
		menuBar.add(deformMenu);

		controlPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		controlPanel.add(menuBar);
		
		fractalTab.addTab("F-fractal", fFractal);
		fractalTab.addTab("G-fractal", gFractal);
		
		editPanel.add(fractalTab);
		editPanel.setPreferredSize(new Dimension(300, 500));
		
		contentPanel.setLayout(new BorderLayout());
		contentPanel.add(controlPanel, BorderLayout.NORTH);
		contentPanel.add(canvas, BorderLayout.CENTER);
		contentPanel.add(editPanel, BorderLayout.EAST);

		this.setContentPane(contentPanel);
		
		fFractal.setItNumber(itNumber);
		gFractal.setItNumber(itNumber);
		
		refreshCanvas();

		viewMenuItem.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JDialog frame = new JDialog();
				JRenderCanvas rCanv = new JRenderCanvas(600,600);
				
				fList = fFractal.getFractalComponentList();
				rCanv.setComponentList(fList);		
				rCanv.setItNumber(itNumber);
				
				frame.setTitle("Rendered frame");
				frame.setSize(new Dimension(600,600));
				frame.add(rCanv);
				frame.setVisible(true);
				frame.setResizable(false);
			}
		});
		
		viewControls.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if (canvas.iscVisible()) {
					canvas.setcVisible(false);
					canvas.repaint();
				}
				else {
					canvas.setcVisible(true);
					canvas.repaint();
				}
			}
			
		});
		
		exitMenuItem.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);			
			}
			
		});
		
		saveMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				FileOutputStream fileOutput;
				ObjectOutputStream out;
				WrappingObj wrapObject;
				String fileName;
				String directory;
				JFileChooser fileChooser = new JFileChooser();
				int rVal = fileChooser.showSaveDialog(UIFrame.this);

				if (rVal == JFileChooser.APPROVE_OPTION) {
					fileName = fileChooser.getSelectedFile().getName();
					directory = fileChooser.getCurrentDirectory().toString();
					
					try {
						fileOutput = new FileOutputStream(directory + "/" + fileName);
						out = new ObjectOutputStream(fileOutput);
						
						wrapObject = new WrappingObj(cList,fList,gList);
												
						out.writeObject(wrapObject);
					
						out.close();
						fileOutput.close();
						
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}				
			}			
		});
		
		loadMenuItem.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileChooser = new JFileChooser();
				WrappingObj wrapObject;
				FileInputStream fileInput;
				ObjectInputStream in;
				String fileName;
				String directory;
				
				 int rVal = fileChooser.showOpenDialog(UIFrame.this);
			      if (rVal == JFileChooser.APPROVE_OPTION) {
					fileName = fileChooser.getSelectedFile().getName();
					directory = fileChooser.getCurrentDirectory().toString();	
					
			    	try {
			    		  
						fileInput = new FileInputStream(directory + "/" + fileName);
						in = new ObjectInputStream(fileInput);
						
						wrapObject = new WrappingObj();
						wrapObject = (WrappingObj) in.readObject();
						
						fList.clear();
						gList.clear();
						cList.clear();
						
						fList.addAll(wrapObject.getfFractal());
						gList.addAll(wrapObject.getgFractal());
						cList.addAll(wrapObject.getcList());
						
						fFractal.setFractalComponentList(fList);
						gFractal.setFractalComponentList(gList);
						
						in.close();
						fileInput.close();
						
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
			    	  
			      }					
			}
		});
		
		settingsMenu.addActionListener(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JDialog settingsDialog = new JSettingsPanel(itNumber);
				settingsDialog.setVisible(true);
				settingsDialog.setBounds(200,200,200,130);
				settingsDialog.setResizable(false);
				
				settingsDialog.addWindowListener(new WindowAdapter(){
					
					@Override
					public void windowClosed(WindowEvent e){
						setIterationNumber(((JSettingsPanel)e.getSource()).getItNumber());
					}
					
				});
			}
		});	
		
		curveMenu.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JDialog curvesDialog = new JCurvesPanel(cList,fList,gList);
				curvesDialog.setBounds(200,200,400,200);
				curvesDialog.setResizable(false);
				curvesDialog.setVisible(true);
				
				curvesDialog.addWindowListener(new WindowAdapter(){
					@Override
					public void windowClosed(WindowEvent e){
						setcList(((JCurvesPanel) e.getSource()).getcList());
					}
				});
			}
			
		});
		
		newMenuItem.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				fList.clear();
				gList.clear();
				cList.clear();
				
				Triangle triangle1 = new Triangle(0.0f,0.0f,1.0f,0.0f,0.0f,1.0f);
				Triangle triangle2 = new Triangle(2.0f,2.0f,3.0f,2.0f,2.0f,3.0f);				
				
				Transform transform1 = triangle1.toTransform();
				Transform transform2 = triangle2.toTransform();
				
				FractalComponent comp1 = new FractalComponent(triangle1,transform1,Color.GREEN);
				FractalComponent comp2 = new FractalComponent(triangle2,transform2,Color.GREEN);
				
				fList.add(comp1);
				gList.add(comp2);
				
				fFractal.setFractalComponentList(fList);
				gFractal.setFractalComponentList(gList);
			}
		});
		
		deformItem.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				boolean valid;
				Thread thread;
				
				//Ki van-e toltve az osszes gorbe
				valid = true;
				for (int i=0; i<cList.size(); i++) {
					if ((cList.get(i).getaCurve().length <=1) || (cList.get(i).getbCurve().length <=1) || (cList.get(i).getcCurve().length <=1)) valid=false;
				}
				
				if (valid) {
					HFractal hFractal = new HFractal(canvas,cList);
					thread = new Thread(hFractal);
					thread.start();
				}
				else {
					JOptionPane.showMessageDialog(null,"Kérem ellenőrízze, hogy megadta-e az összes görbét!","Hiba",JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
	}
	
	public void refreshCanvas() {
		fList = fFractal.getFractalComponentList();
		gList = gFractal.getFractalComponentList();
		
		fVisible = fFractal.isfVisible();
		gVisible = gFractal.isfVisible();
		
		canvas.setFComponentList(fList);
		canvas.setGComponentList(gList);
		canvas.setfVisible(fVisible);
		canvas.setgVisible(gVisible);
		cList = Curves.updateCurves(cList,fList,gList);
		canvas.setCurves(cList);
	}

	public void setIterationNumber(int itNumber) {
		this.itNumber = itNumber;
		fFractal.setItNumber(itNumber);
		gFractal.setItNumber(itNumber);
	}

	public int getItNumber() {
		return itNumber;
	}

	public LinkedList<Curves> getcList() {
		return cList;
	}

	public void setcList(LinkedList<Curves> cList) {
		this.cList = cList;
		canvas.setCurves(cList);
	}
}


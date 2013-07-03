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

//A főablak
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
	private JMenuItem viewCurves;
	private JMenuItem viewFComp;
	private JMenuItem viewGComp;
	private JMenuItem deformItem;
	private JMenuItem geneticItem;
	
	private JMenuItem curveMenu;
	private JMenuItem randomCurvesMenu;
	private JMenuItem settingsMenu;
	
	private int itNumber;
	
	private	 boolean fVisible;
	private boolean gVisible;
	
	private static FractalPanel fFractal;
	private static FractalPanel gFractal;	
	
	private LinkedList<FractalComponent> fList;
	private LinkedList<FractalComponent> gList;
	private LinkedList<Curves> cList;
	private HFractal hFractal;
	
	public static JCanvas canvas;
	public static Triangle defTriangle;
	
	
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
		viewControls = new JMenuItem("Kontrol pontok láthatósága");
		viewCurves = new JMenuItem("Görbék láthatósága");
		viewFComp = new JMenuItem("F komp. láthatósága");
		viewGComp = new JMenuItem("G kom. láthatósága");
		deformItem = new JMenuItem("Deformál");
		geneticItem = new JMenuItem("Genetikus");
		
		curveMenu = new JMenuItem("Görbék");
		randomCurvesMenu = new JMenuItem("Random görbék");
		settingsMenu = new JMenuItem("Beállítás");

		defTriangle = new Triangle(0.0f,0.0f,1.0f,0.0f,0.0f,1.0f);
		Triangle fTriangle = new Triangle(1.0f,-1.0f,2.0f,-1.0f,1.0f,0.0f);
		Triangle gTriangle = new Triangle(2.0f,2.0f,3.0f,2.0f,2.0f,3.0f);
		
		hFractal = null;
		
		fFractal = new FractalPanel(this,fTriangle);
		gFractal = new FractalPanel(this,gTriangle);
		fractalTab = new JTabbedPane();
		
		canvas = new JCanvas(fFractal,gFractal);
		
		menuBar.setPreferredSize(new Dimension(985,26));
		
		fileMenu.add(newMenuItem);
		fileMenu.add(loadMenuItem);
		fileMenu.add(saveMenuItem);
		fileMenu.add(exitMenuItem);
		
		editMenu.add(curveMenu);
		editMenu.add(randomCurvesMenu);
		editMenu.add(settingsMenu);

		viewMenu.add(viewMenuItem);
		viewMenu.add(viewControls);
		viewMenu.add(viewCurves);
		viewMenu.add(viewFComp);
		viewMenu.add(viewGComp);
		
		deformMenu.add(deformItem);
		deformMenu.add(geneticItem);
		
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
				
				if (fractalTab.getSelectedIndex() == 0) {
					fList = fFractal.getFractalComponentList();				
					rCanv.setComponentList(fList);
				}
				else {
					gList = gFractal.getFractalComponentList();				
					rCanv.setComponentList(gList);					
				}
				
				
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
				if (canvas.isControlsVisible()) {
					canvas.setControlsVisible(false);
					canvas.repaint();
				}
				else {
					canvas.setControlsVisible(true);
					canvas.repaint();
				}
			}
			
		});
		
		viewCurves.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if (canvas.isCurvesVisible()) {
					canvas.setCurvesVisible(false);
					canvas.repaint();
				}
				else {
					canvas.setCurvesVisible(true);
					canvas.repaint();
				}
			}
		});		
		
		viewFComp.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if (canvas.isfVisible()) {
					canvas.setfVisible(false);
					fFractal.setfVisible(false);
					canvas.repaint();
				}
				else {
					canvas.setfVisible(true);
					fFractal.setfVisible(true);
					canvas.repaint();
				}
			}
		});			
		
		viewGComp.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if (canvas.isgVisible()) {
					canvas.setgVisible(false);
					gFractal.setfVisible(false);
					canvas.repaint();
				}
				else {
					canvas.setgVisible(true);
					gFractal.setfVisible(true);
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
						
						//Töröljük a vászonról is a már kigenerált köztes fraktálokat
						canvas.sethComponentList(new LinkedList<FractalComponent>());
						
						fList.addAll(wrapObject.getfFractal());
						gList.addAll(wrapObject.getgFractal());
						cList.addAll(wrapObject.getcList());
						
						fFractal.setFractalComponentList(fList);
						gFractal.setFractalComponentList(gList);
						
						in.close();
						fileInput.close();
						
					} catch (FileNotFoundException e) {
						e.printStackTrace();
						JOptionPane.showMessageDialog(null,"Az állomány nem található!","Hiba",JOptionPane.ERROR_MESSAGE);
					} catch (IOException e) {
						e.printStackTrace();
						JOptionPane.showMessageDialog(null,"Hiba történt az állomány olvasásakor!","Hiba",JOptionPane.ERROR_MESSAGE);
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
						JOptionPane.showMessageDialog(null,"Az állomány nem felel meg a szabványnak!","Hiba",JOptionPane.ERROR_MESSAGE);
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
		
		randomCurvesMenu.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				JRandomCurvesPanel randomPanel = new JRandomCurvesPanel(UIFrame.this,fList,gList);				
				randomPanel.setBounds(100,100,230,200);
				randomPanel.setVisible(true);
				
				randomPanel.addWindowListener(new WindowAdapter(){
					@Override
					public void windowClosed(WindowEvent e){
						setcList(((JRandomCurvesPanel) e.getSource()).getcList());
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
				
				canvas.sethComponentList(null);
			}
		});
		
		deformItem.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				Thread thread = null;
				
				if (isCurvesValid()) {
					if ((hFractal == null) || (!hFractal.isVisible())) {
						hFractal = new HFractal(canvas,cList);
						hFractal.setVisible(true);
					}
					thread = new Thread(hFractal);
					thread.start();
				}
				else {
					JOptionPane.showMessageDialog(null,"Kérem ellenőrízze, hogy megadta-e az összes görbét!","Hiba",JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		geneticItem.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {				
				if (isCurvesValid()) {
					JGeneticPanel geneticPanel = new JGeneticPanel(UIFrame.this,cList);
					geneticPanel.setResizable(false);		
					geneticPanel.setVisible(true);						
				}
				else {
					JOptionPane.showMessageDialog(null,"Kérem ellenőrízze, hogy megadta-e az összes görbét!","Hiba",JOptionPane.ERROR_MESSAGE);
				}				
			}
			
		});
		
	}
	
	public boolean isCurvesValid() {
		boolean valid;
		
		//Ki van-e töltve az összes görbe
		valid = (!cList.isEmpty());
		for (int i=0; i<cList.size(); i++) {
			if ((cList.get(i).getaCurve().length <=1) || (cList.get(i).getbCurve().length <=1) || (cList.get(i).getcCurve().length <=1)) valid=false;
		}		
		
		return valid;
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


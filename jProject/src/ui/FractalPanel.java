package ui;

import graphics.JRenderCanvas;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import collect.FractalComponent;
import collect.Transform;
import collect.Triangle;

public class FractalPanel extends JPanel{

	private static final long serialVersionUID = 1L;
	private JPanel editPanel;
	private JPanel visiblePanel;
	private JTriangPanel triangPanel;
	private JTriangPanel transformPanel;
	private JTabbedPane tabPane;
	private JButton addTriang;
	private JButton removeTriang;
	private JComboBox<String> selectTriangle;
	private ColorSelector colorChooser;
	private JLabel triangSettings;
	private JLabel visibleLabel;
	private JLabel boxDim;
	private JCheckBox visibleCheck;
	private JAddTriangFrame addFrame;
	private LinkedList <FractalComponent> fList;
	private UIFrame uiFrame;
	private JRenderCanvas rCanvas;
	private Triangle triangle;
	private int itNumber;
	private boolean fVisible;
	
	public FractalPanel(UIFrame ui,Triangle triang) {
		
		uiFrame = ui;
		triangle = triang;
		fVisible = true;
		
		fList = new LinkedList <FractalComponent>();		
		
		visiblePanel = new JPanel();
		editPanel = new JPanel();
		triangPanel = new JTriangPanel();
		transformPanel = new JTriangPanel();
		tabPane = new JTabbedPane();	
		selectTriangle = new JComboBox<String>();
		colorChooser = new ColorSelector();
		triangSettings = new JLabel("A Háromszög adatai:");
		visibleLabel = new JLabel("Látható:");
		boxDim = new JLabel("Box dimenzió:");
		visibleCheck = new JCheckBox();
		addTriang = new JButton("Új háromszög");
		removeTriang = new JButton("Törlés");		
		rCanvas = new JRenderCanvas();
		
		rCanvas.setComponentList(fList);
		rCanvas.setItNumber(itNumber);
		selectTriangle.setPreferredSize(new Dimension(150, 26));
		visiblePanel.setPreferredSize(new Dimension(300,26));
		
		tabPane.addTab("Háromsz.", triangPanel);
		tabPane.addTab("Transzform.", transformPanel);
		
		visibleCheck.setSelected(true);
		visiblePanel.add(visibleLabel);
		visiblePanel.add(visibleCheck);
		
		editPanel.add(triangSettings);
		editPanel.add(selectTriangle);
		editPanel.add(colorChooser);
		editPanel.add(tabPane);
		editPanel.add(visiblePanel);
		editPanel.add(addTriang);
		editPanel.add(removeTriang);
		editPanel.add(rCanvas);
		editPanel.add(boxDim);
		editPanel.setPreferredSize(new Dimension(300,600));	
		
		this.add(editPanel);
		
		init();
		
		addTriang.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addFrame = new JAddTriangFrame(fList);
				addFrame.addWindowListener(new WindowAdapter(){
					
					@Override
					public void windowClosed(WindowEvent e){
						//Beteszi a lennyilo menube az uj elem sorszamat
						fList = addFrame.getList();
						
						if (selectTriangle.getItemCount() < fList.size()) {
							for (int i = selectTriangle.getItemCount() + 1; i <= fList
									.size(); i++) {
								selectTriangle.addItem(Integer.toString(i));
							}
						}
						
						setRemoveTriangButtonState();
						refresh();						
					}
					
				});
			}
		});

		selectTriangle.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				FractalComponent component;
				int index = selectTriangle.getSelectedIndex();
				
				component = fList.get(index);
				colorChooser.setColor(component.getColor());
				
				refresh();
			}
		});

		removeTriang.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int index = selectTriangle.getSelectedIndex();
				fList.remove(index);											//Torlom a listabol a megfelelo elemet
				selectTriangle.removeItemAt(selectTriangle.getItemCount() - 1);	//Torlom a lenyillo menubol az elem sorszamat
				setRemoveTriangButtonState();									//Beallitom a torles gomb erteket
				refresh();														//Frissitem az ablakokat
			}
		});
		
		colorChooser.addColorListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent e) {
				setComponents();			
			}
			
		});
		
		visibleCheck.addItemListener(new ItemListener(){

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					fVisible = true;	
					refresh();
				}
				else {
					fVisible = false;
					refresh();
				}
			}
			
		});
		
		rCanvas.addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent e) {
				//Boxdimenzio kiiratasa
				boxDim.setText("Box dimenzió: " + String.format("%.6f",rCanvas.getBoxDimension()));				
			}
			
		});
	}
	
	public void init() {
		Transform transform;
			
		triangPanel.setFields(triangle.A, triangle.B, triangle.C);
		transform = triangle.toTransform();
		
		fList.add(new FractalComponent(triangle,transform,colorChooser.getColor()));		
		
		selectTriangle.addItem("1");	//A lista elemszamat novelem 1-el
		
		triangPanel.addFocusListener(new triangListener());			//A haromszoget beallito mezokre figyelot teszek
		transformPanel.addFocusListener(new transformListener());	//A transzformaciokat beallito mezokre figyelot teszek
		
		rCanvas.setItNumber(itNumber);
		
		setRemoveTriangButtonState();		//A torles gomb allapotat frissitem
		refreshPanels();					//Frissitem a paneleket
	}	
	
	//Beallitom a torles gomb allapotat
	public void setRemoveTriangButtonState() {
		if (selectTriangle.getItemCount() > 1)
			removeTriang.setEnabled(true);
		else
			removeTriang.setEnabled(false);
	}

	//Frissitem a transzformacio adatait tarolo panelt
	public void refreshPanels() {
		int index = selectTriangle.getSelectedIndex();
		
		FractalComponent component;
		component = fList.get(index);
		
		//Frissiti a haromszog panelek adatait 
		triangPanel.setFields(component.triang.A, component.triang.B, component.triang.C);
		transformPanel.setFields(component.transform.getValue(0,2),component.transform.getValue(1,2),
								component.transform.getValue(0,1),component.transform.getValue(1,1),
								component.transform.getValue(0,0),component.transform.getValue(1,0));
		
		//Szin valaszto beallitasa
		colorChooser.setColor(component.getColor());
	}
	
	public void refresh() {
		refreshPanels();		
		rCanvas.repaint();		
		uiFrame.refreshCanvas();		
	}
	
	public void setComponents() {
		Triangle triang;
		Transform transform;
		FractalComponent component;
		int index = selectTriangle.getSelectedIndex();
		
		triang = triangPanel.getTriangSettings();	//Frissitem a kijelolt haromszog erteket
		transform = triang.toTransform();			//Ujraszamolom a transformaciot
		
		component = new FractalComponent(triang,transform,colorChooser.getColor());
		fList.set(index, component);
		
		refresh();	//Frissitem a paneleket		
	}

	class triangListener implements FocusListener {

		@Override
		public void focusGained(FocusEvent e) {

		}

		//Amikor egy mezo elveszti a fokuszt, akkor frissul a lista
		@Override
		public void focusLost(FocusEvent e) {
			setComponents();
		}

	}
	
	class transformListener implements FocusListener {

		@Override
		public void focusGained(FocusEvent e) {

		}

		//Amikor egy transform mezo elveszti a fokuszt, akkor frissul a lista
		@Override
		public void focusLost(FocusEvent e) {
			Triangle triang;
			Transform transform;
			FractalComponent component;
			
			triang = transformPanel.getTriangSettings();
			int index = selectTriangle.getSelectedIndex();			
			
			triang.B.x += triang.A.x;
			triang.B.y += triang.A.y;
			
			triang.C.x += triang.A.x;
			triang.C.y += triang.A.y;			
			
			transform = triang.toTransform();
		
			component = new FractalComponent(triang,transform,colorChooser.getColor());
			fList.set(index, component);
			refresh();
		}
	}
	
	public LinkedList <FractalComponent> getFractalComponentList() {
		return fList;
	}

	public void setFractalComponentList(LinkedList<FractalComponent> fList) {
		this.fList = fList;
		
		//Ha a lista kissebb mint az elozo, akkor ki kell toroljek elemeket a listabol
		while (selectTriangle.getItemCount() > fList.size()) {
			selectTriangle.removeItemAt(selectTriangle.getItemCount()-1);
		}
		
		//Ha uj fraktal komponensek toltottek be akkor novelem a select lista meretet
		for (int i=selectTriangle.getItemCount(); i<fList.size(); i++) {
			selectTriangle.addItem(Integer.toString(i+1));
		}
		
		refresh();
	}

	public boolean isfVisible() {
		return fVisible;
	}

	public int getItNumber() {
		return itNumber;
	}

	public void setItNumber(int itNumber) {
		this.itNumber = itNumber;
		rCanvas.setItNumber(itNumber);
		refresh();
	}
	
	public void setfVisible(boolean b) {
		fVisible = b;
		visibleCheck.setSelected(b);
		refresh();		
	}
}

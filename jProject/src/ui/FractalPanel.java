package ui;

import graphics.JRenderCanvas;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import collect.FractalComponent;
import collect.Transform;
import collect.Triangle;

public class FractalPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JPanel editPanel;
	private JTriangPanel triangPanel;
	private JTriangPanel transformPanel;
	private JTabbedPane tabPane;
	private JButton addTriang;
	private JButton removeTriang;
	private JComboBox<String> selectTriangle;
	private ColorSelector colorChooser;
	private JLabel triangSettings;
	private JAddTriangFrame addFrame;
	private LinkedList <FractalComponent> fList;
	private UIFrame uiFrame;
	private JRenderCanvas rCanvas;
	private Triangle triangle;
	
	public FractalPanel(UIFrame ui,Triangle triang) {
		
		uiFrame = ui;
		triangle = triang;
		
		fList = new LinkedList <FractalComponent>();		
		
		editPanel = new JPanel();
		triangPanel = new JTriangPanel();
		transformPanel = new JTriangPanel();
		tabPane = new JTabbedPane();	
		selectTriangle = new JComboBox<String>();
		colorChooser = new ColorSelector();
		triangSettings = new JLabel("A Háromszög adatai:");
		addTriang = new JButton("Új háromszög");
		removeTriang = new JButton("Törlés");		
		rCanvas = new JRenderCanvas();
		
		rCanvas.setComponentList(fList);
		selectTriangle.setPreferredSize(new Dimension(150, 26));
		
		tabPane.addTab("Háromsz.", triangPanel);
		tabPane.addTab("Transzform.", transformPanel);
		
		editPanel.add(triangSettings);
		editPanel.add(selectTriangle);
		editPanel.add(colorChooser);
		editPanel.add(tabPane);
		editPanel.add(addTriang);
		editPanel.add(removeTriang);
		editPanel.add(rCanvas);
		editPanel.setPreferredSize(new Dimension(300,500));	
		
		this.add(editPanel);
		
		init();
		
		addTriang.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addFrame = new JAddTriangFrame(fList);
				addFrame.ok.addActionListener(new okPressed());
				addFrame.ok.addActionListener(new exitDialog());
				addFrame.cancel.addActionListener(new exitDialog());
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
	}
	
	public void init() {
		Transform transform;
			
		triangPanel.setFields(triangle.A, triangle.B, triangle.C);
		transform = triangle.toTransform();
		
		fList.add(new FractalComponent(triangle,transform,colorChooser.getColor()));		
		
		selectTriangle.addItem("1");	//A lista elemszamat novelem 1-el
		
		triangPanel.addFocusListener(new triangListener());			//A haromszoget beallito mezokre figyelot teszek
		transformPanel.addFocusListener(new transformListener());	//A transzformaciokat beallito mezokre figyelot teszek
		
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
		FractalComponent component;
		int index = selectTriangle.getSelectedIndex();
		component = fList.get(index);
		
		//Frissiti a haromszog panelek adatait 
		triangPanel.setFields(component.triang.A, component.triang.B, component.triang.C);
		transformPanel.setFields(component.transform.getValue(0,2),component.transform.getValue(1,2),
								component.transform.getValue(0,1),component.transform.getValue(1,1),
								component.transform.getValue(0,0),component.transform.getValue(1,0));
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
	
	//Uj haromszog hozzaadasa
	class okPressed implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			//Lementi a beallitasokat, es kiszamolja az uj transformaciot
			addFrame.saveSettings();
			
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
	}

	//Kilep a haromszog hozzaadas dialogusbol
	class exitDialog implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			addFrame.dispose();
		}

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
		for (int i=selectTriangle.getItemCount(); i<fList.size(); i++) {
			selectTriangle.addItem(Integer.toString(i+1));
			refresh();
		}
	}
	
	

}

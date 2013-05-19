package ui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.geom.Point2D;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import collect.FractalComponent;
import collect.FractalComponentList;
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
	private JLabel triangSettings;
	private JAddTriangFrame addFrame;
	private FractalComponentList fList;
	private UIFrame uiFrame;
	
	public FractalPanel(UIFrame ui) {
		
		uiFrame = ui;
		
		fList = new FractalComponentList();		
		
		editPanel = new JPanel();
		triangPanel = new JTriangPanel();
		transformPanel = new JTriangPanel();
		tabPane = new JTabbedPane();	
		selectTriangle = new JComboBox<String>();
		triangSettings = new JLabel("A Háromszög adatai:");
		addTriang = new JButton("Új háromszög");
		removeTriang = new JButton("Törlés");		
		
		selectTriangle.setPreferredSize(new Dimension(150, 26));
		
		tabPane.addTab("Háromsz.", triangPanel);
		tabPane.addTab("Transzform.", transformPanel);		
		
		editPanel.add(triangSettings);
		editPanel.add(selectTriangle);
		editPanel.add(tabPane);
		editPanel.add(addTriang);
		editPanel.add(removeTriang);
		editPanel.setPreferredSize(new Dimension(300, 335));	
		
		this.add(editPanel);
		
		init();
		
		this.setVisible(true);
		
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
			public void itemStateChanged(ItemEvent arg0) {
				refresh();
			}
		});

		removeTriang.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int index = selectTriangle.getSelectedIndex();
				
				//Torlom a listabol a megfelelo elemet 
				fList.deleteItem(index);

				//Torlom a lenyillo menubol az elem sorszamat
				selectTriangle.removeItemAt(selectTriangle.getItemCount() - 1);
				
				//Beallitom a torles gomb erteket
				setRemoveTriangButtonState();
				
				//Frissitem az ablakokat
				refresh();
			}
		});
	}
	
	public void init() {
		Transform transform;
		FractalComponent component = new FractalComponent();
		
		//Hozzaadom az elso transzformaciot
		/*Point2D.Float A = new Point2D.Float(0.3f,-0.2f);
		Point2D.Float B = new Point2D.Float(0.9f,0.2f);
		Point2D.Float C = new Point2D.Float(-0.2f,0.6f);*/
		
		Point2D.Float A;
		Point2D.Float B; 
		Point2D.Float C;
		
		Triangle triang;
		
		A = new Point2D.Float(0.0f,0.0f);
		B = new Point2D.Float(0.0f,0.5f);
		C = new Point2D.Float(0.5f,0.0f);
		
		//Beszurom a listaba az elso elemet
		triang = new Triangle(A, B, C); 
		
		triangPanel.setFields(A, B, C);
		transform = triang.toTransform();
			
		component.setTriang(triang);
		component.setTransform(transform);
		
		fList.insertItem(component);
		
		A = new Point2D.Float(0.5f,0.0f);
		B = new Point2D.Float(0.5f,0.5f);
		C = new Point2D.Float(1.0f,0.0f);
		
		//Beszurom a listaba az elso elemet
		triang = new Triangle(A, B, C); 
		
		triangPanel.setFields(A, B, C);
		transform = triang.toTransform();
			
		component.setTriang(triang);
		component.setTransform(transform);
		
		fList.insertItem(component);	
		
		A = new Point2D.Float(0.0f,0.5f);
		B = new Point2D.Float(0.0f,1.0f);
		C = new Point2D.Float(0.5f,0.5f);
		
		//Beszurom a listaba az elso elemet
		triang = new Triangle(A, B, C); 
		
		triangPanel.setFields(A, B, C);
		transform = triang.toTransform();
			
		component.setTriang(triang);
		component.setTransform(transform);
		
		fList.insertItem(component);	
		
		//A lista elemszamat novelem 1-el
		selectTriangle.addItem("1");
		selectTriangle.addItem("2");
		selectTriangle.addItem("3");
		//A haromszoget beallito mezokre figyelot teszek
		triangPanel.addFocusListener(new triangListener());
		//A transzformaciokat beallito mezokre figyelot teszek
		transformPanel.addFocusListener(new transformListener());		
		//A torles gomb allapotat frissitem
		setRemoveTriangButtonState();
		
		//Frissitem a paneleket
		refreshPanels();
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
		component = fList.getValue(index);
		
		//Frissiti a haromszog panelek adatait 
		triangPanel.setFields(component.triang.A, component.triang.B, component.triang.C);
		transformPanel.setFields(component.transform.getValue(0,2),component.transform.getValue(1,2),
								component.transform.getValue(0,1),component.transform.getValue(1,1),
								component.transform.getValue(0,0),component.transform.getValue(1,0));
	}
	
	public void refresh() {
		refreshPanels();
		uiFrame.refreshCanvas();
	}
	
	//Uj haromszog hozzaadasa
	class okPressed implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			//Lementi a beallitasokat, es kiszamolja az uj transformaciot
			addFrame.saveSettings();
			//Beteszi a lennyilo menube az uj elem sorszamat
			fList = addFrame.getList();
			if (selectTriangle.getItemCount() < fList.getLength()) {
				for (int i = selectTriangle.getItemCount() + 1; i <= fList
						.getLength(); i++) {
					selectTriangle.addItem(Integer.toString(i));
				}
			}
			setRemoveTriangButtonState();
			uiFrame.refreshCanvas();
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
			Triangle triang;
			Transform transform;
			FractalComponent component = new FractalComponent();
			int index = selectTriangle.getSelectedIndex();
			
			//Frissitem a kijelolt haromszog erteket
			triang = triangPanel.getTriangSettings();
			component.setTriang(triang);
			
			//Ujraszamolom a transformaciot
			transform = triang.toTransform();
			component.setTransform(transform);
			
			fList.updateValue(component, index);
			
			//Frissitem a paneleket
			refresh();
		}

	}
	
	class transformListener implements FocusListener {

		@Override
		public void focusGained(FocusEvent e) {

		}

		//Amikor egy mezo elveszti a fokuszt, akkor frissul a lista
		@Override
		public void focusLost(FocusEvent e) {
			Triangle triang;
			Transform transform;
			FractalComponent component = new FractalComponent();
			triang = transformPanel.getTriangSettings();
			int index = selectTriangle.getSelectedIndex();			
			
			triang.B.x += triang.A.x;
			triang.B.y += triang.A.y;
			
			triang.C.x += triang.A.x;
			triang.C.y += triang.A.y;			
			
			transform = triang.toTransform();
			
			component.setTriang(triang);
			component.setTransform(transform);
			
			fList.updateValue(component, index);
			refresh();
		}
	}

	public FractalComponentList getFractalComponentList() {
		return fList;
	}

}

package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.geom.Point2D;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import collect.Curves;
import collect.FractalComponent;
import collect.Triangle;

import layout.SpringUtilities;
//A görbék adatait szerkesztő felület
public class JCurvesPanel extends JDialog {

	private static final long serialVersionUID = 1L;
	private JButton ok;
	private JButton cancel;
	private JButton help;
	private JPanel contentPanel;
	private JPanel settingsPanel;
	private JPanel buttonPanel;
	private JPanel selectPanel;
	private JLabel selectLabel;
	private JLabel aPointLabel;
	private JLabel bPointLabel;
	private JLabel cPointLabel;
	private JTextField aPointField;
	private JTextField bPointField;
	private JTextField cPointField;
	private JComboBox<String> selectConnection;
	private LinkedList<FractalComponent> fList;
	private LinkedList<FractalComponent> gList;
	private LinkedList<Curves> cList;

	public JCurvesPanel(LinkedList<Curves> cL,LinkedList<FractalComponent> fL,
			LinkedList<FractalComponent> gL) {
		setTitle("Görbék");
		this.fList = fL;
		this.gList = gL;
		this.cList = cL;

		ok = new JButton("Ok");
		cancel = new JButton("Mégse");
		help = new JButton("?");

		buttonPanel = new JPanel();
		selectPanel = new JPanel();
		contentPanel = new JPanel();
		settingsPanel = new JPanel();
		selectConnection = new JComboBox<String>();
		selectLabel = new JLabel("Válasszon:");
		aPointLabel = new JLabel("A: ");
		bPointLabel = new JLabel("B: ");
		cPointLabel = new JLabel("C: ");
		aPointField = new JTextField();
		bPointField = new JTextField();
		cPointField = new JTextField();

		selectConnection.setPreferredSize(new Dimension(150, 26));

		contentPanel.setLayout(new BorderLayout());
		settingsPanel.setLayout(new SpringLayout());

		settingsPanel.add(aPointLabel);
		settingsPanel.add(aPointField);
		settingsPanel.add(bPointLabel);
		settingsPanel.add(bPointField);
		settingsPanel.add(cPointLabel);
		settingsPanel.add(cPointField);

		SpringUtilities.makeCompactGrid(settingsPanel, 3, 2, 3, 3, 3, 3);

		selectPanel.add(selectLabel);
		selectPanel.add(selectConnection);

		buttonPanel.add(ok);
		buttonPanel.add(cancel);
		buttonPanel.add(help);

		contentPanel.add(selectPanel, BorderLayout.NORTH);
		contentPanel.add(settingsPanel, BorderLayout.CENTER);
		contentPanel.add(buttonPanel, BorderLayout.SOUTH);

		init();

		this.setContentPane(contentPanel);

		ok.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Triangle triang1;
				Triangle triang2;
				int index;
				FractalComponent component;	
				
				Point2D.Float[] aCurve = null;
				Point2D.Float[] bCurve = null;
				Point2D.Float[] cCurve = null;
				
				index = selectConnection.getSelectedIndex();
				
				component = fList.get(index);
				triang1 = component.getTriang();

				component = gList.get(index);
				triang2 = component.getTriang();				
				
				aCurve = addControlPoint(aPointField.getText(),aCurve,triang1.A,triang2.A);
				bCurve = addControlPoint(bPointField.getText(),bCurve,triang1.B,triang2.B);
				cCurve = addControlPoint(cPointField.getText(),cCurve,triang1.C,triang2.C);
				
				Curves curves = new Curves(index,aCurve, bCurve, cCurve,component.getColor());
			
				if (cList.size() > index) {
					cList.set(index, curves);
				} else {
					cList.add(curves);					
				}

				dispose();
			}

		});

		cancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}

		});

		help.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane
						.showMessageDialog(
								null,
								"<html>A pontokat a következő alakban adja meg: <br/> x1,y1,x2,y2,...,xn,yn</html>",
								"Tipp", JOptionPane.INFORMATION_MESSAGE);
			}

		});
		
		selectConnection.addItemListener(new ItemListener(){

			@Override
			public void itemStateChanged(ItemEvent arg0) {
				refreshFields();				
			}
			
		});
	}

	//Megnézi, hogy melyik háromszögek között húzható görbe
	public void init() {
		int min;

		min = (fList.size() < gList.size()) ? gList.size() : gList.size();

		for (int i = 0; i < min; i++) {
			selectConnection.addItem("f(" + (i + 1) + ") - g(" + (i + 1) + ")");
		}
		
		refreshFields();
	}

	public void setFieldText(JTextField textField,Point2D.Float[] points) {
		String s = new String("");
		
		for (int i=1; i<points.length-1; i++) {
			s += points[i].x + "," + points[i].y + ",";
		}
		
		if (s.length()>0) textField.setText(s.substring(0,s.length()-1));		
	}
	
	public void refreshFields() {
		int index;
		Curves curves;
		Point2D.Float[] points;
				
		index = selectConnection.getSelectedIndex();

		aPointField.setText("");
		bPointField.setText("");
		cPointField.setText("");		
		
		if (!cList.isEmpty() && (cList.size() > index)) {
			curves = cList.get(index);
			
			points = curves.getaCurve();
			setFieldText(aPointField,points);
			
			points = curves.getbCurve();
			setFieldText(bPointField,points);
			
			points = curves.getcCurve();
			setFieldText(cPointField,points);	
		}
	}
	
	//Kontrollpontok szerkesztése
	public Point2D.Float[] addControlPoint(String s,Point2D.Float[] points,Point2D.Float begin,Point2D.Float end) {
		int length;
		String[] koord;		
		
		if (s.compareTo("") > 0) {
			koord = s.split(",");
			length = koord.length;

			points = new Point2D.Float[length / 2 + 2];

			points[0] = begin;

			for (int i = 0; i < length - 1; i = i + 2) {
				points[i / 2 + 1] = new Point2D.Float(Float
						.parseFloat(koord[i]), Float
						.parseFloat(koord[i + 1]));
			}

			points[points.length - 1] = end;
		} else {
			points = new Point2D.Float[1];
			points[0] = new Point2D.Float(0.0f,0.0f);
		}	
		
		return points;
	}
	
	public LinkedList<Curves> getcList() {
		return cList;
	}

	public void setcList(LinkedList<Curves> cList) {
		this.cList = cList;
	}	
}

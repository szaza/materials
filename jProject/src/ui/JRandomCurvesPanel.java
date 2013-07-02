package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

import collect.Curves;
import collect.FractalComponent;
import collect.Triangle;

public class JRandomCurvesPanel extends JDialog {

	private static final long serialVersionUID = 1L;
	private JButton ok;
	private JButton cancel;
	private JPanel contentPanel;
	private JPanel buttonPanel;
	private JLabel setSize;
	private JLabel cpNumber;
	private JSlider setSlider;
	private JSlider cpnSlider;
	private LinkedList <FractalComponent> fList;
	private LinkedList <FractalComponent> gList;
	
	private LinkedList<Curves> cList;
	
	public JRandomCurvesPanel(UIFrame ui,LinkedList <FractalComponent> fL,LinkedList <FractalComponent> gL) {
		super();
		setTitle("Véletlenszerű görbék");
		
		this.fList = fL;
		this.gList = gL;
		
		ok = new JButton("Ok");
		cancel = new JButton("Mégse");
		contentPanel = new JPanel();
		buttonPanel = new JPanel();
		setSize = new JLabel("Értéktartomány:");
		cpNumber = new JLabel("Kontrol pontok száma:");
		setSlider = new JSlider(1,5);
		cpnSlider = new JSlider(0,10);
		
		cList = new LinkedList<Curves>();
		
		setSlider.setMajorTickSpacing(4);
		setSlider.setMinorTickSpacing(1);
		setSlider.setPaintTicks(true);
		setSlider.setPaintLabels(true);
		
		cpnSlider.setMajorTickSpacing(5);
		cpnSlider.setMinorTickSpacing(1);
		cpnSlider.setPaintTicks(true);
		cpnSlider.setPaintLabels(true);		
		
		buttonPanel.add(ok);
		buttonPanel.add(cancel);
		
		contentPanel.add(setSize);
		contentPanel.add(setSlider);
		contentPanel.add(cpNumber);
		contentPanel.add(cpnSlider);
		contentPanel.add(buttonPanel);
		
		this.setContentPane(contentPanel);
		this.setResizable(false);
		
		cancel.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		
		ok.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
				int min;
				Triangle triang1;
				Triangle triang2;
				FractalComponent component;
				
				Curves curves = null;
				
				//Kiszamolom, hogy hany darab gorbet kell generaljon
				min = (fList.size() < gList.size()) ? gList.size() : gList.size();
				
				for (int i = 0; i < min; i++) {
					component = gList.get(i);
					triang2 = component.getTriang();					
					
					component = fList.get(i);
					triang1 = component.getTriang();					
					
					curves = new Curves();
					curves.setRandomCurves(triang1,triang2,cpnSlider.getValue(),setSlider.getValue(),component.getColor());
					cList.add(curves);
				}
				
				dispose();
				
			}			
		});
	}

	public LinkedList<Curves> getcList() {
		return cList;
	}

	public void setcList(LinkedList<Curves> cList) {
		this.cList = cList;
	}
}

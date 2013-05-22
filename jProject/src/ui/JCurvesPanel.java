package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import collect.FractalComponent;

import layout.SpringUtilities;

public class JCurvesPanel extends JDialog{

	private static final long serialVersionUID = 1L;
	private JButton ok;
	private JButton cancel;	
	private JPanel contentPanel;
	private JPanel settingsPanel;
	private JPanel buttonPanel;
	private JPanel selectPanel;
	private JLabel selectLabel;
	private JLabel aPointLabel;
	private JLabel bPointLabel;
	private JLabel cPointLabel;	
	private JCustomField aPointField;
	private JCustomField bPointField;
	private JCustomField cPointField;
	private JComboBox<String> selectConnection;
	private LinkedList<FractalComponent> fList;
	private LinkedList<FractalComponent> gList;

	
	public JCurvesPanel(LinkedList<FractalComponent> fList,
			LinkedList<FractalComponent> gList) {
		setTitle("Görbék");
		this.fList = fList;
		this.gList = gList;
		
		contentPanel = new JPanel();
		settingsPanel = new JPanel();
		buttonPanel = new JPanel();
		selectPanel = new JPanel();
		selectConnection = new JComboBox<String>();
		ok = new JButton("Ok");
		cancel = new JButton("Mégse");
		selectLabel = new JLabel("Válasszon:");
		aPointLabel = new JLabel("A: ");
		bPointLabel = new JLabel("B: ");
		cPointLabel = new JLabel("C: ");
		aPointField = new JCustomField(360,26,"");
		bPointField = new JCustomField(360,26,"");
		cPointField = new JCustomField(360,26,"");		
		
		selectConnection.setPreferredSize(new Dimension(150,26));
		
		contentPanel.setLayout(new BorderLayout());
		settingsPanel.setLayout(new SpringLayout());
				
		settingsPanel.add(aPointLabel);
		settingsPanel.add(aPointField);
		settingsPanel.add(bPointLabel);
		settingsPanel.add(bPointField);
		settingsPanel.add(cPointLabel);
		settingsPanel.add(cPointField);		
		
		SpringUtilities.makeCompactGrid(settingsPanel,3,2,3,3,3,3);		
		
		selectPanel.add(selectLabel);
		selectPanel.add(selectConnection);

		buttonPanel.add(ok);
		buttonPanel.add(cancel);
		
		contentPanel.add(selectPanel,BorderLayout.NORTH);
		contentPanel.add(settingsPanel,BorderLayout.CENTER);
		contentPanel.add(buttonPanel,BorderLayout.SOUTH);
		
		init();
		
		this.setContentPane(contentPanel);
		
		ok.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();				
			}
			
		});
		
		cancel.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();				
			}
			
		});		
	}
	
	public void init() {
		int min;
		
		min = (fList.size() < gList.size()) ? gList.size() : gList.size();
		
		for (int i=0; i<min; i++) {
			selectConnection.addItem("f(" + (i+1) + ") - g(" + (i+1) + ")");
		}
	}
}

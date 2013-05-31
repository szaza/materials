package ui;

import graphics.JCanvas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import collect.Curves;
import collect.GeneticAlg;

public class JGeneticPanel extends JDialog {

	private static final long serialVersionUID = 1L;
	private JButton ok;
	private JButton cancel;
	private JPanel contentPanel;
	private JPanel buttonPanel;
	private JLabel bottomLimitLabel;
	private JLabel topLimitLabel;
	private JCustomField bottomLimitEdit;
	private JCustomField topLimitEdit;
	private JCanvas canvas;
	private LinkedList<Curves> cList;
	
	public JGeneticPanel(JCanvas canvas,LinkedList<Curves> cList) {
		super();
		setTitle("Genetikus algoritmus");
		
		this.canvas = canvas;
		this.cList = cList;
		
		ok = new JButton("Ok");
		cancel = new JButton("Mégse");
		contentPanel = new JPanel();
		buttonPanel = new JPanel();
		bottomLimitLabel = new JLabel("Alsó határ:");
		topLimitLabel = new JLabel("Alsó határ:");
		bottomLimitEdit = new JCustomField("1");
		topLimitEdit = new JCustomField("2");
		
		buttonPanel.add(ok);
		buttonPanel.add(cancel);
		
		contentPanel.add(bottomLimitLabel);
		contentPanel.add(bottomLimitEdit);
		contentPanel.add(topLimitLabel);
		contentPanel.add(topLimitEdit);
		contentPanel.add(buttonPanel);
		
		this.setContentPane(contentPanel);	
		
		ok.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				Thread thread = null;
				
				topLimitEdit.setEditable(false);
				bottomLimitEdit.setEditable(false);
				ok.setEnabled(false);
				
				GeneticAlg genetic = new GeneticAlg(JGeneticPanel.this.canvas,JGeneticPanel.this.cList);
				genetic.setTopLimit(Double.parseDouble(topLimitEdit.getText()));
				genetic.setBottomLimit(Double.parseDouble(bottomLimitEdit.getText()));
				thread = new Thread(genetic);
				thread.start();
			}
			
		});
		
		cancel.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();				
			}
			
		});
	}	
}

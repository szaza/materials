package ui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;

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
	private JTextField bottomLimitEdit;
	private JTextField topLimitEdit;
	private LinkedList<Curves> cList;
	private JProgressBar progress;
	private UIFrame uiFrame;
	private volatile Thread thread;
	GeneticAlg genetic;

	
	public JGeneticPanel(UIFrame ui,LinkedList<Curves> cL) {
		super();
		setTitle("Genetikus algoritmus");
		
		this.uiFrame = ui;
		this.cList = new LinkedList <Curves> (); 
				this.cList.addAll(cL);		
		this.setBounds(200,200,300,170);			
		
		ok = new JButton("Ok");
		cancel = new JButton("Mégse");
		contentPanel = new JPanel();
		buttonPanel = new JPanel();
		bottomLimitLabel = new JLabel("Alsó határ:");
		topLimitLabel = new JLabel("Alsó határ:");
		bottomLimitEdit = new JTextField("1.3");
		topLimitEdit = new JTextField("1.5");
		progress = new JProgressBar(0,100);		
		
		Dimension d = new Dimension(150,26);
		
		bottomLimitEdit.setPreferredSize(d);
		topLimitEdit.setPreferredSize(d);
		
		progress.setPreferredSize(new Dimension(240,26));
		progress.setStringPainted(true);
		
		buttonPanel.add(ok);
		buttonPanel.add(cancel);
		
		contentPanel.add(bottomLimitLabel);
		contentPanel.add(bottomLimitEdit);
		contentPanel.add(topLimitLabel);
		contentPanel.add(topLimitEdit);
		contentPanel.add(progress);
		contentPanel.add(buttonPanel);
		
		this.setContentPane(contentPanel);	
		
		genetic  = new GeneticAlg(uiFrame,this);			
		
		ok.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
				topLimitEdit.setEditable(false);
				bottomLimitEdit.setEditable(false);
				ok.setEnabled(false);
				
				genetic.setTopLimit(Double.parseDouble(topLimitEdit.getText()));
				genetic.setBottomLimit(Double.parseDouble(bottomLimitEdit.getText()));
				thread = new Thread(genetic);
				thread.start();
			}
			
		});
		
		cancel.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if (genetic  != null) {
					genetic.stop();
					Thread tmp = thread;
					tmp.interrupt();
					thread = null;
				}
				dispose();				
			}
			
		});
	}
	
	public LinkedList <Curves> getCList() {
		return cList;
	}
	
	public JProgressBar getProgress() {
		return progress;
	}
	
}

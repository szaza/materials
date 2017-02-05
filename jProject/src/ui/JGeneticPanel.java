package ui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.*;

import collect.Curves;
import collect.GeneticAlg;
//A genetikus algoritmuson végezhető beállítások megjelenítése
public class JGeneticPanel extends JDialog {

	private static final long serialVersionUID = 1L;
	private JButton ok;
	private JButton cancel;
	private JPanel contentPanel;
	private JPanel buttonPanel;
	private JLabel bottomLimitLabel;
	private JTextField bottomLimitEdit;
	private LinkedList<Curves> cList;
	private JProgressBar progress;
	private UIFrame uiFrame;
	private JLabel mutationPerCrossOverLabel;
	private JSlider mutationPerCrossOverSlider;
	private volatile Thread thread;
	private GeneticAlg genetic;

	
	public JGeneticPanel(UIFrame ui,LinkedList<Curves> cL) {
		super();
		setTitle("Genetikus algoritmus");
		
		this.uiFrame = ui;
		this.cList = new LinkedList <Curves> (); 
				this.cList.addAll(cL);		
		this.setBounds(200,200,300,195);
		
		ok = new JButton("Ok");
		cancel = new JButton("Mégse");
		contentPanel = new JPanel();
		buttonPanel = new JPanel();
		bottomLimitLabel = new JLabel("Alsó határ:");
		bottomLimitEdit = new JTextField("1.3");

		mutationPerCrossOverLabel = new JLabel("Mutáció/Keresztezés:");
		mutationPerCrossOverSlider = new JSlider(JSlider.HORIZONTAL,0,15,5);
		mutationPerCrossOverSlider.setMajorTickSpacing(5);
		mutationPerCrossOverSlider.setMinorTickSpacing(1);
		mutationPerCrossOverSlider.setPaintTicks(true);
		mutationPerCrossOverSlider.setPaintLabels(true);

		progress = new JProgressBar(0,100);		
		
		Dimension d = new Dimension(150,26);
		
		bottomLimitEdit.setPreferredSize(d);
		
		progress.setPreferredSize(new Dimension(240,26));
		progress.setStringPainted(true);
		
		buttonPanel.add(ok);
		buttonPanel.add(cancel);
		
		contentPanel.add(bottomLimitLabel);
		contentPanel.add(bottomLimitEdit);
		contentPanel.add(mutationPerCrossOverLabel);
		contentPanel.add(mutationPerCrossOverSlider);
		contentPanel.add(progress);
		contentPanel.add(buttonPanel);
		
		this.setContentPane(contentPanel);	
		
		genetic  = new GeneticAlg(uiFrame,this);			
		
		ok.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
				bottomLimitEdit.setEditable(false);
				ok.setEnabled(false);
				
				genetic.setBottomLimit(Double.parseDouble(bottomLimitEdit.getText()));
				genetic.setMutationPerCrossOver(mutationPerCrossOverSlider.getValue());

				thread = new Thread(genetic);
				thread.start();
			}
			
		});
		
		cancel.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if (thread  != null) {
					genetic.stop();
					Thread tmp = thread;
					thread = null;
					tmp.interrupt();
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

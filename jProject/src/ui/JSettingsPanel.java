package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class JSettingsPanel extends JDialog {

	private static final long serialVersionUID = 1L;
	private JButton ok;
	private JButton cancel;
	private JPanel contentPanel;
	private JPanel buttonPanel;
	private JLabel iterationLabel;
	private JCustomField iterationEdit;
	private int itNumber;
	
	public JSettingsPanel(int itNumber) {
		super();
		setTitle("Beállítások");
		this.itNumber = itNumber;
		
		ok = new JButton("Ok");
		cancel = new JButton("Mégse");
		contentPanel = new JPanel();
		buttonPanel = new JPanel();
		this.iterationLabel = new JLabel("Iterációk száma");
		this.iterationEdit = new JCustomField(Integer.toString(itNumber));
		
		buttonPanel.add(ok);
		buttonPanel.add(cancel);
		
		contentPanel.add(iterationLabel);
		contentPanel.add(iterationEdit);
		contentPanel.add(buttonPanel);
		
		this.setContentPane(contentPanel);
		
		ok.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				setItNumber(Integer.parseInt(iterationEdit.getText()));
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

	public int getItNumber() {
		return itNumber;
	}

	public void setItNumber(int itNumber) {
		this.itNumber = itNumber;
	}
	
}

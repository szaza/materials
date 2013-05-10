package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.geom.Point2D;
import javax.swing.*;
import collect.Triangle;
import collect.TriangleList;
import graphics.JCanvas;

public class UIFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel controlPanel;
	private JPanel contentPanel;
	private JPanel editPanel;
	private JTriangPanel innerPanel;
	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JMenuItem saveMenu;
	private JMenuItem exitMenu;
	private JCanvas canvas;
	private JButton addTriang;
	private JButton removeTriang;
	private JComboBox<String> selectTriangle;
	private JLabel triangSettings;
	private JAddTriangFrame addFrame;

	TriangleList tList = new TriangleList();

	public UIFrame() {
		this.setTitle("Fractal");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		contentPanel = new JPanel();
		controlPanel = new JPanel();
		editPanel = new JPanel();
		innerPanel = new JTriangPanel();
		menuBar = new JMenuBar();
		fileMenu = new JMenu("File");
		saveMenu = new JMenuItem("Save");
		exitMenu = new JMenuItem("Exit");
		canvas = new JCanvas();
		selectTriangle = new JComboBox<String>();
		triangSettings = new JLabel("A Háromszög adatai:");
		addTriang = new JButton("Új háromszög");
		removeTriang = new JButton("Törlés");


		fileMenu.add(saveMenu);
		fileMenu.add(exitMenu);

		menuBar.add(fileMenu);

		selectTriangle.setPreferredSize(new Dimension(150, 26));

		controlPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		controlPanel.add(menuBar);

		editPanel.add(triangSettings);
		editPanel.add(selectTriangle);
		editPanel.add(innerPanel);
		editPanel.add(addTriang);
		editPanel.add(removeTriang);

		editPanel.setPreferredSize(new Dimension(300, 400));

		contentPanel.setLayout(new BorderLayout());
		contentPanel.add(controlPanel, BorderLayout.NORTH);
		contentPanel.add(canvas, BorderLayout.CENTER);
		contentPanel.add(editPanel, BorderLayout.EAST);

		init();

		this.setContentPane(contentPanel);

		addTriang.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addFrame = new JAddTriangFrame(tList);
				addFrame.ok.addActionListener(new okPressed());
				addFrame.ok.addActionListener(new exitDialog());
				addFrame.cancel.addActionListener(new exitDialog());
			}
		});

		selectTriangle.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				refreshInnerPanel();

			}
		});

		removeTriang.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int index = selectTriangle.getSelectedIndex();
				tList.deleteItem(index);

				selectTriangle.removeItemAt(selectTriangle.getItemCount() - 1);
				refreshInnerPanel();
				setRemoveTriangButtonState();
			}
		});

	}

	public void init() {
		Point2D.Double A = new Point2D.Double(0.0d,0.0d);
		Point2D.Double B = new Point2D.Double(-1.0d,0.0d);
		Point2D.Double C = new Point2D.Double(0.0d,-1.0d);

		tList.insertItem(new Triangle(A, B, C));
		selectTriangle.addItem("1");
		innerPanel.setFields(A, B, C);
		innerPanel.addFocusListener(new fieldListener());
		setRemoveTriangButtonState();
	}

	public void setRemoveTriangButtonState() {
		if (selectTriangle.getItemCount() > 1)
			removeTriang.setEnabled(true);
		else
			removeTriang.setEnabled(false);
	}

	public void refreshInnerPanel() {
		int index = selectTriangle.getSelectedIndex();
		Triangle triang;
		triang = tList.getValue(index);
		innerPanel.setFields(triang.getA(), triang.getB(), triang.getC());
	}

	class okPressed implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			addFrame.saveSettings();
			tList = addFrame.gettList();
			if (selectTriangle.getItemCount() < tList.getLength()) {
				for (int i = selectTriangle.getItemCount() + 1; i <= tList
						.getLength(); i++) {
					selectTriangle.addItem(Integer.toString(i));
				}
			}
			setRemoveTriangButtonState();

		}
	}

	class exitDialog implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			addFrame.dispose();
		}

	}

	class fieldListener implements FocusListener {

		@Override
		public void focusGained(FocusEvent e) {

		}

		@Override
		public void focusLost(FocusEvent e) {
			int index = selectTriangle.getSelectedIndex();
			Triangle triang;
			triang = innerPanel.getTriangSettings();
			tList.updateValue(triang, index);
		}

	}
}


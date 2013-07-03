package collect;

import graphics.JCanvas;
import graphics.JRenderCanvas;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

//A köztes fraktálok komponenseit tartalmazó adatszerkezet
public class HFractal extends JDialog implements Runnable {
	
	private static final long serialVersionUID = 1L;
	private int deformLength=100;
	private Curves curves;
	private Triangle triang;
	private Transform transform;
	
	//Az f és g fraktálokhoz tartozó görbék
	protected Point2D.Float[] aCurve;
	protected Point2D.Float[] bCurve;
	protected Point2D.Float[] cCurve;
	
	//A H köztes fraktálhoz tartozó pontok
	private Point2D.Float aPoint;
	private Point2D.Float bPoint;
	private Point2D.Float cPoint;	
	
	protected LinkedList <FractalComponent> hList;
	protected LinkedList<Curves> cList;
	
	private JPanel contentPanel;
	private JPanel controlPanel;
	private JLabel boxDimension;
	private JButton restart;
	protected JCanvas canvas;
	protected JRenderCanvas rCanvas;
	
	public HFractal(JCanvas canvas,LinkedList <Curves> cL) {
		this.canvas = canvas;
		this.cList = cL;
		
		contentPanel = new JPanel();
		controlPanel = new JPanel();
		boxDimension = new JLabel("Box-dimenzió: ");
		restart = new JButton("Újra");
		rCanvas = new JRenderCanvas(600,600);
		hList = new LinkedList<FractalComponent>();
		
		contentPanel.setLayout(new BorderLayout());
		controlPanel.setLayout(new BorderLayout());
		
		rCanvas.setComponentList(hList);
		
		controlPanel.add(boxDimension,BorderLayout.WEST);
		controlPanel.add(restart,BorderLayout.EAST);
		contentPanel.add(rCanvas,BorderLayout.CENTER);
		contentPanel.add(controlPanel,BorderLayout.SOUTH);
		
		this.setTitle("Fraktál Deformázió");
		this.setContentPane(contentPanel);
		this.setBounds(50,50,600,670);
		this.setResizable(false);
		
		restart.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Thread thread = new Thread(HFractal.this);
				thread.start();
			}
			
		});
		
		rCanvas.addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent e) {
				boxDimension.setText("Box-dimenzió: " + String.format("%.6f",rCanvas.getBoxDimension()));			
			}
			
		});
	}
	
	public synchronized LinkedList<FractalComponent> updateHList(int t) {
		LinkedList <FractalComponent> tmpList = new LinkedList<FractalComponent>(); 					
		
		//Bejárom a görbéket tartalmazó listát
		for (int i=0; i<cList.size(); i++) {
			curves = cList.get(i);
			
			aCurve = curves.getaCurve();
			bCurve = curves.getbCurve();
			cCurve = curves.getcCurve();
			
			//Kiszámítom a görbék pontjait egy adott t időpillanatban
			aPoint = Curves.getCurvePoint((float) t / deformLength, aCurve); 
			bPoint = Curves.getCurvePoint((float) t / deformLength, bCurve); 
			cPoint = Curves.getCurvePoint((float) t / deformLength, cCurve); 
		
			triang = new Triangle(aPoint,bPoint,cPoint);
			transform = triang.toTransform();
		
			tmpList.add(new FractalComponent(triang,transform,curves.getColor()));
		}	
		
		return tmpList;
	}
	
	@Override
	public void run() {
		
		//Ha a görbéket tartalmazó lista nem üres
		if (!cList.isEmpty()) {		
			for (int j=0; j<=deformLength; j++) {
				hList = updateHList(j);
				
				//Az UIFramen levő canvast frissíti
				canvas.sethComponentList(hList);
				canvas.repaint();
				
				//A render canvast frissíti
				rCanvas.setComponentList(hList);
				
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}

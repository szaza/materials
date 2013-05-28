package collect;

import graphics.JCanvas;
import graphics.JRenderCanvas;

import java.awt.geom.Point2D;
import java.util.LinkedList;

import javax.swing.JDialog;
import javax.swing.JPanel;

public class HFractal extends JDialog implements Runnable {
	
	private static final long serialVersionUID = 1L;
	private int deformLength=10;
	private Curves curves;
	private Triangle triang;
	private Transform transform;
	
	//Az f es g fraktalokhoz tartozo gorbek
	private Point2D.Float[] aCurve;
	private Point2D.Float[] bCurve;
	private Point2D.Float[] cCurve;
	
	//A H fraktalhoz tartozo pontok
	private Point2D.Float aPoint;
	private Point2D.Float bPoint;
	private Point2D.Float cPoint;	
	
	private LinkedList <FractalComponent> hList;
	private LinkedList<Curves> cList;
	
	private JPanel contentPanel;
	private JCanvas canvas;
	private JRenderCanvas rCanvas;
	
	public HFractal(JCanvas canvas,LinkedList <Curves> cL) {
		this.canvas = canvas;
		this.cList = cL;
		
		contentPanel = new JPanel();
		rCanvas = new JRenderCanvas(600,620);
		hList = new LinkedList<FractalComponent>();
		
		rCanvas.setComponentList(hList);
		contentPanel.add(rCanvas);
		
		this.setTitle("Fraktál Deformázió");
		this.setContentPane(contentPanel);
		this.setBounds(50,50,600,600);
		this.setResizable(false);
		this.setVisible(true);
	}
	
	public synchronized LinkedList<FractalComponent> updateHList(int t) {
		LinkedList <FractalComponent> tmpList = new LinkedList<FractalComponent>(); 					
		
		//Bejarom a gorbeket tartalmazo listat
		for (int i=0; i<cList.size(); i++) {
			curves = cList.get(i);
			
			aCurve = curves.getaCurve();
			bCurve = curves.getbCurve();
			cCurve = curves.getcCurve();
			
			aPoint = Curves.getCurvePoint((float) t / deformLength, aCurve); //Kiszamitom az a gorbe pontjat egy adott t idopillanatbban
			bPoint = Curves.getCurvePoint((float) t / deformLength, bCurve); //Kiszamitom az a gorbe pontjat egy adott t idopillanatbban
			cPoint = Curves.getCurvePoint((float) t / deformLength, cCurve); //Kiszamitom az a gorbe pontjat egy adott t idopillanatbban
		
			triang = new Triangle(aPoint,bPoint,cPoint);
			transform = triang.toTransform();
		
			tmpList.add(new FractalComponent(triang,transform,curves.getColor()));
		}	
		
		return tmpList;
	}
	
	@Override
	public void run() {
		
		if (!cList.isEmpty()) {			//Ha a gorbeket tartalmazo lista nem ures		
			for (int j=0; j<=deformLength; j++) {
				hList = updateHList(j);
				
				canvas.sethComponentList(hList);
				canvas.repaint();
				
				rCanvas.setComponentList(hList);
				
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}

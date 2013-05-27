package collect;

import graphics.JCanvas;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.LinkedList;

public class HFractal implements Runnable {
	
	private int deformLength=100;
	private Curves curves;
	private Triangle triang;
	private Transform transform;
	private FractalComponent component;
	
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
	
	private JCanvas canvas;
	
	public HFractal(JCanvas canvas,LinkedList <Curves> cL) {
		this.canvas = canvas;
		this.cList = cL;
		
		hList = new LinkedList<FractalComponent>();
	}
	
	@Override
	public void run() {
		
		if (!cList.isEmpty()) {			//Ha a gorbeket tartalmazo lista nem ures		
			for (int j=0; j<=deformLength; j++) {
				synchronized (this) {
					hList.clear(); //Uritem a koztes fraktalokat tartlamazo listat						
					
					//Bejarom a gorbeket tartalmazo listat
					for (int i=0; i<cList.size(); i++) {
						curves = cList.get(i);
						
						aCurve = curves.getaCurve();
						bCurve = curves.getbCurve();
						cCurve = curves.getcCurve();
						
						aPoint = Curves.getCurvePoint((float) j / deformLength, aCurve); //Kiszamitom az a gorbe pontjat egy adott j idopillanatbban
						bPoint = Curves.getCurvePoint((float) j / deformLength, bCurve); //Kiszamitom az a gorbe pontjat egy adott j idopillanatbban
						cPoint = Curves.getCurvePoint((float) j / deformLength, cCurve); //Kiszamitom az a gorbe pontjat egy adott j idopillanatbban
					
						triang = new Triangle(aPoint,bPoint,cPoint);
						transform = triang.toTransform();
					
						component = new FractalComponent(triang,transform,Color.CYAN);
						hList.add(component);
					}
				}
				
				canvas.sethComponentList(hList);
				canvas.repaint();
				try {
					Thread.sleep(2);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}

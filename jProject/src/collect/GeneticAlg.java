package collect;

import graphics.JCanvas;

import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;

public class GeneticAlg extends HFractal {

	private static final long serialVersionUID = 1L;
	private double topLimit;
	private double bottomLimit;
	
	public GeneticAlg(JCanvas canvas, LinkedList<Curves> cL) {
		super(canvas, cL);
	}
	
	public double calculateBoxDimension(LinkedList <FractalComponent> hList) {
		int index;
		int xKoord;
		int yKoord;
		int mapKey;
		int width = super.getWidth();
		int height = super.getWidth();
		float scale = width / 6;
		int[][] boxMargin = new int[2][2];
		
		Point2D.Double point = new Point2D.Double(0,0);
		FractalComponent component = null;
		Random rnd = new Random();
		Map<Object,Integer> map = new HashMap<Object,Integer>();		
		
		boxMargin[0][0] = width;
		boxMargin[1][0] = height;
		
		for (int i=0; i<30000; i++) {
			index = rnd.nextInt(hList.size());
			component = hList.get(index);
			point = component.transform.transform(point);
			
			xKoord = (int)(point.x * scale);
			yKoord = (int)(point.y * scale);
			
			mapKey = width * xKoord + yKoord;
			if (!map.containsKey(mapKey)) map.put(mapKey, 1);
			
			//Bekeretezi az abrat
			if (boxMargin[0][0] > xKoord) boxMargin[0][0] = xKoord; //Bal szele
			if (boxMargin[0][1] < xKoord) boxMargin[0][1] = xKoord; //Jobb szele
			
			if (boxMargin[1][0] > yKoord) boxMargin[1][0] = yKoord; //Teteje
			if (boxMargin[1][1] < yKoord) boxMargin[1][1] = yKoord; //Alja		
		}
		
		
		int shapeWidth = boxMargin[0][1] - boxMargin[0][0] + 1;
		int shapeHeight = boxMargin[1][1] - boxMargin[1][0] + 1;
		double r = Math.max(shapeWidth,shapeHeight);
		
		if (r == 1) return 0;
		else {
			return Math.log10(map.size()) / Math.log10(r);
		}		
	}
	
	public Point2D.Float[] modifyCurves(Point2D.Float[] tmpCurve) {
		float x;
		float y;
		float tg;
		
		x = (tmpCurve[tmpCurve.length-1].x - tmpCurve[0].x);
		y = (tmpCurve[tmpCurve.length-1].y - tmpCurve[0].y);
		
		tg = y / x;
		
		for (int i=1; i<tmpCurve.length-1; i++) {
			tmpCurve[i].y = tmpCurve[i].x * tg;
		}		
		
		return tmpCurve;
	}
	
	public void mutation(LinkedList <FractalComponent> hList,LinkedList<Curves> cList) {
		Curves curves;
		
		for (int j=0; j<cList.size(); j++) {
			curves = cList.get(j);
			
			aCurve = curves.getaCurve();
			aCurve = modifyCurves(aCurve);
			curves.setaCurve(aCurve);
			
			bCurve = curves.getbCurve();
			bCurve = modifyCurves(bCurve);
			curves.setbCurve(bCurve);
			
			cCurve = curves.getcCurve();
			cCurve = modifyCurves(cCurve);
			curves.setcCurve(cCurve);			
			
			cList.set(j,curves);
		}
		
		canvas.setCurves(cList);
	}

	@Override
	public void run() {
		double boxDimension;
		
		//Ha a gorbeket tartalmazo lista nem ures
		if (!cList.isEmpty()) {		
			for (int j=0; j<=100; j++) {
				hList = updateHList(j);
								
				boxDimension = calculateBoxDimension(hList);				
				
				//Ellenorzom, hogy a jelenlegi allapot jo-e
				if ((boxDimension < bottomLimit) || (boxDimension > topLimit)) {  //Amig nem jo
					mutation(hList,cList);
				}
				
			}
		}
	}

	public double getTopLimit() {
		return topLimit;
	}

	public void setTopLimit(double topLimit) {
		this.topLimit = topLimit;
	}

	public double getBottomLimit() {
		return bottomLimit;
	}

	public void setBottomLimit(double bottomLimit) {
		this.bottomLimit = bottomLimit;
	}	
}

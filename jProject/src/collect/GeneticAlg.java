package collect;

import graphics.JCanvas;

import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;

public class GeneticAlg extends HFractal {

	private static final long serialVersionUID = 1L;
	private double topLimit;
	private double bottomLimit;
	private double mediaBoxDim;	//A box-dimenziok atlaga
	private LinkedList <Integer> bC; //Bad control points
	
	public GeneticAlg(JCanvas canvas, LinkedList<Curves> cL) {
		super(canvas, cL);
		
		bC = new LinkedList <Integer> ();
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
	
	public Point2D.Float[] modifyCurves(Point2D.Float[] tmpCurve,LinkedList <Integer> bC) {
		int [] directions = {-1,0,1};
		int rnd=0;
		
		Random generator = new Random();
		
		for (Integer i : bC) {
			//General egy random szamot es hozzaadja a kontrolpont x koordinatajahoz
			rnd = generator.nextInt(3);
			tmpCurve[i].x += directions[rnd];
			
			//General egy random szamot es hozzaadja a kontrolpont y koordinatajahoz
			rnd = generator.nextInt(3);
			tmpCurve[i].y += directions[rnd];
		}
		
		return tmpCurve;
	}
	
	public void mutation() {
		Curves curves;
		
		LinkedList <Curves> tmpCList = new LinkedList <Curves> ();
		tmpCList.addAll(cList);
		
		for (int j=0; j<cList.size(); j++) {
			curves = cList.get(j);
			
			aCurve = curves.getaCurve();
			aCurve = modifyCurves(aCurve,bC);
			curves.setaCurve(aCurve);
			
			bCurve = curves.getbCurve();
			bCurve = modifyCurves(bCurve,bC);
			curves.setbCurve(bCurve);
			
			cCurve = curves.getcCurve();
			cCurve = modifyCurves(cCurve,bC);
			curves.setcCurve(cCurve);			
			
			cList.set(j,curves);
		}
		
		/*
		hList = updateHList(j);
		boxDimension = calculateBoxDimension(hList);
		
		//Ha a box-dimenzio nem esik a ket hatar kozze
		if (boxDimension > topLimit || boxDimension < bottomLimit) {
			badControls[j] = true;
		}		
		*/
		
		canvas.setCurves(cList);
	}
	
	//Genetikus algoritmus
	public void geneticAlg() {
		int length;
		boolean [] badControls;
		double boxDimension;
		
		length = cList.size();
		badControls = new boolean[length];
		
		Arrays.fill(badControls, false);
		
		//Inicializalja az atlag box-dimenziot
		mediaBoxDim = 0;
			
		//A kontrolpontok szama szerint felosztja a gorbeket, es minden lepesben figyelem a box-dimenziot
		for (int j=0; j<length; j++) {
			hList = updateHList(j);
			boxDimension = calculateBoxDimension(hList);
			
			mediaBoxDim += boxDimension;
			
			//Ha a box-dimenzio nem esik a ket hatar kozze
			if (boxDimension > topLimit || boxDimension < bottomLimit) {
				badControls[j] = true;
			}
		}
		
		//Uritem a rossz kontrol pontok listajat
		bC.clear();
		
		//Megkeressuk, hogy melyik kontrol pontok lehetnek hibasak
		for (int i=1; i<length-1; i++) {
			bC.add(i);
		}
		mutation();
	}

	@Override
	public void run() {		
		//Ha a gorbeket tartalmazo lista nem ures
		if (!cList.isEmpty()) {		
			geneticAlg();
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

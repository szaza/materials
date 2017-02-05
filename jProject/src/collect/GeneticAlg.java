package collect;

import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;

import javax.swing.JOptionPane;
import javax.swing.JProgressBar;

import ui.JGeneticPanel;
import ui.UIFrame;

//Genetikus algoritmus amely úgy próbálja módosítani a görbéket, hogy a köztes fraktálok box-dimenziója
//valamilyen alsó és felső határ közzé essen
public class GeneticAlg extends HFractal {

	private static final long serialVersionUID = 1L;
	private boolean interrupted;
	private UIFrame uiFrame;
	private JProgressBar progressBar;
	private JGeneticPanel gPanel;	
	private double bottomLimit;
	private double mediaBoxDim;
	private double prevBoxDim;
	private int badControls;
	private int prevBadControls;
	private int mutationPerCrossOver;
	private Random generator;
	
	public GeneticAlg(UIFrame ui, JGeneticPanel genetic) {
		super(UIFrame.canvas,genetic.getCList());
		this.uiFrame = ui;
		this.gPanel = genetic;
		this.progressBar = gPanel.getProgress();
		this.mutationPerCrossOver = 5;
	}
	
	//Box dimenziót számol
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
		boxMargin[0][1] = 0;
		boxMargin[1][0] = height;
		boxMargin[1][1] = 0;
		
		for (int i=0; i<30000; i++) {
			index = rnd.nextInt(hList.size());
			component = hList.get(index);
			point = component.transform.transform(point);
			
			xKoord = (int)(point.x * scale);
			yKoord = (int)(point.y * scale);
			
			mapKey = width * xKoord + yKoord;
			if (!map.containsKey(mapKey)) map.put(mapKey, 1);
			
			//Megkeresi a fraktál képét
			if ((boxMargin[0][0] > xKoord) && (xKoord > 0)) boxMargin[0][0] = xKoord; //Bal széle
			if (boxMargin[0][1] < xKoord) boxMargin[0][1] = xKoord; //Jobb széle
			
			if ((boxMargin[1][0] > yKoord) && (yKoord > 0)) boxMargin[1][0] = yKoord; //Teteje
			if (boxMargin[1][1] < yKoord) boxMargin[1][1] = yKoord; //Alja		
		}
		
		//Kiszámítja a létrejött fraktál szélességét és magasságát
		int shapeWidth = Math.abs(boxMargin[0][1] - boxMargin[0][0]) + 1;
		int shapeHeight = Math.abs(boxMargin[1][1] - boxMargin[1][0]) + 1;
		double r = Math.max(shapeWidth,shapeHeight);
		
		if (r == 1) return 0;
		else {
			return Math.log10(map.size()) / Math.log10(r);	//Kiszámítja a box-dimenziót
		}		
	}

	//Kiszámolja a köztes fraktálok box-dimenziójának az átlagát
	public int mediaBoxDimension() {
		int badControl;
		int subdivision;
		double boxDimension;
		
		badControl = 0;
		mediaBoxDim = 0;
		subdivision = 100;
		
		//A kontrollpontok száma szerint felosztja a görbéket, és minden lépésben figyeli a box-dimenziót
		for (int j=0; j<subdivision; j++) {
			hList = updateHList(j);
			boxDimension = calculateBoxDimension(hList);
			
			mediaBoxDim += boxDimension;
			
			//Ha a box-dimenzió nem esik a két határ közzé
			if (boxDimension < bottomLimit) {
				badControl++;
			}
		}
		
		mediaBoxDim = mediaBoxDim / subdivision;
		return badControl;
	}	
	
	public void initGeneticAlg() {
		mediaBoxDim = 0;
		interrupted = false;
		
		generator = new Random();		
	}
	
	//Módosítja egy görbe véletlenszerűen kiválasztott kontrollpontját
	public Point2D.Float[] modifyCurves(Point2D.Float[] tmpCurve) {
		float [] directions = {-0.5f,0,0.5f};
		int index1,index2;
		int curveIndex=0;
		double tmpBoxDim;
		
		curveIndex = generator.nextInt(tmpCurve.length - 2) + 1;
		index1 = generator.nextInt(3);
		index2 = generator.nextInt(3);
		
		//Ha jó irányba változik a box-dimenzió, akkor többször is lefut az algoritmus
		do {
			tmpBoxDim = mediaBoxDim;
			tmpCurve[curveIndex].x += directions[index1];
			tmpCurve[curveIndex].y += directions[index2];
			badControls = mediaBoxDimension();
			if (badControls == 0) interrupted = true;
				  
		} while ((mediaBoxDim > tmpBoxDim) && (!interrupted));
		
		tmpCurve[curveIndex].x -= directions[index1];
		tmpCurve[curveIndex].y -= directions[index2];							
		
		return tmpCurve;
	}
	
	//MUTÁCIÓ: Véletlenszerűen kiválaszt egy görbét, majd módosítja valamelyik kontrollpontját
	public void mutation() {
		Curves curves;
		int index,rnd;
		
		//Véletlenszerűen kiválaszt egy görbe-családot
		index = generator.nextInt(cList.size());
		curves = cList.get(index);
		
		rnd = generator.nextInt(3);
		
		switch (rnd) {
			case 0:
				aCurve = curves.getaCurve();
				aCurve = modifyCurves(aCurve);
				curves.setaCurve(aCurve);
			break;
			
			case 1:
				bCurve = curves.getbCurve();
				bCurve = modifyCurves(bCurve);
				curves.setbCurve(bCurve);
			break;
			
			case 2:
				cCurve = curves.getcCurve();
				cCurve = modifyCurves(cCurve);
				curves.setcCurve(cCurve);
			break;
		}
		
		cList.set(index,curves);
	}
	
	public void crossing(Curves curves1,Curves curves2) {
		int cIndex1,cIndex2,rnd;
		Point2D.Float p;
		
		//Kivalasztok egy-egy gorbet a ket csaladbol
		rnd = generator.nextInt(3);
		Point2D.Float[] tmpCurve1 = curves1.getCurve(rnd);
		
		rnd = generator.nextInt(3);
		Point2D.Float[] tmpCurve2 = curves2.getCurve(rnd);
		
		//Kivalasztok egy-egy kontrollpontot
		cIndex1 = generator.nextInt(tmpCurve1.length - 2) + 1;
		cIndex2 = generator.nextInt(tmpCurve2.length - 2) + 1;
		
		p = tmpCurve1[cIndex1];
		tmpCurve1[cIndex1] = tmpCurve2[cIndex2];
		tmpCurve2[cIndex2] = p;
		
		badControls = mediaBoxDimension();
		if (badControls == 0) interrupted = true;
	}
	
	public void crossOver() {
		Curves curves1,curves2;
		int index1,index2;
		
		if (!interrupted) {
			//Véletlenszerűen kiválaszt ket görbe-családot
			index1 = generator.nextInt(cList.size());
			curves1 = cList.get(index1);
			
			index2 = generator.nextInt(cList.size());
			curves2 = cList.get(index2);
			
			crossing(curves1,curves2);
		}
	}
	
	//Szelekció
	public void selection(LinkedList <Curves> tmpCList) {
		if ((mediaBoxDim > prevBoxDim) && (badControls <= prevBadControls)) {
			System.out.println("Box Dim: " + mediaBoxDim + " BadControls: " + badControls);			
			prevBoxDim = mediaBoxDim;
			prevBadControls = badControls;
			uiFrame.setcList(cList);
		} else {
			cList = tmpCList;
		}
	}

	//DEEP COPY: Másolatot készít a görbe készletről
	public LinkedList <Curves> deepCopy() {
		
		LinkedList <Curves> tmpCList = new LinkedList <Curves> ();

		for (int j=0; j<cList.size(); j++) {
			tmpCList.add(new Curves(cList.get(j)));
		}
		
		return tmpCList;
	}
	
	//Genetikus algoritmus
	public void geneticAlg() {
		int counter,maxIterationNumber;
		
		initGeneticAlg();
		
		counter=0;
		maxIterationNumber = 300;
		prevBadControls = mediaBoxDimension(); 
		
		if (prevBadControls > 0) {
			
			//Az elozo box-dimenzio
			prevBoxDim = mediaBoxDim;			
			
			while ((!interrupted) && (counter<maxIterationNumber)) {
				//Mutáció
				for (int i=0; i<mutationPerCrossOver; i++) {
					LinkedList<Curves> tmpCList = deepCopy();
					mutation();
					selection(tmpCList);
				}
				
				//Keresztezés
				LinkedList <Curves> tmpCList2 = deepCopy();
				crossOver();
				selection(tmpCList2);				
				
				counter++;
				
				if (counter % 3 == 0) progressBar.setValue(counter/3);				
			}
			
			if (badControls > 0) 
				JOptionPane.showMessageDialog(null,"<html>A genetikus algoritmus lefutott! <br/> Sajnos nem találta meg az optimumot! <br/> Próbálja meg még egyszer! </html>","Vége",JOptionPane.ERROR_MESSAGE);
			else
				JOptionPane.showMessageDialog(null,"A genetikus algoritmus sikeresen lefutott!","Vége",JOptionPane.INFORMATION_MESSAGE);			
			
			System.out.println("Vege");		
			gPanel.dispose();			
		}
	}

	@Override
	public void run() {		
		//Ha a gorbeket tartalmazó lista nem üres
		if (!cList.isEmpty()) {		
			geneticAlg();
		}
	}

	public void stop() {
		interrupted = true;
	}

	public double getBottomLimit() {
		return bottomLimit;
	}

	public void setBottomLimit(double bottomLimit) {
		this.bottomLimit = bottomLimit;
	}

	public void setMutationPerCrossOver(int mutationPerCrossOver) {
		this.mutationPerCrossOver = mutationPerCrossOver;
	}
}

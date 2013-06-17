package collect;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.Random;

public class Curves implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private int parentId;
	private Point2D.Float[] aCurve;
	private Point2D.Float[] bCurve;
	private Point2D.Float[] cCurve;
	private Color color;
	
	public Curves() {
		aCurve = new Point2D.Float[0];
		bCurve = new Point2D.Float[0];
		cCurve = new Point2D.Float[0];
	}
	
	public Curves(int parentId,Point2D.Float[] aCurves,Point2D.Float[] bCurves,Point2D.Float[] cCurves,Color color) {
		this.parentId = parentId;
		this.aCurve = aCurves;
		this.bCurve = bCurves;
		this.cCurve = cCurves;
		this.color = color;
	}

	//Kiszamitja egy gorbe pontjat egy adott t idopillanatban
	//t->[0,1] kozott van
	public static Point2D.Float getCurvePoint(float t,Point2D.Float[] points) {
		float x=0;
		float y=0;
		int exp = points.length-1;
		int coef = 0;
		
		for (int i=0; i<points.length; i++) {
			coef = (i<points.length/2) ? i * exp : (points.length - (i+1)) * exp;
			coef = (coef == 0) ? 1 : coef;
						
			x += Math.pow(t,i)*Math.pow((1-t),(exp-i))*coef*points[i].x;
			y += Math.pow(t,i)*Math.pow((1-t),(exp-i))*coef*points[i].y;
		}
		
		return new Point2D.Float(x,y);
	}

	//Frissiti a lista minden elemet a fraktalok fuggvenyeben
	public static LinkedList<Curves> updateCurves(LinkedList<Curves> cList,
			LinkedList<FractalComponent> fList,
			LinkedList<FractalComponent> gList) {
		
		FractalComponent fComponent;
		FractalComponent gComponent;
		Curves curves;
		Point2D.Float[] aCurve;
		Point2D.Float[] bCurve;
		Point2D.Float[] cCurve;
		
		//Bejarom a listat
		for (int i=0; i<cList.size(); i++) {
			fComponent = fList.get(i);
			gComponent = gList.get(i);
			curves = cList.get(i);
			
			//Lekerem egy adott fraktal komponenshez tartozo harom gorbet
			aCurve = curves.getaCurve();
			bCurve = curves.getbCurve();
			cCurve = curves.getcCurve();
			
			//Az a gorbek elso es utolso pontja a fraktal komponensek egy-egy pontja lesz
			aCurve[0].x = fComponent.getTriang().A.x;
			aCurve[0].y = fComponent.getTriang().A.y;
			aCurve[aCurve.length-1].x = gComponent.getTriang().A.x;
			aCurve[aCurve.length-1].y = gComponent.getTriang().A.y;	
			
			bCurve[0].x = fComponent.getTriang().B.x;
			bCurve[0].y = fComponent.getTriang().B.y;
			bCurve[bCurve.length-1].x = gComponent.getTriang().B.x;
			bCurve[bCurve.length-1].y = gComponent.getTriang().B.y;				
			
			cCurve[0].x = fComponent.getTriang().C.x;
			cCurve[0].y = fComponent.getTriang().C.y;
			cCurve[cCurve.length-1].x = gComponent.getTriang().C.x;
			cCurve[cCurve.length-1].y = gComponent.getTriang().C.y;					
			
			curves.setaCurve(aCurve);
			curves.setbCurve(bCurve);
			curves.setcCurve(cCurve);
			
			curves.setColor(fComponent.getColor());
			
			cList.set(i,curves);			
		}
		
		return cList;
	}	
	
	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public Point2D.Float[] getaCurve() {
		return aCurve;
	}

	public void setaCurve(Point2D.Float[] aCurve) {
		this.aCurve = aCurve;
	}

	public Point2D.Float[] getbCurve() {
		return bCurve;
	}

	public void setbCurve(Point2D.Float[] bCurve) {
		this.bCurve = bCurve;
	}

	public Point2D.Float[] getcCurve() {
		return cCurve;
	}

	public void setcCurve(Point2D.Float[] cCurve) {
		this.cCurve = cCurve;
	}
		
	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	//Veletlenszeru gorbek generalasa
	public void setRandomCurves(Triangle triang1, Triangle triang2,int kp,int partition,Color color) {
		float x,y;
		int partition2 = 2*partition;
		Random generator = new Random();
		
		kp += 2;	//A kezdo es vegpont miatt 
				
		aCurve = new Point2D.Float[kp];
		bCurve = new Point2D.Float[kp];
		cCurve = new Point2D.Float[kp];		
		
		aCurve[0] = triang1.A;							
		aCurve[kp-1] = triang2.A; 
		
		bCurve[0] = triang1.B;							
		bCurve[kp-1] = triang2.B;
		
		cCurve[0] = triang1.C;							
		cCurve[kp-1] = triang2.C;
		
		
		for (int i=0; i<3; i++) {
			for (int j=1; j<kp-1; j++) {
				x = generator.nextInt(partition2) - partition;
				y = generator.nextInt(partition2) - partition;
				
				switch (i) {
					case 0: aCurve[j] = new Point2D.Float(x,y); break;
					case 1: bCurve[j] = new Point2D.Float(x,y); break;
					case 2: cCurve[j] = new Point2D.Float(x,y); break;
				}
			}
		}
		
		setColor(color);
	}
}

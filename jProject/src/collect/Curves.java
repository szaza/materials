package collect;

import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.LinkedList;

public class Curves implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private int parentId;
	private Point2D.Float[] aCurve;
	private Point2D.Float[] bCurve;
	private Point2D.Float[] cCurve;
	
	public Curves(int parentId,Point2D.Float[] aCurves,Point2D.Float[] bCurves,Point2D.Float[] cCurves) {
		this.parentId = parentId;
		this.aCurve = aCurves;
		this.bCurve = bCurves;
		this.cCurve = cCurves;
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

	public static LinkedList<Curves> updateCurves(LinkedList<Curves> cList,
			LinkedList<FractalComponent> fList,
			LinkedList<FractalComponent> gList) {
		
		FractalComponent fComponent;
		FractalComponent gComponent;
		Curves curves;
		Point2D.Float[] aCurve;
		Point2D.Float[] bCurve;
		Point2D.Float[] cCurve;
		
		for (int i=0; i<cList.size(); i++) {
			fComponent = fList.get(i);
			gComponent = gList.get(i);
			curves = cList.get(i);
			
			aCurve = curves.getaCurve();
			bCurve = curves.getbCurve();
			cCurve = curves.getcCurve();
			
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
			
			cList.set(i,curves);			
		}
		
		return cList;
	}
}

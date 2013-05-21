package collect;

import java.awt.geom.Point2D;
import java.io.Serializable;

public class Triangle implements Serializable {

	private static final long serialVersionUID = 1L;

	public Point2D.Float A;
	public Point2D.Float B;
	public Point2D.Float C;

	public Triangle(){
		A = new Point2D.Float();
		B = new Point2D.Float();
		C = new Point2D.Float();
	}
	
	public Triangle(Point2D.Float a,Point2D.Float b, Point2D.Float c) {
		A = a;
		B = b;
		C = c;
	}
	
	public Triangle(float ax,float ay,float bx, float by, float cx, float cy) {
		A = new Point2D.Float(ax,ay);
		B = new Point2D.Float(bx,by);;
		C = new Point2D.Float(cx,cy);
	}
	
	public Triangle(Triangle triang) {
		A = triang.getA();
		B = triang.getB();
		C = triang.getC();
	}
	
	public Polygon2D getPolygon(float scalex,float scaley) {
		float x[] = {A.x * scalex, B.x * scalex, C.x * scalex};
		float y[] = {A.y * scaley, B.y * scaley, C.y * scaley};
		
		return new Polygon2D(x,y,3);
	}		
	
	public Point2D.Float getA() {
		return A;
	}

	public void setA(Point2D.Float a) {
		A = a;
	}

	public Point2D.Float getB() {
		return B;
	}

	public void setB(Point2D.Float b) {
		B = b;
	}

	public Point2D.Float getC() {
		return C;
	}

	public void setC(Point2D.Float c) {
		C = c;
	}
	
	public Transform toTransform() {
		return new Transform(A,B,C);
	}

	@Override
	public String toString() {
		return "[" + A.x + "," + A.y + "]" + "[" + B.x + "," + B.y + "]" + "[" + C.x + "," + C.y + "]";
	}
}

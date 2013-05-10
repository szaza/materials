package collect;

import java.awt.geom.Point2D;
import java.io.Serializable;

public class Triangle implements Serializable {

	private static final long serialVersionUID = 1L;

	private Point2D.Double A;
	private Point2D.Double B;
	private Point2D.Double C;
	
	public Point2D.Double getA() {
		return A;
	}

	public void setA(Point2D.Double a) {
		A = a;
	}

	public Point2D.Double getB() {
		return B;
	}

	public void setB(Point2D.Double b) {
		B = b;
	}

	public Point2D.Double getC() {
		return C;
	}

	public void setC(Point2D.Double c) {
		C = c;
	}

	public Triangle(){
		A = new Point2D.Double();
		B = new Point2D.Double();
		C = new Point2D.Double();
	}
	
	public Triangle(Point2D.Double a,Point2D.Double b, Point2D.Double c) {
		A = a;
		B = b;
		C = c;
	}
	
	@Override
	public String toString() {
		return "[" + A.x + "," + A.y + "]" + "[" + B.x + "," + B.y + "]" + "[" + C.x + "," + C.y + "]";
	}
}

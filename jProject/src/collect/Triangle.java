package collect;

import collect.Point;
import java.io.Serializable;

public class Triangle implements Serializable {

	private static final long serialVersionUID = 1L;

	private Point A;
	private Point B;
	private Point C;
	
	public Point getA() {
		return A;
	}

	public void setA(Point a) {
		A = a;
	}

	public Point getB() {
		return B;
	}

	public void setB(Point b) {
		B = b;
	}

	public Point getC() {
		return C;
	}

	public void setC(Point c) {
		C = c;
	}

	public Triangle(){
		A = new Point();
		B = new Point();
		C = new Point();
	}
	
	public Triangle(Point a,Point b, Point c) {
		A = a;
		B = b;
		C = c;
	}
}

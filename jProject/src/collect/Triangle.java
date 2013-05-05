package collect;

import java.awt.Point;
import java.io.Serializable;

public class Triangle implements Serializable {

	private static final long serialVersionUID = 1L;

	Point A;
	Point B;
	Point C;
	
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

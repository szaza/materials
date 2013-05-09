package collect;

import java.io.Serializable;

import javax.vecmath.Point3f;

public class Triangle implements Serializable {

	private static final long serialVersionUID = 1L;

	private Point3f A;
	private Point3f B;
	private Point3f C;
	
	public Point3f getA() {
		return A;
	}

	public void setA(Point3f a) {
		A = a;
	}

	public Point3f getB() {
		return B;
	}

	public void setB(Point3f b) {
		B = b;
	}

	public Point3f getC() {
		return C;
	}

	public void setC(Point3f c) {
		C = c;
	}

	public Triangle(){
		A = new Point3f();
		B = new Point3f();
		C = new Point3f();
	}
	
	public Triangle(Point3f a,Point3f b, Point3f c) {
		A = a;
		B = b;
		C = c;
	}
	
	@Override
	public String toString() {
		return "[" + A.x + "," + A.y + "]" + "[" + B.x + "," + B.y + "]" + "[" + C.x + "," + C.y + "]";
	}
}

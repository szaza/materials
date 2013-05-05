package collect;

public class Triangle {
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

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
	
	public Polygon2D getPolygon(float offsetX,float offsetY,float scale) {
		float x[] = {A.x * scale + offsetX,B.x * scale + offsetX,C.x * scale + offsetX};
		float y[] = {-A.y * scale + offsetY,-B.y * scale + offsetY,-C.y * scale + offsetY};
		
		return new Polygon2D(x,y,3);
	}	
	
	@Override
	public String toString() {
		return "[" + A.x + "," + A.y + "]" + "[" + B.x + "," + B.y + "]" + "[" + C.x + "," + C.y + "]";
	}
}

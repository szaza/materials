package collect;

import java.awt.geom.Point2D;
import java.io.Serializable;

//Kiszámítja az affin transzformációkat a megadott háromszögek alapján
public class Transform implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private double[][] transform;
	
	public Transform() {
		transform = new double[3][3];
	}
	
	public Transform(Point2D.Float A,Point2D.Float B,Point2D.Float C) {
		transform = new double[3][3];
		transform[0][0] = C.x - A.x;
		transform[0][1] = B.x - A.x;
		transform[0][2] = A.x;
		
		transform[1][0] = C.y - A.y;
		transform[1][1] = B.y - A.y;
		transform[1][2] = A.y;
		
		transform[2][0] = 0;
		transform[2][1] = 0;
		transform[2][2] = 1;		
	}	
	
	//Elvégzi a transzformációt egy x koordinátára nézve
	public double transformX(double x, double y){
		return (transform[0][1] * x) + (transform[0][0]*y) + transform[0][2];
	}
	
	//Elvégzi a transzformációt egy y koordinátára nézve
	public double transformY(double x, double y){
		return (transform[1][1] * x) + (transform[1][0]*y) + transform[1][2];
	}	
	
	//Elvégzi a transzformációt egy pontra nézve
	public Point2D.Double transform(Point2D.Double pont) {
		double x;
		double y;
		
		x = (transform[0][1]*pont.x) + (transform[0][0] * pont.y) + transform[0][2];
		y = (transform[1][1]*pont.x) + (transform[1][0] * pont.y) + transform[1][2];
		
		return new Point2D.Double(x, y);
		
	}
	
	//Lekéri a transzformációs mátrix valamely értékét
	public float getValue(int i, int j) {
		try {
			return (float)transform[i][j];
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Invalid index!");
		}
		return 0;
	}
	
	@Override
	public String toString() {
		return "[" + transform[0][0] + "," + transform[0][1] + "," + transform[0][2] + "]" +
				"[" + transform[1][0] + "," + transform[1][1] + "," + transform[1][2] + "]" +
				"[" + transform[2][0] + "," + transform[2][1] + "," + transform[2][2] + "]";
	}
	
}

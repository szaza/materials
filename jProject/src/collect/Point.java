package collect;

import java.awt.geom.Point2D;

public class Point extends Point2D {
	
	private Point2D.Double Point;

	public Point() {
		this (0,0);
	}	
	
	public Point(double x, double y) {
		Point = new Point2D.Double();
		Point.setLocation(x,y);
	}
	
	@Override
	public double getX() {
		return Point.getX();
	}

	@Override
	public double getY() {
		return Point.getY();
	}

	@Override
	public void setLocation(double x, double y) {
		Point.setLocation(x,y);		
	}

}

package graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.media.j3d.Appearance;
import javax.media.j3d.Background;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.LineArray;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TriangleArray;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3d;

import collect.Item;
import collect.Triangle;
import collect.TriangleList;

import com.sun.j3d.utils.universe.SimpleUniverse;


public class JCanvas extends Canvas3D {

	private static final long serialVersionUID = 1L;
	private int width;
	private int height;
	private SimpleUniverse universe;
	private float scale;
	
	public JCanvas(GraphicsConfiguration config) {
		super(config);
		width=700;
		height=600;
		scale = 6;
		this.setPreferredSize(new Dimension(width,height));
		this.setBackground(Color.DARK_GRAY);
		this.setDoubleBufferEnable(true);
		
		universe = new SimpleUniverse(this);
		BranchGroup group = new BranchGroup();
		
	    Background bg = new Background();
	    bg.setColor(new Color3f(Color.DARK_GRAY));
	    BoundingSphere sphere = new BoundingSphere(new Point3d(0,0,0),0);
	    bg.setCapability(Background.ALLOW_COLOR_WRITE);
	    bg.setApplicationBounds(sphere);	    
	    group.addChild(bg);		
		
	    defaultTriangle(group);
	    scale(universe,6);
	    grid(group);
	    
		universe.addBranchGraph(group);
		this.addMouseWheelListener(new MouseWheelListener() {
			
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				float mod = e.getUnitsToScroll() / 3;
				if ((scale + mod < 12) && (scale + mod > 1)) {
					scale += mod;
					scale(universe,scale);
				}
			}
		});
	}
	
	public void grid(BranchGroup group) {
		Appearance app = new Appearance();
		Point3f[] plaPts = new Point3f[2];
		app.setColoringAttributes(new ColoringAttributes(new Color3f(Color.GRAY),1));
		
		for (int j=-30; j<=30; j++) {
			float y = 0.1f*j;
		    plaPts[0] = new Point3f(-3f,y, 0.0f);
		    plaPts[1] = new Point3f(3f,y, 0.0f);
		    LineArray pla = new LineArray(2, LineArray.COORDINATES);
		    pla.setCoordinates(0, plaPts);
		    Shape3D plShape = new Shape3D(pla, app);
		    group.addChild(plShape);
		}
		
		for (int i=-30; i<=30; i++) {
			float x = 0.1f*i;
		    plaPts[0] = new Point3f(x,-3f, 0.0f);
		    plaPts[1] = new Point3f(x,3f, 0.0f);
		    LineArray pla = new LineArray(2, LineArray.COORDINATES);
		    pla.setCoordinates(0, plaPts);
		    Shape3D plShape = new Shape3D(pla, app);
		    group.addChild(plShape);			
		}
				
	}
	
	public void defaultTriangle(BranchGroup group) {
		Appearance app = new Appearance();
		Point3f[] plaPts = new Point3f[3];
		plaPts[0] = new Point3f(0.0f,0.0f, 0.0f);
	    plaPts[1] = new Point3f(1.0f,0.0f, 0.0f);
	    plaPts[2] = new Point3f(0.0f,1.0f, 0.0f);
	    
	    TriangleArray pla = new TriangleArray(3,TriangleArray.COORDINATES);
	    pla.setCoordinates(0,plaPts);
	    Shape3D plShape = new Shape3D(pla, app);
	    group.addChild(plShape);
	}
	
	public void scale(SimpleUniverse universe,float scaleValue) {
		Transform3D scale = new Transform3D();
	    scale.lookAt( new  Point3d(0.0, 0.0, scaleValue)
        , new  Point3d( 0.0, 0.0, 0.0 )
        , new Vector3d( 0.0, 1.0, 0.0) );
	    scale.invert();	
	    universe.getViewingPlatform().getViewPlatformTransform().setTransform(scale);
	}
	
	public void drawTList(TriangleList tList) {
		Item i = tList.first;
		Triangle triang;
		
		while (i!=null) {
			triang = i.getTriang();
			drawTriangle(triang.getA(),triang.getB(),triang.getC());
			i = i.getNext();
		}
	}
	
	public void drawTriangle(Point3f a,Point3f b, Point3f c) {
		BranchGroup newTriang = new BranchGroup();
		Appearance app = new Appearance();
		Point3f[] plaPts = new Point3f[3];
		//PolygonAttributes polyAttribs = new PolygonAttributes(PolygonAttributes.POLYGON_LINE, PolygonAttributes.POLYGON_LINE,2);
		//app.setPolygonAttributes(polyAttribs);
		
		plaPts[0] = a;
	    plaPts[1] = b;
	    plaPts[2] = c;
	    
	    TriangleArray pla = new TriangleArray(3,TriangleArray.COORDINATES);
	    pla.setCoordinates(0,plaPts);
	    Shape3D plShape = new Shape3D(pla, app);
	    newTriang.addChild(plShape);
	    
	    universe.addBranchGraph(newTriang);
	}	
	
}

package graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import javax.media.j3d.Appearance;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.LineArray;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Transform3D;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3d;

import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.universe.SimpleUniverse;


public class JCanvas extends Canvas3D {

	private static final long serialVersionUID = 1L;
	private int width;
	private int height;
	
	public JCanvas(GraphicsConfiguration config) {
		super(config);
		width=700;
		height=600;
		this.setPreferredSize(new Dimension(width,height));
		this.setBackground(Color.GRAY);
		this.setDoubleBufferEnable(true);
		
		SimpleUniverse universe = new SimpleUniverse(this);
		BranchGroup group = new BranchGroup();
		Transform3D scale = new Transform3D();
	    scale.lookAt( new  Point3d(0.0, 0.0, 4.0)
        , new  Point3d( 0.0, 0.0, 0.0 )
        , new Vector3d( 1.0, 0.0, 0.0) );
	    scale.invert();
		
	    grid(group);
	    
		group.addChild(new ColorCube(0.3));
		universe.getViewingPlatform().getViewPlatformTransform().setTransform(scale);
		universe.addBranchGraph(group);
	}
	
	public void grid(BranchGroup group) {
		Appearance app = new Appearance();
		Point3f[] plaPts = new Point3f[2];
		
		for (int j=-20; j<=20; j++) {
			float y = 0.1f*j;
		    plaPts[0] = new Point3f(-2f,y, 0.0f);
		    plaPts[1] = new Point3f(2f,y, 0.0f);
		    LineArray pla = new LineArray(2, LineArray.COORDINATES);
		    pla.setCoordinates(0, plaPts);
		    Shape3D plShape = new Shape3D(pla, app);
		    group.addChild(plShape);
		}
		
		for (int i=-20; i<=20; i++) {
			float x = 0.1f*i;
		    plaPts[0] = new Point3f(x,-2f, 0.0f);
		    plaPts[1] = new Point3f(x,2f, 0.0f);
		    LineArray pla = new LineArray(2, LineArray.COORDINATES);
		    pla.setCoordinates(0, plaPts);
		    Shape3D plShape = new Shape3D(pla, app);
		    group.addChild(plShape);			
		}
				
	}
}

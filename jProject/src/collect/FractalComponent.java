package collect;

import java.awt.Color;
import java.io.Serializable;

//A fraktálokat alkotó háromszögeket és transzformációkat tárolja
public class FractalComponent implements Serializable {

	private static final long serialVersionUID = 1L;
	public Triangle triang;
	public Transform transform;
	private Color color;

	public FractalComponent(Triangle triang,Transform transform,Color color) {
		this.triang = triang;
		this.transform = transform;
		this.color = color;
	}
	
	public Triangle getTriang() {
		return triang;
	}

	public void setTriang(Triangle triang) {
		this.triang = triang;
	}	
	
	public Transform getTransform() {
		return transform;
	}

	public void setTransform(Transform transform) {
		this.transform = transform;
	}
	
	public void setComponent(FractalComponent component) {
		this.triang = component.triang;
		this.transform = component.transform;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public String toString() {
		return triang.toString() + " " + transform.toString() + "\n";
	}
};
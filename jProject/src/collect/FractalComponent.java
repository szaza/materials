package collect;

import java.io.Serializable;

public class FractalComponent implements Serializable {

	private static final long serialVersionUID = 1L;
	public Triangle triang;
	public Transform transform;
	public FractalComponent next;
	public FractalComponent prev;

	public FractalComponent() {
		next = null;
		prev = null;
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

	public String toString() {
		return triang.toString() + " " + transform.toString();
	}
};
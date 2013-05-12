package collect;

import java.awt.geom.AffineTransform;

public class TransformItem {
	
	private AffineTransform transform;
	public TransformItem next;
	public TransformItem prev;
	
	public TransformItem() {
		next = null;
		prev = null;
	}

	public AffineTransform getTransform() {
		return transform;
	}

	public void setTransform(AffineTransform transform) {
		this.transform = transform;
	}
}

package collect;

import java.io.Serializable;

public class Item implements Serializable {

	private static final long serialVersionUID = 1L;
	public Triangle triang;
	public Item next;
	public Item prev;

	public Item() {
		next = null;
		prev = null;
	}
	
	public Triangle getTriang() {
		return triang;
	}

	public void setTriang(Triangle triang) {
		this.triang = triang;
	}
	
	public String toString() {
		return triang.toString();
	}
};
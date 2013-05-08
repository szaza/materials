package collect;

import java.io.Serializable;

public class Item implements Serializable {

	private static final long serialVersionUID = 1L;
	private Triangle triang;
	private Item next;
	private Item prev;

	public Item() {
		setNext(null);
		setPrev(null);
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

	public Item getNext() {
		return next;
	}

	public void setNext(Item next) {
		this.next = next;
	}

	public Item getPrev() {
		return prev;
	}

	public void setPrev(Item prev) {
		this.prev = prev;
	}
};
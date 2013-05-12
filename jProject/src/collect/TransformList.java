package collect;

import java.awt.geom.AffineTransform;

public class TransformList {
	
	private int length;
	public TransformItem last, first;
	
	public TransformList() {
		first = new TransformItem();
		length = 0;

		first.prev = null;
		last = first;
		last.next = null;		
	}
	
	// Beszúrom az első elemet
	public void insertItemAsFirst(AffineTransform transform) {
		first.setTransform(transform);
		length++;
	}	
	
	// Beszúrom az új elemet a lista végére
	public void insertItemAsLast(AffineTransform transform) {
		TransformItem q = new TransformItem();
		q.setTransform(transform);
		last.next = q;
		q.prev = last;
		q.next = null;
		last = q;
		length++;
	}
	
	// Beszúrom az új elemet
	public void insertItem(AffineTransform transform) {
		if (length == 0)
			this.insertItemAsFirst(transform);
		else
			this.insertItemAsLast(transform);
	}	
	
	// Töröl egy elemet a listabol
	public void deleteItem(int index) {
		int count = 0;
		TransformItem p = first;

		while ((p != null) && (count < index)) {
			p = p.next;
			count++;
		}

		if (p != first)
			p.prev.next = p.next;
		if (p != last)
			p.next.prev = p.prev;
		if (p == last)
			last = p.prev;	
		if (p==first)
			first = p.next;
		
		length--;
	}

	// Lekerem az n-ik elem erteket
	public AffineTransform getValue(int n) {
		int count = 0;
		TransformItem tmp;
		tmp = first;

		while ((tmp != null) && (count < n)) {
			tmp = tmp.next;
			count++;
		}

		return tmp.getTransform();
	}
	
	//Felulirok egy elem erteket
	public void updateValue(AffineTransform transform, int n) {
		int count = 0;
		TransformItem tmp;
		tmp = first;

		while ((tmp != null) && (count < n)) {
			tmp = tmp.next;
			count++;
		}
		tmp.setTransform(transform);
	}
	
	public int getLength() {
		return length;
	}	
}

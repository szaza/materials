package collect;

import java.io.Serializable;
import collect.Item;

public class TriangleList implements Serializable {

	private static final long serialVersionUID = 1L;
	private int length;
	public Item last, first;

	public TriangleList() {
		first = new Item();
		length = 0;

		first.prev = null;
		last = first;
		last.next = null;
	}

	// Beszúrom az első elemet
	public void insertItemAsFirst(Triangle triang) {
		first.setTriang(triang);
		length++;
	}

	// Beszúrom az új elemet a lista végére
	public void insertItemAsLast(Triangle triang) {
		Item q = new Item();
		q.setTriang(triang);
		last.next = q;
		q.prev = last;
		q.next = null;
		last = q;
		length++;
	}

	// Beszúrom az új elemet
	public void insertItem(Triangle triang) {
		if (length == 0)
			this.insertItemAsFirst(triang);
		else
			this.insertItemAsLast(triang);
	}

	// Töröl egy elemet a listabol
	public void deleteItem(int index) {
		int count = 0;
		Item p = first;

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
	public Triangle getValue(int n) {
		int count = 0;
		Item tmp;
		tmp = first;

		while ((tmp != null) && (count < n)) {
			tmp = tmp.next;
			count++;
		}

		return tmp.getTriang();
	}

	// Felulirom egy elem erteket
	public void updateValue(Triangle triang, int n) {
		int count = 0;
		Item tmp;
		tmp = first;

		while ((tmp != null) && (count < n)) {
			tmp = tmp.next;
			count++;
		}
		tmp.setTriang(triang);
	}

	public int getLength() {
		return length;
	}
}

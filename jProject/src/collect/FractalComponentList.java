package collect;

import java.io.Serializable;
import collect.FractalComponent;

public class FractalComponentList implements Serializable {

	private static final long serialVersionUID = 1L;
	private int length;
	public FractalComponent last, first;

	public FractalComponentList() {
		first = new FractalComponent();
		length = 0;

		first.prev = null;
		last = first;
		last.next = null;
	}

	// Beszúrom az első elemet
	public void insertItemAsFirst(FractalComponent component) {
		first.setComponent(component);
		length++;
	}

	// Beszúrom az új elemet a lista végére
	public void insertItemAsLast(FractalComponent component) {
		FractalComponent q = new FractalComponent();
		q.setComponent(component);
		last.next = q;
		q.prev = last;
		q.next = null;
		last = q;
		length++;
	}

	// Beszúrom az új elemet
	public void insertItem(FractalComponent component) {
		if (length == 0)
			this.insertItemAsFirst(component);
		else
			this.insertItemAsLast(component);
	}

	// Töröl egy elemet a listabol
	public void deleteItem(int index) {
		int count = 0;
		FractalComponent p = first;

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
	public FractalComponent getValue(int n) {
		int count = 0;
		FractalComponent tmp;
		tmp = first;

		while ((tmp != null) && (count < n)) {
			tmp = tmp.next;
			count++;
		}

		return tmp;
	}

	// Felulirom egy elem erteket
	public void updateValue(FractalComponent component, int n) {
		int count = 0;
		FractalComponent tmp;
		tmp = first;

		while ((tmp != null) && (count < n)) {
			tmp = tmp.next;
			count++;
		}
		tmp.setComponent(component);
	}

	public int getLength() {
		return length;
	}
	
	public FListIterator getIterator() {
		return new TListIteratorImpl();
	}
	
	class TListIteratorImpl implements FListIterator {

		private FractalComponent current;
		private FractalComponent tmp;
		
		public TListIteratorImpl() {
			current = first;
			tmp = null;
		}
		
		@Override
		public boolean hasMoreElements() {
			return (current != null);
		}

		@Override
		public FractalComponent nextElement() {
			tmp = current;
			current = current.next;
			return tmp;
		}		
		
	}	
}

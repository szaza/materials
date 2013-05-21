package collect;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;

public class WrappingObj implements Serializable {

	private static final long serialVersionUID = 1L;
	private ArrayList <FractalComponent> fFractal;
	private ArrayList <FractalComponent> gFractal;
	
	public WrappingObj() {
		super();
		this.fFractal = new ArrayList <FractalComponent>();
		this.gFractal = new ArrayList <FractalComponent>();
	}
	
	public WrappingObj(LinkedList<FractalComponent> fFractal,
			LinkedList<FractalComponent> gFractal) {
		super();
		this.fFractal = new ArrayList <FractalComponent>();
		this.gFractal = new ArrayList <FractalComponent>();		
		this.fFractal.addAll(fFractal);
		this.gFractal.addAll(gFractal);
	}

	public ArrayList<FractalComponent> getfFractal() {
		return fFractal;
	}

	public void setfFractal(ArrayList<FractalComponent> fFractal) {
		this.fFractal = fFractal;
	}

	public ArrayList<FractalComponent> getgFractal() {
		return gFractal;
	}

	public void setgFractal(ArrayList<FractalComponent> gFractal) {
		this.gFractal = gFractal;
	}	
}

package collect;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;

//Egy burkoló osztály
//A szerializációhoz szükséges
public class WrappingObj implements Serializable {

	private static final long serialVersionUID = 1L;
	private ArrayList <FractalComponent> fFractal;
	private ArrayList <FractalComponent> gFractal;
	private ArrayList <Curves> cList;
	
	public WrappingObj() {
		super();
		this.fFractal = new ArrayList <FractalComponent>();
		this.gFractal = new ArrayList <FractalComponent>();
		this.cList = new ArrayList <Curves>();
	}
	
	public WrappingObj(LinkedList<Curves> cList, LinkedList<FractalComponent> fFractal,
			LinkedList<FractalComponent> gFractal) {
		super();
		this.fFractal = new ArrayList <FractalComponent>();
		this.gFractal = new ArrayList <FractalComponent>();
		this.cList = new ArrayList <Curves>();
		
		this.fFractal.addAll(fFractal);
		this.gFractal.addAll(gFractal);
		this.cList.addAll(cList);
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

	public ArrayList<Curves> getcList() {
		return cList;
	}

	public void setcList(ArrayList<Curves> cList) {
		this.cList = cList;
	}	
}

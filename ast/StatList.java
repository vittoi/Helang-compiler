package Compilador_Atv1.ast;

import java.util.ArrayList;

public class StatList{
	public StatList(ArrayList<Stat> l){
		this.list = l;
	}

	public void genC() {

	   for( Stat s : list )
	       s.genC();
	}
	
	public void run() {
		for( Stat s : list )
		       s.run();
	}
	
	private ArrayList<Stat> list;
}

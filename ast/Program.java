package Compilador_Atv1.ast;

import java.util.ArrayList;

public class Program {
	private VarList list;
	private ArrayList<Stat> stat;

	public Program(VarList list, ArrayList<Stat>  stat) {
		this.list = list;
		this.stat = stat;
	}
	public Program(VarList list) {
		this.list = list;
	}

	public void genC() {
	
	    System.out.println("#include <stdio.h>");
	    System.out.println("void main() {");
	    if ( list != null ) {        
	        list.genC();
	    }
	    if(stat != null){
			for ( Stat s : stat )
				s.genC();
	
	    }
	    
	    System.out.println("}");
	}

	public void run() {
	    if(stat != null){
			for ( Stat s : stat )
				s.run();
	
	    }
	}
	
}
	
package Compilador_Atv1.ast;

import java.util.ArrayList;

public class VarList{
	private ArrayList<Variable> ids;
	public VarList(ArrayList<Variable> ids) {
		this.ids = ids;
	}
	
	public void genC() {
		for(Variable id : ids) {
			System.out.println("int "+ id.getName() + ";\n");
		}
		
	}

}

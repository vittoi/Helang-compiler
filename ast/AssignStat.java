package Compilador_Atv1.ast;

public class AssignStat extends Stat {
	private Variable id;
	private Expr expr;

	public AssignStat(Variable id, Expr expr) {
		this.id = id;
		this.expr = expr;
	}

	@Override
	public void genC() {
		// TODO Auto-generated method stub
		System.out.print(id.getName() + " = ");
		expr.genC();
		System.out.println(";");
		
	}

	@Override
	public void run() {
		id.setValue(expr.run());
		
	}
	
}

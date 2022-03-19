package Compilador_Atv1.ast;

public class PrintlnStat extends Stat {
	public PrintlnStat(Expr expr) {
		this.expr = expr;
	}
	
	private Expr expr;

	@Override
	public void genC() {
		
		System.out.print("printf('%d\n',");
		expr.genC();     
		System.out.println(");");
		
	}

	@Override
	public void run() {
		System.out.println(expr.run());
		
	}
}

package Compilador_Atv1.ast;

public class PrintStat extends Stat{
	public PrintStat(Expr expr) {
		this.expr = expr;
	}
	
	public void genC() {
		System.out.print("printf('%d',");
		expr.genC(); 
		System.out.print(");\n");  
	}
	
	private Expr expr;

	@Override
	public void run() {
		System.out.print(expr.run());
		
	}
}

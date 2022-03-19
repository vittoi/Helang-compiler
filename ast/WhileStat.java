package Compilador_Atv1.ast;

public class WhileStat extends Stat{
	private Expr expr;
	private StatList list;

	public WhileStat(Expr expr, StatList l) {
		this.expr = expr;
		this.list = l;
	}

	@Override
	public void genC() {
		System.out.print("while( ");
		expr.genC();
		System.out.println("){");
		list.genC();
		System.out.println("\n}");
	}

	@Override
	public void run() {
		int value = expr.run();
		while(value == 1) {
			list.run();
			value = expr.run();
		}
	
	}
}

package Compilador_Atv1.ast;

import Compilador_Atv1.Lexer.Symbol;

public class SignalExpr extends Expr {

	public SignalExpr(Expr expr, Symbol op) {
		super();
		this.expr = expr;
		this.op = op;
	}

	private Expr expr;
	private Symbol op;

	@Override
	public void genC() {
	}

	@Override
	public int run() {
		return op == Symbol.MENOS ? -expr.run() : expr.run();
	}
}

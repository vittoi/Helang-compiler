package Compilador_Atv1.ast;

import Compilador_Atv1.Lexer.Symbol;

public class NumberExpr extends Expr {
	private Symbol op = null;
	private int value;

	public NumberExpr(Symbol op, int num) {
		this.op = op;
		this.value = num;
	}
	
	public NumberExpr(int num) {
		this.value = num;
	}
	
	@Override
	public void genC() {
		if(op != null) {
			System.out.println(op + " ");
		}
		System.out.print(value);
		
	}

	@Override
	public int run() {
		if(op == Symbol.MENOS)
			return value*-1;
		else
			return value;
			
	}

}

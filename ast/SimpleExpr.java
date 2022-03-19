package Compilador_Atv1.ast;

import Compilador_Atv1.Lexer.Symbol;

public class SimpleExpr extends Expr{
	private NumberExpr num;
	private Expr expr = null;
	private Expr simple;
	private Symbol op = null;
	private Variable ident = null;
	
	public SimpleExpr(NumberExpr num) {
		this.num = num;
	}
	
	public SimpleExpr(Expr expr) {
		this.expr = expr;
	}
	public SimpleExpr(Symbol op, Expr simple) {
		this.op = op;
		this.simple = simple;
	}
	public SimpleExpr(Variable ident) {
		this.ident = ident;
	}
	@Override
	public void genC() {
		if(op != null) {
			System.out.print(op+" ");	
			simple.genC();
			
		}else if(expr != null) {
			System.out.print("( ");
			expr.genC();
			System.out.print(" )");
		}else if(ident != null) {
			System.out.print(ident.getName());
		}else if(num != null) {
			num.genC();
		}
		
	}
	@Override
	public int run() {
		if(op != null) {
			switch(op) {
				case MAIS:
						return simple.run();
				case MENOS:
					
						return (-1*simple.run());
				case NAO:
					if(simple.run()!=0)
						return 0;
					else
						return 1;
					default:
						throw new RuntimeException("Operador unitário inválido");
			}
		}else if(expr != null) {
			return expr.run();
		}else if(ident != null) {
			return ident.getValue();
		}else{
			return num.run();
		}
		
	}

}

package Compilador_Atv1.ast;

import Compilador_Atv1.Lexer.Symbol;

public class CompositeExpr extends Expr {

	Expr esq, dir;
	Symbol op;
	
	public CompositeExpr(Expr esq, Symbol op, Expr dir) {
		super();
		this.esq = esq;
		this.dir = dir;
		this.op = op;
	}
	public CompositeExpr(Expr esq) {
		super();
		this.esq = esq;
	}
	
	@Override
	public void genC() {
		esq.genC();
		if(op != null && dir != null) {
			System.out.print(" "+ op);
			dir.genC();
		}
		
	}

	@Override
	public int run() {
		switch(op) {
		case OR:
			if(esq.run()!=0 || dir.run()!=0)
				return 1;
			else
				return 0;
		case AND:
			if(esq.run()!=0 && dir.run()!=0)
				return 1;
			else
				return 0;
		case MENOR:
			if(esq.run() < dir.run())
				return 1;
			else
				return 0;
		case MENOR_IGUAL:
			if(esq.run() <= dir.run())
				return 1;
			else
				return 0;
		case MAIOR:
			if(esq.run() > dir.run())
				return 1;
			else
				return 0;
		case MAIOR_IGUAL:
			if(esq.run() >= dir.run())
				return 1;
			else
				return 0;
		case IGUALDADE:
			if(esq.run() == dir.run())
				return 1;
			else
				return 0;
		case DIFERENTE:
			if(esq.run() != dir.run())
				return 1;
			else
				return 0;
		case MAIS:
			return(esq.run() + dir.run());
		case MENOS:
			return(esq.run() - dir.run());
		case MULTIPLICACAO:
			return(esq.run() * dir.run());
		case DIVISAO:
			return(esq.run() / dir.run());
		case MODULO:
			return(esq.run() % dir.run());
		default:
			throw new RuntimeException("Operador invalido");
		}
	}
}

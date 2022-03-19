package Compilador_Atv1;

import java.util.ArrayList;
import java.util.HashMap;

import Compilador_Atv1.Lexer.Lexer;
import Compilador_Atv1.Lexer.Symbol;
import Compilador_Atv1.ast.AssignStat;
import Compilador_Atv1.ast.CompositeExpr;
import Compilador_Atv1.ast.Expr;
import Compilador_Atv1.ast.ForStat;
import Compilador_Atv1.ast.IfStat;
import Compilador_Atv1.ast.NumberExpr;
import Compilador_Atv1.ast.PrintStat;
import Compilador_Atv1.ast.PrintlnStat;
import Compilador_Atv1.ast.Program;
import Compilador_Atv1.ast.SimpleExpr;
import Compilador_Atv1.ast.Stat;
import Compilador_Atv1.ast.StatList;
import Compilador_Atv1.ast.VarList;
import Compilador_Atv1.ast.Variable;
import Compilador_Atv1.ast.WhileStat;



public class Compiler {
	private Lexer lexer;
	private HashMap<String, Variable> varTable;
	
	public Compiler() {
		
	}
   
	public Program program(char[] input) {
		
		lexer = new Lexer(input);
		lexer.nextToken();
		this.varTable = new HashMap<>();
		ArrayList<Stat> list = new ArrayList<Stat>();
		VarList varList = null;
		if(lexer.token == Symbol.VAR) {	
			
			varList = varList();
		}
		
		while ( lexer.token == Symbol.IDENT || lexer.token == Symbol.IF || 
				lexer.token == Symbol.FOR || lexer.token == Symbol.PRINT || lexer.token == Symbol.PRINTLN 
				|| lexer.token == Symbol.WHILE) {
			list.add(stat());
		}
			// lexer.token == Symbol.EOF
		if(list.size() == 0 && varList != null) {
			
			return new Program(varList);
		}else if(list.size() > 0 && varList != null) {
			return new Program(varList, list);
		}else {
			erro("esperava uma lista de variavel");
		}
		return null;
	}
	
	private VarList varList() {
		ArrayList<Variable> identList = new ArrayList<Variable>();
		while(lexer.token == Symbol.VAR) {
			lexer.nextToken();
			if(lexer.token == Symbol.INT) {
				lexer.nextToken();
				
				if(lexer.token == Symbol.IDENT) {
					String varName = lexer.getStringValue();

					if(varTable.get(varName) == null) {
						Variable nova = new Variable(varName);
						identList.add(nova);
						varTable.put(varName,nova);
					}else {
						erro("Essa variavel ja existe");
					}
					lexer.nextToken();
					if(lexer.token == Symbol.PONTO_VIRGULA) {
						lexer.nextToken();
					}else {
						erro("Esperava um ;");
					}
				}else{
					erro("Esperava um identificador");
				}
			}else {
				erro("Esperava um Int");
			}
		}
		return new VarList(identList);
	}
	

	private Expr expr() {
		Expr esq = andExpr();
		Symbol op = null;
		Expr dir = null;
		
		if(lexer.token == Symbol.OR) {
			op = lexer.token;
			lexer.nextToken();
			dir = andExpr();
			return new CompositeExpr(esq, op, dir);
		}else {
			return esq;
		}
	}

	private Expr andExpr() {
		Expr esq = relExpr();
		
		if(lexer.token == Symbol.AND) {
			Symbol op = null;
			Expr dir = null;
			op = lexer.token;
			lexer.nextToken();
			dir = relExpr();
			return new CompositeExpr(esq, op, dir);
		}else {
			return esq;
		}
	}
	
	private Expr relExpr() {
		Expr esq = addExpr();
		Expr dir = null;
		
		if(lexer.token == Symbol.MENOR || lexer.token == Symbol.MENOR_IGUAL || lexer.token == Symbol.MAIOR || lexer.token == Symbol.MAIOR_IGUAL || lexer.token == Symbol.IGUALDADE || lexer.token == Symbol.DIFERENTE) {
			Symbol op = lexer.token;
			lexer.nextToken();
			dir = addExpr();
			return new CompositeExpr(esq, op, dir);
		}else {
			return esq;
		}
	}
	
	private Expr addExpr() {
		Expr esq = multiExpr();
		Expr dir = null;
		Symbol op = null;
		
		while(lexer.token == Symbol.MAIS || lexer.token == Symbol.MENOS) {
			op = lexer.token;
			lexer.nextToken();
			dir = multiExpr();
			
			esq = new CompositeExpr(esq, op, dir);
		}
		return esq;
	}
	
	private Expr multiExpr() {
		Expr esq = simpleExpr();
		Expr dir = null;
		Symbol op = null;
		while(lexer.token == Symbol.MULTIPLICACAO || lexer.token == Symbol.DIVISAO || lexer.token == Symbol.MODULO) {
			op = lexer.token;
			lexer.nextToken();
			dir = simpleExpr();
			
			esq = new CompositeExpr(esq, op, dir);
		}
		return esq;
	}
	
	private Expr simpleExpr(){
		Expr expr = null;
		Symbol op = null;
		if(lexer.token == Symbol.NUMBER ) { 
			return numberExpr();

		} else if ( lexer.token == Symbol.MAIS || lexer.token == Symbol.MENOS) {
			op = lexer.token;
			lexer.nextToken();
			expr = simpleExpr();
			return new SimpleExpr(op, expr);
		
		}else if(lexer.token == Symbol.ABRE_PARENTESE){
			lexer.nextToken();
			expr = expr();
			
			if(lexer.token == Symbol.FECHA_PARENTESE) {
				
				lexer.nextToken();
				return new SimpleExpr(expr);
			}else {
				erro("Esperava um )");
			}
		}else if(lexer.token == Symbol.NAO) {
			op = lexer.token;
			lexer.nextToken();
			expr = simpleExpr();
			return new SimpleExpr(op, expr);
			
		}else if(lexer.token == Symbol.IDENT) {
			String varName = lexer.getStringValue();

			if(varTable.get(varName) != null) {
				Variable ident = varTable.get(varName);
				lexer.nextToken();
				return new SimpleExpr(ident);
			}else {
				erro("A variavel não foi declarada");
			}
			
		}else {
			erro("Nao encontrou nenhuma expressao valida");
		}
		return null;
	}
		
	private Stat stat() {
		switch(lexer.token) {
			case IDENT:
				return assignStat();
			case IF:
				return ifStat();
			case FOR:
				return forStat();
			case PRINT:
				return printStat();
			case PRINTLN:
				return printlnStat();
			case WHILE:
				return whileStat();
			default:
				erro("Não encontrou o Symbol");
				return null;
		}

	}

	private AssignStat assignStat() {
		String varName = lexer.getStringValue();
		Variable ident = null;
		
		if(varTable.get(varName) != null) {
			ident = varTable.get(varName);
			lexer.nextToken();
		}else {
			erro("A variavel não foi declarada");
		}
		
		if(lexer.token == Symbol.IGUAL) {
			lexer.nextToken();	
		}else {
			erro("Esperavamos um = aqui");
		}
		Expr expr = expr();
		if(lexer.token == Symbol.PONTO_VIRGULA) {
			lexer.nextToken();
			return new AssignStat(ident, expr);
		}else {
			erro("esperava um ;");
			return null;
		}
	}

	private IfStat ifStat() {
		StatList listElsePart = null;
		StatList listIfPart = null;
		
		lexer.nextToken();
		
		Expr expr = expr();
		if(lexer.token == Symbol.ABRE_CHAVES) {
			lexer.nextToken();
			listIfPart = statList();
		}else {
			erro("Esperava {");
		}
		
		if(lexer.token == Symbol.ELSE) {
			
			lexer.nextToken();
			if(lexer.token == Symbol.ABRE_CHAVES) {
				lexer.nextToken();
				listElsePart = statList();
			}else {
				erro("Esperava {");
			}
			return new IfStat(expr, listIfPart, listElsePart);
		}
		return new IfStat(expr, listIfPart);
	}
	
	private ForStat forStat() {
		
		Variable ident = null;
		Expr exprEsq = null;
		Expr exprDir = null;
		StatList list = null;
		
		lexer.nextToken();
		if(lexer.token == Symbol.IDENT ) {
			String varName = lexer.getStringValue();
			if(varTable.get(varName) == null) {
				
				ident = new Variable(varName);
				varTable.put(varName, ident);
			}else {
				erro("A variavel já foi declarada");
			}
			lexer.nextToken();
			
			if(lexer.token == Symbol.EM) {
				
				lexer.nextToken();
				exprEsq = expr();
				if(lexer.token == Symbol.PONTOPONTO) {
					
					lexer.nextToken();
					exprDir = expr();
					if(lexer.token == Symbol.ABRE_CHAVES) {//por conta do Statlist
						
						lexer.nextToken();
						list = statList();
					}else {
						erro("Esperava um {");
					}
				}else {
					erro("Esperava um ..");
				}
			}else {
				erro("esperava um in");
			}
		}else {
			erro("esperava um identificador");
		}
		return new ForStat(ident, exprEsq, exprDir, list);
	}
	
	private PrintStat printStat() {
		lexer.nextToken();
		Expr expr = expr();
		if(lexer.token == Symbol.PONTO_VIRGULA) {
			lexer.nextToken();
		}else {
			erro("esperava um ;");
		}
		return new PrintStat(expr);
	}
	
	private PrintlnStat printlnStat() {
		lexer.nextToken();
		Expr expr = expr();
		if(lexer.token == Symbol.PONTO_VIRGULA) {
			lexer.nextToken();
		}else {
			erro("esperava um ;");
		}
		return new PrintlnStat(expr);
	}
	
	private StatList statList() {
		ArrayList<Stat> list = new ArrayList<Stat>();
		
		while(lexer.token == Symbol.IDENT || lexer.token == Symbol.IF || lexer.token == Symbol.FOR || lexer.token == Symbol.PRINT || lexer.token == Symbol.PRINTLN || lexer.token == Symbol.WHILE) {
			list.add(stat());
		}
		
		if(lexer.token != Symbol.FECHA_CHAVES) {
			erro("esperava um }");
		}
		lexer.nextToken();
		return new StatList(list);
	}
	
	private WhileStat whileStat() {
		lexer.nextToken();
		Expr expr = expr();
		StatList list = null;
		if(lexer.token == Symbol.ABRE_CHAVES) {
			lexer.nextToken();
			list  = statList();
		}else {
			erro("Esperava {");
		}
		return new WhileStat(expr, list);
	}

	private NumberExpr numberExpr() {
		int num = lexer.getNumberValue();
		lexer.nextToken();
		return new NumberExpr(num);
	}

	public void erro(String mensagem)
	{
		
		System.out.println(mensagem);
		throw new RuntimeException(mensagem);
	}
}

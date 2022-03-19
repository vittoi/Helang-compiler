package Compilador_Atv1.Lexer;

public enum Symbol {
	
	IDENT("Identifier"),//
	IGUAL("="),//
	PONTO_VIRGULA(";"),//
	IF("if"),//
	ELSE("else"),//
	FOR("for"),//
	EM("in"),//
	PONTOPONTO(".."),//
	PRINT("print"),//
	PRINTLN("println"),//
	ABRE_CHAVES("{"),//
	FECHA_CHAVES("}"),//
	WHILE("while"),//
	OR("||"),//
	AND("&&"),//
	ABRE_PARENTESE("("),//
	FECHA_PARENTESE(")"),//
	NAO("!"),//
	MENOR("<"),//
	MENOR_IGUAL("<="),//
	MAIOR(">"),//
	MAIOR_IGUAL(">="),//
	IGUALDADE("=="),//
	DIFERENTE("!="),//
	MAIS("+"),//
	MENOS("-"),//
	MULTIPLICACAO("*"),//
	DIVISAO("/"),//
	MODULO("%"),
	EOF("eof"),//
	NUMBER("Number"),//
	VAR("var"),//
	INT("Int");//
	
	Symbol(String name){
		this.name = name;
	}
	
	public String toString() {return name;}
	private String name;
}

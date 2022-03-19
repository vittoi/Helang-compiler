package Compilador_Atv1.Lexer;

import java.util.Hashtable;
import Compilador_Atv1.Compiler;


public class Lexer {
	static private Hashtable<String, Symbol> keywordsTable;
	
	private int numberValue;
	public Symbol token;
    private String stringValue;
    private int  tokenPos;
    private char []input;
    private Compiler compiler;
    
	 static {
	       keywordsTable = new Hashtable<String, Symbol>();
	       keywordsTable.put( "var", Symbol.VAR );
	       keywordsTable.put( "if", Symbol.IF );
	       keywordsTable.put( "else", Symbol.ELSE );
	       keywordsTable.put( "Int", Symbol.INT );
	       keywordsTable.put( "for", Symbol.FOR );
	       keywordsTable.put( "in", Symbol.EM );
	       keywordsTable.put( "print", Symbol.PRINT );
	       keywordsTable.put( "println", Symbol.PRINTLN );
	       keywordsTable.put( "while", Symbol.WHILE );
	  
	    }
	 
	 public Lexer( char []input) {
	        this.input = input;
	        input[input.length - 1] = '\0';
	        tokenPos = 0;
	        compiler = new Compiler();
	    }
	 
	 public void nextToken() {
	        char ch;
	        while (  (ch = input[tokenPos]) == ' ' || ch == '\r' || ch == '\t' || ch == '\n')  {
	          tokenPos++;
	         }
	        
	        if ( ch == '\0') {
	          token = Symbol.EOF;
	        }else {
	            if ( Character.isLetter( ch ) ) {
	                StringBuffer ident = new StringBuffer();
	                
	                while ( Character.isLetter( input[tokenPos] ) ) {
	                    ident.append(input[tokenPos]);
	                    tokenPos++;
	                }
	                stringValue = ident.toString();
	                Symbol value = keywordsTable.get(stringValue);//TODO Proxima coisa a fazer
	                if ( value == null )
	                   token = Symbol.IDENT;
	                else
	                   token = value;//Valor pode ser uma palavra reservada da linguagem
	               
	            }else if ( Character.isDigit( ch ) ) {
	            	
	                StringBuffer number = new StringBuffer();
	                
	                while ( Character.isDigit( input[tokenPos] ) ) {
	                    number.append(input[tokenPos]);
	                    tokenPos++;
	                }
	                token = Symbol.NUMBER;
	              
	                numberValue = Integer.valueOf(number.toString()).intValue();
	                
	            }else {
	                tokenPos++;
	                switch ( ch ) {
	                    case '+' :
	                      token = Symbol.MAIS;
	                      break;
	                    case '-' :
	                      token = Symbol.MENOS;
	                      
	                      break;
	                    case '*' :
	                      token = Symbol.MULTIPLICACAO;
	                      break;
	                    case '/' :
	                      token = Symbol.DIVISAO;
	                      break;
	                    case '<' :
	                      if ( input[tokenPos] == '=' ){
	                    	tokenPos++;
	                        token = Symbol.MENOR_IGUAL;
	                      }else{
	                        token = Symbol.MENOR;
	                      }
	                      break;
	                    case '>' :
	                      if ( input[tokenPos] == '=' ) {
	                        tokenPos++;
	                        token = Symbol.MAIOR_IGUAL;
	                      }else {
	                        token = Symbol.MAIOR;
	                        }
	                      break;
	                    case '=' :
	                      if ( input[tokenPos] == '=' ) {
	                        tokenPos++;
	                        token = Symbol.IGUALDADE;
	                      }else {
	                        token = Symbol.IGUAL;
	                      }
	                      break;
	                    case '(' :
	                      token = Symbol.ABRE_PARENTESE;
	                      break;
	                    case ')' :
	                      token = Symbol.FECHA_PARENTESE;
	                      break;
	                    case '!' :
	                    	if(input[tokenPos] == '=') {
	                    		tokenPos++;
	                    		token = Symbol.DIFERENTE;
	                    	}else {
	                    		token = Symbol.NAO;
	                    	}
	                      break;
	                    case ';' :
	                      token = Symbol.PONTO_VIRGULA;
	                    break;
	                    case '.' :
	                    	if(input[tokenPos] == '.')
	                    		tokenPos++;
	                    		token = Symbol.PONTOPONTO;
	                     break;
	                    case '{' :
	                        token = Symbol.ABRE_CHAVES;
	                    break;
	                    case '}' :
	                        token = Symbol.FECHA_CHAVES;
	                    break;
	                    case '|' :
	                     	if(input[tokenPos] == '|') {
	                     		tokenPos++;
	                     		token = Symbol.OR;
	                     	}
	                    break;
	                    case '&' :
	                    	if(input[tokenPos] == '&') {
	                    		tokenPos++;
	                     		token = Symbol.AND;
	                    	}
	                    break;
	                    case '%' :
	                     	token = Symbol.MODULO;
	                    break;
	                    default :
	                    	String erro = "caracter errado: " + ch;
	                    	compiler.erro(erro);
	                }
	            }
	          }
	    }
	 
	 public String getStringValue() {
		 return stringValue;
	 }
	 public int getNumberValue() {
		 return numberValue;
	 }
		
}

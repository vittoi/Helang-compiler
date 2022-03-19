# Helang-compiler

java main.Program -{opção} "<nome-arquivo>he"
  
__ Opções: __
* run - Executa o codigo helang do arquivo.
* gen - Gera o código em C que gera o mesmo resultado quando compilado.
  
__ O Compilador é dado pela gramática: __
  
* Program ::= VarList { Stat }
* VarList ::= { "var" Int Ident ";" }
* Stat ::= AssignStat | IfStat | ForStat | PrintStat |
* PrintlnStat | WhileStat
* AssignStat ::= Ident "=" Expr ";"
* IfStat ::= "if" Expr StatList [
* "else" StatList ]
* ForStat ::= "for" Id "in" Expr ".." Expr StatList
* PrintStat ::= "print" Expr ";"
* PrintlnStat ::= "println" Expr ";"
* StatList ::= "{" { Stat } "}"
* WhileStat ::= "while" Expr StatList
* Expr ::= AndExpr [ "||" AndExpr ]
* AndExpr ::= RelExpr [ "&&" RelExpr ]
* RelExpr ::= AddExpr [ RelOp AddExpr ]
* AddExpr ::= MultExpr { AddOp MultExpr }
* MultExpr ::= SimpleExpr { MultOp SimpleExpr }
* SimpleExpr ::= Number | ’(’ Expr ’)’ | "!" SimpleExpr
* | AddOp SimpleExpr | Ident
* RelOp ::= ’<’ | ’<=’ | ’>’ | ’>=’| ’==’ | ’!=’
* AddOp ::= ’+’| ’-’
* MultOp ::= ’*’ | ’/’ | ’%’
* Number ::= [’+’|’-’] Digit { Digit }

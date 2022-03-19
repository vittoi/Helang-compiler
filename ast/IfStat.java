package Compilador_Atv1.ast;

public class IfStat extends Stat {
	public IfStat(Expr expr, StatList ifPart, StatList elsePart) {
		this.expr = expr;
		this.ifPart = ifPart;
		this.elsePart = elsePart;
	}
	
	public IfStat(Expr expr, StatList listIfPart) {
		this.expr = expr;
		this.ifPart = listIfPart;
	}

	private Expr expr;
	private StatList ifPart;
	private StatList elsePart = null;
	@Override
	public void genC() {
		
		System.out.print("if ( ");
        expr.genC();
        System.out.println(" ) { ");
        if ( ifPart != null )
          ifPart.genC();                   
        System.out.print("\n}");
        if ( elsePart != null ) {
          System.out.println("else {");
          elsePart.genC();
          System.out.println("}");
        }
	}

	@Override
	public void run() {
		int value = expr.run();
		if(value ==1) {
			ifPart.run();
		}else if(value == 0 && elsePart !=null) {
			elsePart.run();
		}
		
	}
}

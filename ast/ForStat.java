package Compilador_Atv1.ast;

public class ForStat extends Stat {
	private Variable id;
	private Expr exprDe;
	private Expr exprAte;
	private StatList list;

	public ForStat(Variable id, Expr exprDe, Expr exprAte, StatList l ) {//TODO tem q descobrir oq e o IDENT nesse caso
		this.id = id;
		this.exprDe = exprDe;
		this.exprAte = exprAte;
		this.list = l;
	}

	@Override
	public void genC() {
		

		System.out.print("for ( " + id.getName() + " = ");
        exprDe.genC();
        System.out.print("; " + id.getName() + " <= ");
        exprAte.genC();
        System.out.println("; " + id.getName() + "++ )");

        if ( list != null ) { 
              list.genC();          
        }
		
	}

	@Override
	public void run() {
		int d = exprDe.run();
		int t = exprAte.run();
		if(t>d) {
			for(int i = d; i<=t; i++) {
				id.setValue(i);
				list.run();
			}
		}else {
			throw new RuntimeException("Parametros do For não seguem a regra");
		}
		// TODO Auto-generated method stub
		
	}
}

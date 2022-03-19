package Compilador_Atv1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import Compilador_Atv1.ast.Program;

public class Main {
	private static char []input;
	
	public static void main(String [] args ) {
		File file;
		FileReader stream = null;
		String mode = null;

		if(args.length == 4) {
			file = new File(args[3]);
			try {
				stream = new FileReader(file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			input = new char[ (int ) file.length() + 1 ];
			
			try {
				stream.read( input, 0, (int ) file.length() );
			} catch (IOException e) {
				e.printStackTrace();
			}
			mode = args[2];
		}
		
        Compiler compiler = new Compiler();
        Program program = compiler.program(input);
        
        if(mode == "-gen") {
        	program.genC();
        }else if(mode == "-run") {
        	program.run();
        }else {
        	compiler.erro("Modo não suportado");
        }
    }

}

package Domini;

import java.util.Scanner;

public class DriverAsgn {
    private Scanner S = new Scanner(System.in);
	public void run() {

		int op;
		Asgn A;
		Aula aula = new Aula("A5", "001", 40, "T");
		Classe classe = new Classe("01", "t", 1, 10, 40);
		A = new Asgn(aula, classe);
		System.out.println("Benvingut al Driver de la classe Asgn");
		System.out.println("La classe Asgn es una classe de assignacions. Es dir, assigna un element de la classe Aula amb un element de la classe Classe.");
		System.out.println("Posem per exemple que tenim una Aula: A5 (edifici) 01 (codi), i una Classe; 01 (codi) teoria (tipus) 2 (durada en hores) 20 (grup). La classe Asgn tan sols relaciona aquests dos elements (Aula-Classe).");
		System.out.println("Per consultar la Aula prem 0, per consultar la Classe prem 1, per modificar la Aula prem 2, per modificar la Classe prem 3");
		op = S.nextInt();
		while (op != 4) {
			if (op == 0) System.out.println(A.getAula());
			else if (op == 1) System.out.println(A.getClasse());
			else if (op == 2) {
				System.out.println("Edifici:");
				String edifici = S.next();
				System.out.println("Codi:");
				String codi = S.next();
				System.out.println("Capacitat:");
				int capacitat = S.nextInt();
                                System.out.println("Tipus:");
				String tipus = S.next();
				aula = new Aula(edifici, codi, capacitat, tipus);
				A.setAula(aula);
			}
			else if (op == 3) {
				System.out.println("Codi de la Classe:");
				String codi = S.next();
				System.out.println("Tipus de la Classe:");
				String tipus = S.next();
				System.out.println("Durada de la Classe:");
				int durada = S.nextInt();
				System.out.println("Grup de la Classe:");
				int grup = S.nextInt();
				System.out.println("Capacitat de la Classe:");
				int capacitat = S.nextInt();
				classe = new Classe(codi, tipus, durada, grup, capacitat);
				A.setClasse(classe);
			}
		}
		

	}
}

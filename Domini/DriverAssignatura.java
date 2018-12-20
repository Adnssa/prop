package Domini;

import java.util.ArrayList;
import java.util.Scanner;

public class DriverAssignatura {
    private Scanner S = new Scanner(System.in);
    private Scanner X = new Scanner(System.in);
	public void run() {

		Assignatura A;
		int op;
		String nom;
		String acronim;
		String quatri;
                ArrayList<String> correquisits = null;
		System.out.println("Benvingut al Driver de la Classe Assignatura");
		System.out.println("Una Assignatura consta de un nom, un acronim i un quatrimestre. Introdueix les dades per crear una Assingatura.");
		System.out.println("Nom de la Assignatura:");
		nom = S.nextLine();
		System.out.println("Acronim de la Assignatura:");
		acronim = S.next();
		System.out.println("Quatrimestre de la Assignatura:");
		quatri = S.next();
                //TODO: Correquisits
		A = new Assignatura(nom, acronim, quatri, correquisits);
		System.out.println("Has creat una Assignatura. Per consultar-la prem 1, per modificar-la prem 2, per acabar prem 3");
		op = S.nextInt();
		while (op != 3) {
			if (op == 1) {
				nom = A.getNom();
				acronim = A.getAcronim();
				quatri = A.getQuatri();
				System.out.println("Assignatura: "+nom+ ", " +acronim+ ", " +quatri+".");
			}
			if (op == 2) {
				System.out.println("Que vols modificar? Prem 0 per modificar el nom, 1 per modificar el acronim o 2 per modificar el quatrimestre.");
				op = S.nextInt();
				if (op == 0) {
					//Scanner X = new Scanner(System.in);
					System.out.println("Nom");
					String nom2 = X.nextLine();
					A.setNom(nom2);
					//X.close();
				}
				else if (op == 1) {
					System.out.println("Acronim:");
					acronim = S.next();
					A.setAcronim(acronim);
				}
				else if (op == 2) {
					System.out.println("Codi:");
					quatri = S.next();
					A.setQuatri(quatri);
				}
			}
			System.out.println("Per consultar l'Assignatura prem 1, per modificar-la prem 2, per acabar prem 3");
			op = S.nextInt();
		}

	}
}

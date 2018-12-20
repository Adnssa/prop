package Domini;

import java.util.ArrayList;
import java.util.Scanner;

public class DriverPestudis { //falta afegir coses tant aqui com a la classe Pestudis
	/*public static void main(String[] args) {
		DriverPestudis DP = new DriverPestudis();
		DP.run();
	}*/
	private Scanner S = new Scanner(System.in);
	private Scanner X = new Scanner(System.in);
	public void run() {
		
		Pestudis p;
		Assignatura a1 = new Assignatura("Projectes de Programacio", "PROP", "Q4");
		Assignatura a2 = new Assignatura("Parallelism", "PAR", "Q4");
		int op;
		ArrayList<Assignatura> assignatures = new ArrayList<Assignatura>();
		assignatures.add(a1);
		assignatures.add(a2);
		p = new Pestudis(assignatures);	
		System.out.println("Benvingut al Driver de la classe Pestudis");
		System.out.println("Aquesta classe consiteix en el Pla D'estudis del horari, que basicament es una llista de Assignatures");
		System.out.println("Una assignatura consta de un nom, un acronim i un quatrimestre. Tenim com a exemple un Pla d'Estudis amb 2 assignatures. Si vols consultar-lo prem 0, si vols afegir assignatures al Pla d'Estudis prem 1, si vols modificar assignatures del Pla d'Estudis prem 2, si vols sortir prem 3.");
		op = S.nextInt();
		while (op != 3) {
			if (op == 0) {
				assignatures = p.getAssignatures();
				for (int i=0; i < assignatures.size(); ++i) {
					int j=i+1;
					String n = assignatures.get(i).getNom();
					String a = assignatures.get(i).getAcronim();
					String q = assignatures.get(i).getQuatri();
					System.out.println("Assignatura "+j+": " +n+"," +a+", " +q+".");
				}
			}
			if (op == 1) {
				Assignatura A;
				String noms;
				String acronim;
				String quatri;
				System.out.println("Una Assignatura consta de un nom, un acronim i un quatrimestre. Introdueix les dades per crear una Assingatura.");
				System.out.println("Nom de la Assignatura:");
				noms = X.nextLine();
				System.out.println("Acronim de la Assignatura:");
				acronim = S.next();
				System.out.println("Quatrimestre de la Assignatura:");
				quatri = S.next();
				A = new Assignatura(noms, acronim, quatri);
				p.afegirAsig(A);
			}
			if (op == 2) {
				System.out.println("Introdueix el numero de la assignatura que vols modificar:");
				int asignatura_modificada = S.nextInt() -1;
				System.out.println("Introdueix les noves dades. Prem 1 x modificar nom, 2 per modificar acronim, 3 per modificar quatrimestre, 4 per guardar i acabar");
				op = S.nextInt();
				while (op != 4) {
					if (op == 1) {
						System.out.println("Nom de la Asignatura:");
						String n = S.next();
						p.modificarNomAssignatura(asignatura_modificada, n);
					}
					else if (op == 2) {
						System.out.println("Acronim:");
						String a = S.next();
						p.modificarAcronimAssignatura(asignatura_modificada, a);
					}
					else if (op == 3) {
						System.out.println("Quatrimestre:");
						String q = S.next();
						p.modificarQuatrimestreAssignatura(asignatura_modificada, q);
					}
					System.out.println("Introdueix les noves dades. Prem 1 x modificar nom, 2 per modificar acronim, 3 per modificar quatrimestre, 4 per guardar i acabar");
					op = S.nextInt();
				}
			}
			System.out.println("Si vols consultar-lo prem 0, si vols afegir assignatures al Pla d'Estudis prem 1, si vols modificar assignatures del Pla d'Estudis prem 2, si vols sortir prem 3.");
			op = S.nextInt();
		}
	}
}

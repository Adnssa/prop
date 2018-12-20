package Domini;

import java.util.Scanner;

public class MenuInteractiu {
	private Scanner S = new Scanner(System.in);
	private int op;
	private DriverPestudis DP;
	private DriverClasse DC;
	private DriverAula DA;
	private DriverAssignatura DAs;
	private DriverHorari DH;
	private ScheduleGenerator SG;
	
	public void executa() {
		System.out.println("Benvingut al Menu interactiu del generador d'horaris:");
		funcions();
		op = S.nextInt();
		while (op != 0) {
			if (op == 1) {
				DP = new DriverPestudis();
				DP.run();
			}
			if (op == 2) {
				DC = new DriverClasse();
				DC.run();
			}
			if (op == 3) {
				DA = new DriverAula();
				DA.run();
			}
			if (op == 4) {
				DAs = new DriverAssignatura();
				DAs.run();
			}
			if (op == 5) {
				String file;
				file = S.next();
				SG = new ScheduleGenerator(file);
			}
			if (op == 6) {
				SG.print();
			}
			if (op == 7) {
				Horari horari = new Horari();
				horari.listFiles();
			}
			if (op == 8) {
				String file;
				file = S.next();
				Horari horari = new Horari();
				horari.loadFiles(file);
			}
			funcions();
			op = S.nextInt();
		}
	}
	private void funcions() {
		System.out.println("Prem 1 per anar al Driver de la Classe Pla D'Estudis");
		System.out.println("Prem 2 per anar al Driver de la Classe Classe");
		System.out.println("Prem 3 per anar al Driver de la Classe Aula");
		System.out.println("Prem 4 per anar al Driver de la Classe Assignatura");
		System.out.println("Prem 5 per assignar un fitxer per generar l'Horari");
		System.out.println("Prem 6 per generar l'horari (i guardar-l'ho)");
		System.out.println("Prem 7 per llistar els horaris guardats");
		System.out.println("Prem 8 per carregar un horari guardat");
		System.out.println("Prem 0 per sortir");
	}
	
}

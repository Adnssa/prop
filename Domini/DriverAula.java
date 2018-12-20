package Domini;

import java.util.Scanner;

public class DriverAula {
    private Scanner S = new Scanner(System.in);
	public void run() {

		Aula a;
		int op;
		String edifici;
		String codi;
		int capacitat;
                String tipus;
		System.out.println("Benvingut al Driver de la Classe Aula");
		System.out.println("Una Aula consta de un edifici, un codi, una capacitat i un tipus. Ara has de introduir els atributs per crear la Aula.");
		System.out.println("Edifici:");
		edifici = S.next();
		System.out.println("Codi:");
		codi = S.next();
		System.out.println("Capacitat:");
		capacitat = S.nextInt();
                System.out.println("Tipus:");
		tipus = S.next();
		a = new Aula(edifici, codi, capacitat, tipus);
		System.out.println("Has creat una Aula. Per consultar-la prem 1, per modificar-la prem 2, per acabar prem 3");
		op = S.nextInt();
		while (op != 3) {
			if (op == 1) {
				edifici = a.getEdifici();
				codi = a.getCodi();
				capacitat = a.getCapacitat();
				System.out.println("Aula: "+edifici+", " +codi+", " +capacitat+".");
			}
			else if (op == 2) {
				System.out.println("Que vols modificar? Prem 0 per modificar l'edifici, 1 per modificar el codi, 2 per modificar la capacitat, 3 per modificar el tipus");
				op = S.nextInt();
				if (op == 0) {
					System.out.println("Edifici:");
					edifici = S.next();
					a.setEdifici(edifici);
				}
				else if (op == 1) {
					System.out.println("Codi:");
					codi = S.next();
					a.setCodi(codi);
				}
				else if (op == 2) {
					System.out.println("Capacitat:");
					capacitat = S.nextInt();
					a.setCapacitat(capacitat);
				}
                                else if (op == 3) {
					System.out.println("Tipus:");
					tipus = S.next();
					a.setTipus(tipus);
				}
			}
			System.out.println("Per consultar l'Aula prem 1, per modificar-la prem 2, per acabar prem 3");
			op = S.nextInt();
		}

	}
}

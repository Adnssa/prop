package Domini;

import java.util.ArrayList;

public class Pestudis {
	private ArrayList<Assignatura> m_assignatures = new ArrayList<Assignatura>();

	public Pestudis(ArrayList<Assignatura> assignatures) {
		// Init Arguments
		m_assignatures = assignatures;
		return;
	}

	//GETTERS
	public ArrayList<Assignatura> getAssignatures() {
		return m_assignatures;
	}
	
	/*public ArrayList<Assignatura> getAssignaturesPerQuatrimestre(String q) { //trivial cerca de Assignatures x Quatrimestre
		ArrayList<Assignatura> a = new ArrayList<Assignatura>();
		for (int i=0; i<m_assignatures.size();++i) {
			if (m_assignatures.get(i).getQuatri() == q) a.add(m_assignatures.get(i));
		}
		return a;
	}*/


	//SETTERS
	public void setAssignatures(ArrayList<Assignatura> assignatures) {
		m_assignatures = assignatures;
	}
	
	public void afegirAsig(Assignatura a) { //afegir una asig al pla d'estudis
		m_assignatures.add(a);
	}

	public void modificarNomAssignatura(int a_modificada, String nom) { //modificar nom d'una asig
m_assignatures.get(a_modificada).setNom(nom);

	}
	
	public void modificarAcronimAssignatura(int a_modificada, String acronim) { //modificar acronim d'una asig
		//m_assignatures.elementAt(a_modificada).setAcronim(acronim);
		m_assignatures.get(a_modificada).setAcronim(acronim);
	}
	
	public void modificarQuatrimestreAssignatura(int a_modificada, String quatri) { //modificar quatri d'una asig
		m_assignatures.get(a_modificada).setQuatri(quatri);
	}
}

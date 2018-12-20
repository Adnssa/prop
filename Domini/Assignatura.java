package Domini;

import java.util.List;
import java.util.ArrayList;

public class Assignatura {
	private String m_nom;
	private String m_acronim;
	private String m_quatri;
        private ArrayList<String> correquisits;

	public Assignatura(String nom, String acronim, String quatri, List<String> cor) {
		m_nom = nom;
		m_acronim = acronim;
		m_quatri = quatri;
                correquisits = new ArrayList<String>(cor);
		return;
	}

	//GETTERS
	public String getNom() {
		return m_nom;
	}

	public String getAcronim() {
		return m_acronim;
	}

	public String getQuatri(){
		return m_quatri;
	}
	
        public ArrayList<String> getCor(){
		return correquisits;
	}

	//SETTERS
	public void setNom(String nom) {
		m_nom = nom;
	}

	public void setAcronim(String acronim) {
		m_acronim = acronim;
	}

	public void setQuatri(String quatri){
		m_quatri = quatri;
	}

	//TO-DO: Llista de GRUPS, CLASSES i HORARIS?

}

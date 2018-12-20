package Domini;

public class Asgn {
	private Aula m_aula;
	private Classe m_classe;

	public Asgn(Aula aula, Classe classe) {
		// Init Arguments
		// Treat Data
		m_aula = aula;
		m_classe = classe;
		return;
	}

	//GETTERS
	public Aula getAula() {
		return m_aula;
	}

	public Classe getClasse() {
		return m_classe;
	}

	//SETTERS
	public void setAula(Aula aula) {
		m_aula = aula;
	}

	public void setClasse(Classe classe) {
		m_classe = classe;
	}

}

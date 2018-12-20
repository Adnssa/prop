package Domini;

public class Time {
	private int h;
	private int d;
        private Boolean flag;

	public Time(int dia, int hora) {
		// Init Arguments
		// Treat Data
                d = dia;
                h = hora;
                flag = false;
		return;
	}

	//GETTERS
	public int getDia() {
		return d;
	}

	public int getHora() {
		return h;
	}
	
        public Boolean getFlag() {
		return flag;
	}

	//SETTERS
	public void setDia(int dia) {
		d = dia;
	}

	public void setHora(int hora) {
		h = hora;
        }

	public void setFlag(Boolean fl) {
		flag = fl;
        }
}

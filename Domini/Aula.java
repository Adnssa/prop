package Domini;

import java.util.List;
import java.util.ArrayList;

//import  java.util.string;

public class Aula {
    private String m_edifici;
    private String m_codi;
    private ArrayList<ArrayList<Boolean>> horari;
    private int capacitat;
    private String tipus;

    public Aula(String edifici, String codi, int c, String t) {
        m_edifici = edifici;
        m_codi = codi;
        capacitat = c;
        tipus = t;

        // Init Horari
        horari = new ArrayList<ArrayList<Boolean>>();
        for (int i = 0;i < 7;i++) {
            ArrayList<Boolean> dia = new ArrayList<Boolean>();
            for (int j = 0;j < 24;j++) {
                dia.add(false);
            }
            horari.add(dia);
        }
    }

    //GETTERS
    public String getEdifici() {
        return m_edifici;
    }

    public String getCodi() {
        return m_codi;
    }

    public String getId() {
        return m_edifici+m_codi;
    }

    public String getTipus() {
        return tipus;
    }

    public Boolean getIsEmpty(Time t, int durada) {
        int d = t.getDia();
        int h = t.getHora();
        Boolean result = false;

        for (int i = 0;i < durada;i++) {
            result = (result || horari.get(d).get(h+i));
        }
        return result; 

    }
    public int getCapacitat() {
        return capacitat;
    }

    //SETTERS
    public void setEdifici(String edifici) {
        m_edifici = edifici;
    }

    public void setCodi(String codi) {
        m_codi = codi;
    }

    public void setTipus(String t) {
        tipus = t;
    }

    public void setUse(Time t, int durada) {
        int d = t.getDia();
        int h = t.getHora();
        Boolean tr = true;
        for (int i = 0;i < durada;i++) {
            horari.get(d).add(h+i, tr);
        }
    }

    public void unsetUse(Time t, int durada) {
        int d = t.getDia();
        int h = t.getHora();
        Boolean tr = false;
        for (int i = 0;i < durada;i++) {
            horari.get(d).add(h+i, tr);
        }
    }

    public void setCapacitat(int c) {
        capacitat = c;
    }
}

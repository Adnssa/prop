package Domini;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

public class Classe {
    private String m_codi;
    private String m_tipus;
    private int m_durada;
    private int m_grup;
    private int capacitat;
    private Boolean isUsed;
    private ArrayList<ArrayList<HashMap<String, Integer>>> horari = new ArrayList<ArrayList<HashMap<String, Integer>>>();
    private int startH;
    private int endH;
    private List<Aula> aularr;

    public Classe(String codi, String tipus, int durada, int grup, int c, int sh, int eh, List<Aula> aulr) {
        // Init Arguments
        m_codi = codi;
        m_tipus = tipus;
        m_durada = durada;
        m_grup = grup;
        capacitat = c;
        startH = sh;
        endH = eh;
        aularr = aulr;
        initHorari();
        return;
    }

    private void initHorari() {
        horari = new ArrayList<ArrayList<HashMap<String, Integer>>>(); 
        for (int i = 0;i < 7 ;i++) {
            ArrayList<HashMap<String,Integer>> dia = new ArrayList<HashMap<String,Integer>>(); 
            for (int j = 0;j < 24;j++) {
                HashMap<String, Integer> alist = new HashMap<String, Integer>();
                for (int k = 0;k < aularr.size();k++) {
                    // TODO: TEORIA O LAB
                    Aula a = aularr.get(k);
                    int rest = 0;
                    if (j < startH || j > endH) {
                        rest++;
                    } 
                    
                    if (capacitat > a.getCapacitat()) {
                        rest++;
                    }

                    if (!m_tipus.equals(a.getTipus())) {
                        rest++;
                    }

                    alist.put(a.getId(), rest);
                }
                dia.add(alist);
            }
            horari.add(dia);
        }
    }

    private Boolean isOk(int d, int h, String aula) {
        if ((h+m_durada) >= 24) {
            return false;
        }

        for (int k = 0;k < m_durada;k++) {
            int r = horari.get(d).get(h+k).get(aula);
            if (r != 0) {
                return false;
            }
        }
        return true;
    }

    public Time getFirstH(String aula) {
        Time t = new Time(0,0);
        for (int i = 0;i < horari.size() ;i++) {
            ArrayList<HashMap<String, Integer>> dia = horari.get(i);
            for (int j = 0;j < dia.size();j++) {
                Boolean r = isOk(i, j, aula);
                if (r) {
                    t.setDia(i);
                    t.setHora(j);
                    return t;
                }

            }
        }
        t.setDia(0);
        t.setHora(0);
        t.setFlag(true);
        return t;
    }
    
    public Time getNextH(Time t) {
        int k;
        Boolean first = true;
        for (int i = t.getDia();i < horari.size() ;i++) {
            ArrayList<HashMap<String, Integer>> dia = horari.get(i);
            if (first) {
                k = t.getHora()+1;
                first = false;
            } else {
                k = 0;
            }
            for (int j = k;j < dia.size();j++) {
                for (int l = 0;l < aularr.size();l++) {
                    Aula a = aularr.get(l);
                    Boolean r = isOk(i, j, a.getId());
                    if (r) {
                        t.setDia(i);
                        t.setHora(j);
                        return t;
                    }
                }

            }
        }
        t.setDia(0);
        t.setHora(0);
        t.setFlag(true);
        return t;
    }

    //GETTERS
    public String getCodi() {
        return m_codi;
    }

    public String getTipus() {
        return m_tipus;
    }

    public int getDurada() {
        return m_durada;
    }

    public int getGrup() {
        return m_grup;
    }

    public int getCapacitat() {
        return capacitat;
    }

    public Boolean getIsUsed() {
        return isUsed;
    }

    public Boolean hasBeenUsed(Time t) {
        return false;
    }

    //SETTERS
    public void setCodi(String codi) {
        m_codi = codi;
    }

    public void setTipus(String tipus) {
        m_tipus = tipus;
    }

    public void setDurada(int durada) {
        m_durada = durada;
    }

    public void setGrup(int grup) {
        m_grup = grup;
    }

    public void setIsUsed(Boolean isU) {
        isUsed = isU;
    }

    public void setCapacitat(int c) {
        capacitat = c;
    }

    public void setRest(Time t, int dur) {
        int d = t.getDia();
        int h = t.getHora();

        for (int i = 0;i < dur;i++) {
            for (int j = 0;j < aularr.size();j++) {
                Aula a = aularr.get(j);
                int aux = horari.get(d).get(h+i).get(a.getId());
                aux++;
                horari.get(d).get(h+i).put(a.getId(), aux);
            }
        }
    }
    
    public void setRestA(Time t, int dur, String aula) {
        int d = t.getDia();
        int h = t.getHora();

        for (int i = 0;i < dur;i++) {
            int aux = horari.get(d).get(h+i).get(aula);
            aux++;
            horari.get(d).get(h+i).put(aula, aux);
        }
    }

    public void unsetRest(Time t, int dur) {
        int d = t.getDia();
        int h = t.getHora();

        for (int i = 0;i < dur;i++) {
            for (int j = 0;j < aularr.size();j++) {
                Aula a = aularr.get(j);
                int aux = horari.get(d).get(h+i).get(a.getId());
                aux--;
                horari.get(d).get(h+i).put(a.getId(), aux);
            }
        }
    }

}

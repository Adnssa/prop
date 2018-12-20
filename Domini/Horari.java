package Domini;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import java.io.PrintStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.bethecoder.ascii_table.impl.CollectionASCIITableAware;
import com.bethecoder.ascii_table.impl.JDBCASCIITableAware;
import com.bethecoder.ascii_table.spec.IASCIITableAware;
import com.bethecoder.ascii_table.ASCIITable;
import com.bethecoder.ascii_table.ASCIITableHeader;

import java.util.Vector; 
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Stack;
import java.util.Iterator;

public class Horari {

    // Horari (Days) -> (Hours) -> (Classes)
    private ArrayList<ArrayList<ArrayList<Asgn>>> m_horari = new ArrayList<ArrayList<ArrayList<Asgn>>>();
    
    private ArrayList<ArrayList<HashMap>> m_h = new ArrayList<ArrayList<HashMap>>();
    private Map<String, List<String>> restrictions;

    private List<Classe> clasarr;
    private List<Assignatura> assigarr;
    private List<Aula> aularr;
    private List<Restriction> resarr;
    private Map<String, Integer> cls;
    private Map<String, Integer> aul;

    public Horari(
            List<Aula> aul_a, 
            List<Assignatura> assig_a, 
            List<Classe> class_a,
            List<Restriction> res_a
            ) {
        System.out.println("hola");
        // Init Arguments

        aularr = aul_a;
        assigarr = assig_a;
        clasarr = class_a;
        resarr = res_a;

        genRest();
        printRestrictions();
        //genHorari();
        //ArrayList<ArrayList<HashMap>> hor = new ArrayList<ArrayList<HashMap>>();
        List<Integer> classes = getClassesList();
        preAules();
        //prepareToG();
        prepareForGeneration();
        Boolean result = genHorariR(classes);
        if (result) {
            System.out.println("SATISFIABLE");
        } else {
            System.out.println("UNSATISFIABLE");
        }
        // print();

        return;
    }

    public Horari(){
        //Only used for listing Schedules
        return;
    }

    public void preAules() {
        aul = new HashMap();
        for (int i = 0;i < aularr.size(); i++) {
            Aula a = aularr.get(i);
            aul.put(a.getId(), i);
        }
    }

    private List<Integer> getClassesList() {
        List<Integer> ret = new ArrayList<Integer>();
        cls = new HashMap();
        for (int i = 0;i < clasarr.size(); i++) {
            Classe c = clasarr.get(i);
            cls.put(c.getCodi(), i);
            ret.add(i);
        }

        return ret;
    }

    public String[][] print2() {
        String[][] data = new String[12][5];
        int n = 0;
        for (int i = 0;i < 12;i++) {
            String[] hora = new String[5];
            for (int j = 0;j < 5;j++) {
                String s = "";
                if (j == 0) {
                    s = Integer.toString(i);
                } else {
                    int k = j-1;
                    HashMap h = m_h.get(k).get(i);
                    if (h.size() == 0) {
                        s = "--";
                    } else {
                        Iterator it = h.entrySet().iterator();
                        while (it.hasNext()) {
                            Map.Entry pair = (Map.Entry)it.next();
                            s = s+" ["+pair.getKey()+" "+pair.getValue()+"]";
                            it.remove();
                        }
                    }
                }
                hora[j] = s;
            }
            data[i] = hora;
        }
        return data;
    }
    public void print() {
        System.out.println("START HORARI PRINT");
        System.out.println("#################");

        String [] header = { 
            "Hora", 
            "Dilluns", 
            "Dimarts", 
            "Dimecres",
            "Dijous", 
            "Divendres",
            "Dissabte",
            "Diumenge"
        };
        
        ASCIITableHeader[] headerObjs = {
            new ASCIITableHeader("Hora", ASCIITable.ALIGN_RIGHT),
            new ASCIITableHeader("Dilluns", ASCIITable.ALIGN_CENTER),
            new ASCIITableHeader("Dimarts", ASCIITable.ALIGN_CENTER),
            new ASCIITableHeader("Dimecres", ASCIITable.ALIGN_CENTER),
            new ASCIITableHeader("Dijous", ASCIITable.ALIGN_CENTER),
            new ASCIITableHeader("Divendres", ASCIITable.ALIGN_CENTER),
            new ASCIITableHeader("Dissabte", ASCIITable.ALIGN_CENTER),
            new ASCIITableHeader("Diumenge", ASCIITable.ALIGN_CENTER),
        };
        String[][] data = new String[24][8];
        int n = 0;
        for (int i = 0;i < 24;i++) {
            String[] hora = new String[8];
            for (int j = 0;j < 8;j++) {
                String s = "";
                if (j == 0) {
                    s = Integer.toString(i);
                } else {
                    int k = j-1;
                    HashMap h = m_h.get(k).get(i);
                    if (h.size() == 0) {
                        s = "--";
                    } else {
                        Iterator it = h.entrySet().iterator();
                        while (it.hasNext()) {
                            Map.Entry pair = (Map.Entry)it.next();
                            s = s+" ["+pair.getKey()+" "+pair.getValue()+"]";
                            it.remove();
                        }
                    }
                }
                hora[j] = s;
            }
            data[i] = hora;
        }
        ASCIITable.getInstance().printTable(headerObjs, data);

        System.out.println("Desitjes guardar aquest horari? Prem 1 si es aixi, prem 2 en cas contrari");
        int op2;
        Scanner S = new Scanner(System.in);
        Boolean horariguardat = false;
		op2 = S.nextInt();
		while (op2 != 2 && !horariguardat) {
			if (op2 == 1) {
				String file;
				file = S.next();
                saveHorari(file, headerObjs, data);
                horariguardat = true;
			}
		}
    }

    private void saveHorari(String fileName, ASCIITableHeader[] headerObjs, String[][] data){
        try (PrintStream out = new PrintStream(new FileOutputStream("./Horaris/" + fileName + ".txt")))
        {
            out.println(ASCIITable.getInstance().getTable(headerObjs, data));    
            out.close();
        }
        catch(FileNotFoundException error)
        {
            System.out.println(error.getMessage());
        }
    }

    public void listFiles(){
        File[] files = new File("./Horaris").listFiles();
        for (File f : files){
            System.out.println(f.getName());
        }
    }

    public void loadFiles(String file){
        try
        {
            String content = new Scanner(new File("./Horaris/" + file)).useDelimiter("\\Z").next();
            System.out.println(content);
        }
        catch(FileNotFoundException error)
        {
            System.out.println(error.getMessage());
        }
    }

    public Boolean areIn(List<String> a, ArrayList<String> b) {
        if (a == null || b == null) {
            return false;
        }
        for (int i = 0;i < a.size();i++) {
            String as = a.get(i);
            for (int j = 0;j < b.size() ;j++) {
                String bs = b.get(i);
                if (as.equals(bs)) {
                    return true;
                }
            }
        }
        return false;
    }

    private ArrayList<ArrayList<Asgn>> getHores(Classe c, int d, int h) {
        ArrayList<ArrayList<Asgn>> hores = new ArrayList<ArrayList<Asgn>>(); 
        int durada = c.getDurada();
        for (int i = 0;i < durada ;i++) {
            hores.add(m_horari.get(d).get(h+i));
        }
        return hores;
    }

    private Boolean emptyHours(ArrayList<ArrayList<Asgn>> hores) {
        for (int i = 0;i < hores.size();i++) {
            if (hores.get(i).size() != 0) {
                return false;
            }
        }
        return true;
    }

    private ArrayList<String> getClasses(ArrayList<ArrayList<Asgn>> hores) {
        ArrayList classes = new ArrayList<String>();
        for (int i = 0;i < hores.size();i++) {
            List<Asgn> hora = hores.get(i);
            for (int j = 0;j < hora.size();j++) {
                Asgn asign = hora.get(j);
                classes.add(asign.getClasse().getCodi());
            }
        }
        return classes;
    }

    private void genClassArray() {
        // for (int i = 0;i < clasarr.size() ;i++) {
        //     System.out.println(c.getTipus());
        // }
    }

    private void prepareForGeneration() {
        genClassArray();
        // Instantiate Horari Matrix
        for (int i = 0;i < 7 ;i++) {
            ArrayList<HashMap> dia = new ArrayList<HashMap>();
            for (int j = 0;j < 24 ;j++) {
                HashMap hora = new HashMap();
                dia.add(hora);
                // System.out.println(i);
            }
            m_h.add(dia);
        }
    }

    private Time getNextHour(Time t) {
        int d = t.getDia();
        int h = t.getHora();
        if (h <= 22) {
            t.setHora(h+1);
        } else {
            if (d <= 5) {
                t.setHora(0);
                t.setDia(d+1);
            } else {
                t.setHora(0);
                t.setDia(d+1);
                t.setFlag(true);
            }
        }
        return t;
    }

    private Aula getUsableAula(Time t, Classe c) {
        // TODO: Check The Aula for being used and restrictions
        for (int i = 0;i < aularr.size() ;i++) {
            Aula aula = aularr.get(i);
            if (!aula.getIsEmpty(t, c.getDurada()) && aula.getCapacitat() >= c.getCapacitat()) {
                return aularr.get(i);
            }
        }

        return null;
    }

    private void assignClass(Time t, Aula a, Classe c) {
        int d = t.getDia();
        int h = t.getHora();
        //Asgn as = new Asgn(a, c);

        for (int i = 0;i < c.getDurada();i++) {
            m_h.get(d).get(h+i).put(a.getId(), c.getCodi());
        }
        a.setUse(t, c.getDurada());

        for (int i = 0;i < clasarr.size() ;i++) {
            Classe caux = clasarr.get(i);
            if (c.getCodi() != caux.getCodi()) {
                caux.setRestA(t, c.getDurada(), a.getId());
            }
        }
        
        // Set the restrictions
        setRes(t, c);
    }

    private void setRes(Time t, Classe c) {
        List<String> r = restrictions.get(c.getCodi());
        for (int i = 0;i < r.size();i++) {
            String cl = r.get(i);
            Classe c2 = clasarr.get(cls.get(cl));
            c2.setRest(t, c.getDurada());
        }
        
    }

    private int getNextClass(Time t) {
        int d = t.getDia();
        int h = t.getHora();
        for (int i = 0;i < clasarr.size() ;i++) {
            Classe c = clasarr.get(i);
            if (!c.getIsUsed() && c.hasBeenUsed(t)) {
                return i;
            }
        }
        return -1;
    }

    private void unsetRes(Time t, Classe c) {
        List<String> r = restrictions.get(c.getCodi());
        for (int i = 0;i < r.size();i++) {
            String cl = r.get(i);
            Classe c2 = clasarr.get(cls.get(cl));
            c2.unsetRest(t, c.getDurada());
        }
        
    }

    private void unasignClass(Time t, Aula a, Classe c) {
        int d = t.getDia();
        int h = t.getHora();

        for (int i = 0;i < c.getDurada();i++) {
            m_h.get(d).get(h+i).remove(a.getId());
        }

        a.unsetUse(t, c.getDurada());

        unsetRes(t, c);
    }

    public List<Integer> copyList(List<Integer> l) {
        List<Integer> l2 = new ArrayList<Integer>();
        for (int i = 0;i < l.size();i++) {
            l2.add(l.get(i));
        }
        return l2;
    }

    public Boolean genHorariR(List<Integer> classes) {
        // REAL
        // Base Case
        if (classes.size() == 0) {
            return true;
        }
        
        // Recursion 
        for (int i = 0;i < classes.size() ;i++) {
            for (int j = 0;j < aularr.size() ;j++) {
                Classe c = clasarr.get(classes.get(i));
                Aula aula = aularr.get(j);
                Time tt = c.getFirstH(aula.getId()); // Get the first free hour 
                if (tt.getFlag()) {
                    // Did it fail for Aula?
                    continue;
                }
                
                Boolean run = true;
                List<Integer> aux = copyList(classes);
                aux.remove(i);
                while (run) {
                    assignClass(tt, aula, c);

                    Boolean result = genHorariR(aux);
                    if (result) {
                        return true;
                    }
                    unasignClass(tt, aula, c);

                    tt = c.getNextH(tt);
                    if (tt.getFlag()) {
                        run = false;
                        //return false;
                    }
                }
            }
        }
        return false;
    }

    public void printRestrictions() {
        for(String key: restrictions.keySet()) {
            List<String> l = restrictions.get(key);
            for (int i = 0;i < l.size() ;i++) {
                System.out.println(key + " - " + l.get(i));
            }
        }
        System.out.println();
    }

    private void setBoth(String a, String b) {
        List<String> aux = restrictions.get(a);
        if (aux == null) {
            aux = new ArrayList<String>();
        }
        if (!aux.contains(b)) {
            aux.add(b);
            restrictions.put(a, aux);
        } 

        aux = restrictions.get(b);
        if (aux == null) {
            aux = new ArrayList<String>();
        }
        if (!aux.contains(a)) {
            aux.add(a);
            restrictions.put(b, aux);
        } 

        return;
    }

    private void treatCaseOne(Restriction r) {
        String a = r.getVin().get(0);
        String b = r.getVout().get(0);
        setBoth(a,b);
        return;
    }

    private void treatCaseTwo(Restriction r) {
        List<String> a = r.getVin();
        List<String> b = r.getVout();
        for (int i = 0;i < a.size() ;i++) {
            for (int j = 0;j < b.size() ;j++) {
                setBoth(a.get(i), b.get(j));
            }
        } 
        return;
    }

    public void treatRestriction(Restriction r) {
        switch (Integer.parseInt(r.getType())) {
            case 1:
                treatCaseOne(r);
                break;
            case 2:
                treatCaseTwo(r);
                break;
            default:
                break;
        }
        return;
    }


    private ArrayList<String> getClassesfromAssig(String asig) {
        ArrayList<String> resultat = new ArrayList<String>();
        for (int i = 0;i < clasarr.size() ;i++) {
            Classe c = clasarr.get(i);
            String code = c.getCodi();
            int ind = code.indexOf("-"); 
            String as = code.substring(0, ind);
            if (as.equals(asig)) {
                resultat.add(c.getCodi());
            }
        }
        return resultat;
    }

    private HashMap<String, ArrayList<Assignatura>> getQuatrisArray() {
        HashMap<String, ArrayList<Assignatura>> resultat = new HashMap<String, ArrayList<Assignatura>>();
        for (int i = 0;i < assigarr.size() ;i++) {
            Assignatura a = assigarr.get(i); // Get Assignatura
            ArrayList<Assignatura> aux = resultat.get(a.getQuatri());
            if (aux == null) {
                aux = new ArrayList<Assignatura>();
            }
            aux.add(a);
            resultat.put(a.getQuatri(), aux);
        }
        return resultat;
    }

    private void treatQuatri() {
        // Per cada Assig els seus Corequisists no poden
        // tenir classe al mateix moment
        HashMap<String, ArrayList<Assignatura>> quatris = getQuatrisArray();
        Iterator it = quatris.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            ArrayList<Assignatura> quatri = quatris.get(pair.getKey());
            for (int j = 0;j < quatri.size() ;j++) {
                // JSONObject asc = (JSONObject) cor.get(j);
                Assignatura a = quatri.get(j);
                for (int k = 0;k < quatri.size() ;k++) {
                    Assignatura as = quatri.get(k);
                    if (j != k) {
                        ArrayList<String> ascClasses = getClassesfromAssig(a.getAcronim());
                        ArrayList<String> vin = getClassesfromAssig(as.getAcronim());
                        Restriction res = new Restriction("2", vin, ascClasses, "false");
                        treatRestriction(res);
                        
                    }
                }
                break;
            }

            it.remove(); // avoids a ConcurrentModificationException
        }

    }
    private void treatCorrequisits() {
        // Per cada Assig els seus Corequisists no poden
        // tenir classe al mateix moment
        for (int i = 0;i < assigarr.size() ;i++) {
            Assignatura a = assigarr.get(i); // Get Assignatura
            ArrayList<String> cor = a.getCor(); // Get Correquisits
            for (int j = 0;j < cor.size() ;j++) {
                // JSONObject asc = (JSONObject) cor.get(j);
                String asc = cor.get(j);
                ArrayList<String> ascClasses = getClassesfromAssig(asc.toString());
                ArrayList<String> vin = getClassesfromAssig(a.getAcronim());
                Restriction res = new Restriction("2", vin, ascClasses, "false");
                treatRestriction(res);
            }
            
        }
    }

    private void treatAssigTl(String a) {
        ArrayList<String> teo = new ArrayList<String>();
        ArrayList<String> lab = new ArrayList<String>();

        ArrayList<String> classes = getClassesfromAssig(a);
        for (int i = 0;i < classes.size() ;i++) {
            String classe = classes.get(i);
            int sindex = classe.indexOf("-");
            String type = classe.substring(sindex+1, sindex+2);
            if (type.equals("T")) {
                teo.add(classe);
            } else {
                lab.add(classe);
            }
        }

        Restriction res = new Restriction("2", teo, lab, "false");
        treatRestriction(res);
    }
    
    private void treatTl() {
        for (int i = 0;i < assigarr.size() ;i++) {
            treatAssigTl(assigarr.get(i).getAcronim());
        }

    }

    private void treatAssig() {
        // Treat Corequisits
        treatCorrequisits();
        // Treat Quatri
        treatQuatri();
        // Treat Teo vs Lab
        treatTl();
    }

    public void genRest() {
        restrictions = new HashMap();

        // Treat Canonical Restriction
        treatAssig();

        // Treat non canonical Restriction
        for (int i = 0; i < resarr.size(); i++ ) {
            Restriction r = resarr.get(i);
            treatRestriction(r);
        } 
        return;
    }

}

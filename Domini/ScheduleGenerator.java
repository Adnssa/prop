package Domini;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.ArrayList;

import java.io.File;
import java.io.FileReader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;



public class ScheduleGenerator {

    static List<Classe> clasarr;
    static List<Assignatura> assigarr;
    static List<Aula> aularr;
    static List<Restriction> resarr;
    private Horari horari;


    public ScheduleGenerator(String file) {
        //System.out.println("START SCHEDULE GENERATOR");

        // Start Json Parse
        JSONParser parser = new JSONParser();
        try {
            // Get File
            //Object obj = parser.parse(new FileReader(
            //            "../../var/pe/pla_estudis_1.json"));
            Object obj = parser.parse(new FileReader(file));

            // Parse Json Object
            JSONObject jsonObject = (JSONObject) obj;

            initValues(jsonObject);

        } catch (Exception e) {
            e.printStackTrace();
        }

        horari = new Horari(aularr, assigarr, clasarr, resarr);
        return;
    }

    private static void initValues(JSONObject jsonObject) {
        // Parse Aules
        JSONArray aules = (JSONArray) jsonObject.get("aules");
        initAules(aules);

        // Parse Classes
        JSONArray classes = (JSONArray) jsonObject.get("classes");
        initClasses(classes);

        // Parse Assignatures
        JSONArray assignatures = (JSONArray) jsonObject.get("assignatures");
        initAssignatures(assignatures);

        // Parse Assignatures
        JSONArray restriccions = (JSONArray) jsonObject.get("restrictions");
        initRestrictions(restriccions);
    }

    private static void initAules(JSONArray aules) {
        aularr = new ArrayList<Aula>();
        for (int i = 0;i < aules.size() ; i++ ) {
            JSONObject au = (JSONObject) aules.get(i);

            String edifici = au.get("edifici").toString();
            String codi = au.get("codi").toString();
            int capacitat = Integer.parseInt(au.get("capacitat").toString());
            String tipus = au.get("tipus").toString();

            Aula aul = new Aula(edifici, codi, capacitat, tipus);
            aularr.add(aul);
        }
        return;
    }

    private static void initClasses(JSONArray classes) {
        clasarr = new ArrayList<Classe>();
        for (int i = 0;i < classes.size() ; i++ ) {
            JSONObject cl = (JSONObject) classes.get(i);
            String code = cl.get("code").toString();
            String type = cl.get("type").toString();
            int length = Integer.parseInt(cl.get("length").toString());
            int group = Integer.parseInt(cl.get("group").toString());
            int capacitat = Integer.parseInt(cl.get("capacitat").toString());
            int startH = Integer.parseInt(cl.get("startH").toString());
            int endH = Integer.parseInt(cl.get("endH").toString());

            Classe cla = new Classe(code, type, length, group, capacitat, startH, endH, aularr);
            clasarr.add(cla);
        }
        return;
    }

    private static void initAssignatures(JSONArray assignatures) {
        assigarr = new ArrayList<Assignatura>();
        for (int i = 0;i < assignatures.size() ; i++ ) {
            JSONObject as = (JSONObject) assignatures.get(i);

            String nom = as.get("name").toString();
            String acronim = as.get("acronim").toString();
            String quatri = as.get("quatri").toString();
            
            JSONArray cor = (JSONArray) as.get("correquisits");
            List<String> cora = jsonToArray(cor); 

            Assignatura asig = new Assignatura(nom, acronim, quatri, cora);
            assigarr.add(asig);
        }
        return;
    }

    private static void initRestrictions(JSONArray restriccions) {
        resarr = new ArrayList<Restriction>();
        for (int i = 0;i < restriccions.size() ; i++ ) {
            JSONObject rs = (JSONObject) restriccions.get(i);

            String type = rs.get("type").toString();

            JSONArray vina = (JSONArray) rs.get("varin");
            JSONArray vouta = (JSONArray) rs.get("varout");

            List<String> vin = jsonToArray(vina); 
            List<String> vout = jsonToArray(vouta); 

            String editable = rs.get("editable").toString();

            Restriction res = new Restriction(type, vin, vout, editable);
            resarr.add(res);
        }
        return;
    }

    public static List<String> jsonToArray(JSONArray jsonArray) {
        List<String> list = new ArrayList<String>();
        for (int i=0; i<jsonArray.size(); i++) {
            list.add( jsonArray.get(i).toString() );
        }
        return list;
    }

    public void print(){
        horari.print();
    }
    public String[][] print2() {
        return horari.print2();
    }
}

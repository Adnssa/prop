package Domini;

import java.util.List;

public class Restriction {
    private String type;
    private List<String> vin;
    private List<String> vout;
    private String editable;

    public Restriction(
        String t, 
        List<String> vi, 
        List<String> vo,
        String e
    ) {
        // Init Arguments
        type = t;
        vin = vi;
        vout = vo;
        editable = e;
        return;
    }

    public String getType() {
        return type;
    }
    public List<String> getVin() {
        return vin;
    }
    public List<String> getVout() {
        return vout;
    }
    public String getEditable() {
        return editable;
    }

}
package Presentacio;

import Domini.*;
import java.io.File;

public class CtrlPresentacio{

    private CtrlDomini m_domini;
    
    private JFrameBenvinguda benvinguda;
    private JFrameModificarHorari ModificarHorari;
    private JFrameCarregaHorari carregaHorari;
    private JFrameHorari Horari;
    private JFrameNovaAssignatura NovaAssignatura;
    private JFrameNovaAula NovaAula;
    private JFrameBorrarAssignatura BorrarAssignatura;
    private JFrameBorrarAula BorrarAula;
    private ScheduleGenerator SG;
    public String[][] prova = new String[12][5];
    public File aux;
    public String filePath;
    

    public void CtrlPresentacio() {
        m_domini = new CtrlDomini();
    }

    public void init(){
        benvinguda = new JFrameBenvinguda(this);
        benvinguda.run();
    }
    
    public void ModificarHorari(){
        ModificarHorari = new JFrameModificarHorari(this);
        ModificarHorari.run();
    }
    
    public void carregaHorari(){
        carregaHorari = new JFrameCarregaHorari(this);
        carregaHorari.run();
    }
    
    public void Horari(){
        Horari = new JFrameHorari(this);
        Horari.run();
    }
    
    public void NovaAssignatura(){
        NovaAssignatura = new JFrameNovaAssignatura(this);
        NovaAssignatura.run();
    }
    
    public void NovaAula(){
        NovaAula = new JFrameNovaAula(this);
        NovaAula.run();
    }
    
    public void BorrarAssignatura(){
        BorrarAssignatura = new JFrameBorrarAssignatura(this);
        BorrarAssignatura.run();
    }
    
    public void BorrarAula(){
        BorrarAula = new JFrameBorrarAula(this);
        BorrarAula.run();
    }
    
    public void CarregaFile() {
        SG = new ScheduleGenerator(filePath);
    }
    
    public String[][] printHorari() {
        return SG.print2();
    }
}
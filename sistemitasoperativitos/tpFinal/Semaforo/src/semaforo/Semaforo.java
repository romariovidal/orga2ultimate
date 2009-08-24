/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package semaforo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tomas
 */
public class Semaforo implements Serializable {

    private Boolean esP;
    private Integer nroVariableDelSemaforo = -15;
    private Character letra;
    private List<Integer> procesosAPuntoDeEntrar;
    private List<Integer> procesosEnColaWait;
    private static final long serialVersionUID = 667;

    /* Funciones de construcción de semáforos */
    public Semaforo(Boolean esP, Integer valor, List<Integer> colaWait, List<Integer> cola, Character tipoProceso) {
        this.esP = esP;
        this.nroVariableDelSemaforo = valor;
        this.letra = tipoProceso;
        this.procesosAPuntoDeEntrar = colaWait;
        this.procesosEnColaWait = cola;
    }

    public static Semaforo crearP(Integer nroVariableDelSemaforo, Character tipoProceso){
        return new Semaforo(true, nroVariableDelSemaforo, new ArrayList<Integer>(), new ArrayList<Integer>(), tipoProceso);
    }

    public static Semaforo crearV(Integer nroVariableDelSemaforo, Character tipoProceso){
        return new Semaforo(false, nroVariableDelSemaforo, new ArrayList<Integer>(), new ArrayList<Integer>(), tipoProceso);
    }


    /* Funciones con funcionalidad */
    public void llegaProcesoNuevo(Integer procId){
        this.procesosAPuntoDeEntrar.add(procId);
    }

    public void procesoEntraAlWait(Integer procId){
        this.procesosEnColaWait.add(procId);
    }

    public Integer retirarProcesoAPuntoDeEntrar(){
        Integer res = null;
        if(!procesosAPuntoDeEntrar.isEmpty()){
            res = procesosAPuntoDeEntrar.get(0);
            procesosAPuntoDeEntrar.remove(0);
        }

        return res;
    }

    public Integer retirarProcesoADeWAIT() {
        Integer res = null;
        if(!procesosEnColaWait.isEmpty()){
            res = procesosEnColaWait.get(0);
            procesosEnColaWait.remove(0);
        }

        return res;
    }

    /**
     *  @return devuelve un proceso de la cola de wait y lo retira
     */
    public Integer huboUnSignal() {
        throw new UnsupportedOperationException("Not yet implemented");
    }


    /* NUEVOS GETTERS LOCOS */
    public Boolean alguienEsperaParaEntrar(){
        return (this.procesosAPuntoDeEntrar.size()>0);
    }

    public Boolean alguienEnLaColaWAIT(){
        return (this.procesosEnColaWait.size()>0);
    }




    /* Viejas */

    public Boolean puedoPasar(){
//        if(this.valor>0){
//            return true;
//        }else{
//            return false;
//        }
        return false;
    }

    public void pasar(Integer numeroProceso){
        this.procesosEnColaWait.remove(numeroProceso);
        this.nroVariableDelSemaforo--;
    }

    //public void addProceso(Integer proceso){
    //    procesosEnColaWait.add(proceso);
    //}

/**
 *
 * @return devuelve el siguiente valor de la cola y NO lo remueve.
 */
    public Integer peek(){
        Integer res=null;
        if(!procesosEnColaWait.isEmpty()){
            res = procesosEnColaWait.get(0);
        }
        return res;
    }

/**
 *
 * @return devuelve el siguiente valor de la cola y lo remueve.
 */
    public Integer remove(){
        Integer res = null;
        if(!procesosEnColaWait.isEmpty()){
            res = procesosEnColaWait.get(0);
            procesosEnColaWait.remove(0);
        }

        return res;

    }

    public String mostrar(Boolean previo){
        String temp = "";

        if(previo){
            for(Integer k=0; k< this.procesosAPuntoDeEntrar.size(); k++){
                if(k>0)
                    temp+=", ";

                temp += this.letra +"" + this.procesosAPuntoDeEntrar.get(k);
            }
        } else {
            if(this.getEsP())
                temp += "P(X"+ this.getValor()+")";
            else
                temp += "V(X"+ this.getValor()+")";

            temp += " - ";

            for(Integer k=0; k< this.getCantProc(); k++){
                if(k>0)
                    temp+=", ";

                temp += this.letra +"" + this.getProceso(k);
            }
        }
        return temp;
    }

    void borrarTodosLosProcesos() {
        procesosEnColaWait = new ArrayList<Integer>();
    }

    /* Getters y Setters */
    public Boolean getEsP() {
        return esP;
    }

    public Integer getValor() {
        return nroVariableDelSemaforo;
    }
    
    public List<Integer> getProcesosEnCola() {
        return procesosEnColaWait;
    }

    public void setEsP(Boolean esP) {
        this.esP = esP;
    }

    public void setValor(Integer valor) {
        this.nroVariableDelSemaforo = valor;
    }
    
    public void setProcesosEnCola(List<Integer> procesosEnCola) {
        this.procesosEnColaWait = procesosEnCola;
    }

    public Integer getCantProc() {
        return this.procesosEnColaWait.size();
    }

    public Integer getProceso(Integer i){
        return this.procesosEnColaWait.get(i);
    }







}

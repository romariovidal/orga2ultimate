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
    private Integer valor;
    private List<Integer> procesosEnCola;

    public Semaforo(Boolean esP, Integer valor, List<Integer> cola) {
        this.esP = esP;
        this.valor = valor;
        this.procesosEnCola = cola;
    }

    public static Semaforo crearP(Integer valor){
        return new Semaforo(true, valor, new ArrayList<Integer>());
    }

    public static Semaforo crearV(Integer valor){
        return new Semaforo(false, valor, new ArrayList<Integer>());
    }

    public void addProceso(Integer proceso){
        procesosEnCola.add(proceso);
    }

/**
 *
 * @return devuelve el siguiente valor de la cola y NO lo remueve.
 */
    public Integer peek(){
        Integer res=null;
        if(!procesosEnCola.isEmpty()){
            res = procesosEnCola.get(0);
        }
        return res;
    }

/**
 *
 * @return devuelve el siguiente valor de la cola y lo remueve.
 */
    public Integer remove(){
        Integer res = null;
        if(!procesosEnCola.isEmpty()){
            res = procesosEnCola.get(0);
            procesosEnCola.remove(0);
        }

        return res;

    }

    public Boolean getEsP() {
        return esP;
    }

    public Integer getValor() {
        return valor;
    }
    
    public List<Integer> getProcesosEnCola() {
        return procesosEnCola;
    }

    public void setEsP(Boolean esP) {
        this.esP = esP;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
    }
    
    public void setProcesosEnCola(List<Integer> procesosEnCola) {
        this.procesosEnCola = procesosEnCola;
    }

    public Integer getCantProc() {
        return this.procesosEnCola.size();
    }

    public Integer getProceso(Integer i){
        return this.procesosEnCola.get(i);
    }

    public String mostrar(){
        String temp = "";

        if(this.getEsP())
            temp += "P(X"+ this.getValor()+")";
        else
            temp += "V(X"+ this.getValor()+")";

        temp += " - ";

        for(Integer k=0; k< this.getCantProc(); k++){
            if(k>0)
                temp+=", ";

            temp += (char) (65+this.valor) +"" + this.getProceso(k);
        }
        return temp;
    }
    

}

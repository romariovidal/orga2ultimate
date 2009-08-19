/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package semaforo;

import java.util.Queue;

/**
 *
 * @author tomas
 */
public class Semaforo {

    private Boolean esP;
    private Integer valor;
    private Queue<Integer> procesosEnCola;

    public Semaforo(Boolean esP, Integer valor, Queue<Integer> cola) {
        this.esP = esP;
        this.valor = valor;
        this.procesosEnCola = cola;
    }

    public static Semaforo crearP(Integer valor){
        return new Semaforo(true, valor, null);
    }

    public static Semaforo crearV(Integer valor){
        return new Semaforo(false, valor, null);
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
            res = procesosEnCola.peek();
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
            res = procesosEnCola.remove();
        }

        return res;

    }

    public Boolean getEsP() {
        return esP;
    }

    public Integer getValor() {
        return valor;
    }
    
    public Queue<Integer> getProcesosEnCola() {
        return procesosEnCola;
    }

    public void setEsP(Boolean esP) {
        this.esP = esP;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
    }
    
    public void setProcesosEnCola(Queue<Integer> procesosEnCola) {
        this.procesosEnCola = procesosEnCola;
    }

    public Integer getCantProc() {
        return this.procesosEnCola.size();
    }

    public Integer getProceso(Integer i){
        return 3;
    }
    

}

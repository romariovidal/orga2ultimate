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
public class Columna implements Serializable {

    private List<Integer>   colaInicial = new ArrayList<Integer>();
    private List<Semaforo>  semaforosSuperiores = new ArrayList<Semaforo>();
    private List<Integer>   procesosEnZonaCritica = new ArrayList<Integer>();
    private List<Integer>   colaInferior = new ArrayList<Integer>();
    private List<Semaforo>  semaforosInferiores = new ArrayList<Semaforo>();
    private Integer         procesoID = 1;
    private List<Integer>   procesosTerminados = new ArrayList<Integer>();
    private static final long serialVersionUID = 666;

    public Columna() {
    }

    public void avanzarColaArriba(){
        for (Integer i=0;i<semaforosSuperiores.size()-2;i++){
            Semaforo semaforo = semaforosSuperiores.get(i);
            if((semaforo.getProcesosEnCola().size() != 0) && ((semaforo.getEsP() && semaforo.getValor()>0) || !semaforo.getEsP())){
                Integer procesoEnMovimiento = semaforo.remove();
                semaforosSuperiores.get(i+1).addProceso(procesoEnMovimiento);
                
            }
        }
    }

    public void encolarEnSemaforoSuperior(Integer proceso){
        this.semaforosSuperiores.get(0).addProceso(proceso);

    }

    public void removePrimeroColaInicial(){
        colaInicial.remove(0);
    }

    public void removePrimeroColaInferior(){
        colaInferior.remove(0);
    }

    public void vaciarColumnas(){
        List<Integer> none = new ArrayList<Integer>();
        this.colaInferior.retainAll(none);
    }

    public void agregarNuevoProcesoAColaInicial(){
        colaInicial.add(procesoID);
        procesoID++;
    }

    public List<Integer> getProcesosEnZonaCritica() {
        return procesosEnZonaCritica;
    }

    public List<Integer> getProcesosTerminados() {
        return procesosTerminados;
    }

    void agregarSemaforoSuperior(Semaforo semaforo) {
        semaforosSuperiores.add(semaforo);
    }

    void agregarSemaforoInferior(Semaforo semaforo) {
        semaforosInferiores.add(semaforo);
    }

    public List<Integer> getColaInferior() {
        return colaInferior;
    }

    public List<Integer> getColaInicial() {
        return colaInicial;
    }

    public List<Semaforo> getSemaforosInferiores() {
        return semaforosInferiores;
    }

    public List<Semaforo> getSemaforosSuperiores() {
        return semaforosSuperiores;
    }

    public Integer cantSemaforosSup(){
        return this.semaforosSuperiores.size();
    }

    public Integer cantSemaforosInf(){
        return this.semaforosInferiores.size();
    }

    public String mostrarSemaforoSup(Integer i){
        return this.semaforosSuperiores.get(i).mostrar();
    }

    public String mostrarSemaforoInf(Integer i){
        return this.semaforosInferiores.get(i).mostrar();
    }

    void borrarTodosLosProcesos() {
        colaInicial = new ArrayList<Integer>();

        for (Semaforo s : semaforosSuperiores) { s.borrarTodosLosProcesos();}

        procesosEnZonaCritica = new ArrayList<Integer>();

        colaInferior = new ArrayList<Integer>();

        for (Semaforo s : semaforosInferiores) { s.borrarTodosLosProcesos();}

        procesosTerminados = new ArrayList<Integer>();
    }

}
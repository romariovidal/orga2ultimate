/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package semaforo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author tomas
 */
public class Instancia implements Serializable {

    private List<Columna> listaColumna; // lista de las columnas copadas que tienen casi todo.
    private Integer[] valoresSemaforos; //valor de las variables de los semaforos.
    private LinkedList<Character> resultado; // lista de los distintos tipos de procesos que van finalizando su corrida.
    private Integer cantTiposProcesos;
    private Integer cantSemaforos;
    private static final long serialVersionUID = 665;
    private Boolean zonaCriticaOcupada = false;

    /* Funciones de construcción */
    public Instancia(Integer cantidadTiposProcesos, Integer cantidadSemaforos) {
        this.cantTiposProcesos = cantidadTiposProcesos;
        this.cantSemaforos = cantidadSemaforos;
        listaColumna = new ArrayList<Columna>();
        for (int i = 0; i < cantidadTiposProcesos; i++) {
            listaColumna.add(new Columna(this, i));
        }

        this.valoresSemaforos = new Integer[cantidadSemaforos];
        for(Integer i=0; i< cantidadSemaforos; i++){
            this.valoresSemaforos[i] = 0;
        }
        this.resultado = new LinkedList<Character>();
    }

    public void agregarSemaforoSuperior(Semaforo semaforo, Integer numTipoProc){
        //System.out.println("Antes " + this.listaColumna.get(numTipoProc).getSemaforosSuperiores().size());
        this.listaColumna.get(numTipoProc).agregarSemaforoSuperior(semaforo);
        //System.out.println("Despues " + this.listaColumna.get(numTipoProc).getSemaforosSuperiores().size());
    }

    public void agregarSemaforoInferior(Semaforo semaforo, Integer numTipoProc){
        //System.out.println("Antes " + this.listaColumna.get(numTipoProc).getSemaforosInferiores().size());
        this.listaColumna.get(numTipoProc).agregarSemaforoInferior(semaforo);
        //System.out.println("Despues " + this.listaColumna.get(numTipoProc).getSemaforosInferiores().size());

    }

    public void borrarTodosLosProcesos(){
        if(!listaColumna.equals(null)){
            for (Columna columna : listaColumna) {
                columna.borrarTodosLosProcesos();
                columna.reiniciarProcesoID();
            }
        }

        this.resultado = new LinkedList<Character>();
        this.zonaCriticaOcupada = false;
    }

    /* Funciones de funcionalidad */
    public void  crearProceso(Integer tipoProceso, SemaforoView padre) {
        this.listaColumna.get(tipoProceso).llegaProcesoNuevo(padre);
    }

    public void nextStep(SemaforoView padre){
        Boolean yaMovi = false;
        for(Integer i=0; i<listaColumna.size() && !yaMovi; i++){
            yaMovi = this.listaColumna.get(i).siPuedeMuevalo(padre);
        }        
    }

    public void liberarZonaCritica(){
        for(Integer i=0; i<listaColumna.size(); i++){
            this.listaColumna.get(i).liberarZonaCritica();
        }

        this.zonaCriticaOcupada = false;
    }

    public Boolean zonaCriticaOcupada(){
        return this.zonaCriticaOcupada;
    }

    public void ocuparZonaCritica(){
        this.zonaCriticaOcupada = true;
    }

    /* Getteres */
    public List<Character> getResultado() {
        return resultado;
    }

    public Integer[] getValoresSemaforos() {
        return valoresSemaforos;
    }

    public Columna getColumna(Integer nroSem) {
        return this.listaColumna.get(nroSem);
    }

    public List<Columna> getListaColumna() {
        return listaColumna;
    }

    public Integer getCantSemaforos() {
        return cantSemaforos;
    }

    public Integer getCantTiposProcesos() {
        return cantTiposProcesos;
    }

    public Integer getValorSemaforo(Integer i) {
        return this.valoresSemaforos[i];
    }


    /* Setteres */
    public void setValoresSemaforos(Integer[] valoresSemaforos) {
        this.valoresSemaforos = valoresSemaforos;
    }

    public void setResultado(LinkedList<Character> resultado) {
        this.resultado = resultado;
    }

    public void  setValorSemaforo(Integer i, Integer valor) {
        this.valoresSemaforos[i] = valor;
    }

    public void  ocurreP(Integer i) {
        this.valoresSemaforos[i]--;
    }

    /**
     *
     * @return devuelve true sii debe enviarse un signal
     */
    public Boolean  ocurreV(Integer i) {
        this.valoresSemaforos[i]++;
        if (this.valoresSemaforos[i]<=0)
            return true;
        else
            return false;
    }

    public void signal(Integer signal, SemaforoView padre) {
        Boolean yaDesperteUnProceso = false;
        for(Integer i=0; i<listaColumna.size() && !yaDesperteUnProceso; i++){
            yaDesperteUnProceso = this.listaColumna.get(i).llegaSignal(signal, padre);
        }        
    }

    public void procesoTermino (Character tipoProc){
        this.resultado.addLast(tipoProc);
    }

}

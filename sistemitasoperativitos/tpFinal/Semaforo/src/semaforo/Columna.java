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

    private List<Semaforo>  semaforosSuperiores = new ArrayList<Semaforo>();
    private List<Integer>   procesosEnZonaCritica = new ArrayList<Integer>();
    private List<Semaforo>  semaforosInferiores = new ArrayList<Semaforo>();
    private List<Integer>   procesosTerminados = new ArrayList<Integer>();

    private Instancia instanciaPadre;
    private Integer         procesoID = 1;
    private static final long serialVersionUID = 666;
    private Character letra;

    public Columna(Instancia i, Integer letra) {
        this.instanciaPadre = i;
        this.letra = (char) (65+letra);

    }

    public void avanzarColaArriba(){
        for (Integer i=0;i<semaforosSuperiores.size()-2;i++){
            Semaforo semaforo = semaforosSuperiores.get(i);
            if((semaforo.getProcesosEnCola().size() != 0) && ((semaforo.getEsP() && semaforo.getValor()>0) || !semaforo.getEsP())){
                Integer procesoEnMovimiento = semaforo.remove();
//                semaforosSuperiores.get(i+1).addProceso(procesoEnMovimiento);
                
            }
        }
    }

    public void encolarEnSemaforoSuperior(Integer proceso){
        //this.semaforosSuperiores.get(0).addProceso(proceso);
    }

    /* Nuevas funciones de movimiento */
    public void llegaProcesoNuevo(SemaforoView padre){
        if(this.semaforosSuperiores.size()>0) //Si hay semaforos superiores
            this.semaforosSuperiores.get(0).llegaProcesoNuevo(procesoID);
        else {//sino, lo agrego a la zona crítica
            this.procesosEnZonaCritica.add(procesoID);            
            if(padre.getInstancia().zonaCriticaOcupada()){
                padre.appendLog("ZONA CRITICA OCUPADA POR MÁS DE UN PROCESO.");
            }
            padre.getInstancia().ocuparZonaCritica();
        }

        procesoID++;
    }

    public void llegaProcesoALaZonaInferior(Integer procID){
        if(this.semaforosInferiores.size()>0) //Si hay semaforos superiores
            this.semaforosInferiores.get(0).llegaProcesoNuevo(procID);
        else //sino, lo agrego a la zona crítica
            this.procesosTerminados.add(procID);
    }

    /**
     * 
     *@return que intenta avanza un proceso en la columna, si lo logra, devuelve true, sino, false
     */
    public Boolean siPuedeMuevalo(SemaforoView padre){
        Boolean res = false;

        Semaforo semaforActual; Integer tipoSemaforoActual;
        Integer totalSemaforo = this.semaforosSuperiores.size() + this.semaforosInferiores.size();
        Integer posSemaforo;
        Boolean estoyArriba;
        Boolean huboSignal = false;
        Integer signal = -1;

        for(Integer posSemaforoGlobal=0; posSemaforoGlobal< totalSemaforo && !res; posSemaforoGlobal++){
            posSemaforo = posSemaforoGlobal;

            if(posSemaforoGlobal>= this.semaforosSuperiores.size()){
                posSemaforo = posSemaforoGlobal - this.semaforosSuperiores.size();
                semaforActual = this.semaforosInferiores.get(posSemaforo);
                estoyArriba = false;
            } else {
                semaforActual = this.semaforosSuperiores.get(posSemaforo);
                estoyArriba = true;
            }
            tipoSemaforoActual = semaforActual.getValor();

            if(semaforActual.alguienEsperaParaEntrar()){//Proceso esperando para entrar
                // Actualizo el valor del semaforo
                if(semaforActual.getEsP()){
                    this.instanciaPadre.ocurreP(tipoSemaforoActual);
                } else {
                    huboSignal = this.instanciaPadre.ocurreV(tipoSemaforoActual);
                    signal = tipoSemaforoActual;
                    
                }

                // Veo donde queda el proceso;
                Integer procesoMoviendose = semaforActual.retirarProcesoAPuntoDeEntrar();
                if(semaforActual.getEsP()){
                    if (this.instanciaPadre.getValorSemaforo(tipoSemaforoActual)<0) { //Condiciones de WAIT
                        semaforActual.procesoEntraAlWait(procesoMoviendose);
                        padre.appendLog("Ejecutando una P. " + this.letra.toString()
                                + procesoMoviendose.toString() + " -> WAIT ");
                    } else { //Paso de largo
                        padre.appendLog("Ejecutando una P. " + this.letra.toString()
                                + procesoMoviendose.toString());
                        this.encolarEnSiguiente(estoyArriba, posSemaforo, procesoMoviendose, padre);
                    }
                } else {
                    if(huboSignal){
                        padre.appendLog("Ejecutando una V. " + this.letra.toString()
                            + procesoMoviendose.toString() + " - Signal X" + tipoSemaforoActual);
                    } else {
                        padre.appendLog("Ejecutando una V. " + this.letra.toString()
                                + procesoMoviendose.toString());
                    }
                    
                    this.encolarEnSiguiente(estoyArriba, posSemaforo, procesoMoviendose, padre);
                }



                res = true;
            } else {
                //Debería ser P
                //ACÁ NO DEBERÍA SUCEDER NADA. SÓLO CUANDO ES UN V DEBERÍA MANDAR UN SIGNAL Y DESPERTAR A ALGUIEN
                //if(semaforActual.alguienEnLaColaWAIT()){
                //    if(this.instanciaPadre.getValorSemaforo(tipoSemaforoActual)>=0) { //Condiciones de WAIT
                //        Integer procesoMoviendose = semaforActual.huboUnSignal();
                //        this.encolarEnSiguiente(estoyArriba, posSemaforo, procesoMoviendose);
                //    }
                //}
            }
        }

        if(huboSignal){
            this.instanciaPadre.signal(signal, padre);
        }

        return res;
    }

    /**
     * 
     * @param signalID
     * @return true sii se desperto a un proceso
     */
    public Boolean llegaSignal(Integer signalID, SemaforoView padre){
        Boolean res = false;

        Semaforo semaforActual; Integer tipoSemaforoActual;
        Integer totalSemaforo = this.semaforosSuperiores.size() + this.semaforosInferiores.size();
        Integer posSemaforo;
        Boolean estoyArriba;
        Integer procesoMoviendose;
        for(Integer posSemaforoGlobal=0; posSemaforoGlobal< totalSemaforo && !res; posSemaforoGlobal++){
            posSemaforo = posSemaforoGlobal;

            if(posSemaforoGlobal>= this.semaforosSuperiores.size()){
                posSemaforo = posSemaforoGlobal - this.semaforosSuperiores.size();
                semaforActual = this.semaforosInferiores.get(posSemaforo);
                estoyArriba = false;
            } else {
                semaforActual = this.semaforosSuperiores.get(posSemaforo);
                estoyArriba = true;
            }
            tipoSemaforoActual = semaforActual.getValor();

            if(semaforActual.getEsP() &&
                tipoSemaforoActual.equals(signalID) &&
                semaforActual.alguienEnLaColaWAIT()){
                    procesoMoviendose = semaforActual.retirarProcesoADeWAIT();
                    this.encolarEnSiguiente(estoyArriba, posSemaforo, procesoMoviendose, padre);
                    res = true;
            }
        }
        return res;
    }

    /**
     *
     * @param ubicacion = true si está en un semaforo superior,  false si está en un semaforo inferior
     * @param posSemaforo = posicion de la lista en la que está el semaforo actual
     * @param procesoMoviendose = id del proceso
     */
    private void encolarEnSiguiente(Boolean estaArriba, Integer posSemaforo, Integer procesoMoviendose, SemaforoView padre) {
        //throw new UnsupportedOperationException("Not yet implemented");
        String pista = "";
        if (estaArriba){
           if(posSemaforo<(this.semaforosSuperiores.size()-1)){
                // Paso al siguiente semaforo
                this.semaforosSuperiores.get(posSemaforo+1).llegaProcesoNuevo(procesoMoviendose);
            } else {
                // Paso a la zona crítica
                this.procesosEnZonaCritica.add(procesoMoviendose);
                padre.appendLog("Proceso entrando a la zona crítica: " + this.letra.toString() + procesoMoviendose.toString());
                if(padre.getInstancia().zonaCriticaOcupada()){
                    padre.appendLog("ZONA CRITICA OCUPADA POR MÁS DE UN PROCESO.");
                }
                padre.getInstancia().ocuparZonaCritica();
            }
        } else { //Esta abajo
           if(posSemaforo<(this.semaforosInferiores.size()-1)){
                // Paso al siguiente semaforo
                this.semaforosInferiores.get(posSemaforo+1).llegaProcesoNuevo(procesoMoviendose);
            } else {
                // Paso a la zona de terminados
                this.procesosTerminados.add(procesoMoviendose);
                this.instanciaPadre.procesoTermino(this.letra);
                padre.appendLog("Proceso terminado: " + this.letra.toString() + procesoMoviendose.toString());
            }
        }
        System.out.println(pista + " para ubicacion: " + estaArriba +
                " - posSemaforo: " + posSemaforo + " - idProc: " + procesoMoviendose);
    }

    public void liberarZonaCritica(){
        for (Integer procesoDeZonaCritica : procesosEnZonaCritica) {
            this.llegaProcesoALaZonaInferior(procesoDeZonaCritica);
        }
        procesosEnZonaCritica = new ArrayList<Integer>();
    }

    /* Getters y Setters */
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

    public void borrarTodosLosProcesos() {
        //colaInicial = new ArrayList<Integer>();

        for (Semaforo s : semaforosSuperiores) { s.borrarTodosLosProcesos();}

        procesosEnZonaCritica = new ArrayList<Integer>();

        //colaInferior = new ArrayList<Integer>();

        for (Semaforo s : semaforosInferiores) { s.borrarTodosLosProcesos();}

        procesosTerminados = new ArrayList<Integer>();
    }

     public void reiniciarProcesoID() {
        this.procesoID = 1;
    }

    /* Funciones para mostrar */
    public String mostrarPrevioASemaforoSup(Integer i){
        return this.semaforosSuperiores.get(i).mostrar(true);
    }

    public String mostrarSemaforoSup(Integer i){
        return this.semaforosSuperiores.get(i).mostrar(false);
    }

    public String mostrarPrevioASemaforoInf(Integer i){
        return this.semaforosInferiores.get(i).mostrar(true);
    }

    public String mostrarSemaforoInf(Integer i){
        return this.semaforosInferiores.get(i).mostrar(false);
    }


}
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package semaforo;

import java.util.List;

/**
 *
 * @author tomas
 */
public class SemaforoSolver {
/*
    static Instancia nextStep(Instancia instancia){

        for (Columna columna : instancia.getListaColumna()) {
            if(puedoEncolarArriba(columna)){
                encolarArriba(columna);
            }

            if(puedoAvanzarColaArriba(columna)){
                avanzarColaArriba(columna);
                return instancia;
            }

            if(puedoEncolarAbajo(columna)){
                encolarAbajo(columna);
            }

            if(puedoAvanzarColaAbajo(columna)){
                avanzarColaAbajo(columna);
                return instancia;
            }
        }
        return instancia;
    }

    private static void avanzarColaAbajo(Columna columna) {
        for (Integer i=0;i<columna.getSemaforosInferiores().size()-1;i++) {
            if((columna.getSemaforosInferiores().get(i).getEsP() && columna.getSemaforosInferiores().get(i).getValor()>0) && columna.getSemaforosInferiores().get(i).getCantProc()!=0){
                columna.getSemaforosInferiores().get(i).setValor(columna.getSemaforosInferiores().get(i).getValor()-1);
                columna.getSemaforosInferiores().get(i+1).addProceso(columna.getSemaforosInferiores().get(i).getProceso(0));
                columna.getSemaforosInferiores().get(i).getProcesosEnCola().remove(0);
                return;
            }
        }
    }



    private static boolean puedoAvanzarColaAbajo(Columna columna) {
        List<Semaforo> listSem = columna.getSemaforosInferiores();
        for (Semaforo semaforo : listSem) {
            if((!semaforo.getEsP())||(semaforo.getEsP() && semaforo.getValor()>0) && semaforo.getCantProc()!=0){
                return true;
            }
        }
        return false;    }


    private static void encolarAbajo(Columna columna) {
        columna.getSemaforosInferiores().get(0).addProceso(columna.getColaInicial().get(0));
        columna.removePrimeroColaInferior();
    }
    
    private static boolean puedoEncolarAbajo(Columna columna) {
        Boolean res=false;
        Semaforo semaforo = columna.getSemaforosInferiores().get(0);

        if((semaforo.getEsP() &&  semaforo.getValor()>0)||!semaforo.getEsP()){
            res=true;
        }
        return res;
    }

    private static void avanzarColaArriba(Columna columna) {
        for (Integer i=0;i<columna.getSemaforosSuperiores().size()-1;i++) {
            if(((!columna.getSemaforosSuperiores().get(i).getEsP())||(columna.getSemaforosSuperiores().get(i).getEsP() && columna.getSemaforosSuperiores().get(i).getValor()>0)) && columna.getSemaforosSuperiores().get(i).getCantProc()!=0){
                columna.getSemaforosSuperiores().get(i).setValor(columna.getSemaforosSuperiores().get(i).getValor()-1);
                columna.getSemaforosSuperiores().get(i+1).addProceso(columna.getSemaforosSuperiores().get(i).getProceso(0));
                columna.getSemaforosSuperiores().get(i).getProcesosEnCola().remove(0);
                return;
            }
        }
    }

    
    private static boolean puedoAvanzarColaArriba(Columna columna) {
        List<Semaforo> semaforos = columna.getSemaforosSuperiores();
        for (Semaforo semaforo : semaforos) {
            if(((!semaforo.getEsP())||(semaforo.getEsP() && semaforo.getValor()>0)) && semaforo.getCantProc()!=0){
                return true;
            }            
        }
        return false;
    }

    private static void encolarArriba(Columna columna) {
       columna.encolarEnSemaforoSuperior(columna.getColaInicial().get(0));
       columna.removePrimeroColaInicial();
    }
    
    private static boolean puedoEncolarArriba(Columna columna) {       
        if(columna.getColaInicial().size()!=0){
            return true;
        }
        return false;
    }

    private static boolean colasSuperioresVacias(Instancia instancia) {
        List<Columna> columnas = instancia.getListaColumna();
        for (Columna columna : columnas) {
            if(!columna.getColaInicial().isEmpty()){
                return false;
            }
        }
        return true;
    }

    private static boolean colasInferioresVacias(Instancia instancia) {
        List<Columna> columnas = instancia.getListaColumna();
        for (Columna columna : columnas) {
            if(!columna.getColaInferior().isEmpty()){
                return false;
            }
        }
        return true;
    }



    private static boolean zonasCriticasVacias(Instancia instancia) {
        List<Columna> columnas = instancia.getListaColumna();
        for (Columna columna : columnas) {
            if(!columna.getProcesosEnZonaCritica().isEmpty()){
                return false;
            }
        }
        return true;
    }
    */
}

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
public class Columna {

    private List<Integer>   colaInicial=null;
    private List<Semaforo>  semaforosSuperiores=null;
    private List<Integer>   semaforosEnZonaCritica=null;
    private Boolean         zonaCriticaOcupada=false;
    private List<Integer>   colaInferior=null;
    private List<Semaforo>  semaforosInferiores=null;

    public Columna() {
    }

    public void setColaInferior(List<Integer> colaInferior) {
        this.colaInferior = colaInferior;
    }

    public void setColaInicial(List<Integer> colaInicial) {
        this.colaInicial = colaInicial;
    }

    public void setSemaforosEnZonaCritica(List<Integer> semaforosEnZonaCritica) {
        this.semaforosEnZonaCritica = semaforosEnZonaCritica;
    }

    public void setSemaforosInferiores(List<Semaforo> semaforosInferiores) {
        this.semaforosInferiores = semaforosInferiores;
    }

    public void setSemaforosSuperiores(List<Semaforo> semaforosSuperiores) {
        this.semaforosSuperiores = semaforosSuperiores;
    }

    public void setZonaCriticaOcupada(Boolean zonaCriticaOcupada) {
        this.zonaCriticaOcupada = zonaCriticaOcupada;
    }

    public List<Integer> getColaInferior() {
        return colaInferior;
    }

    public List<Integer> getColaInicial() {
        return colaInicial;
    }

    public List<Integer> getSemaforosEnZonaCritica() {
        return semaforosEnZonaCritica;
    }

    public List<Semaforo> getSemaforosInferiores() {
        return semaforosInferiores;
    }

    public List<Semaforo> getSemaforosSuperiores() {
        return semaforosSuperiores;
    }

    public Boolean getZonaCriticaOcupada() {
        return zonaCriticaOcupada;
    }

}

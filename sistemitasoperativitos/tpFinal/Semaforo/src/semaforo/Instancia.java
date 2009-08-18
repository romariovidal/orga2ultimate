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
public class Instancia {

    List<List<Semaforo>> semaforosSuperiores; //valor de los semaforos superiores.
    List<List<Semaforo>> semaforosInferiores; //valor de los semaforos inferiores.
    Integer[] valoresSemaforos; //valor de las variables de los semaforos.
    List<Character> resultado; // lista de los distintos tipos de procesos que van finalizando su corrida.

    public Instancia(List<List<Semaforo>> semaforosSuperiores, List<List<Semaforo>> semaforosInferiores, Integer[] valoresSemaforos, List<Character> resultado) {
        this.semaforosSuperiores = semaforosSuperiores;
        this.semaforosInferiores = semaforosInferiores;
        this.valoresSemaforos = valoresSemaforos;
        this.resultado = resultado;
    }

    public void agregarSemaforoSuperior(Semaforo semaforo, Integer numTipoProc){
        this.semaforosSuperiores.get(numTipoProc).add(semaforo);
    }

    public void agregarSemaforoInferior(Semaforo semaforo, Integer numTipoProc){
        this.semaforosInferiores.get(numTipoProc).add(semaforo);
    }

    public List<Character> getResultado() {
        return resultado;
    }

    public List<List<Semaforo>> getSemaforosInferiores() {
        return semaforosInferiores;
    }

    public List<List<Semaforo>> getSemaforosSuperiores() {
        return semaforosSuperiores;
    }

    public Integer[] getValoresSemaforos() {
        return valoresSemaforos;
    }

    public void setResultado(List<Character> resultado) {
        this.resultado = resultado;
    }

    public void setSemaforosInferiores(List<List<Semaforo>> semaforosInferiores) {
        this.semaforosInferiores = semaforosInferiores;
    }

    public void setSemaforosSuperiores(List<List<Semaforo>> semaforosSuperiores) {
        this.semaforosSuperiores = semaforosSuperiores;
    }

    public void setValoresSemaforos(Integer[] valoresSemaforos) {
        this.valoresSemaforos = valoresSemaforos;
    }

    

}

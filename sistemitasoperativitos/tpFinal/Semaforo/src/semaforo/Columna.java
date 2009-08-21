/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package semaforo;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tomas
 */
public class Columna {

    private List<Integer>   colaInicial=new ArrayList<Integer>();
    private List<Semaforo>  semaforosSuperiores=null;
    private List<Integer>   procesosEnZonaCritica=new ArrayList<Integer>();
    private List<Integer>   colaInferior=new ArrayList<Integer>();
    private List<Semaforo>  semaforosInferiores=null;

    public Columna() {
    }

    void agregarSemaforoSuperior(Semaforo semaforo) {
        semaforosSuperiores.add(semaforo);
    }

    void agregarSemaforoInferior(Semaforo semaforo) {
        semaforosInferiores.add(semaforo);
    }



}

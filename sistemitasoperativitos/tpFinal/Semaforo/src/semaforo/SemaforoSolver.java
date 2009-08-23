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

    static Instancia nextStep(Instancia instancia){

        //chequeo que las colas no esten todas vacias. En ese caso no hay nada que hacer.
        if (colasVacias(instancia)) {
            System.out.println("Estan todas las colas vacias, tanto las superiores como las inferiores");
        }
        if(zonasCriticasVacias(instancia)){
            System.out.println("Estan todas las zonas criticas vacias");
        }

        


        return instancia;
    }

    private static boolean colasVacias(Instancia instancia) {
        List<Columna> columnas = instancia.getListaColumna();
        for (Columna columna : columnas) {
            if(!(columna.getColaInicial().isEmpty() && columna.getColaInferior().isEmpty())){
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
}

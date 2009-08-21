/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package semaforo;

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


        return instancia;
    }

    private static boolean colasVacias(Instancia instancia) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

}

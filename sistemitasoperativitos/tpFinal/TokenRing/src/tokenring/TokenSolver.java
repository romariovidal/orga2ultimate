/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tokenring;

/**
 *
 * @author tomas
 */
public class TokenSolver {

    public Instance nextStep(Instance instance){
        // cheque que el coordinador este realmente bajo.
        int intCoord=instance.getCoordinador();
        if(instance.getNodos()[intCoord].equals(true)){
            instance.log("El coordinador esta vivo!... is alive!");
            return instance;
        }








        return instance;
    }


}

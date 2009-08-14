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

    private Instance instance;

    public TokenSolver(Instance instance) {
        this.instance = instance;
    }



    public void  nextStep(){
        // cheque que el coordinador este realmente bajo.
        int intCoord=instance.getCoordinador();
        if(instance.getNodos()[intCoord].equals(true)){
            instance.log("El coordinador esta vivo!... is alive!");
            instance.setFinish(true);
        }
    }


}

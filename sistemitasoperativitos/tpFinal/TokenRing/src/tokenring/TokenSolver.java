/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tokenring;

import java.util.List;

/**
 *
 * @author tomas
 */
public class TokenSolver {

    private Instance instance;
    private Integer proxNodo;

    public TokenSolver(Instance instance, Integer nodoInicial) {
        this.instance = instance;
        this.instance.agregarAListados(nodoInicial);

        proxNodo = proximoNodo(nodoInicial);
        this.instance.setFinish(false);
        this.instance.log("Nodo " + nodoInicial +
                        " arma el mensaje para la elección.\n\tMensaje actual: " +
                        this.instance.printLog());
        this.instance.setHayMensajeDandoVuelta(true);
        this.instance.setSender(nodoInicial);
        this.instance.setReceiver(proxNodo);
    }



    public void nextStep(){
        // cheque que el coordinador este realmente bajo.
        int intCoord=instance.getCoordinador();
        if(instance.getNodos()[intCoord].equals(true)){
            instance.log("El coordinador esta vivo!... is alive!");
            instance.setFinish(true);
        }
        
        List<Integer> lista = this.instance.getListados();

        //System.out.println("Analizando " + proxNodo + " el último es " + lista.get(lista.size()-1));
        if(esta(proxNodo,lista)){
            Integer nuevoCoordinador = max(lista);
            //System.out.println("Terminé, el nuevo coordinador es " + nuevoCoordinador);
            this.instance.log("Terminada la elección - Nuevo coordinador: " + nuevoCoordinador);
            this.instance.setFinish(true);
            this.instance.setCoordinador(nuevoCoordinador);
            this.instance.setHayMensajeDandoVuelta(false);
        } else {
            if(instance.getStatusNodo(proxNodo)){//Caso en que recibe y está levantado
                this.instance.setSender(proxNodo);
                instance.agregarAListados(proxNodo);
                this.instance.log("Nodo " + proxNodo + 
                        " recibe el mensaje y lo reenvía agregandose\n\tMensaje actual: " +
                        this.instance.printLog());
                
                //System.out.println("Agregando nodo al mensaje " + proxNodo);
            } else {//Caso en que en el que recibe y está caido
                this.instance.log("Nodo " + proxNodo +
                        " esta caido, se reenvía el mensaje al siguiente\n\tMensaje actual: " +
                        this.instance.printLog());
            }
            proxNodo = proximoNodo(proxNodo);
            this.instance.setReceiver(proxNodo);
        }
    }

    private static Integer proximoNodo(Integer unNodo){
        return (unNodo+1)%8;
    }

    private boolean esta(Integer proxNodo, List<Integer> lista) {
        for (Integer i=0; i<lista.size(); i++){
            if (lista.get(i).equals(proxNodo))
                return true;
        }
        return false;
    }

    private Integer max(List<Integer> lista) {
        Integer j=-1;
        for (Integer i=0; i<lista.size(); i++){
            if (lista.get(i)>j)
                j = lista.get(i);
        }
        
        return j;
    }


}

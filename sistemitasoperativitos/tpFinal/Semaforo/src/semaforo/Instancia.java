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
public class Instancia {

    private List<Columna> listaColumna; // lista de las columnas copadas que tienen casi todo.
    private Integer[] valoresSemaforos; //valor de las variables de los semaforos.
    private List<Character> resultado; // lista de los distintos tipos de procesos que van finalizando su corrida.
    private Integer cantTiposProcesos;
    private Integer cantSemaforos;

    public Instancia(Integer cantidadTiposProcesos, Integer cantidadSemaforos) {
        this.cantTiposProcesos = cantidadTiposProcesos;
        this.cantSemaforos = cantidadSemaforos;
        listaColumna = new ArrayList<Columna>();
        for (int i = 0; i < cantidadTiposProcesos; i++) {
            listaColumna.add(new Columna());
        }

        this.valoresSemaforos = new Integer[cantidadSemaforos];
        for(Integer i=0; i< cantidadSemaforos; i++){
            this.valoresSemaforos[i] = 0;
        }
        this.resultado = new ArrayList<Character>();
    }

    public void agregarSemaforoSuperior(Semaforo semaforo, Integer numTipoProc){
        //System.out.println("Antes " + this.listaColumna.get(numTipoProc).getSemaforosSuperiores().size());
        this.listaColumna.get(numTipoProc).agregarSemaforoSuperior(semaforo);
        //System.out.println("Despues " + this.listaColumna.get(numTipoProc).getSemaforosSuperiores().size());
    }

    public void agregarSemaforoInferior(Semaforo semaforo, Integer numTipoProc){
        //System.out.println("Antes " + this.listaColumna.get(numTipoProc).getSemaforosInferiores().size());
        this.listaColumna.get(numTipoProc).agregarSemaforoInferior(semaforo);
        //System.out.println("Despues " + this.listaColumna.get(numTipoProc).getSemaforosInferiores().size());

    }

    public List<Character> getResultado() {
        return resultado;
    }

    public Integer[] getValoresSemaforos() {
        return valoresSemaforos;
    }

    public void setResultado(List<Character> resultado) {
        this.resultado = resultado;
    }



    public void setValoresSemaforos(Integer[] valoresSemaforos) {
        this.valoresSemaforos = valoresSemaforos;
    }

    public List<String> listaDeSemaforosSuperiores(Integer i){
        return this.listaDeSemaforos(true, i);
    }

    public List<String> listaDeSemaforosInferiores(Integer i){
        return this.listaDeSemaforos(false, i);
    }

    public Columna getColumna(Integer nroSem) {
        return this.listaColumna.get(nroSem);
    }

    public List<Columna> getListaColumna() {
        return listaColumna;
    }


    private List<String> listaDeSemaforos(Boolean b, Integer i) {
//        List<String> res = new ArrayList<String>();
//        List<List<Semaforo>> l = null;
//        if(b)
//            l = this.semaforosSuperiores;
//        else
//            l = this.semaforosInferiores;
//
//        List<Semaforo> lista = l.get(i);
//        String temp; Semaforo semTemp = null;
//        for(Integer j=0; j<lista.size(); j++){
//            temp = "";
//            semTemp = lista.get(j);
//            if(semTemp.getEsP() != null){
//                if(semTemp.getEsP())
//                    temp += "P(X"+semTemp.getValor()+")";
//                else
//                    temp += "V(X"+semTemp.getValor()+")";
//            } else {
//                temp += "Previo";
//            }
//
//            temp += " - ";
//
//            for(Integer k=0; k< semTemp.getCantProc(); k++){
//                if(k>0)
//                    temp+=", ";
//
//                temp += (char) (65+i) +"" + semTemp.getProceso(k);
//            }
//
//            res.add(temp);
//        }
//
//
//        return res;
        return null;
    }

    public Integer getCantSemaforos() {
        return cantSemaforos;
    }

    public Integer getCantTiposProcesos() {
        return cantTiposProcesos;
    }

    public Integer getValorSemaforo(Integer i) {
        return this.valoresSemaforos[i];
    }

    public void  setValorSemaforo(Integer i, Integer valor) {
        this.valoresSemaforos[i] = valor;
    }

}

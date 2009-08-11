/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package banquero2;

/**
 *
 * @author tomas
 */
public class BanqueroAlgorithm {

    private Vector finish= new Vector(0);
    private Matriz necesidad;
    private Vector disponibles;
    private Vector request;
    private Matriz asignacion;
    private int i=1;
    private int proceso;
    private int paso;

    public BanqueroAlgorithm(Matriz necesidad, Vector disponibles, Vector request, Matriz asignacion, int proceso) {
        this.necesidad = necesidad;
        this.disponibles = disponibles;
        this.request = request;
        this.asignacion = asignacion;
        this.proceso = proceso;
    }
    


    public BanqueroAlgorithm(){}

    void nextStep(){


    }
    /**
     * Si Request <= Need(i), ir al paso 2, sino terminar.
     */
    void pasoUno(){
        if(necesidad.arregloMenorOIgualQue(proceso, request)){
            paso++;
        }else{
            paso=-1;
        }
    }


    /**
     * Si Request <= Available, ir al paso 3, sino terminar.
     */
    void pasoDos(){
        if(request.mayorOIgual(disponibles)){
            paso++;
        }else{
            paso=-1;
        }
    }
    /**
     * Simular asignacion y correr el algoritmo de seguridad.
     */
    void pasoTres(){
        paso++;
    }

    /**
     * Finish[i] == false
     */
    void pasoCuatro(){
        if(finish.dameValor(i)==0){
            paso++;
        }
        i++;
        if(i>8){
        paso=11;
        }
    }

    /**
     * Need(i) <= Work
     */
    void pasoCinco(){
        if(necesidad.filaEsMenorOIgual(i, disponibles)){
            paso++;
        }
    }
}

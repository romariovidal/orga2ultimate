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
    private Integer procesoActual=1;
    private Integer proceso;
    private Integer paso=1;

    public BanqueroAlgorithm(Matriz necesidad, Vector disponibles, Vector request, Matriz asignacion, Integer proceso) {
        this.necesidad = necesidad;
        this.disponibles = disponibles;
        this.request = request;
        this.asignacion = asignacion;
        this.proceso = proceso;
    }

    public Matriz getAsignacion() {
        return asignacion;
    }

    public Vector getDisponibles() {
        return disponibles;
    }

    public Vector getFinish() {
        return finish;
    }

    public Integer getI() {
        return procesoActual;
    }

    public Matriz getNecesidad() {
        return necesidad;
    }

    public Integer getPaso() {
        return paso;
    }

    public Integer getProceso() {
        return proceso;
    }

    public Vector getRequest() {
        return request;
    }
    


    public BanqueroAlgorithm(){}

    void nextStep(){

        switch(paso){
            case 1:
                pasoUno();
                break;
            case 2:
                pasoDos();
                break;
            case 3:
                pasoTres();
                break;
            case 4:
                pasoCuatro();
                break;
            case 5:
                pasoCinco();
                break;
            case 6:
                pasoSeis();
                break;
            case 7:
                pasoSiete();
                break;
            case 8:
                pasoOcho();
                break;
            case 9:
                pasoNueve();
                break;
            case 10:
                pasoDiez();
                break;
            default:
                System.out.println("Error: numero de paso invalido, para la simulacion");
        }

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
        if(finish.dameValor(procesoActual)==0){
            paso++;
        }
        procesoActual++;
        if(procesoActual>8){
        paso=6;
        }
    }

    /**
     * Need(i) <= Work
     */
    void pasoCinco(){
        if(necesidad.filaEsMenorOIgual(procesoActual, disponibles)){
            paso+=2;
        }
    }

    /**
     * Si no existe i que cumpla estas condiciones ir al paso 3.
     */
    void pasoSeis(){
        paso=11;
    }
    
    void pasoSiete(){
        disponibles.agregar(asignacion.dameFila(procesoActual));
        asignacion.ponerCerosEnFila(procesoActual);
        paso++;
    }

    void pasoOcho(){
        finish.asignar(procesoActual, 1);
        paso++;
    }

    void pasoNueve(){
        paso=4;
        procesoActual=1;
    }

    void pasoDiez(){
        Vector vector = new Vector(1);
        if(finish.mayorOIgual(vector)){
            System.out.println("El sistema esta en estado seguro");
        }
    }
}

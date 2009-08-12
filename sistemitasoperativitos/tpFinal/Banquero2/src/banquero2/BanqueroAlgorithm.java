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
    private Integer[] modificaciones;
        /*  modificaciones[0] finish - vector
         *  modificaciones[1] necesidad - matriz
         *  modificaciones[2] disponibles - vector
         *  modificaciones[3] asignacion - matriz
         *  modificaciones[4] request - vector
         * -1 todo, 0 nada, i fila o celda según corresponda
         */

    public BanqueroAlgorithm(Matriz necesidad, Vector disponibles, Vector request, Matriz asignacion, Integer proceso) {
        this.necesidad = necesidad;
        this.disponibles = disponibles;
        this.request = request;
        this.asignacion = asignacion;
        this.proceso = proceso;
        this.modificaciones = new Integer[5];
        this.reiniciarModif();
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

    public Integer[] getModificaciones() {
        return modificaciones;
    }
    


    public BanqueroAlgorithm(){}

    void nextStep(){
        this.reiniciarModif();
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
        this.modificaciones[1] = -1;
        this.modificaciones[4] = -1;
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
        this.modificaciones[4] = -1;
        this.modificaciones[2] = -1;
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
        //System.out.println("El valor de la matriz de finish para " + procesoActual + " es "+finish.dameValor(procesoActual));
        this.modificaciones[0] = procesoActual;

        if(finish.dameValor(procesoActual).equals(0)){
            paso++;
        } else {
            procesoActual++;
            if(procesoActual>8){
                paso=6;
            }
        }
    }

    /**
     * Need(i) <= Work
     */
    void pasoCinco(){
        this.modificaciones[1] = procesoActual;
        if(necesidad.filaEsMenorOIgual(procesoActual, disponibles)){
            paso+=2;
        } else {
            procesoActual++;
            if(procesoActual>8){
                paso=6;
            } else {
                paso = 4;
            }
        }
    }

    /**
     * Si no existe i que cumpla estas condiciones ir al paso 3.
     */
    void pasoSeis(){
        paso=10;
    }
    
    void pasoSiete(){
        this.modificaciones[2] = -1;
        this.modificaciones[3] = procesoActual;
        disponibles.agregar(asignacion.dameFila(procesoActual));
        asignacion.ponerCerosEnFila(procesoActual);
        paso++;
    }

    void pasoOcho(){
        this.modificaciones[0] = procesoActual;
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
        } else {
            System.out.println("El sistema esta en estado seguro");
        }
    }

    private void reiniciarModif(){
        for(Integer i=0; i<this.modificaciones.length; i++)
            this.modificaciones[i] = 0;
    }
}

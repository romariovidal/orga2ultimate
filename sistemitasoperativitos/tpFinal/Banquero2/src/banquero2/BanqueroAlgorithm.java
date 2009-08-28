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

    private Vector finish;
    private Matriz necesidad;
    private Vector disponibles;
    private Vector request;
    private Matriz asignacion;
    private Integer procesoActual=0;
    private Integer proceso;
    private Integer paso=1;
    private Integer[] modificaciones;
    private Boolean corriendo = true;

    private Integer cantidadProcesos;
    private Integer cantidadRecursos;

    private String status;
    private String status1;
        /*  modificaciones[0] finish - vector
         *  modificaciones[1] necesidad - matriz
         *  modificaciones[2] disponibles - vector
         *  modificaciones[3] asignacion - matriz
         *  modificaciones[4] request - vector
         *  modificaciones[5] iValor - label
         * -1 todo, 0 nada, i fila o celda según corresponda
         */

    public BanqueroAlgorithm(Matriz necesidad, Vector disponibles, Vector request, Matriz asignacion, Integer proc) {
        this.necesidad = necesidad;
        this.disponibles = disponibles;
        this.request = request;
        this.asignacion = asignacion;
        if (proc.equals(0))
            proc = 1;
        this.proceso = proc;
        this.procesoActual = proc;
        this.modificaciones = new Integer[6];
        this.reiniciarModif();
        this.modificaciones[1] = proc;
        this.modificaciones[4] = -1;
        this.status = "Corriendo Banquero";
        this.status1 = "";

        this.cantidadProcesos = this.necesidad.tamProc();
        this.cantidadRecursos = this.necesidad.tamRec();

        this.finish = new Vector(this.cantidadProcesos, 0);
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

    public String getStatus() {
        return status;
    }

    public Boolean getCorriendo() {
        return corriendo;
    }

    public String getStatus1() {
        return status1;
    }



    //public BanqueroAlgorithm(){}

    void nextStep(){
        this.reiniciarModif();

        //System.out.println("Estoy en el paso " + paso + " revisando el proceso " + procesoActual);
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
            case 11:
                pasoOnce();
                break;
            default:
                System.out.println("Error: numero de paso invalido, para la simulacion");
        }

        switch(paso){
            case 1:                
                break;
            case 2:
                this.modificaciones[4] = -1;
                this.modificaciones[2] = -1;
                break;
            case 3:
                this.modificaciones[4] = -1;
                this.modificaciones[2] = -1;
                this.modificaciones[1] = proceso;
                this.modificaciones[3] = proceso;
                break;
            case 4:
                this.modificaciones[4] = -1;
                this.modificaciones[2] = -1;
                this.modificaciones[1] = proceso;
                this.modificaciones[3] = proceso;
                break;
            case 5:
                this.modificaciones[5] = -1;
                this.modificaciones[0] = procesoActual;
                break;
            case 6:
                this.modificaciones[1] = procesoActual;
                this.modificaciones[2] = -1;
                break;
            case 7:
                break;
            case 8:
                this.modificaciones[2] = -1;
                this.modificaciones[3] = procesoActual;
                break;
            case 9:
                this.modificaciones[0] = procesoActual;
                this.modificaciones[2] = -1;
                this.modificaciones[3] = procesoActual;
                break;
            case 10:
                this.modificaciones[0] = procesoActual;
                break;
            case 11:
                this.modificaciones[0] = -1;
                break;
            default:
                System.out.println("Error: numero de paso invalido, para la simulacion");
        }
        //System.out.println("Estoy en el paso " + paso + " revisando el proceso " + procesoActual);

    }
    /**
     * Si Request <= Need(i), ir al paso 2, sino terminar.
     */
    void pasoUno(){
        if(necesidad.arregloMenorOIgualQue(proceso, request)){
            paso++;
        }else{
            paso=-1;
            this.status = "Terminado. Pide más de lo que anunció como máximo.";
            this.status1 = "";
            this.corriendo = false;
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
            this.status = "Terminado. No hay recursos disponible para satisfacer el pedido.";
            this.status1 = "";
            this.corriendo = false;
        }
    }
    /**
     * Simular asignacion y correr el algoritmo de seguridad.
     */
    void pasoTres(){
        disponibles.quitar(request);
        necesidad.restarFila(proceso, request);
        asignacion.agregar(proceso, request);
        //System.out.println("Aca debería cambiar");
        paso++;
    }

     void pasoCuatro(){
        paso++;
        procesoActual = 1;
        this.status = "Corriendo Algoritmo de Seguridad";
        this.status1 = "";
    }

    /**
     * Finish[i] == false
     */
    void pasoCinco(){
//        System.out.println("El valor de la matriz de finish para " +
//                procesoActual + "/" + this.cantidadProcesos +
//                " es "+finish.dameValor(procesoActual) + "/" + finish.tam());
        

        if(finish.dameValor(procesoActual).equals(0)){
            paso++;
        } else {
            procesoActual++;
            this.modificaciones[5] = -1;
            if(procesoActual>this.cantidadProcesos){
                paso=7;
            }
        }
    }

    /**
     * Need(i) <= Work
     */
    void pasoSeis(){
        
        if(necesidad.filaEsMenorOIgual(procesoActual, disponibles)){
            paso+=2;
        } else {
            procesoActual++;
            this.modificaciones[5] = -1;
            if(procesoActual>this.cantidadProcesos){
                paso=7;
            } else {
                paso = 5;
            }
        }
    }

    /**
     * Si no existe i que cumpla estas condiciones ir al paso 3.
     */
    void pasoSiete(){
        paso=11;
    }
    
    void pasoOcho(){
        
        disponibles.agregar(asignacion.dameFila(procesoActual));
        //asignacion.ponerCerosEnFila(procesoActual);
        paso++;
    }

    void pasoNueve(){
        
        finish.asignar(procesoActual, 1);
        paso++;
    }

    void pasoDiez(){
        paso=5;
        procesoActual=1;
        this.modificaciones[5] = -1;
    }

    void pasoOnce(){
        Vector vector = new Vector(this.cantidadProcesos,1);
        if(finish.mayorOIgual(vector)){
            System.out.println("El sistema esta en estado seguro");
            this.status = "Terminado. Se puede otorgar el pedido ya que existe";
            this.status1 = "una secuencia segura después de otorgarlo.";
        } else {
            System.out.println("El sistema esta en estado seguro");
            this.status = "Terminado. No se puede otorgar ya que queda en estado inseguro.";
            this.status1 = "";
        }
        this.corriendo = false;

    }

    private void reiniciarModif(){
        for(Integer i=0; i<this.modificaciones.length; i++)
            this.modificaciones[i] = 0;
    }
}

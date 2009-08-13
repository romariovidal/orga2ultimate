package tokenring;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author tomas
 */
public class Instance {

    private Boolean[] nodos; //nodos que estan vivos cuando comienza la corrida del algoritmo.
    private List<Integer> listados; //nodos que estan el la lista de donde se eligira el coordinador.
    private Integer viejoCoordinador; //el que todos creen que es el coordinador.
    private List<String> log; //mensajes de log para mostrar en la consola.
    private Integer nuevoCoordinador; //el que sera coordinador cuando termine la corrida del algoritmo. (podria ser el mismo que el viejo si es que cuando se mando a correr la simulacion el coordinador estaba vivo)
    
    private Instance(Integer viejoCoordinador) {
        this.nodos = new Boolean[8];
        this.listados = new LinkedList<Integer>();        
        this.viejoCoordinador = viejoCoordinador;
        this.log = new LinkedList<String>();
    }

    public static Instance create(Integer inicial, Integer viejoCoordinador){
        Instance instance = new Instance(viejoCoordinador);
    return instance;
    }

    public Integer getNuevoCoordinador(){
        return this.nuevoCoordinador;
    }

    public List<String> getLog(){
        return log;
    }

    public void subirNodo(Integer nodo){
        this.nodos[nodo]=true;
    }

    public void bajarNodo(Integer nodo){
        this.nodos[nodo]=false;
    }

    /**
     *
     * @return el nodo que inicializa el algoritmo.
     */
    public Integer dameInicial(){
        return this.listados.get(listados.size()-1);
    }

    /**
     * Agrega un mensaje de log a la lista.
     * @param logMsg
     */
    public void log(String logMsg){
        this.log.add(logMsg);
    }

    public void agregarAListados(Integer nodo){
        this.listados.add(nodo);
    }

    public List<Integer> getListados() {
        return listados;
    }

    public Boolean[] getNodos() {
        return nodos;
    }

    public Integer getViejoCoordinador() {
        return viejoCoordinador;
    }

    public void setListados(List<Integer> listados) {
        this.listados = listados;
    }

    public void setLog(List<String> log) {
        this.log = log;
    }

    public void setNodos(Boolean[] nodos) {
        this.nodos = nodos;
    }

    public void setNuevoCoordinador(Integer nuevoCoordinador) {
        this.nuevoCoordinador = nuevoCoordinador;
    }

    public void setViejoCoordinador(Integer viejoCoordinador) {
        this.viejoCoordinador = viejoCoordinador;
    }



}

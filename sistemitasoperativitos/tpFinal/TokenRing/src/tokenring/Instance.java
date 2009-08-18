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
    private Integer coordinador; //el que todos creen que es el coordinador.
    private List<String> log; //mensajes de log para mostrar en la consola.
    private Boolean finish;
    
    private Instance() {
        this.nodos = new Boolean[8];
        for (int i=0;i<nodos.length;i++) {
            this.nodos[i]=true;
        }
        this.listados = new LinkedList<Integer>();        
        this.coordinador = 7;
        this.log = new LinkedList<String>();
        this.finish=true;
    }

    public void setFinish(Boolean finish) {
        this.finish = finish;
    }

    public Boolean getFinish() {
        return finish;
    }

    public static Instance create(){
        Instance instance = new Instance();
    return instance;
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

    public Boolean statusNodo(Integer nodo){
        return this.nodos[nodo];
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

    public Integer getCoordinador() {
        return coordinador;
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

    public void setCoordinador(Integer coordinador) {
        this.coordinador = coordinador;
    }

    public Boolean getStatusNodo(Integer i) {
        return this.nodos[i];
    }

    public String printLog() {
        String temp = "";

        for (Integer i=0; i<listados.size(); i++){
            if(!temp.equals("")){
                temp += ", ";
            }
            temp += listados.get(i).toString();

        }

        return temp;
    }



}

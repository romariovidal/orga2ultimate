/*
 * TokenRingApp.java
 */

package tokenring;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;
import javax.swing.Timer;
import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;

/**
 * The main class of the application.
 */
public class TokenRingApp extends SingleFrameApplication implements ActionListener {

    private List<Integer> ayaAlCoorinador;
    /**
     * At startup create and show the main frame of the application.
     */
    @Override protected void startup() {
        this.tokenView = new TokenRingView(this, this.tokenInstance);
        //this.tokenView.setInstance(this.tokenInstance);
        this.timer = new Timer(3000, this);
        this.timer.setInitialDelay(0);
        this.timerChange();
         this.ayaAlCoorinador= new LinkedList<Integer>();
        show(this.tokenView);
    }

    /**
     * This method is to initialize the specified window by injecting resources.
     * Windows shown in our application come fully initialized from the GUI
     * builder, so this additional configuration is not needed.
     */
    @Override protected void configureWindow(java.awt.Window root) {
    }

    /**
     * A convenient static getter for the application instance.
     * @return the instance of TokenRingApp
     */
    public static TokenRingApp getApplication() {        
        return Application.getInstance(TokenRingApp.class);
    }

    /**
     * Main method launching the application.
     */
    public static void main(String[] args) {
        launch(TokenRingApp.class, args);        
    }

    private Instance tokenInstance = Instance.create();
    private TokenRingView tokenView;
    private Timer timer;
    private TokenSolver tokenSolver = null;

    public void refrescarGraficos(){
        //this.tokenView.appendLog(new java.util.Date().toString());
        //this.tokenView.redibujar(this.tokenInstance);
    }

    public void agregarLog(String st){
        this.tokenView.appendLog(st);
    }

    public void timerChange(){
        if(this.timer.isRunning()){
            this.timer.stop();
        } else{
            this.timer.start();
        }
    }

    public void actionPerformed(ActionEvent arg0) {
        if(tokenSolver != null){
            System.out.println("El solver está andando");
            tokenSolver.nextStep();
            tokenView.appendLog(tokenInstance.getLog().get(tokenInstance.getLog().size()-1));

            if (tokenInstance.getFinish()){
                tokenSolver = null;
            }
        } else {
            //System.out.println("La cola de ack es de largo: " +this.ayaAlCoorinador.size() );
            if(!this.ayaAlCoorinador.isEmpty()){
                Integer enviador = this.ayaAlCoorinador.get(this.ayaAlCoorinador.size()-1);
                this.ayaAlCoorinador.remove(enviador);
                Integer coordinador = this.tokenInstance.getCoordinador();
                if(this.tokenInstance.getNodos()[coordinador]){
                    this.tokenView.appendLog("IAA del coordinador a  " + enviador);
                } else {
                    this.tokenView.appendLog("Nodo " + enviador + " AYA -> TIMEOUT");
                    this.tokenView.appendLog("Nodo " + enviador + " inicia proceso de elección de nuevo coordinador");
                    this.tokenSolver = new TokenSolver(tokenInstance, enviador);
                    tokenView.appendLog(tokenInstance.getLog().get(tokenInstance.getLog().size()-1));
                }
                //System.out.println("\t\tAhora es de " + this.ayaAlCoorinador.size() );
            }
        } 
        
        
        this.tokenView.redibujar(tokenInstance);
    }

    void bajarNodo(Integer i) {
        this.tokenInstance.bajarNodo(i);
        this.tokenView.appendLog("El nodo " + i + " está ahora offline");
        this.refrescarGraficos();
    }

    void enviarAYA(Integer i) {
        this.ayaAlCoorinador.add(i);
        this.tokenView.appendLog("AYA de " + i + " al coordinador");
        this.refrescarGraficos();
    }

    void subirNodo(Integer i) {
        this.tokenInstance.subirNodo(i);
        this.tokenView.appendLog("El nodo " + i + " está ahora online");
        this.refrescarGraficos();        
    }
    
}

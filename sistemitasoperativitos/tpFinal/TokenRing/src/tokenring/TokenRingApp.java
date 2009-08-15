/*
 * TokenRingApp.java
 */

package tokenring;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;

/**
 * The main class of the application.
 */
public class TokenRingApp extends SingleFrameApplication implements ActionListener {
    
    /**
     * At startup create and show the main frame of the application.
     */
    @Override protected void startup() {
        this.tokenView = new TokenRingView(this);
        this.tokenView.setInstance(this.tokenInstance);
        this.timer = new Timer(5000, this);
        this.timer.setInitialDelay(0);
        this.timerChange();
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

    public void refrescarGraficos(){
        this.tokenView.appendLog(new java.util.Date().toString());
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
        this.refrescarGraficos();
    }
    
}

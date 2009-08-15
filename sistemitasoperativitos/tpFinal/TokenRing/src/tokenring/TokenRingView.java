/*
 * TokenRingView.java
 */

package tokenring;

import java.awt.Dimension;
import java.awt.GridLayout;
import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.TaskMonitor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.GroupLayout;
import javax.swing.Timer;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

/**
 * The application's main frame.
 */
public class TokenRingView extends FrameView implements ActionListener {
    private TokenRingApp appSide;
    private Instance tokenInstance;

    public TokenRingView(TokenRingApp app) {
        super(app);
        this.appSide = app;

        initComponents();
        initComponents2();

        // status bar initialization - message timeout, idle icon and busy animation, etc
        ResourceMap resourceMap = getResourceMap();
        int messageTimeout = resourceMap.getInteger("StatusBar.messageTimeout");
        messageTimer = new Timer(messageTimeout, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                statusMessageLabel.setText("");
            }
        });
        messageTimer.setRepeats(false);
        int busyAnimationRate = resourceMap.getInteger("StatusBar.busyAnimationRate");
        for (int i = 0; i < busyIcons.length; i++) {
            busyIcons[i] = resourceMap.getIcon("StatusBar.busyIcons[" + i + "]");
        }
        busyIconTimer = new Timer(busyAnimationRate, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                busyIconIndex = (busyIconIndex + 1) % busyIcons.length;
                statusAnimationLabel.setIcon(busyIcons[busyIconIndex]);
            }
        });
        idleIcon = resourceMap.getIcon("StatusBar.idleIcon");
        statusAnimationLabel.setIcon(idleIcon);
        progressBar.setVisible(false);

        // connecting action tasks to status bar via TaskMonitor
        TaskMonitor taskMonitor = new TaskMonitor(getApplication().getContext());
        taskMonitor.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                String propertyName = evt.getPropertyName();
                if ("started".equals(propertyName)) {
                    if (!busyIconTimer.isRunning()) {
                        statusAnimationLabel.setIcon(busyIcons[0]);
                        busyIconIndex = 0;
                        busyIconTimer.start();
                    }
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(true);
                } else if ("done".equals(propertyName)) {
                    busyIconTimer.stop();
                    statusAnimationLabel.setIcon(idleIcon);
                    progressBar.setVisible(false);
                    progressBar.setValue(0);
                } else if ("message".equals(propertyName)) {
                    String text = (String)(evt.getNewValue());
                    statusMessageLabel.setText((text == null) ? "" : text);
                    messageTimer.restart();
                } else if ("progress".equals(propertyName)) {
                    int value = (Integer)(evt.getNewValue());
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(false);
                    progressBar.setValue(value);
                }
            }
        });
    }

    @Action
    public void showAboutBox() {
        if (aboutBox == null) {
            JFrame mainFrame = TokenRingApp.getApplication().getMainFrame();
            aboutBox = new TokenRingAboutBox(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        TokenRingApp.getApplication().show(aboutBox);
    }

    void setInstance(Instance tokenInstance) {
        this.tokenInstance = tokenInstance;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();
        statusPanel = new javax.swing.JPanel();
        javax.swing.JSeparator statusPanelSeparator = new javax.swing.JSeparator();
        statusMessageLabel = new javax.swing.JLabel();
        statusAnimationLabel = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();

        mainPanel.setName("mainPanel"); // NOI18N

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 252, Short.MAX_VALUE)
        );

        menuBar.setName("menuBar"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(tokenring.TokenRingApp.class).getContext().getResourceMap(TokenRingView.class);
        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(tokenring.TokenRingApp.class).getContext().getActionMap(TokenRingView.class, this);
        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
        helpMenu.setName("helpMenu"); // NOI18N

        aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        statusPanel.setName("statusPanel"); // NOI18N

        statusPanelSeparator.setName("statusPanelSeparator"); // NOI18N

        statusMessageLabel.setName("statusMessageLabel"); // NOI18N

        statusAnimationLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        statusAnimationLabel.setName("statusAnimationLabel"); // NOI18N

        progressBar.setName("progressBar"); // NOI18N

        javax.swing.GroupLayout statusPanelLayout = new javax.swing.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(statusPanelSeparator, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(statusMessageLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 226, Short.MAX_VALUE)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusAnimationLabel)
                .addContainerGap())
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addComponent(statusPanelSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(statusMessageLabel)
                    .addComponent(statusAnimationLabel)
                    .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3))
        );

        setComponent(mainPanel);
        setMenuBar(menuBar);
        setStatusBar(statusPanel);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JLabel statusAnimationLabel;
    private javax.swing.JLabel statusMessageLabel;
    private javax.swing.JPanel statusPanel;
    // End of variables declaration//GEN-END:variables

    private final Timer messageTimer;
    private final Timer busyIconTimer;
    private final Icon idleIcon;
    private final Icon[] busyIcons = new Icon[15];
    private int busyIconIndex = 0;

    private final static String newline = "\n";

    private JDialog aboutBox;

    private SimulacionDibujo unDibujo;
    private JButton[] botonesUpDown;
    private JButton[] botonesCoordinador;
    private JTextArea logs;
    

    private Integer v1 = 80;
    private Integer v2 = 550;
    private Integer h1 = 600;
    private Integer h2 = 400;
    private Integer h3 = h1+h2;


    private void initComponents2() {
        this.statusMessageLabel.setText("Estamos trabajando para usted");
        this.unDibujo = new SimulacionDibujo();

        JPanel botonera = new JPanel();
        this.botonesUpDown = new JButton[8];
        this.botonesCoordinador = new JButton[8];
        crearBotonera(botonera);

        JPanel consola = new JPanel();
        this.logs = new JTextArea();
        //this.logs.setSize(new Dimension (400, 200));
        logs.setEditable(false);
        this.appendLog("Iniciando simulación");
        
        JScrollPane areaScrollPane = new JScrollPane(logs);
        areaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        //areaScrollPane.setPreferredSize(new Dimension(h2-80, v2-80));

        consola.setLayout(new GridLayout(1, 1));
        consola.add(areaScrollPane);
        consola.setBorder(new TitledBorder("Logs"));

        GroupLayout layoutGral = new GroupLayout(mainPanel);
        mainPanel.setLayout(layoutGral);
        mainPanel.setSize(new Dimension(h3, v1+v2));
        layoutGral.setAutoCreateGaps(true);
        layoutGral.setAutoCreateContainerGaps(true);
        layoutGral.setHorizontalGroup(
                layoutGral.createSequentialGroup() //.addGap(160,260,36)                   
                    .addGroup(layoutGral.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(botonera, h3, h3, h3)
                        .addGroup(layoutGral.createSequentialGroup()
                            .addComponent(this.unDibujo, h1, h1, h1)
                            .addComponent(consola, h2, h2, h2)
                        )
                    )                    
                );
        layoutGral.setVerticalGroup(
                layoutGral.createSequentialGroup() //.addGap(160,260,36)
                .addComponent(botonera, v1, v1, v1)
                .addGroup(layoutGral.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(this.unDibujo, v2, v2, v2)
                    .addComponent(consola, v2, v2, v2)
                 )
                );

    }

    private void crearBotonera (JPanel botonera) {
        GridLayout layout = new GridLayout(2, 8);
        layout.setHgap(2);
        layout.setVgap(2);

        botonera.setLayout(layout);

        for (Integer i=0; i< this.botonesUpDown.length; i++){
            this.botonesUpDown[i] = new JButton("Offline - nodo " + i);
            this.botonesUpDown[i].setToolTipText("Cambiar estado del nodo " + i);
            botonera.add(this.botonesUpDown[i]);
            this.botonesUpDown[i].addActionListener(this);
        }        

        for (Integer i=0; i< this.botonesCoordinador.length; i++){
            this.botonesCoordinador[i] = new JButton("AYA Coordinador ");
            this.botonesCoordinador[i].setToolTipText("Enviar AYA al coordinador desde el nodo " + i);
            botonera.add(this.botonesCoordinador[i]);
            this.botonesCoordinador[i].addActionListener(this);
        }
        
    }

    public void actionPerformed(ActionEvent evt) {
        for (Integer i=0; i< this.botonesUpDown.length; i++){
            if( evt.getSource().equals( this.botonesUpDown[i] ) ){
                System.out.println( "Se ha pulsado el botón de Offline nodo " + i );
                this.appendLog("Se ha pulsado el botón de Offline nodo " + i);
                
                if(this.tokenInstance.statusNodo(i)){
                    this.tokenInstance.bajarNodo(i);
                    this.appendLog("El nodo " + i + " está ahora offline");
                } else {
                    this.tokenInstance.subirNodo(i);
                    this.appendLog("El nodo " + i + " está ahora online");
                }

                //this.unDibujo.paint();
                this.redibujar(this.tokenInstance);
            }
        }

        for (Integer i=0; i< this.botonesCoordinador.length; i++){
            if( evt.getSource().equals( this.botonesCoordinador[i] ) ){
                System.out.println( "El nodo " + i + " está AYA al coordinador");
                this.appendLog("El nodo " + i + " está AYA al coordinador");
                this.unDibujo.limpiar();
                this.appSide.agregarLog("AYA de " + i + " al coordinador");
            }
        }
    }

    public void appendLog(String st){
        this.logs.append(st + newline);
    }

    public void redibujar(Instance tokenInstance) {
        this.unDibujo.redibujar(tokenInstance);
    }

}

/*
 * SemaforoView.java
 */

package semaforo;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.TaskMonitor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.GroupLayout;
import javax.swing.Timer;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

/**
 * The application's main frame.
 */
public class SemaforoView extends FrameView implements ActionListener {
    private JPanel panelSegundo;
    private VistaSemaforoCarga[] semView;
    private JTextField[] valoresVarSemaforos;
    private JButton buttonEmpezar;
    private JButton buttonGuardar;
    private JPanel panelTercero;
    private VistaSemaforoSimulacion[] semViewSimul;
    private JTextField[] valoresVarSemaforosSim;
    private JButton buttonStartStop;
    private JButton buttonLiberarZonaCritica;
    private JButton buttonTerminar;
    private JTextArea logs;
    private JTextArea resultado;
    private final static String newline = "\n";
    private Timer timer;
    private JTextField intevalo;
    private JTextField timeout;



    public SemaforoView(SingleFrameApplication app) {
        super(app);

        initComponents();

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
            JFrame mainFrame = SemaforoApp.getApplication().getMainFrame();
            aboutBox = new SemaforoAboutBox(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        SemaforoApp.getApplication().show(aboutBox);
    }

    private JPanel botoneraDeAccion() {
        JPanel panelRigthUp = new JPanel();
        Integer cantSem = this.semInstance.getCantSemaforos();
        panelRigthUp.setBorder(new TitledBorder("Valores de las variables"));
        //this.panelSegundo.setSize(anchoPanel+20, altoProceso+20);

        GridLayout layout = new GridLayout(cantSem, 2);
        layout.setHgap(10);
        layout.setVgap(10);

        panelRigthUp.setLayout(layout);
        this.valoresVarSemaforosSim = new JTextField[cantSem];

        for (Integer i=0; i<cantSem; i++){
            this.valoresVarSemaforosSim[i] = new JTextField(2);
            this.valoresVarSemaforosSim[i].setText(this.semInstance.getValorSemaforo(i).toString());
            this.valoresVarSemaforosSim[i].setHorizontalAlignment(JTextField.CENTER);
            this.valoresVarSemaforosSim[i].setEditable(false);
            panelRigthUp.add(new JLabel("   X" + i + ":"));
            panelRigthUp.add(this.valoresVarSemaforosSim[i]);
        }

        //panelRigthUp.setSize(10, 10);

        JPanel panelRigthDown = new JPanel();

        //panelRigthDown.setBorder();
        //this.panelSegundo.setSize(anchoPanel+20, altoProceso+20);

        GridLayout layout2 = new GridLayout(4, 1);
        layout2.setHgap(10);
        layout2.setVgap(10);

        this.buttonStartStop = new JButton("Play");
        this.buttonLiberarZonaCritica = new JButton("Liberar zona crítica");
        this.buttonTerminar = new JButton("Terminar");

        this.buttonStartStop.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonStartStopClicked(evt);
            }
        });

        JPanel intervaloPanel = new JPanel(new GridLayout(2,2));
        intervaloPanel.add(new JLabel("Intervalo: "));
        this.intevalo = new JTextField("20");
        this.intevalo.setColumns(3);
        intervaloPanel.add(this.intevalo);
        
        intervaloPanel.add(new JLabel("Timeout en zona crítica: "));
        this.timeout = new JTextField("3");
        this.timeout.setColumns(3);
        intervaloPanel.add(this.timeout);


        panelRigthDown.setLayout(layout2);

        panelRigthDown.add(intervaloPanel);
        panelRigthDown.add(this.buttonStartStop);
        panelRigthDown.add(this.buttonLiberarZonaCritica);        
        panelRigthDown.add(this.buttonTerminar);

        this.buttonTerminar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonTerminarClicked(evt);
            }
        });

        this.buttonLiberarZonaCritica.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                liberarZonaCritica();
            }
        });

        JPanel panelRigthAll = new JPanel();

        GridLayout layout3 = new GridLayout(2, 1);
        layout3.setHgap(10);
        layout3.setVgap(10);

        panelRigthAll.setLayout(layout3);

        panelRigthAll.add(panelRigthUp);
        panelRigthAll.add(panelRigthDown);

        return panelRigthAll;
    }

    private JPanel botoneraDeCarga(Integer cantSem) {
        JPanel panelRigthUp = new JPanel();

        panelRigthUp.setBorder(new TitledBorder("Valores de las variables"));
        //this.panelSegundo.setSize(anchoPanel+20, altoProceso+20);

        GridLayout layout = new GridLayout(cantSem, 2);
        layout.setHgap(10);
        layout.setVgap(10);

        panelRigthUp.setLayout(layout);
        this.valoresVarSemaforos = new JTextField[cantSem];

        for (Integer i=0; i<cantSem; i++){
            this.valoresVarSemaforos[i] = new JTextField(2);
            this.valoresVarSemaforos[i].setText(this.semInstance.getValorSemaforo(i).toString());
            this.valoresVarSemaforos[i].setHorizontalAlignment(JTextField.CENTER);
            panelRigthUp.add(new JLabel("   X" + i + ":"));
            panelRigthUp.add(this.valoresVarSemaforos[i]);
        }

        //panelRigthUp.setSize(10, 10);

        JPanel panelRigthDown = new JPanel();

        //panelRigthDown.setBorder();
        //this.panelSegundo.setSize(anchoPanel+20, altoProceso+20);

        GridLayout layout2 = new GridLayout(2, 1);
        layout2.setHgap(10);
        layout2.setVgap(10);

        this.buttonEmpezar = new JButton("Empezar");
        this.buttonGuardar = new JButton("Guardar");

        this.buttonEmpezar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonEmpezarClicked(evt);
            }
        });

        this.buttonGuardar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonGuardarClicked(evt);
            }
        });

        panelRigthDown.setLayout(layout2);
        
        panelRigthDown.add(this.buttonGuardar);
        panelRigthDown.add(this.buttonEmpezar);
        

        JPanel panelRigthAll = new JPanel();

        GridLayout layout3 = new GridLayout(2, 1);
        layout3.setHgap(10);
        layout3.setVgap(10);

        panelRigthAll.setLayout(layout3);

        panelRigthAll.add(panelRigthUp);
        panelRigthAll.add(panelRigthDown);

        return panelRigthAll;
    }

    @SuppressWarnings("empty-statement")
    private void cargarPanelSegundo(Boolean serializado) {
        Integer anchoProceso = 50;
        Integer altoProceso = 550;
        Integer cantidadProcesos;
        Integer cantidadSemaforos;
        if(!serializado){
            cantidadProcesos = new Integer (String.valueOf(this.cantProc.getSelectedItem()));
            cantidadSemaforos = new Integer (String.valueOf(this.cantSemaforos.getSelectedItem()));
        } else {
            cantidadProcesos = this.semInstance.getCantTiposProcesos();
            cantidadSemaforos = this.semInstance.getCantSemaforos();
        }

        this.panelSegundo = new JPanel();
        this.panelSegundo.setBorder(new TitledBorder("Configuración de semáforos."));
        Integer anchoPanel = anchoProceso * cantidadProcesos;
        this.panelSegundo.setSize(anchoPanel+20, altoProceso+20);

        GridLayout layout = new GridLayout(1, cantidadProcesos+1);
        layout.setHgap(10);
        layout.setVgap(10);

        this.panelSegundo.setLayout(layout);
        this.semView = new VistaSemaforoCarga[cantidadProcesos];

        for (Integer i=0; i<cantidadProcesos; i++){
            this.semView[i] = new VistaSemaforoCarga(i, cantidadSemaforos, altoProceso, 
                    anchoProceso, this.semInstance);
            this.panelSegundo.add(this.semView[i]);
            this.semView[i].botonAbajo.addActionListener(this);
            this.semView[i].botonArriba.addActionListener(this);
        }


        JPanel panelSegundoRight = this.botoneraDeCarga(cantidadSemaforos);
        this.panelSegundo.add(panelSegundoRight);
    }

    private void cargarPanelTercero() {
        Integer anchoProceso = 50;
        Integer altoProceso = 550;
        Integer cantidadProcesos = this.semInstance.getCantTiposProcesos();

        GridLayout layoutSup = new GridLayout(1, cantidadProcesos);
        layoutSup.setHgap(10);
        layoutSup.setVgap(10);
        JPanel panelSup = new JPanel(layoutSup);
        this.semViewSimul = new VistaSemaforoSimulacion[cantidadProcesos];

        for (Integer i=0; i<cantidadProcesos; i++){
            this.semViewSimul[i] = new VistaSemaforoSimulacion(i, altoProceso, anchoProceso, this.semInstance);
            panelSup.add(this.semViewSimul[i]);
            //this.semView[i].botonAbajo.addActionListener(this);
            final Integer t = i;
            this.semViewSimul[i].botonAgregarProc.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    jButtonAgregarProcesoClicked(evt, t);
                }
            });
            
        }

        JPanel panelTerceroRight = this.botoneraDeAccion();

        JPanel consola = new JPanel();
        this.logs = new JTextArea();
        logs.setEditable(false);
        this.appendLog("Iniciando simulación");

        JScrollPane areaScrollPane = new JScrollPane(logs);
        areaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        consola.setLayout(new GridLayout(1, 1));
        consola.add(areaScrollPane);
        consola.setBorder(new TitledBorder("Logs"));

        JPanel resultadoPanel = new JPanel();
        resultadoPanel.setBorder(new TitledBorder("Secuencia resultado"));
        GridLayout layoutRes = new GridLayout(1,1);
        layoutRes.setHgap(5);
        layoutRes.setVgap(5);
        resultadoPanel.setLayout(layoutRes);
        this.resultado = new JTextArea("");
        this.resultado.setBackground(this.mainPanel.getBackground());
        resultadoPanel.add(this.resultado);

        this.panelTercero = new JPanel();
        this.panelTercero.setBorder(new TitledBorder("Simulación."));
        GroupLayout layoutGral = new GroupLayout(this.panelTercero);
        
        //this.panelTercero.setSize(anchoPanel+20, altoProceso+80);
        this.panelTercero.setLayout(layoutGral);

        layoutGral.setHorizontalGroup(
                layoutGral.createSequentialGroup() //.addGap(160,260,36)
                .addGroup(layoutGral.createParallelGroup()
                    .addComponent(panelSup)
                    .addComponent(consola)
                    )
                .addGroup(layoutGral.createParallelGroup()
                    .addComponent(panelTerceroRight)
                    .addComponent(resultadoPanel)
                    )
                );
        layoutGral.setVerticalGroup(
                layoutGral.createSequentialGroup()
                    .addGroup(layoutGral.createParallelGroup()
                        .addComponent(panelSup)
                        .addComponent(panelTerceroRight)
                    )
                    .addGroup(layoutGral.createParallelGroup()
                        .addComponent(consola, 200, 200, 200)
                        .addComponent(resultadoPanel, 200, 200, 200)//, alto, alto, alto) //.addGap(180, Short.MAX_VALUE))
                    )
                );

    }

    private void actualizarInfoSimulacion(){
        for(Integer i=0; i<this.semInstance.getCantSemaforos(); i++){
            Integer valor = new Integer(this.valoresVarSemaforos[i].getText());
            this.semInstance.setValorSemaforo(i, valor);
        }

        for(Integer i=0; i< this.semViewSimul.length; i++){
            this.semViewSimul[i].redibujarSemaforo();
        }
    }

    private void actualizarInstanciaVarSemaforos() {
        for(Integer i=0; i<this.semInstance.getCantSemaforos(); i++){
            Integer valor = new Integer(this.valoresVarSemaforos[i].getText());
            this.semInstance.setValorSemaforo(i, valor);
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        panelInicial = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        cantProc = new javax.swing.JComboBox();
        cantSemaforos = new javax.swing.JComboBox();
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

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(semaforo.SemaforoApp.class).getContext().getResourceMap(SemaforoView.class);
        panelInicial.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("panelInicial.border.title"))); // NOI18N
        panelInicial.setName("panelInicial"); // NOI18N

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        jButton1.setText(resourceMap.getString("jButton1.text")); // NOI18N
        jButton1.setName("jButton1"); // NOI18N
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });

        jButton2.setText(resourceMap.getString("jButton2.text")); // NOI18N
        jButton2.setName("jButton2"); // NOI18N
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });

        cantProc.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5" }));
        cantProc.setName("cantProc"); // NOI18N

        cantSemaforos.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));
        cantSemaforos.setName("cantSemaforos"); // NOI18N

        javax.swing.GroupLayout panelInicialLayout = new javax.swing.GroupLayout(panelInicial);
        panelInicial.setLayout(panelInicialLayout);
        panelInicialLayout.setHorizontalGroup(
            panelInicialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInicialLayout.createSequentialGroup()
                .addGroup(panelInicialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelInicialLayout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(panelInicialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelInicialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cantSemaforos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cantProc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panelInicialLayout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addComponent(jButton1)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2)))
                .addContainerGap(49, Short.MAX_VALUE))
        );
        panelInicialLayout.setVerticalGroup(
            panelInicialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInicialLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(panelInicialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cantProc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelInicialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cantSemaforos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                .addGroup(panelInicialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addGap(50, 50, 50))
        );

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(panelInicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(202, Short.MAX_VALUE))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelInicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(96, Short.MAX_VALUE))
        );

        menuBar.setName("menuBar"); // NOI18N

        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(semaforo.SemaforoApp.class).getContext().getActionMap(SemaforoView.class, this);
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
            .addComponent(statusPanelSeparator, javax.swing.GroupLayout.DEFAULT_SIZE, 589, Short.MAX_VALUE)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(statusMessageLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 407, Short.MAX_VALUE)
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

    private void jButtonAgregarProcesoClicked(MouseEvent evt, Integer i) {
        //throw new UnsupportedOperationException("Not yet implemented");
        System.out.println("Se cliqueo el proceso " +  i);
        this.appendLog("Creado proceso de tipo "+ (char) (65+i));
        this.semInstance.crearProceso(i, this);
        this.semViewSimul[i].redibujarSemaforo();
    }

    private void jButtonGuardarClicked(java.awt.event.MouseEvent evt) {
        this.actualizarInstanciaVarSemaforos();
        System.out.println( "Se ha pulsado el boton de guardar" );
        String archivo = null;
        try {
            archivo = this.seleccionarArchivo();
            System.out.println("Leido para Guardar " + archivo);
            this.serializar(archivo);
        } catch (IOException io) {
            System.out.println(io.getMessage());
        }        
    }

    private void jButtonEmpezarClicked(MouseEvent evt) {
        this.actualizarInstanciaVarSemaforos();
        this.semInstance.borrarTodosLosProcesos();
        
        this.cargarPanelTercero();
        this.panelSegundo.setVisible(false);
        this.panelTercero.setVisible(true);
        this.mainPanel.removeAll();
        this.mainPanel.setLayout(new GridLayout(1,1));
        this.mainPanel.add(this.panelTercero);

        this.timer = new Timer(3000, this);
        this.timer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                timerOcurrio();
            }
        });

        this.timer.setInitialDelay(0);
        this.timer.stop();
    }

    private void jButtonStartStopClicked(MouseEvent evt) {
        System.out.println( "Se ha pulsado el boton de play" );
        Integer delay = 2000; Integer timeoutZC = null;
        try {
            delay = Integer.parseInt(this.intevalo.getText()) * 100;
        } catch (NumberFormatException ex) {
             delay = 500;
        }

        try {
            timeoutZC = Integer.parseInt(this.timeout.getText());
            if(timeoutZC<=0)
                timeoutZC = null;
        } catch (NumberFormatException ex) {
             timeoutZC = null;
        }

        timer.setDelay(delay);
        if(timer.isRunning()){
            timer.stop();
            this.buttonStartStop.setText("Play");
        } else {
            timer.start();
            this.buttonStartStop.setText("Stop");

            this.appendLog("Velocidad de cada turno: " + delay/1000 + " segundos.");

            if(timeoutZC == null)
                this.appendLog("Modo de desalojo de los procesos de la zona crítica: MANUAL.");
            else
                this.appendLog("Modo de desalojo de los procesos de la zona crítica: AUTOMÁTICO (" + timeoutZC +" turnos).");

        }

        this.semInstance.setTimeoutDeLaZonaCritica(timeoutZC);
        System.out.println("timeout : " + timeoutZC);
    }

    private void jButtonTerminarClicked(MouseEvent evt) {
        this.semInstance.borrarTodosLosProcesos();

        this.panelSegundo.setVisible(true);
        this.panelTercero.setVisible(false);
        this.mainPanel.removeAll();
        this.mainPanel.setLayout(new GridLayout(1,1));
        this.mainPanel.add(this.panelSegundo);
        this.timer.stop();
    }

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        Integer cantidadProcesos = new Integer (String.valueOf(this.cantProc.getSelectedItem()));
        Integer cantidadSemaforos = new Integer (String.valueOf(this.cantSemaforos.getSelectedItem()));

        this.panelInicial.setVisible(false);

        this.semInstance = new Instancia(cantidadProcesos, cantidadSemaforos);
        this.cargarPanelSegundo(false);
        this.panelSegundo.setVisible(true);
        this.mainPanel.removeAll();
        this.mainPanel.setLayout(new GridLayout(1,1));
        this.mainPanel.add(this.panelSegundo);
    }//GEN-LAST:event_jButton1MouseClicked

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
        System.out.println( "Se ha pulsado el boton de cargar" );
        String archivo = null;
        try {
            archivo = this.seleccionarArchivo();
            System.out.println("Leido2 " + archivo); 
            this.desserializar(archivo);
        } catch (IOException io) {
            System.out.println(io.getMessage());
        }       
    }//GEN-LAST:event_jButton2MouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox cantProc;
    private javax.swing.JComboBox cantSemaforos;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JPanel panelInicial;
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

    private JDialog aboutBox;
    private Instancia semInstance;
    private int cuak = 3;

    public void actionPerformed(ActionEvent evt) {
        for (Integer i=0; i< this.semView.length; i++){
            //Semaforos de arriba
            if( evt.getSource().equals( this.semView[i].botonArriba ) ){
                System.out.println( "Se ha pulsado el botón de agregar arriba del proc " + i );
                System.out.println( "\t\tAgregar tipo " + this.semView[i].comboLet.getSelectedIndex()
                            + " el semaforo " + this.semView[i].comboNum.getSelectedIndex() );
                //this.appendLog("Se ha pulsado el botón de Offline nodo " + i)
                Semaforo nuevoSem = null;
                if (this.semView[i].comboLet.getSelectedIndex() == 0)
                    nuevoSem = Semaforo.crearP(this.semView[i].comboNum.getSelectedIndex(), (char) (65+i));
                else
                    nuevoSem = Semaforo.crearV(this.semView[i].comboNum.getSelectedIndex(), (char) (65+i));

                this.semInstance.agregarSemaforoSuperior(nuevoSem, i);
                this.semView[i].redibujarSemaforo();
            }

            //Semaforos de abajo
            if( evt.getSource().equals( this.semView[i].botonAbajo ) ){
                System.out.println( "Se ha pulsado el botón de agregar abajo del proc " + i );
                //this.appendLog("Se ha pulsado el botón de Offline nodo " + i)
                System.out.println( "\t\tAgregar tipo " + this.semView[i].comboLet.getSelectedIndex()
                            + " el semaforo " + this.semView[i].comboNum.getSelectedIndex() );
                
                Semaforo nuevoSem = null;
                if (this.semView[i].comboLet.getSelectedIndex() == 0)
                    nuevoSem = Semaforo.crearP(this.semView[i].comboNum.getSelectedIndex(), (char) (65+i));
                else
                    nuevoSem = Semaforo.crearV(this.semView[i].comboNum.getSelectedIndex(), (char) (65+i));

                this.semInstance.agregarSemaforoInferior(nuevoSem, i);
                this.semView[i].redibujarSemaforo();
            }

            
        }
    }

    public void appendLog(String st){
        this.logs.append(st + newline);
        /*if(cuak%2 == 0){
            this.logs.setForeground(Color.RED);
            this.logs.setFont(this.logs.getFont());
            this.logs.setForeground(Color.blue);
            this.logs.append("0 " +st + newline);
        } else {
            this.logs.setForeground(Color.BLACK);
            this.logs.append("1 " + st + newline);
        }
        cuak++;*/
    }


    private void timerOcurrio() {
        //this.appendLog(new Date().toString());
        this.semInstance.nextStep(this);

        for (VistaSemaforoSimulacion semaforoDibujo : semViewSimul) {
            semaforoDibujo.redibujarSemaforo();
        }
        
        for (Integer i=0; i<this.valoresVarSemaforosSim.length; i++){
            this.valoresVarSemaforosSim[i].setText(this.semInstance.getValorSemaforo(i).toString());
        }

        String resultadoSt = "";
        Integer i =0;
        for(Character c :this.semInstance.getResultado()){
            resultadoSt += c + " ";
            i++;
            if(i.equals(8)){i=0; resultadoSt += "\n";}
        }

        this.resultado.setText(resultadoSt);

    }

    private void liberarZonaCritica() {
        this.appendLog("Liberando zona crítica");
        this.semInstance.liberarZonaCritica();

        for (VistaSemaforoSimulacion semaforoDibujo : semViewSimul) {
            semaforoDibujo.redibujarSemaforo();
        }
    }



    private String seleccionarArchivo() throws IOException {
        JFrame frame2 = new JFrame();
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        //int result = fileChooser.showOpenDialog(frame2);
        int result = fileChooser.showDialog(frame2, "Seleccionar");
        String archivo = "";

        if (result == JFileChooser.APPROVE_OPTION){
            File filename2 = fileChooser.getSelectedFile();
            System.out.println("Leido: " + filename2.getAbsolutePath());
            archivo = new String(filename2.toString());
        } else {
            throw new IOException("No file selected");
        }
        return archivo;
     }

    private void serializar(String archivo) {
        try {
            FileOutputStream f = new FileOutputStream(archivo);
            ObjectOutputStream s = new ObjectOutputStream(f);

            s.writeObject(this.semInstance);

            System.out.println("Grabado");

            s.flush();
        } catch (IOException io) {
            System.out.println(io.getMessage());
        }
    }

    private void desserializar(String archivo) {
        try {
        FileInputStream f = new FileInputStream(archivo);
        ObjectInputStream s = new ObjectInputStream(f);
        
        try {
            this.semInstance = (Instancia) s.readObject();
            this.panelInicial.setVisible(false);

            this.cargarPanelSegundo(true);
            this.panelSegundo.setVisible(true);
            this.mainPanel.removeAll();
            this.mainPanel.setLayout(new GridLayout(1,1));
            this.mainPanel.add(this.panelSegundo);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SemaforoView.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("\tCargado");

        } catch (IOException io) {
            System.out.println(io.getMessage());
        }
//        } catch (ClassNotFoundException cl) {
//            System.out.println(cl.getMessage());
//        }
    }


    public Instancia getInstancia(){
        return this.semInstance;
    }
}

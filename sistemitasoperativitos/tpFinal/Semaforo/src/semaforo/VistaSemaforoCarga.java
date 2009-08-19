/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package semaforo;

import java.awt.Graphics;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

/**
 *
 * @author mlopez
 */
class VistaSemaforoCarga extends JPanel {
    private Integer cantidadSemaforos;
    private Integer nroSem;
    private Graphics miGr;
    public JComboBox comboNum;
    public JComboBox comboLet;
    private DibujoSemaforo elDibujo;
    public JButton botonArriba;
    public JButton botonAbajo;

    public VistaSemaforoCarga(Integer nroSem, Integer cantidadSemaforos, Integer altoProceso, Integer anchoProceso) {
        this.elDibujo = new DibujoSemaforo(altoProceso*6/8, anchoProceso/4, (char) (65+nroSem));

        this.setSize(altoProceso, anchoProceso);
        
        char letra = (char) (65+nroSem);
        this.setBorder(new TitledBorder("Semáforos " + letra));

        this.comboNum = new JComboBox();
        for(Integer i=0; i < cantidadSemaforos; i++)
            this.comboNum.addItem("X" + i);

        this.comboLet = new JComboBox();
        this.comboLet.addItem("P");
        this.comboLet.addItem("V");
        this.botonArriba = new JButton ("Agregar Arriba");
        this.botonAbajo = new JButton ("Agregar Abajo");

        GroupLayout layoutGral = new GroupLayout(this);
        this.setLayout(layoutGral);
        //this.setSize(new Dimension(h3, v1+v2));
        layoutGral.setAutoCreateGaps(true);
        layoutGral.setAutoCreateContainerGaps(true);
        layoutGral.setHorizontalGroup(
                layoutGral.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(this.elDibujo)
                        //.addGroup(layoutGral.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layoutGral.createSequentialGroup() //.addGap(160,260,36))
                            .addComponent(this.comboLet, 60, 60, 60)
                            .addComponent(this.comboNum, 60, 60, 60)
                        )
                        .addComponent(this.botonArriba, 120, 120, 120)
                        .addComponent(this.botonAbajo, 120, 120, 120)
                        
                );
        layoutGral.setVerticalGroup(
                layoutGral.createSequentialGroup() //.addGap(160,260,36)
                    .addGroup(layoutGral.createParallelGroup(GroupLayout.Alignment.LEADING)                        
                        .addComponent(this.comboLet, 20, 20, 20)
                        .addComponent(this.comboNum, 20, 20, 20)
                    )
                    .addComponent(this.botonArriba, 20, 20, 20)
                    .addComponent(this.botonAbajo, 20, 20, 20)
                    .addComponent(this.elDibujo)
                );       

    }


}

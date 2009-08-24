/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package semaforo;

import java.awt.Graphics;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

/**
 *
 * @author mlopez
 */
class VistaSemaforoSimulacion extends JPanel {
    private Integer nroSem;
    private Graphics miGr;
    private DibujoSemaforo elDibujo;
    public JButton botonAgregarProc;
    private Character letra;


    public VistaSemaforoSimulacion(Integer nroSem, Integer altoProceso, Integer anchoProceso, Instancia inst) {
        this.elDibujo = new DibujoSemaforo(altoProceso*6/8, anchoProceso/4, (char) (65+nroSem), inst.getColumna(nroSem));

        this.setSize(altoProceso, anchoProceso);

        this.letra = (char) (65+nroSem);
        this.setBorder(new TitledBorder("Semáforos " + letra + " - Sim"));

        this.botonAgregarProc = new JButton ("Agregar proceso tipo " + letra);

        GroupLayout layoutGral = new GroupLayout(this);
        this.setLayout(layoutGral);
        //this.setSize(new Dimension(h3, v1+v2));
        layoutGral.setAutoCreateGaps(true);
        layoutGral.setAutoCreateContainerGaps(true);
        layoutGral.setHorizontalGroup(
                layoutGral.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(this.elDibujo)
                        //.addGroup(layoutGral.createParallelGroup(GroupLayout.Alignment.LEADING)
                        //.addGroup(layoutGral.createSequentialGroup() //.addGap(160,260,36))
                        //    .addComponent(this.comboLet, 60, 60, 60)
                        //    .addComponent(this.comboNum, 60, 60, 60)
                        //)
                        .addComponent(this.botonAgregarProc, 180, 180, 180)
                );
        layoutGral.setVerticalGroup(
                layoutGral.createSequentialGroup() //.addGap(160,260,36)
                    //.addGroup(layoutGral.createParallelGroup(GroupLayout.Alignment.LEADING)
                    //    .addComponent(this.comboLet, 20, 20, 20)
                    //    .addComponent(this.comboNum, 20, 20, 20)
                    //)
                    .addComponent(this.botonAgregarProc, 20, 20, 20)
                    //.addComponent(this.botonAbajo, 20, 20, 20)
                    .addComponent(this.elDibujo)
                );

    }

    public void redibujarSemaforo(){
        this.elDibujo.repintar();
    }


}
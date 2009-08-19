/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package semaforo;

import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

/**
 *
 * @author mlopez
 */
class DibujoSemaforo extends JPanel {
    private Graphics miGr;
    private Integer alto;
    private Integer ancho;
    private Character letra;

    public DibujoSemaforo(Integer alto, Integer ancho, Character letra) {
        this.alto = alto;
        this.ancho = ancho;
        this.letra = letra;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.miGr = g;
        Graphics2D g2 = (Graphics2D)g;
        this.paint(g2);
        //st = "holaaa";
        //g2.drawString(st, 0, 35);
    }

    public void paint(Graphics2D g2){
        this.pintarLinea(g2);
        g2.drawString(letra.toString(), ancho/4, alto*1/32);
    }

    public void pintarLinea(Graphics2D g2){
        g2.drawLine(ancho/4, alto/16, ancho/4 , alto);
    }

    public void dibujarSemaforos(){

    }


}

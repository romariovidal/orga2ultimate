/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package semaforo;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Date;
import java.util.List;
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
    private Columna columna;

    public DibujoSemaforo(Integer alto, Integer ancho, Character letra, Columna c) {
        this.alto = alto;
        this.ancho = ancho;
        this.letra = letra;
        this.columna = c;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.miGr = g;
        Graphics2D g2 = (Graphics2D)g;
        this.paint(g2);
        this.dibujarSemaforosSup(g2);
        //st = "holaaa";
        //g2.drawString(st, 0, 35);
    }

    public void paint(Graphics2D g2){
        this.pintarLinea(g2);
        g2.drawString(letra.toString(), ancho/4, alto*1/32);
        g2.drawString(new Date()+" ", ancho/2, alto*9/32);
    }

    public void pintarLinea(Graphics2D g2){
        g2.drawLine(ancho/4, alto/16, ancho/4 , alto);
    }

    public void dibujarSemaforosSup(Graphics2D g2) {
        Integer posX = ancho*1/4;
        Integer posY = alto*1/32;
               
        Integer fontSize = g2.getFont().getSize();
        
        for (Integer i=0; i< this.columna.cantSemaforosSup(); i++) {
            g2.drawString(this.columna.mostrarSemaforoSup(i), posX, posY);
            posY += fontSize*6/5;
            System.out.println("Printing: "+this.columna.mostrarSemaforoSup(i));
        }
                        /*List<String> l = this.semInstance.listaDeSemaforosSuperiores(i);
                for(Integer j=0; j< l.size(); j++){
                    System.out.println(l.get(j));
                }*/
    }

    public void repintar(){
        //this.paintComponent(miGr);
        this.dibujarSemaforosSup((Graphics2D) miGr);
    }
}

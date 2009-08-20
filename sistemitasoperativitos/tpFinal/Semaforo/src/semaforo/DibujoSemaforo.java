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
    private List<String> listaStSup;
    private List<String> listaStInf;

    public DibujoSemaforo(Integer alto, Integer ancho, Character letra, List<String> lSup, List<String> lInf) {
        this.alto = alto;
        this.ancho = ancho;
        this.letra = letra;
        this.listaStSup = lSup;
        this.listaStInf = lInf;
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
        List<String> s = this.listaStSup;
        
        Integer fontSize = g2.getFont().getSize();
        System.out.println("Superior " + this.listaStSup.size());
        System.out.println("Inferios " + this.listaStInf.size());
        for (Integer i=0; i< s.size(); i++) {
            g2.drawString(s.get(i), posX, posY);
            posY += fontSize*6/5;
            System.out.println(s.get(i));
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

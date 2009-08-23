/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package semaforo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
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
        this.actualizarMedidas();
    }

    private void actualizarMedidas(){
        this.alto = this.getHeight();
        this.ancho = this.getWidth();
    }

    @Override
    public void paintComponent(Graphics g) {
        this.actualizarMedidas();
        super.paintComponent(g);
        this.miGr = g;
        Graphics2D g2 = (Graphics2D)g;
        this.pintarLetra(g2);
        this.dibujarSemaforos(g2);
        //st = "holaaa";
        //g2.drawString(st, 0, 35);
    }

    private void pintarLetra(Graphics2D g2){
        this.pintarLinea(g2);
        Integer fontSize = g2.getFont().getSize();
        g2.drawString(letra.toString(), ancho/8, fontSize*5/6);
        //g2.drawString(new Date()+" ", ancho/2, alto*9/32);
    }

    private void pintarLinea(Graphics2D g2){
        g2.drawLine(ancho/8, alto/16, ancho/8 , alto);
    }

    private void dibujarSemaforos(Graphics2D g2) {
        Integer fontSize = g2.getFont().getSize();
        Integer posX = ancho*1/4;
        Integer posY = alto*1/16 + fontSize;

        List<Integer> inicial = this.columna.getColaInicial();
        String esperando = "";
        for (Integer i=0; i< inicial.size(); i++) {
            if(esperando!= "")
                esperando += ", ";

            esperando += this.letra +"" + inicial.get(i);
        }
        g2.drawString(esperando, posX, posY);
        posY += fontSize*6/5;

        for (Integer i=0; i< this.columna.cantSemaforosSup(); i++) {
            g2.drawString(this.columna.mostrarSemaforoSup(i), posX, posY);
            posY += fontSize*6/5;
            System.out.println("Printing: "+this.columna.mostrarSemaforoSup(i));
        }

        posY = this.getHeight();

        List<Integer> terminados = this.columna.getProcesosTerminados();
        String terminadosSt = "";
        for (Integer i=0; i< terminados.size(); i++) {
            if(terminadosSt!= "")
                esperando += ", ";

            terminadosSt += this.letra +"" + terminados.get(i);
        }
        g2.drawString(terminadosSt, posX, posY);
        posY -= fontSize*6/5;


        Integer cant =this.columna.cantSemaforosInf()-1;
        for (Integer i= cant; i>=0 ; i--) {
            g2.drawString(this.columna.mostrarSemaforoInf(i), posX, posY);
            posY -= fontSize*6/5;
            System.out.println("Printing: "+this.columna.mostrarSemaforoInf(i));
        }

    }

    public void repintar(){
        this.actualizarMedidas();
        this.clearPanel();
        //this.paintComponent(miGr);
        Graphics g = this.getGraphics();
//        Graphics2D g2 = (Graphics2D)g;
//        this.dibujarSemaforos(g2);
//        this.pintarLinea(g2);
        this.paintComponent(g);
    }

    private void clearPanel() {
        Graphics g = this.getGraphics();
		g.setColor(this.getBackground());
		//g.fillRect(0, 0, p.getBounds().wd, p.getBounds().hd);
        g.fillRect(10, 10, ancho-10, alto-10);
        //this.repaint();
        g.setColor(Color.BLACK);
    }
}

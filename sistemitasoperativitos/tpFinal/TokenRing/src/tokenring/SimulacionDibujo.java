/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tokenring;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.geom.Ellipse2D;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

/**
 *
 * @author mlopez
 */
class SimulacionDibujo extends JPanel {

  int res = 90;//store screen resolution here
  int wd;//store screen wd here
  int hd;//store screen hd here
  int lado = 45;
  int margen = 25;
  private Graphics miGr;
  private String st;
  private Point[] posiciones;

  private Color caidoCol = new Color(0,191,255, 150);
  private Color activoCol = new Color(0,191,255);
  private Color coordCaidoCol = new Color(85,107,47);
  private Color coordActivoCol = new Color(60,179,113);

    public SimulacionDibujo() {
        this.setSize(600, 550);
        this.setBorder(new TitledBorder("Simulación"));
        res = Toolkit.getDefaultToolkit().
                         getScreenResolution();
        wd = this.getSize().width;
        hd = this.getSize().height;

        System.out.println(res + " pixels per inch");
        System.out.println(wd + " pixels wide");
        System.out.println(hd + " pixels high");


        this.posiciones = new Point[8];
        int w = wd -2*margen;
        int h = hd -2*margen;
        int m2 = margen+5;
        
        this.posiciones[0] = new Point(w/2, 0+m2);
        this.posiciones[1] = new Point(w*8/10, h*2/10);
        this.posiciones[2] = new Point(w-m2, h/2);
        this.posiciones[3] = new Point(w*8/10, h*8/10);
        this.posiciones[4] = new Point(w/2, h-m2);
        this.posiciones[5] = new Point(w*2/10, h*8/10);
        this.posiciones[6] = new Point(0+m2, h/2);
        this.posiciones[7] = new Point(w*2/10, h*2/10);

        for(Integer i=0; i<8 ; i++){
            this.posiciones[i].x+=margen -lado/2;
            this.posiciones[i].y+=margen -lado/2;
        }
    }

    //@Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.miGr = g;
        Graphics2D g2 = (Graphics2D)g;
        this.paint(g2);
        //st = "holaaa";
        //g2.drawString(st, 0, 35);
    }

    /*public void paint(){
//        this.paint(this.getGraphics());
        //this.limpiar();
        Graphics g = this.getGraphics();
        Graphics2D g2 = (Graphics2D)g;
        
        this.paint(g2);
    }*/

    public void paint(Graphics2D g2){
        g2.setColor(Color.BLACK);
        
        for(Integer i=0; i<8; i++){
            this.pintarLinea(g2, i, Color.BLACK);
        }

        for(Integer i=0; i<8; i++){
            this.pintarNodo(g2, i, 1);
        }
        System.out.println("¿Dibujé?");

    }

    public void pintarLinea(Graphics2D g2, Integer i, Color col){
        Point[] p = this.posiciones;
        Color anteriorCol = g2.getColor().equals(null)?Color.BLACK:g2.getColor();
        
        g2.setColor(col);
        g2.drawLine(p[i].x+lado/2, p[i].y+lado/2, p[(i+1)%8].x+lado/2, p[(i+1)%8].y+lado/2);

        g2.setColor(anteriorCol);
    }

     /*0 caido, 1 activo, 2 cordinador y caido, 3 coordinador y activo*/
    public void pintarNodo(Graphics2D g2, Integer i, Integer estado){
        Point[] p = this.posiciones;
        Color anteriorCol = g2.getColor().equals(null)?Color.BLACK:g2.getColor();
        Font anteriorFont = g2.getFont();
        Color col = Color.BLACK;
        switch (estado){
            case 0:
                col = caidoCol;
                break;
            case 1:
                col = activoCol;
                break;
            case 2:
                col = coordCaidoCol;
                break;
            case 3:
                col = coordActivoCol;
                break;
        }
        
        Rectangle r2 = new Rectangle(lado,lado);
        r2.setLocation(p[i]);
        g2.setColor(Color.RED);
        g2.fill(r2);
        g2.draw(r2);

        Ellipse2D d2 = new Ellipse2D.Double(p[i].x, p[i].y, lado, lado);
        g2.setColor(col);
        g2.fill(d2);
        g2.draw(d2);

        g2.setColor(Color.BLACK);
        g2.setFont(new Font(g2.getFont().getFontName(), Font.BOLD , 16));
        Integer x= p[i].x+lado*2/5-1;
        Integer y= p[i].y+lado*3/5;
        g2.drawString(i.toString(), x, y);

        g2.setColor(anteriorCol);
        g2.setFont(anteriorFont);
    }

    public void limpiar(){
        clearPanel(this);
    }

    void redibujar(Instance tokenInstance) {
        //this.limpiar();
        Graphics g = this.getGraphics();
        Graphics2D g2 = (Graphics2D)g;

        for(Integer i=0; i<8; i++){
            this.pintarLinea(g2, i, Color.BLACK);
        }

        Integer status;
        for(Integer i=0; i<tokenInstance.getNodos().length; i++){
            System.out.println("Mirando el " + i);
            status = 0;
            if(tokenInstance.getCoordinador().equals(i))
                status+=2;

            if(tokenInstance.statusNodo(i))
                status+=1;

            this.pintarNodo(g2, i, status);
            
        }
        System.out.println("¿Dibujé?");

    }

    private void clearPanel(SimulacionDibujo p) {
        Graphics g = p.getGraphics();
		g.setColor(p.getBackground());
		//g.fillRect(0, 0, p.getBounds().wd, p.getBounds().hd);
        g.fillRect(margen, margen, p.getBounds().width-2*margen, p.getBounds().height-2*margen);
        //this.repaint();
    }

    
}

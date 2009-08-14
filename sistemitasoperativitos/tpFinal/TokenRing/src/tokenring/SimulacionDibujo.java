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

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.miGr = g;
        Graphics2D g2 = (Graphics2D)g;
        // Use g2 for all following operations
        //g2.drawLine(...);
        st = "holaaa";
        g2.drawString(st, 0, 35);
        //g2.draw(new Rectangle2D.Double(
         //      res*0.5,res*0.5,res*1.0,res*1.0));


    }

    public void paint(){
        Graphics g = this.getGraphics();
        Graphics2D g2 = (Graphics2D)g;
        //super.dr
        Point[] p = this.posiciones;
        g2.setColor(Color.BLACK);
        for(Integer i=0; i<8; i++){
            g2.drawLine(p[i].x+lado/2, p[i].y+lado/2, p[(i+1)%8].x+lado/2, p[(i+1)%8].y+lado/2);
        }


        for(Integer i=0; i<8; i++){
            Ellipse2D d2 = new Ellipse2D.Double(posiciones[i].x, posiciones[i].y, lado, lado);

            Rectangle r2 = new Rectangle(lado,lado);
            r2.setLocation(posiciones[i]);
            g2.setColor(Color.RED);
            g2.fill(r2);
        
            g2.draw(r2);

            g2.setColor(Color.GREEN);
            g2.fill(d2);

            g2.draw(d2);
        }        
        System.out.println("¿Dibujé?");

        g2.setColor(Color.BLACK);
        //System.out.println(g2.getFont().getSize());
        g2.setFont(new Font(g2.getFont().getFontName(), Font.BOLD , 16));

        for(Integer i=0; i<8; i++){
            Integer x= p[i].x+lado*2/5-1;
            Integer y= p[i].y+lado*3/5;
            g2.drawString(i.toString(), x, y);
        }

    }

    public void limpiar(){
        clearPanel(this);
    }

    private void clearPanel(SimulacionDibujo p) {
        /*Graphics g = p.getGraphics();
		g.setColor(p.getBackground());
		g.fillRect(0, 0, p.getBounds().wd, p.getBounds().hd);*/
        this.repaint();
    }

    
}

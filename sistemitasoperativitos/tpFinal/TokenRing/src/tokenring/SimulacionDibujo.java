/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tokenring;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.Toolkit;
import java.awt.geom.Ellipse2D;
import java.awt.image.ImageProducer;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
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
    int lado = 25;
    int margen = 30;
    private Graphics miGr;
    private String st;
    private Point[] posiciones;
    private Boolean areaLimpia = true;

    private Color caidoCol = new Color(0,191,255, 150);
    private Color activoCol = new Color(0,191,255);
    private Color coordCaidoCol = new Color(85,107,47);
    private Color coordActivoCol = new Color(60,179,113);
    private Instance tokenInstance;
    private Image[] arrImagen;


    public SimulacionDibujo(Instance inst) {
        
            this.setSize(600, 550);
            this.setBorder(new TitledBorder("Simulación"));
            this.tokenInstance = inst;
            res = Toolkit.getDefaultToolkit().getScreenResolution();
            wd = this.getSize().width;
            hd = this.getSize().height;
            System.out.println(res + " pixels per inch");
            System.out.println(wd + " pixels wide");
            System.out.println(hd + " pixels high");
            this.posiciones = new Point[8];
            int w = wd - 2 * margen;
            int h = hd - 2 * margen;
            int m2 = margen + 5;
            this.posiciones[0] = new Point(w / 2, 0 + m2);
            this.posiciones[1] = new Point(w * 8 / 10, h * 2 / 10);
            this.posiciones[2] = new Point(w - m2, h / 2);
            this.posiciones[3] = new Point(w * 8 / 10, h * 8 / 10);
            this.posiciones[4] = new Point(w / 2, h - m2);
            this.posiciones[5] = new Point(w * 2 / 10, h * 8 / 10);
            this.posiciones[6] = new Point(0 + m2, h / 2);
            this.posiciones[7] = new Point(w * 2 / 10, h * 2 / 10);
            for (Integer i = 0; i < 8; i++) {
                this.posiciones[i].x += margen;
                this.posiciones[i].y += margen;
            }
            
            arrImagen = new Image[4];
    
            URL imageURL[] = new URL[4];
            imageURL[0] = SimulacionDibujo.class.getResource("imagenes/serverOff.png");
            imageURL[1] = SimulacionDibujo.class.getResource("/tokenring/imagenes/serverOn.png");
            imageURL[2] = SimulacionDibujo.class.getResource("/tokenring/imagenes/serverOff-coord.png");
            imageURL[3] = SimulacionDibujo.class.getResource("/tokenring/imagenes/serverOn-coord.png");

        try {
            String path = new java.io.File(".").getCanonicalPath();
            System.out.println("PAth: " + path);
        } catch (IOException ex) {
            Logger.getLogger(SimulacionDibujo.class.getName()).log(Level.SEVERE, null, ex);
        }
            System.out.println("Path actual: " + System.getProperty("user.dir"));
    if (imageURL[0] != null && imageURL[1] != null && imageURL[2] != null && imageURL[3] != null) {
        try {
            URL url = SimulacionDibujo.class.getResource( "imagenes/serverOff.png" );
            Image blueball = Toolkit.getDefaultToolkit().createImage( ( ImageProducer ) url.getContent() );

            //arrImagen[0] = Toolkit.getDefaultToolkit().createImage("./imagenes/serverOff.png");
            arrImagen[0] = blueball; //ImageIO.read(new File(imageURL[0].getFile()));
            arrImagen[1] = Toolkit.getDefaultToolkit().createImage( ( ImageProducer ) imageURL[1].getContent() );
            arrImagen[2] = Toolkit.getDefaultToolkit().createImage( ( ImageProducer ) imageURL[2].getContent() );
            arrImagen[3] = Toolkit.getDefaultToolkit().createImage( ( ImageProducer ) imageURL[3].getContent() );
            //arrImagen[1] = ImageIO.read(new File(imageURL[1].getFile()));
            //arrImagen[2] = ImageIO.read(new File(imageURL[2].getFile()));
            //arrImagen[3] = ImageIO.read(new File(imageURL[3].getFile()));
        } catch (IOException ex) {
            System.out.println("AAAAAAAAAAAHHHHHHH");
            ex.printStackTrace();
        }
    } else {
         System.out.println("Sin imagenes");
         System.exit(1);
    }

    }

    //@Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.miGr = g;
        Graphics2D g2 = (Graphics2D)g;
        
        //g2.drawImage(img, 50, 50, this);
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
        //g2.setColor(Color.BLACK);
        
        //for(Integer i=0; i<8; i++){
        //    this.pintarLinea(g2, i, Color.BLACK);
        //}

        //for(Integer i=0; i<8; i++){
        //    this.pintarNodo(g2, i, 1);
        //}
        //System.out.println("¿Dibujé?");

        for(Integer i=0; i<8; i++){
            this.pintarLinea(g2, i, Color.BLACK);
        }

        Integer status;
        for(Integer i=0; i<tokenInstance.getNodos().length; i++){
            //System.out.println("Mirando el " + i);
            status = 0;
            if(tokenInstance.getCoordinador().equals(i))
                status+=2;

            if(tokenInstance.statusNodo(i))
                status+=1;

            this.pintarNodo(g2, i, status);
            
        }



    }

    public void pintarLinea(Graphics2D g2, Integer i, Color col){
        Point[] p = this.posiciones;
        Color anteriorCol = g2.getColor().equals(null)?Color.BLACK:g2.getColor();
        
        g2.setColor(col);
        g2.drawLine(p[i].x, p[i].y, p[(i+1)%8].x, p[(i+1)%8].y);

        g2.setColor(anteriorCol);
    }

    public void pintarMensaje(Graphics2D g2, Integer sender, Integer receiver, String mensaje){
        Point[] p = this.posiciones;
        g2.setColor(Color.RED);
        Integer xInic = p[sender].x;
        Integer yInic = p[sender].y;

        Integer xFinal = p[receiver].x;
        Integer yFinal = p[receiver].y;
        Stroke sInicial = g2.getStroke();
        Stroke mesg = new BasicStroke(5);
        g2.setStroke(mesg);
        g2.drawLine(xInic, yInic, xFinal, yFinal);
        g2.setStroke(sInicial);
        g2.setColor(Color.BLACK);

        Font anteriorFont = g2.getFont();
        Font nuevaFont = new Font(g2.getFont().getFontName(), Font.BOLD , 16);
        g2.setFont(nuevaFont);
        Integer alturaFuente = nuevaFont.getSize();
        FontMetrics fm = g2.getFontMetrics();
        Integer wordWidth = fm.stringWidth(mensaje);

        g2.setColor(Color.WHITE);
        g2.fillRect((xInic+xFinal)/2 - wordWidth/2 - 3,
                   (yInic+yFinal)/2-alturaFuente - 3,
                   wordWidth + 6,
                   alturaFuente + 6);
        g2.setColor(Color.BLACK);
        g2.drawRect((xInic+xFinal)/2 - wordWidth/2 - 3,
                   (yInic+yFinal)/2-alturaFuente - 3,
                   wordWidth + 6,
                   alturaFuente + 6);
        g2.drawString(mensaje, (xInic+xFinal)/2 - wordWidth/2, (yInic+yFinal)/2);
        g2.setFont(anteriorFont);

    }

     /*0 caido, 1 activo, 2 cordinador y caido, 3 coordinador y activo*/
    public void pintarNodo(Graphics2D g2, Integer i, Integer estado){
        Point[] p = this.posiciones;
        Color anteriorCol = g2.getColor().equals(null)?Color.BLACK:g2.getColor();
        Font anteriorFont = g2.getFont();
        Color col = Color.BLACK;
        /*switch (estado){
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
        g2.drawString(i.toString(), x, y);*/

        Integer x = p[i].x - arrImagen[estado].getWidth(this)/2;
        Integer y = p[i].y - arrImagen[estado].getHeight(this)/2;

        g2.drawImage(arrImagen[estado], x, y, this);


        x = x - lado/4;
        y = y - lado/4;
        Ellipse2D d2 = new Ellipse2D.Double(x, y, lado, lado);
        g2.setColor(Color.WHITE);
        g2.fill(d2);
        g2.draw(d2);

        x = x + 1*lado/3;
        y = y + 6*lado/8;

        g2.setColor(Color.BLACK);
        g2.setFont(new Font(g2.getFont().getFontName(), Font.BOLD , 16));
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

        for(Integer i=0; i<8 && areaLimpia; i++){
            this.pintarLinea(g2, i, Color.BLACK);
        }
        areaLimpia = false;

        if(tokenInstance.getHayMensajeDandoVuelta()){
            this.pintarMensaje(g2, tokenInstance.getSender(), tokenInstance.getReceiver(), tokenInstance.printLog());
            System.out.println("Hay mensaje");
        } else {
            System.out.println("No hay mensaje");
        }


        Integer status;
        for(Integer i=0; i<tokenInstance.getNodos().length; i++){
            //System.out.println("Mirando el " + i);
            status = 0;
            if(tokenInstance.getCoordinador().equals(i))
                status+=2;

            if(tokenInstance.statusNodo(i))
                status+=1;

            this.pintarNodo(g2, i, status);
            
        }
        //System.out.println("¿Dibujé?");

    }

    private void clearPanel(SimulacionDibujo p) {
        Graphics g = p.getGraphics();
		g.setColor(p.getBackground());
		//g.fillRect(0, 0, p.getBounds().wd, p.getBounds().hd);
        g.fillRect(margen, margen, p.getBounds().width-2*margen, p.getBounds().height-2*margen);
        //this.repaint();
        this.areaLimpia = true;
    }

    
}

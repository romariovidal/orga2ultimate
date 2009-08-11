package banquero2;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author mlopez
 */
public class Matriz {
    private Integer[][] matInterna;
    
    public Matriz(){
        this.matInterna = new Integer[9][9];
        for (Integer i=0; i<=8; i++){
            this.matInterna[0][i] = null;
            this.matInterna[i][0] = null;
        }
    }

    public void asignar (Integer fila, Integer columna, Integer valor){
        this.matInterna[fila][columna] = valor;
    }

    public Integer dameValor (Integer fila, Integer columna){
        return this.matInterna[fila][columna];
    }

    public Boolean filaEsMenorOIgual(Integer fila, Vector v){
        Boolean res = true;

        for(Integer i=1; i<=8; i++){
            res &= (this.dameValor(fila, i) <= v.dameValor(i));
        }

        return res;
    }

        public Boolean arregloMenorOIgualQue(Integer fila, Vector v){
        Boolean res = true;

        for(Integer i=1; i<=8; i++){
            res &= (v.dameValor(i) <= this.dameValor(fila, i));
        }

        return res;
    }
        public Vector dameFila(int i){
         Vector res=null;
         for(Integer j=1; j<=8; j++){
            res.asignar(i, this.dameValor(i, j));
        }
            return res;
    }

    void ponerCerosEnFila(Integer procesoActual) {
        for(Integer j=1; j<=8; j++){
            this.asignar(procesoActual,j,0);
        }
    }
    
}

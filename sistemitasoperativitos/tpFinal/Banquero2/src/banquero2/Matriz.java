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
    
    public Matriz(Integer proc, Integer rec){
        this.matInterna = new Integer[proc+1][rec+1];
        for (Integer i=0; i<=proc; i++){
            this.matInterna[i][0] = null;
        }
        for(Integer i=0; i<=rec; i++)
            this.matInterna[0][i] = null;
    }

    public void asignar (Integer fila, Integer columna, Integer valor){
        this.matInterna[fila][columna] = valor;
    }

    public Integer dameValor (Integer fila, Integer columna){
        //System.out.println("Buscando info de " + fila + " " + columna);
        return this.matInterna[fila][columna];
    }

    public Boolean filaEsMenorOIgual(Integer fila, Vector v){
        Boolean res = true;

        for(Integer i=1; i<v.tam(); i++){
            res &= (this.dameValor(fila, i) <= v.dameValor(i));
        }

        return res;
    }

        public Boolean arregloMenorOIgualQue(Integer fila, Vector v){
        Boolean res = true;

        for(Integer i=1; i<v.tam(); i++){
            res &= (v.dameValor(i) <= this.dameValor(fila, i));
        }

        return res;
    }

    public Vector dameFila(int i){
         Vector res= new Vector(this.tamRec());
         for(Integer j=1; j<=this.tamRec(); j++){
            res.asignar(j, this.dameValor(i, j));
        }
            return res;
    }

    void agregar(Integer fila, Vector v) {
        for(Integer j=1; j<=this.tamRec(); j++){
            this.asignar(fila, j, this.dameValor(fila, j)+v.dameValor(j));
        }
    }

    void ponerCerosEnFila(Integer procesoActual) {
        for(Integer j=1; j<=this.tamRec(); j++){
            this.asignar(procesoActual,j,0);
        }
    }

    void restarFila(Integer fila, Vector v) {
        for(Integer j=1; j<=this.tamRec(); j++){
            this.asignar(fila, j, this.dameValor(fila, j)-v.dameValor(j));
        }
    }

    public Integer tamProc(){
        return this.matInterna.length-1;
    }

    public Integer tamRec(){
        return this.matInterna[0].length-1;
    }
    
}

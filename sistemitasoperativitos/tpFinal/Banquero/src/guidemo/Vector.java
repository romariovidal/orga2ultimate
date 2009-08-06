/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package guidemo;

/**
 *
 * @author mlopez
 */
public class Vector {
    private Integer[] vectInterno;

    public Vector(){
        this.vectInterno = new Integer[9];
        for (Integer i=0; i<=9; i++){
            this.vectInterno[0] = null;
        }
    }

    public void asignar (Integer pos, Integer valor){
        this.vectInterno[pos] = valor;
    }

    public Integer dameValor (Integer pos){
        return this.vectInterno[pos];
    }

    public void agregar (Vector v){
        for(Integer i=1; i<=8; i++){
            this.asignar(i, this.dameValor(i) + v.dameValor(i));
            //this.vectInterno[i] += v.dameValor(i);
        }
    }
}

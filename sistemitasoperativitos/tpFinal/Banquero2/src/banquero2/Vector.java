/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package banquero2;

/**
 *
 * @author mlopez
 */
public class Vector {
    private Integer[] vectInterno;

    public Vector(Integer cant){
        this.vectInterno = new Integer[cant+1];
        for (Integer i=0; i<=cant; i++){
            this.vectInterno[i] = null;
        }
    }

    public Vector(Integer cant, Integer b){
        this.vectInterno = new Integer[cant+1];
        for (Integer i=0; i<=cant; i++){
            this.vectInterno[i] = b;
        }
    }

    public void asignar (Integer pos, Integer valor){
        this.vectInterno[pos] = valor;
    }

    public Integer dameValor (Integer pos){
        return this.vectInterno[pos];
    }

    public void agregar (Vector v){
        for(Integer i=1; i< v.tam(); i++){
            this.asignar(i, this.dameValor(i) + v.dameValor(i));
            //this.vectInterno[i] += v.dameValor(i);
        }
    }

    public boolean mayorOIgual(Vector vector){
    boolean res=true;
    for(Integer i=1; i< vector.tam(); i++){
            res = res & this.dameValor(i) <= vector.dameValor(i);
        }
    return res;
    }

    void quitar(Vector v) {
        for(Integer i=1; i< v.tam(); i++){
            this.asignar(i, this.dameValor(i) - v.dameValor(i));
        }
    }

    public Integer tam(){
        return this.vectInterno.length;
    }
}

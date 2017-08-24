package br.com.immunize.navigationdrawer.NAVI.Objects;

import android.text.format.DateFormat;

/**
 * Created by Marla on 10/11/2016.
 */
public class Peso {
//id, data, peso

    public long id;
    public String data;
    public String peso;

    public Peso(){}

    public Peso(long id, String data, String peso){
        this.id = id;
        this.data = data;
        this.peso = peso;
    }
    public long getId (){
        return id;
    }
    public void setId(long id){
        this.id = id;
    }
    public String getData(){
        return data;
    }
    public void setData(String data){
        this.data = data;
    }
    public String getPeso(){
        return peso;
    }
    public void setPeso(String peso){
        this.peso = peso;
    }
}

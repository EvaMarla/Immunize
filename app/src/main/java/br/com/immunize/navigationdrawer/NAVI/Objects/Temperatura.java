package br.com.immunize.navigationdrawer.NAVI.Objects;

import android.text.format.DateFormat;

/**
 * Created by Marla on 14/12/2016.
 */
public class Temperatura {
    //id, data, peso

    private long id;
    private String data;
    private float temperatura;

    public Temperatura(){}

    public Temperatura(long id, String data, float temperatura){
        this.id = id;
        this.data = data;
        this.temperatura = temperatura;
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
    public float getTemperatura(){
        return temperatura;
    }
    public void setTemperatura(float temperatura){
        this.temperatura = temperatura;
    }
}

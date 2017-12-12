package br.com.immunize.navigationdrawer.NAVI.Objects;

import android.text.format.DateFormat;

/**
 * Created by Marla on 14/12/2016.
 */
public class Temperatura {
    //id, data, peso

    private long id;
    private String data;
    private String temperatura;

    public Temperatura(){}

    public Temperatura(long id, String data, float temperatura){
        this.id = id;
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
    public String getTemperatura(){
        return temperatura;
    }
    public void setTemperatura(String temperatura){
        this.temperatura = temperatura;
    }
}

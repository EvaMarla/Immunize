package br.com.immunize.navigationdrawer.NAVI.Objects;

import android.text.format.DateFormat;

/**
 * Created by Marla on 14/12/2016.
 */
public class Remedios {

    //id, data, remedio

    private long id;
    private String data;
    private String remedio;

    public Remedios(){}

    public Remedios(long id, String data, String remedio){
        this.id = id;
        this.data = data;
        this.remedio = remedio;
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
    public String getRemedio(){
        return remedio;
    }
    public void setRemedio(String remedio){
        this.remedio = remedio;
    }
}

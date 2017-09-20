package br.com.immunize.navigationdrawer.NAVI.Objects;

import java.util.Date;

/**
 * Created by Marla on 30/07/2016.
 */
public class Alimentacao {

    //public static long ID_ALIMENTAÇÃO = 0;

    private long idAlimento;
    private String periodo;
    public static String COMEU_ID = "comeu_id";
    private boolean comeu;
    private String data;


    public Alimentacao(){}

    public Alimentacao(String alimentos)
    {
        this.periodo = alimentos;
        //this.data = data;

    }

    public long getId()
    {
        return idAlimento;
    }

    public void setId(long id)
    {
        this.idAlimento = id;
    }

    public String getPeriodo()
    {
        return periodo;
    }

    public void setPeriodo(String periodo)
    {
        this.periodo = periodo;
    }

    public void setData(String data){
        this.data = data;
    }
    public String getData(){
        return data;
    }
}

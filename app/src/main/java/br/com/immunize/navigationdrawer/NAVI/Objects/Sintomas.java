package br.com.immunize.navigationdrawer.NAVI.Objects;

/**
 * Created by Marla on 30/07/2016.
 */
public class Sintomas {

    private long idSintoma;
    private String periodo;
    public static String SINTOMA_ID = "sintoma_id";
    private String data;

    public Sintomas(){}

    public Sintomas( String sintomas)
    {
        this.periodo = sintomas;

    }

    public long getId()
    {
        return idSintoma;
    }

    public void setId(long id)
    {
        this.idSintoma = id;
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

package br.com.immunize.navigationdrawer.NAVI.Objects;

/**
 * Created by Marla on 19/08/2016.
 */
public class Agenda {

    private long idAgenda;
    private String periodo;

    public Agenda( String agenda)
    {
     this.periodo = agenda;
    }

    public long getId()
    {
        return idAgenda;
    }
    public void setId(long id)
    {
        this.idAgenda = id;
    }
    public String getPeriodo()
    {
        return periodo;
    }
    public void setPeriodo(String periodo)
    {
        this.periodo = periodo;
    }
}

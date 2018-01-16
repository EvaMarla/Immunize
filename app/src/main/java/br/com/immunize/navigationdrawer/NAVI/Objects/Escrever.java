package br.com.immunize.navigationdrawer.NAVI.Objects;

/**
 * Created by Marla on 14/12/2016.
 */
public class Escrever {
    //id, data, nota

    private long id;
    private String data;
    private String nota;

    public Escrever(){}

    public Escrever(long id, String data, String nota){
        this.id = id;
        this.data = data;
        this.nota = nota;
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
    public String getNota(){
        return nota+ "\n";
    }
    public void setNota(String nota){
        this.nota = nota;
    }
}

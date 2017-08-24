package br.com.immunize.navigationdrawer.NAVI.Objects;

/**
 * Created by Marla on 17/08/2016.
 */
public class Cartao {

    public static long ID_AO_NASCER = 0;
    public static long ID_DOIS_MESES = 2;
    public static long ID_TRES_MESES = 3;
    public static long ID_QUATRO_MESES = 4;
    public static long ID_CINCO_MESES = 5;
    public static long ID_SEIS_MESES = 6;
    public static long ID_NOVE_MESES = 9;
    public static long ID_DOZE_MESES = 12;
    public static long ID_QUINZE_MESES = 15;

    private long idCartao;

    // Período referente ao mês de vacinação
    private String periodo;

    public  Cartao(){
        //   this.idCartao = Cartao.ID_AO_NASCER;
        //   this.periodo="ao nascer";
    }

    public Cartao(long idcartao, String cartoes)
    {
        this.idCartao = idcartao;
        this.periodo = cartoes;

    }
    public long getId()
    {
        return idCartao;
    }
    public void setId(long id) {
        this.idCartao = id;
    }
    public String getPeriodo() {
        return periodo;
    }
    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }
}


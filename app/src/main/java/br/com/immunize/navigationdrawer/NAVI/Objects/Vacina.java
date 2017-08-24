package br.com.immunize.navigationdrawer.NAVI.Objects;

/**
 * Created by Marla on 19/08/2016.
 */
public class Vacina {

    public static long VACINA_ID_1  = 01;
    public static long VACINA_ID_2  = 02;
    public static long VACINA_ID_3  = 03;
    public static long VACINA_ID_4  = 04;
    public static long VACINA_ID_5  = 05;
    public static long VACINA_ID_6  = 06;
    public static long VACINA_ID_7  = 07;
    public static long VACINA_ID_8  = 8;
    public static long VACINA_ID_9  = 9;
    public static long VACINA_ID_10 = 10;
    public static long VACINA_ID_11 = 11;
    public static long VACINA_ID_12 = 12;
    public static long VACINA_ID_13 = 13;
    public static long VACINA_ID_14 = 14;
    public static long VACINA_ID_15 = 15;
    public static long VACINA_ID_16 = 16;
    public static long VACINA_ID_17 = 17;
    public static long VACINA_ID_18 = 18;
    public static long VACINA_ID_19 = 19;
    public static long VACINA_ID_20 = 20;
    public static long VACINA_ID_21 = 21;
    public static long VACINA_ID_22 = 22;


    public static String VACINA_FOI_TOMADA_ID = "vacina_foi_tomada_id";

    private long idCartao;
    private long idVacinas;
    private String nomevacina;
    private String legendavacina;
    private boolean foiTomada;

    public Vacina (){

    }

    public Vacina(long idCartao, long IdVacinas, String NomeVacina, String legendavacina)
    {
        this.idCartao = idCartao;
        this.idVacinas = IdVacinas;
        this.nomevacina = NomeVacina;
        this.legendavacina = legendavacina;
    }
    public long getId()
    {
        return idVacinas;
    }
    public void setId(long id) {
        this.idVacinas = id;
    }
    public String getNomevacina() {
        return nomevacina;
    }
    public void setNomevacina(String NomeVacina) {
        this.nomevacina = nomevacina;
    }
    public long getIdCartao() {
        return idCartao;
    }
    public void setIdCartao(long idCartao) {
        this.idCartao = idCartao;
    }

    public boolean isFoiTomada() {

        return foiTomada;
    }
    public void setFoiTomada(boolean foiTomada) {
        this.foiTomada = foiTomada;
    }
    public String getLegendaVacina() {
        return legendavacina;
    }
    public void setLegendavacina(String LegendaVacina) {
        this.legendavacina = legendavacina;
    }
}

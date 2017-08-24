package br.com.immunize.navigationdrawer.NAVI.Objects;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.Calendar;

/**
 * Created by Marla on 17/10/2016.
 */
public class Crianca {
    public static long CRIANCA_ID = 0;
    public static String CRIANCA_ID_KEY = "0";
    public static String CRIANCA_NOME_KEY = "1";
    public static String CRIANCA_SEXO_KEY = "2";
    public static String CRIANCA_NASC_DIA_KEY = "3";
    public static String CRIANCA_NASC_MES_KEY = "4";
    public static String CRIANCA_NASC_ANO_KEY = "5";

    private long id;
    private String nome;
    private Calendar bornDate;
    private char sexo;

    public Crianca(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Calendar getBornDate() {
        return bornDate;
    }

    public void setBornDate(Calendar bornDate) {
        this.bornDate = bornDate;
    }

    public char getSexo() {
        return sexo;
    }

    public void setSexo(char sexo) {
        this.sexo = sexo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean persisteCrianca(Context ctx){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(CRIANCA_ID_KEY, String.valueOf(this.id));
        editor.putString(CRIANCA_NOME_KEY, nome);
        editor.putString(CRIANCA_SEXO_KEY, String.valueOf(sexo));
        editor.putInt(CRIANCA_NASC_ANO_KEY, bornDate.get(Calendar.YEAR));
        editor.putInt(CRIANCA_NASC_MES_KEY, bornDate.get(Calendar.MONTH));
        editor.putInt(CRIANCA_NASC_DIA_KEY, bornDate.get(Calendar.DAY_OF_MONTH));

        boolean isCommited = editor.commit();
        return isCommited;
        //   boolean vacinaStatus = prefs.getBoolean(vacina.getId() + Vacina.VACINA_FOI_TOMADA_ID, false);
        //return vacinaStatus;
    }
}

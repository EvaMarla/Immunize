package br.com.immunize.navigationdrawer.NAVI.Banco;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Debug;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import br.com.immunize.navigationdrawer.NAVI.Objects.Alimentacao;
import br.com.immunize.navigationdrawer.NAVI.Objects.Cartao;
import br.com.immunize.navigationdrawer.NAVI.Objects.Escrever;
import br.com.immunize.navigationdrawer.NAVI.Objects.Peso;
import br.com.immunize.navigationdrawer.NAVI.Objects.Remedios;
import br.com.immunize.navigationdrawer.NAVI.Objects.Sintomas;
import br.com.immunize.navigationdrawer.NAVI.Objects.Temperatura;
import br.com.immunize.navigationdrawer.NAVI.Objects.Vacina;

/**
 * Created by Marla on 07/09/2016.
 */
public class BD {
    private static BDCore bd;

    private static int CARTAO_ID = 0;
    private static int CARTAO_PERIODO = 1;
    private static int VACINA_ID = 1;
    private static int VACINA_NOME = 2;
    private static int VACIN_LEGENDA = 3;

    public BD(Context context){
       bd = new BDCore(context);
    }

    public void inserirAlimento(Alimentacao alimento){
        SQLiteDatabase db = bd.getWritableDatabase();

        ContentValues valoresAlimento = new ContentValues();
        valoresAlimento.put("nome", alimento.getPeriodoComAlimento());
        valoresAlimento.put("data", alimento.getData());
        db.insert("alimentacao", null, valoresAlimento);
        db.close();
    }

    public void inserirSintoma(Sintomas sintoma){
        SQLiteDatabase db = bd.getWritableDatabase();

        ContentValues valoresSintomas = new ContentValues();
        valoresSintomas.put("nome", sintoma.getPeriodoComSintoma());
        valoresSintomas.put("data", sintoma.getData());
        db.insert("sintoma", null, valoresSintomas);
        db.close();
    }

    public void inserirPeso(Peso peso){
        SQLiteDatabase db = bd.getWritableDatabase();

        ContentValues valoresPeso = new ContentValues();
        valoresPeso.put("nome", peso.getPeso());
        valoresPeso.put("data", peso.getData());
        db.insert("pesos", null, valoresPeso);
        db.close();
    }

    public void inserirTemperatura(Temperatura temperatura){
        SQLiteDatabase db = bd.getWritableDatabase();

        ContentValues valoresTemperatura = new ContentValues();
        valoresTemperatura.put("nome", temperatura.getTemperatura());
        valoresTemperatura.put("data", temperatura.getData());
        db.insert("temperaturas", null, valoresTemperatura);
        db.close();
    }

    public void inserirEscrever(Escrever escrever){
        SQLiteDatabase db = bd.getWritableDatabase();

        ContentValues valoresEscrever = new ContentValues();
        valoresEscrever.put("nome", escrever.getNota());
        valoresEscrever.put("data", escrever.getData());
        db.insert("escrever", null, valoresEscrever);
        db.close();
    }

    public void inserirRemedio(Remedios remedios){
        SQLiteDatabase db = bd.getWritableDatabase();

        ContentValues valoresRemedio = new ContentValues();
        valoresRemedio.put("nome", remedios.getRemedio());
        valoresRemedio.put("data", remedios.getData());
        db.insert("remedios", null, valoresRemedio);
        db.close();
    }

    public void atualizar(Cartao cartao, Vacina vacina){

        SQLiteDatabase db = bd.getWritableDatabase();

        ContentValues valoresCartao = new ContentValues();
        valoresCartao.put("nome", cartao.getPeriodo());

        ContentValues valoresVacina = new ContentValues();
        valoresVacina.put("idCartao", vacina.getNomevacina());
        valoresVacina.put("nome", vacina.getLegendaVacina());
        valoresVacina.put("descricao", vacina.getIdCartao());

        db.update("cartao", valoresCartao, "_id = ?", new String[]{""+cartao.getId()});

        db.update("vacina", valoresVacina, "_id = ?", new String[]{""+vacina.getId()});

        db.close();
    }


    public void deletarPorData (String data){

        SQLiteDatabase db = bd.getWritableDatabase();
        db.execSQL("DELETE FROM " + "remedios"+ " WHERE " + "data" + "= '" + data + "'");
        db.execSQL("DELETE FROM " + "escrever"+ " WHERE " + "data" + "= '" + data + "'");
        db.execSQL("DELETE FROM " + "temperaturas"+ " WHERE " + "data" + "= '" + data + "'");
        db.execSQL("DELETE FROM " + "pesos"+ " WHERE " + "data" + "= '" + data + "'");
        db.execSQL("DELETE FROM " + "sintoma"+ " WHERE " + "data" + "= '" + data + "'");
        db.execSQL("DELETE FROM " + "alimentacao"+ " WHERE " + "data" + "= '" + data + "'");
        db.close();
    }

    public static List<Cartao> getCartoes(Context ctx){

        SQLiteDatabase db = bd.getWritableDatabase();
        List<Cartao> list = new ArrayList<Cartao>();
        String[] colunas = new String[]{"_id", "nome"};


        Cursor cursor = db.query("cartao", colunas, null, null, null, null, null);

        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            do{
                Cartao c = new Cartao();
                c.setId(cursor.getLong(CARTAO_ID));
                c.setPeriodo(cursor.getString(CARTAO_PERIODO));
                list.add(c);
            }while (cursor.moveToNext());
        }
        db.close();
        return (list);
    }

    public static List<Vacina> getVacinas(long idCartao, Context ctx){
        SQLiteDatabase db = bd.getWritableDatabase();

        List<Vacina> listVacina = new ArrayList<Vacina>();
      //  List<Vacina> vacinasList = Utils.vacinasList(ctx);
        String[] colunas = new String[]{"idCartao", "_id", "nome", "descricao"};

        Cursor cursor = db.query("vacina", colunas, null, null, null, null, null);

        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            do{
                Vacina v = new Vacina();
                v.setIdCartao(cursor.getLong(CARTAO_ID));
                v.setId(cursor.getLong(VACINA_ID));
                v.setNomevacina(cursor.getString(VACINA_NOME));
                v.setLegendavacina(cursor.getString(VACIN_LEGENDA));
                listVacina.add(v);
               /* for (int i = 0; i < vacinasList.size(); i++) {
                    if (idCartao == vacinasList.get(i).getIdCartao()) {
                        listVacina.add(vacinasList.get(i));
                    }
                } */
            }while (cursor.moveToNext());
        }
        return (listVacina);
    }
}

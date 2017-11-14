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
import br.com.immunize.navigationdrawer.NAVI.Objects.Sintomas;
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

    public void inserir(Cartao cartao, Vacina vacina){
        SQLiteDatabase db = bd.getWritableDatabase();

        ContentValues valoresCartao = new ContentValues();
        valoresCartao.put("nome", cartao.getPeriodo());

        ContentValues valoresVacina = new ContentValues();
        valoresVacina.put("nome", vacina.getNomevacina());
        valoresVacina.put("descricao", vacina.getLegendaVacina());
        valoresVacina.put("idCartao", vacina.getIdCartao());


        db.insert("cartao", null, valoresCartao);
        db.insert("vacina", null, valoresVacina);

        db.close();
    }

    public void inserirAlimento(Alimentacao alimento){
        SQLiteDatabase db = bd.getWritableDatabase();

        ContentValues valoresAlimento = new ContentValues();
        valoresAlimento.put("nome", alimento.getPeriodo());
        valoresAlimento.put("data", alimento.getData());
        db.insert("alimentacao", null, valoresAlimento);

        db.close();
    }

    /*
    public void inserirSintoma(Sintomas sintoma, int posicao){
        SQLiteDatabase db = bd.getWritableDatabase();

        ContentValues valoresSintomas = new ContentValues();
        valoresSintomas.put("nome", sintoma.getPeriodo());
        valoresSintomas.put("data", sintoma.getData());
        db.insert("sintoma", null, valoresSintomas, posicao);
        db.close();
    }

    */
    public void inserirSintoma(Sintomas sintoma){
        SQLiteDatabase db = bd.getWritableDatabase();

        ContentValues valoresSintomas = new ContentValues();
        valoresSintomas.put("nome", sintoma.getPeriodo());
        valoresSintomas.put("data", sintoma.getData());
        db.insert("sintoma", null, valoresSintomas);
        db.close();
    }

    public String getDataInfo(String table, CalendarView cv)
    {

        SQLiteDatabase db = bd.getWritableDatabase();
        String queryString ="SELECT data FROM " + table +")" +
                "WHERE data = "+ cv.toString();
        Cursor c = db.rawQuery(queryString, null);
        c.moveToNext();
        return c.getString(c.getColumnIndex("data"));
    }


    public void getAlimento(){

        SQLiteDatabase db = bd.getReadableDatabase();
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
    public void deletar(Cartao cartao, Vacina vacina){
        SQLiteDatabase db = bd.getWritableDatabase();
        db.delete("cartao", "_id = " + cartao.getId(), null);

        db.delete("vacina", "_id = " + vacina.getId(), null);

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

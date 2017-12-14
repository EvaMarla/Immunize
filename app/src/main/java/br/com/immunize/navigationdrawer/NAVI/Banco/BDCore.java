package br.com.immunize.navigationdrawer.NAVI.Banco;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Marla on 07/09/2016.
 */
public class BDCore extends SQLiteOpenHelper {

    private static final String NOME_BANCO = "immunize_db_v11";
    private static final int VERSAO_BANCO = 1;

    public BDCore(Context ctx){

        super(ctx, NOME_BANCO, null, VERSAO_BANCO);

    }

    @Override
    public void onCreate(SQLiteDatabase db){

        db.execSQL("CREATE TABLE cartao(_id INTEGER PRIMARY KEY, nome TEXT NOT NULL);");
        db.execSQL("create table vacina(idCartao LONG NOT NULL, _id INTEGER PRIMARY KEY AUTOINCREMENT, nome TEXT NOT NULL, descricao TEXT NOT NULL);");

        db.execSQL("CREATE TABLE alimentacao(_id INTEGER PRIMARY KEY AUTOINCREMENT, data TEXT NOT NULL, nome TEXT NOT NULL );");
        db.execSQL("CREATE TABLE sintoma(_id INTEGER PRIMARY KEY AUTOINCREMENT, data TEXT NOT NULL, nome TEXT NOT NULL );");
        db.execSQL("CREATE TABLE pesos(_id INTEGER PRIMARY KEY AUTOINCREMENT, data TEXT NOT NULL, nome TEXT NOT NULL)");
        db.execSQL("CREATE TABLE temperaturas(_id INTEGER PRIMARY KEY AUTOINCREMENT, data TEXT NOT NULL, nome TEXT NOT NULL)");
        db.execSQL("CREATE TABLE escrever(_id INTEGER PRIMARY KEY AUTOINCREMENT, data TEXT NOT NULL, nome TEXT NOT NULL)");
        db.execSQL("CREATE TABLE remedios(_id INTEGER PRIMARY KEY AUTOINCREMENT, data TEXT NOT NULL, nome TEXT NOT NULL)");

        Log.i("banco", db.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versaoAntiga, int novaVersao) {

        db.execSQL("drop table cartao;");
        onCreate(db);
    }

    public String getDataInfo(String table, String data)
    {
        String temp1= "";
        String ret = "";
        SQLiteDatabase db = this.getWritableDatabase();

        String queryString ="SELECT * FROM '" + table +"'" + " WHERE data = '"+ data + "'";

        Cursor c = db.rawQuery(queryString, null);
        while(c.moveToNext()) {
            temp1 = c.getString(c.getColumnIndex("data"));
            if (temp1.equals(data)) {
                ret += c.getString(c.getColumnIndex("nome"));
                ret += "\n";
            }
        }
        return ret;
    }
    /*public String getDataInfo(String table, String data)
    {
        String temp1= "";
        String ret = "Itens desse dia: \n";
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString ="SELECT * FROM '" + table +"'" + " WHERE data = '"+ data + "'";

        *//*
           QUERY PARA ACESSO EM DUAS TABELAS AO MESMO TEMPO

           String queryString ="SELECT * FROM '" + table +"'" + //REVER PQ ESSA DATA VEM ERRADA...
               " WHERE data = '"+ data + "' UNION SELECT * FROM 'sintoma'" + //REVER PQ ESSA DATA VEM ERRADA...
                   " WHERE data = '"+ data + "'";*//*


        //  String queryString ="SELECT * FROM '" + table +"'";

        Cursor c = db.rawQuery(queryString, null);
      *//*  int i=0;
        while(c.moveToNext()) {
            Log.i("Valor pos = " + i + "  Nome:", "" + c.getString(c.getColumnIndex("nome")));
            Log.i("Valor pos = " + i + "  Data:", "" + c.getString(c.getColumnIndex("data")));
            i++;
        }

        return c.getString(c.getColumnIndex("nome"));
*//*

        //  int i=0;
        while(c.moveToNext()) {
            temp1 = c.getString(c.getColumnIndex("data"));
            if(temp1.equals(data)) {
                ret += c.getString(c.getColumnIndex("nome"));
                ret += "\n";
            }
            //     Log.i("Valor pos = "+i+"  Nome:", "" + c.getString(c.getColumnIndex("nome")));
            //   Log.i("Valor pos = "+i+"  Data:", "" + c.getString(c.getColumnIndex("data")));
            //    i++;
        }

        return ret;
    }*/

}

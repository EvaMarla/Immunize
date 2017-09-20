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

    private static final String NOME_BANCO = "immunize_db";
    private static final int VERSAO_BANCO = 1;

    public BDCore(Context ctx){

        super(ctx, NOME_BANCO, null, VERSAO_BANCO);

    }

    @Override
    public void onCreate(SQLiteDatabase db){

        db.execSQL("CREATE TABLE cartao(_id INTEGER PRIMARY KEY, nome TEXT NOT NULL);");
        db.execSQL("create table vacina(idCartao LONG NOT NULL, _id INTEGER PRIMARY KEY AUTOINCREMENT, nome TEXT NOT NULL, descricao TEXT NOT NULL);");
        db.execSQL("CREATE TABLE alimentacao(_id INTEGER PRIMARY KEY AUTOINCREMENT, data TEXT NOT NULL, periodo TEXT NOT NULL );");
        db.execSQL("CREATE TABLE sintoma(_id INTEGER PRIMARY KEY AUTOINCREMENT, data TEXT NOT NULL, periodo TEXT NOT NULL );");



        //db.execSQL("SELECT * FROM CARTAO;");
        Log.i("banco", db.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versaoAntiga, int novaVersao) {

        db.execSQL("drop table cartao;");
        onCreate(db);
    }

    public String getDataInfo(String table, String data)
    {

        SQLiteDatabase db = this.getWritableDatabase();
        String queryString ="SELECT data FROM " + table +")" +
                "WHERE data = "+ data;
        Cursor c = db.rawQuery(queryString, null);
        c.moveToNext();
        return c.getString(c.getColumnIndex("data"));
    }

}

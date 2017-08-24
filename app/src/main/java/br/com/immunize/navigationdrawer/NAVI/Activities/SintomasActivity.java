package br.com.immunize.navigationdrawer.NAVI.Activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import br.com.immunize.navigationdrawer.NAVI.Adapter.SintomasAdapter;
import br.com.immunize.navigationdrawer.NAVI.Banco.BD;
import br.com.immunize.navigationdrawer.NAVI.Objects.Sintomas;
import br.com.immunize.navigationdrawer.NAVI.Utils.Utils;
import br.com.immunize.navigationdrawer.R;


public class SintomasActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    SintomasAdapter adapter;
    ArrayList<Sintomas> sintomas;
    BD myBD;
    String dateString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_sintomas);
        super.onCreate(savedInstanceState);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setBackgroundDrawable(getResources().getDrawable(R.drawable.sintomas_titulo));

        sintomas = Utils.sintomasList(this);
        ListView sintomasList = (ListView)findViewById(R.id.listaSintomas);
        sintomasList.setOnItemClickListener(this);
        SintomasAdapter adapter = new SintomasAdapter(this, sintomas);
        sintomasList.setAdapter(adapter);

        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("d/M/yyyy");
        dateString= sdf.format(date);

        myBD = new BD(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Sintomas sintoma = sintomas.get(position);
        sintoma.setData(dateString);
        myBD.inserirSintoma(sintoma);
    }

}

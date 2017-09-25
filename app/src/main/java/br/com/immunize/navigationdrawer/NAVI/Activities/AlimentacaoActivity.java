package br.com.immunize.navigationdrawer.NAVI.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import br.com.immunize.navigationdrawer.NAVI.Adapter.AlimentacaoAdapter;
import br.com.immunize.navigationdrawer.NAVI.Banco.BD;
import br.com.immunize.navigationdrawer.NAVI.Objects.Alimentacao;
import br.com.immunize.navigationdrawer.NAVI.Utils.Utils;
import br.com.immunize.navigationdrawer.R;

public class AlimentacaoActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    AlimentacaoAdapter adapter;
    ArrayList<Alimentacao> alimentos;
    BD myBD;
    String dateString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //super.onCreate(icicle);

      /*   String[] alimentos = new String[]{"Amamentação", "Frutas frescas", "Legumes", "Verduras", "Papa", "Sopa", "Mingau",
                "Guloseimas", "Iogurte", "Suco", "Chá", "Água"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, alimentos);
        setListAdapter(arrayAdapter); }

    protected void onListItemClick(ListView t, View v, int position, long id)
    {
        super.onListItemClick(t, v, position, id);
        Object o = this.getListAdapter().getItem(position);
        String item = o.toString();

    }*/

        setContentView(R.layout.activity_alimentacao);
        super.onCreate(savedInstanceState);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setBackgroundDrawable(getResources().getDrawable(R.drawable.alimentacao_titulo));

        alimentos = Utils.alimentosList(this);
        ListView alimentosList = (ListView)findViewById(R.id.listaAlimentacao);
        alimentosList.setOnItemClickListener(this);
        AlimentacaoAdapter adapter = new AlimentacaoAdapter(this, alimentos);
        alimentosList.setAdapter(adapter);

        long date = System.currentTimeMillis();
     //   SimpleDateFormat sdf = new SimpleDateFormat("d/M/yyyy"); //ALTERAR
      //  dateString= sdf.format(date); //ALTERAR
        Intent it = getIntent();
        dateString = it.getStringExtra("data");

        myBD = new BD(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Alimentacao alimento = alimentos.get(position);
        alimento.setData(dateString);
        myBD.inserirAlimento(alimento);
    }



}
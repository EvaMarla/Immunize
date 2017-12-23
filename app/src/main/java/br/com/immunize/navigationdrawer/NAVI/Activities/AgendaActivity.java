package br.com.immunize.navigationdrawer.NAVI.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import br.com.immunize.navigationdrawer.NAVI.Adapter.AgendaAdapter;
import br.com.immunize.navigationdrawer.NAVI.Objects.Agenda;
import br.com.immunize.navigationdrawer.NAVI.Objects.Alimentacao;
import br.com.immunize.navigationdrawer.NAVI.Utils.Utils;
import br.com.immunize.navigationdrawer.R;


public class AgendaActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    AgendaAdapter adapter;
    ArrayList<Agenda> agenda;

    String data;

    Intent it;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);

        Intent it = getIntent();
        data = it.getStringExtra("data");

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setBackgroundDrawable(getResources().getDrawable(R.drawable.action_bar_transparente));

        agenda = Utils.agendaList(this);
        ListView agendaList = (ListView)findViewById(R.id.ListaAgenda);
        agendaList.setOnItemClickListener(this);
        AgendaAdapter adapter = new AgendaAdapter(this, agenda);
        agendaList.setAdapter(adapter);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Agenda agendaa = agenda.get(position);

        /* if(agendaa != null){
            Toast.makeText(this, "Você selecionou: " + agendaa, Toast.LENGTH_SHORT).show();

        } */

        String item = agendaa.getPeriodo();
        switch (item) {
            case "Escrever":
                it = new Intent(getApplicationContext(), EscreverActivity.class);
                it.putExtra("data", data);
                startActivity(it);
                break;
            case "Alimentação":
                it = new Intent(getApplicationContext(), AlimentacaoActivity.class);
                it.putExtra("data", data);
                startActivity(it);
                break;
            case "Sintomas":
                it = new Intent(getApplicationContext(), SintomasActivity.class);
                it.putExtra("data", data);
                startActivity(it);
                break;
            case "Remédios":
                it = new Intent(getApplicationContext(), RemediosActivity.class);
                it.putExtra("data", data);
                startActivity(it);
                break;
            case "Peso":
                it = new Intent(getApplicationContext(), PesoActivity.class);
                it.putExtra("data", data);
                startActivity(it);
                break;
            case "Temperatura":
                it = new Intent(getApplicationContext(), TemperaturaActivity.class);
                it.putExtra("data", data);
                startActivity(it);
                break;
            default:
                Toast.makeText(this, "Você selecionou: " + item, Toast.LENGTH_SHORT).show();
                finish();
        }
    }

}

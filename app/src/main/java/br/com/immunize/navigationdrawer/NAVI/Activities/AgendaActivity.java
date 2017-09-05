package br.com.immunize.navigationdrawer.NAVI.Activities;

import android.content.Intent;
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
import br.com.immunize.navigationdrawer.NAVI.Utils.Utils;
import br.com.immunize.navigationdrawer.R;


public class AgendaActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    AgendaAdapter adapter;
    ArrayList<Agenda> agenda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_agenda);
        super.onCreate(savedInstanceState);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setBackgroundDrawable(getResources().getDrawable(R.drawable.caderninho_titulo));

        agenda = Utils.agendaList(this);
        ListView agendaList = (ListView)findViewById(R.id.ListaAgenda);
        agendaList.setOnItemClickListener(this);
        AgendaAdapter adapter = new AgendaAdapter(this, agenda);
        agendaList.setAdapter(adapter);

    }

   /*  protected void onListItemClick(ListView t, View v, int position, long id)
    {
        super.onListItemClick(t, v, position, id);
        Object o = this.getListAdapter().getItem(position);
        String item = o.toString();
        switch (item) {
            case "Escrever":
                startActivity(new Intent(this, EscreverActivity.class));
                break;
            case "Alimentação":
                startActivity(new Intent(this, AlimentacaoActivity.class));
                break;
            case "Sintomas":
                startActivity(new Intent(this, SintomasActivity.class));
                break;
            case "Remédios":
                startActivity(new Intent(this, RemediosActivity.class));
                break;
            case "Peso":
                startActivity(new Intent(this, PesoActivity.class));
                break;
            case "Temperatura":
                startActivity(new Intent(this, TemperaturaActivity.class));
                break;
            default:
                Toast.makeText(this, "Você selecionou: " + item, Toast.LENGTH_SHORT).show();
                finish();
        }
    } */

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Agenda agendaa = agenda.get(position);

        /* if(agendaa != null){
            Toast.makeText(this, "Você selecionou: " + agendaa, Toast.LENGTH_SHORT).show();

        } */

        String item = agendaa.getPeriodo();
        switch (item) {
            case "Escrever":
                startActivity(new Intent(this, EscreverActivity.class));
                break;
            case "Alimentação":
                startActivity(new Intent(this, AlimentacaoActivity.class));
                break;
            case "Sintomas":
                startActivity(new Intent(this, SintomasActivity.class));
                break;
            case "Remédios":
                startActivity(new Intent(this, RemediosActivity.class));
                break;
            case "Peso":
                startActivity(new Intent(this, PesoActivity.class));
                break;
            case "Temperatura":
                startActivity(new Intent(this, TemperaturaActivity.class));
                break;
            default:
                Toast.makeText(this, "Você selecionou: " + item, Toast.LENGTH_SHORT).show();
                finish();
        }
    }
}
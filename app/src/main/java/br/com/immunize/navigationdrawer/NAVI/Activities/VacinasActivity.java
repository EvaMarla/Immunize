package br.com.immunize.navigationdrawer.NAVI.Activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.List;

import br.com.immunize.navigationdrawer.NAVI.Adapter.VacinasAdapter;
import br.com.immunize.navigationdrawer.NAVI.Banco.BD;
import br.com.immunize.navigationdrawer.NAVI.Objects.Vacina;
import br.com.immunize.navigationdrawer.NAVI.Utils.Utils;
import br.com.immunize.navigationdrawer.R;

/**
 * Created by Marla on 19/08/2016.
 */
public class VacinasActivity extends AppCompatActivity {

   List<Vacina> vacinas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacina);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setBackgroundDrawable(getResources().getDrawable(R.drawable.cartao_de_vacina_vacinas_titulo));

        long idCartao = getIntent().getLongExtra(CartaoActivity.ID_CARTAO_SELECTED, -1);

        if (idCartao != -1) {

            String periodoCartao = Utils.getNomeCartaoById(idCartao, this);

            //TextView tituloAct = (TextView)findViewById(R.id.nomeMes);

                if (periodoCartao != null) {
                 //   tituloAct.setText(periodoCartao);
                   ab.setTitle(periodoCartao);
            }

           // vacinas = BD.getVacinas(idCartao, this);
            vacinas = Utils.getVacinasByIdCartao(idCartao, this);
            ListView vacinasList = (ListView) findViewById(R.id.lista2);
            VacinasAdapter adapter = new VacinasAdapter(this, vacinas);
            vacinasList.setAdapter(adapter);
        }
    }
}

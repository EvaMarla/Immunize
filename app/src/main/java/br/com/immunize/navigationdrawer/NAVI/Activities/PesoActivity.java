
package br.com.immunize.navigationdrawer.NAVI.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.SimpleDateFormat;

import br.com.immunize.navigationdrawer.NAVI.Banco.BD;
import br.com.immunize.navigationdrawer.NAVI.Banco.BDCore;
import br.com.immunize.navigationdrawer.NAVI.Objects.Peso;
import br.com.immunize.navigationdrawer.R;

public class PesoActivity extends AppCompatActivity {

    EditText txtPeso;
    BD banco;
    BDCore b;
    String dateString;
    String pesoBebe;
    EditText p;
    Peso peso;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peso);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setBackgroundDrawable(getResources().getDrawable(R.drawable.peso_titulo));

        peso = new Peso();

        final Button ok = (Button) findViewById(R.id.button);
        p = (EditText) findViewById(R.id.pesoText);

        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("d/M/yyyy");
        dateString = sdf.format(date);

    }


}

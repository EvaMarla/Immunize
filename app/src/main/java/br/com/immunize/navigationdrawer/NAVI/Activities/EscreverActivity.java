package br.com.immunize.navigationdrawer.NAVI.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import br.com.immunize.navigationdrawer.NAVI.Banco.BD;
import br.com.immunize.navigationdrawer.NAVI.Objects.Escrever;
import br.com.immunize.navigationdrawer.NAVI.Objects.Temperatura;
import br.com.immunize.navigationdrawer.R;


public class EscreverActivity extends AppCompatActivity {

    String dateString;
    BD myBD;
    Escrever escrever;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escrever_layout);

        final Button ok = (Button) findViewById(R.id.btnEscreverok);
        final EditText edtescrever = (EditText) findViewById(R.id.editText);

        long date = System.currentTimeMillis();
        final Intent it = getIntent();
        dateString = it.getStringExtra("data");

        myBD = new BD(this);

        ok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                escrever = new Escrever();
                escrever.setNota("Notas: " + edtescrever.getText().toString());
                escrever.setData(dateString);
                myBD.inserirEscrever(escrever);
                startActivity(new Intent(getApplicationContext(), CalendarioActivity.class));
            }
        });
    }

}

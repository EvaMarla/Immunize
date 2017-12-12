package br.com.immunize.navigationdrawer.NAVI.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import br.com.immunize.navigationdrawer.NAVI.Banco.BD;
import br.com.immunize.navigationdrawer.NAVI.Objects.Escrever;
import br.com.immunize.navigationdrawer.NAVI.Objects.Remedios;
import br.com.immunize.navigationdrawer.R;

public class RemediosActivity extends AppCompatActivity {

    String dateString;
    BD myBD;
    Remedios remedios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remedios);
        final Button ok = (Button) findViewById(R.id.btnRemediosOk);
        final EditText edtRemedio = (EditText) findViewById(R.id.edtTxt);

        long date = System.currentTimeMillis();
        final Intent it = getIntent();
        dateString = it.getStringExtra("data");

        myBD = new BD(this);

        ok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                remedios = new Remedios();
                remedios.setRemedio("Remédio: " + edtRemedio.getText().toString());
                remedios.setData(dateString);
                myBD.inserirRemedio(remedios);
                startActivity(new Intent(getApplicationContext(), CalendarioActivity.class));
            }
        });
    }
}

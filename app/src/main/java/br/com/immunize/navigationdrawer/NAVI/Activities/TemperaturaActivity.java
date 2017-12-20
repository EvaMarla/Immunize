package br.com.immunize.navigationdrawer.NAVI.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import java.text.SimpleDateFormat;

import br.com.immunize.navigationdrawer.NAVI.Banco.BD;
import br.com.immunize.navigationdrawer.NAVI.Objects.Peso;
import br.com.immunize.navigationdrawer.NAVI.Objects.Temperatura;
import br.com.immunize.navigationdrawer.R;


public class TemperaturaActivity extends AppCompatActivity {

    String dateString;
    String temperaturaBebe;
    EditText tempText;
    BD myBD;
    Temperatura temperatura;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperatura);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setBackgroundDrawable(getResources().getDrawable(R.drawable.action_bar_transparente));

        final Button ok = (Button) findViewById(R.id.button);
        tempText = (EditText) findViewById(R.id.tempText);
        tempText.setTypeface(setFonte(this));

        long date = System.currentTimeMillis();
        final Intent it = getIntent();
        dateString = it.getStringExtra("data");

        myBD = new BD(this);

        ok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                temperatura = new Temperatura();
                temperatura.setTemperatura("Temperatura: " + tempText.getText().toString() + "°C");
                temperatura.setData(dateString);
                myBD.inserirTemperatura(temperatura);
                startActivity(new Intent(getApplicationContext(), CalendarioActivity.class));
            }
        });
    }
    public static Typeface setFonte(Context context)
    {
        return Typeface.createFromAsset(context.getAssets(),"ARLRDBD.TTF");
    }
    }

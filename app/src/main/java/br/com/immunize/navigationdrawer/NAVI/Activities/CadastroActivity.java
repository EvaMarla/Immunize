package br.com.immunize.navigationdrawer.NAVI.Activities;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;
import android.support.v4.app.Fragment;

import java.util.Calendar;

import br.com.immunize.navigationdrawer.NAVI.NAVI.MainActivity;
import br.com.immunize.navigationdrawer.NAVI.Objects.Crianca;
import br.com.immunize.navigationdrawer.NAVI.Utils.Utils;
import br.com.immunize.navigationdrawer.R;

/**
 * Created by Marla on 17/10/2016.
 */
public class CadastroActivity extends AppCompatActivity {
    public EditText edtNomeCrianca;
    public DatePicker dtNascimento;
    public RadioButton rdSexoF;
    public RadioButton rdSexoM;
    Crianca crianca;
    ImageView imgBV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_crianca);

        edtNomeCrianca = (EditText)findViewById(R.id.edtNomeCrianca);
        dtNascimento = (DatePicker) findViewById(R.id.datePicker);

        edtNomeCrianca.setTypeface(setFonte(this));

        imgBV = (ImageView) findViewById(R.id.imgBemVindo);

        boolean temCrianca = Utils.temCrianca(this);

        if (temCrianca) {
            temCrianca = true;
            imgBV.setVisibility(View.INVISIBLE);

            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
            String id = prefs.getString(Crianca.CRIANCA_ID_KEY, null);
            edtNomeCrianca.setText(prefs.getString(Crianca.CRIANCA_NOME_KEY, ""));

            int diaNas =   prefs.getInt(Crianca.CRIANCA_NASC_DIA_KEY, 3);
            int mesNas = prefs.getInt(Crianca.CRIANCA_NASC_MES_KEY,4);
            int anoNas = prefs.getInt(Crianca.CRIANCA_NASC_ANO_KEY, 5);

            dtNascimento.updateDate(anoNas, mesNas, diaNas);

        }
        else {
            imgBV.setVisibility(View.VISIBLE);
        }
    }

    public static Typeface setFonte(Context context)
    {
        return Typeface.createFromAsset(context.getAssets(),"ARLRDBD.TTF");
    }

    public void onClickCadastrar (View view)
    {
        Crianca crianca = new Crianca();
        crianca.setId(Crianca.CRIANCA_ID);
        crianca.setNome(edtNomeCrianca.getText().toString());
        crianca.setBornDate(getCalendar(dtNascimento));
      //  crianca.setSexo(sexoSelected(rdSexoF, rdSexoM));

        if(crianca.persisteCrianca(this))
        {
            finish();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Não foi possível cadastrar esta criança, tente novamente.", Toast.LENGTH_SHORT).show();
        }


    }

    private Calendar getCalendar(DatePicker dtNascimento)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, dtNascimento.getDayOfMonth());
        calendar.set(Calendar.MONTH, dtNascimento.getMonth());
        calendar.set(Calendar.YEAR, dtNascimento.getYear());

        return  calendar;
    }
   /* public char sexoSelected (RadioButton rdSexoF, RadioButton rdSexoM)
    {
        if(rdSexoF.isChecked())
        {
            return 'F';
        }
        if(rdSexoM.isChecked())
        {
            return 'M';
        }

        return '0';
    }
    */
}
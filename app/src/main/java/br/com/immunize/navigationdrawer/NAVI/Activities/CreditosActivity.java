package br.com.immunize.navigationdrawer.NAVI.Activities;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import br.com.immunize.navigationdrawer.R;

public class CreditosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creditos);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setBackgroundDrawable(getResources().getDrawable(R.drawable.action_bar_transparente));

        TextView txt = (TextView) findViewById(R.id.txtviewcredits);
        txt.setTypeface(setFonte(this));
        txt.setText("\n " +
                "Programação: Eva Marla\n" +
                "Design: Meyrillan Souza e Ingrid Layne\n" +
                "Documentação: Acsa Gabriely, Giovanna Ballister, Rebeka Simões, e Áquila Lima\n" +
                "Gerenciamento: Eva Marla\n" +
                "\n" +
                "\n" +
                "Ícone da pasta feito pela Freepik\n" +
                "Ícone da câmera feito pela Freepik\n" +
                "Ícone do claquete feito pela Freepik\n" +
                "Ícone da lata de lixo feito pela Icomoon\n" +
                "Ícone da câmera feito pela Smashicons\n" +
                "Ícones do background feitos pela Freepik\n" +
                "ícone do microfone feito por Dave Gandy\n" +
                "Ícone do álbum de fotos feito pela Freepik\n" +
                "Ícone do recém-nascido feito pela Freepik\n" +
                "Ícone da ultrassonografia feito pela Freepik\n" +
                "Ícone do grupo de pessoas feito pela OCHA\n" +
                "Ícone do documento feito pela Chanut is Industries\n" +
                "Todos esses ícones estão no site: www.flaticon.com \n");

    }

    public static Typeface setFonte(Context context)
    {
        return Typeface.createFromAsset(context.getAssets(),"ARLRDBD.TTF");
    }
}

package br.com.immunize.navigationdrawer.NAVI.Utils;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.List;

import br.com.immunize.navigationdrawer.NAVI.Adapter.oAdapter;
import br.com.immunize.navigationdrawer.NAVI.Banco.BD;
import br.com.immunize.navigationdrawer.NAVI.Objects.Peso;
import br.com.immunize.navigationdrawer.R;

/**
 * Created by Marla on 18/12/2016.
 */
public class o extends AppCompatActivity {
    oAdapter adapter;
    List<Peso> peso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_o);
        super.onCreate(savedInstanceState);


    }
}

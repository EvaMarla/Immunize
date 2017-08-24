package br.com.immunize.navigationdrawer.NAVI.MenuAjuda;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import br.com.immunize.navigationdrawer.NAVI.Activities.CadastroActivity;
import br.com.immunize.navigationdrawer.NAVI.Activities.CartaoActivity;
import br.com.immunize.navigationdrawer.NAVI.NAVI.*;
import br.com.immunize.navigationdrawer.NAVI.NAVI.MainActivity;
import br.com.immunize.navigationdrawer.R;

/**
 * Created by Marla on 15/08/2017.
 */
public class Tab3 extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab3, container, false);


        Button button = (Button) rootView.findViewById(R.id.btnVoltarInicial);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }
}

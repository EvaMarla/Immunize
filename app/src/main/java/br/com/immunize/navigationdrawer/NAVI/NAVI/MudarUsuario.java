package br.com.immunize.navigationdrawer.NAVI.NAVI;

import android.support.v4.app.Fragment;
//mudei o import p support.v4
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.immunize.navigationdrawer.R;

/**
 * Created by Marla on 19/09/2016.
 */
public class MudarUsuario extends Fragment {

    View myView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.mudarusuario, container, false);
        return myView;
    }
}

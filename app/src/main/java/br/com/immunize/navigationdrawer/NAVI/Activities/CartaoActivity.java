package br.com.immunize.navigationdrawer.NAVI.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import br.com.immunize.navigationdrawer.NAVI.Adapter.CartaoAdapter;
import br.com.immunize.navigationdrawer.NAVI.Banco.BD;
import br.com.immunize.navigationdrawer.NAVI.Objects.Cartao;
import br.com.immunize.navigationdrawer.NAVI.Utils.Utils;
import br.com.immunize.navigationdrawer.R;


public class CartaoActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    CartaoAdapter adapter;
    List<Cartao> cartoes;
    BD myBD;
    public  static String ID_CARTAO_SELECTED = "ID_CARTAO_SELECTED";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.cartao);
        super.onCreate(savedInstanceState);/*
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setBackgroundDrawable(getResources().getDrawable(R.drawable.cartao_de_vacina_meses_barra));*/

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        myBD = new BD(this);


        //   myBD.inserir(new Cartao(), new Vacina());
        //   cartoes = myBD.getCartoes(this);
        cartoes = Utils.cartoesList(this);
        ListView cartaoList = (ListView) findViewById(R.id.listaCartao);
        cartaoList.setOnItemClickListener(this);
        CartaoAdapter adapter = new CartaoAdapter(this, cartoes);
        cartaoList.setAdapter(adapter);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Cartao cartao = cartoes.get(position);


        if (cartao != null) {
            Intent intent = new Intent(this, VacinasActivity.class);
            intent.putExtra(ID_CARTAO_SELECTED, cartao.getId());
            startActivity(intent);
        }
    }
}


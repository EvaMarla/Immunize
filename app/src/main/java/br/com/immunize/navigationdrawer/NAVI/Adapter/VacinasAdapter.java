package br.com.immunize.navigationdrawer.NAVI.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import br.com.immunize.navigationdrawer.NAVI.Objects.Vacina;
import br.com.immunize.navigationdrawer.NAVI.Utils.Utils;
import br.com.immunize.navigationdrawer.R;

/**
 * Created by Marla on 19/08/2016.
 */
public class VacinasAdapter extends BaseAdapter {

    private List<Vacina> vacinas;
    private Context ctx;


    public VacinasAdapter(Context ctx, List<Vacina> cartoes)
    {
        this.ctx = ctx;
        this.vacinas = cartoes;
    }

    public int getCount()
    {
        return vacinas.size();
    }


    public Object getItem(int position)
    {
        return vacinas.get(position);
    }


    public long getItemId(int position)
    {
        return vacinas.get(position).getId();
    }


    public View getView(int position, View convertView, ViewGroup parent)
    {

        final Vacina vacina = vacinas.get(position);

        convertView = LayoutInflater.from(ctx).inflate(R.layout.vacinaitemlayout, null);

        final LinearLayout lnFundoNomeVacina = (LinearLayout)convertView.findViewById(R.id.fundo_nome_vacina);
        final TextView txtNome = (TextView)convertView.findViewById(R.id.txtVacina);
        ImageView gotinha = (ImageView) convertView.findViewById(R.id.gotinha);

        TextView txtDesc = (TextView)convertView.findViewById(R.id.txtDescVacina);

        txtNome.setText(vacina.getNomevacina());
        txtDesc.setText(vacina.getLegendaVacina());
        String gotas = vacina.getNomevacina();

        if(Utils.getVacinaStatus(ctx,vacina))
        {
            if(gotas == "Rotavírus Humano - Dose 1"|| gotas == "Rotavírus Humano - Dose 2" || gotas == "VIP/VOP - Dose 1" || gotas == "VIP/VOP - Dose 2" || gotas == "VIP/VOP - Dose 3" || gotas == "VIP/VOP - Primeiro Reforço com VOP")
            {
               // lnFundoNomeVacina.setBackgroundResource(R.drawable.cartao_de_vacina_vacinas_gotinha_marcado);
                gotinha.setBackgroundResource(R.drawable.cartao_de_vacina_vacinas_gotinha);
                lnFundoNomeVacina.setBackgroundResource(R.drawable.cartao_de_vacina_vacinas_marcado);
                vacina.setFoiTomada(true);
            }
            else {
                lnFundoNomeVacina.setBackgroundResource(R.drawable.cartao_de_vacina_vacinas_marcado);
                vacina.setFoiTomada(true);
            }
        }
        else
        {
            if(gotas == "Rotavírus Humano - Dose 1"|| gotas == "Rotavírus Humano - Dose 2" || gotas == "VIP/VOP - Dose 1" || gotas == "VIP/VOP - Dose 2" || gotas == "VIP/VOP - Dose 3" || gotas == "VIP/VOP - Primeiro Reforço com VOP")
            {
               // lnFundoNomeVacina.setBackgroundResource(R.drawable.cartao_de_vacina_vacinas_gotinha_marcar);
              //  gotinha.setBackgroundResource(R.drawable.cartao_de_vacina_vacinas_gotinha_marcado);
                gotinha.setBackgroundResource(R.drawable.cartao_de_vacina_vacinas_gotinha);
                lnFundoNomeVacina.setBackgroundResource(R.drawable.cartao_de_vacina_vacinas_marcar);
                vacina.setFoiTomada(false);
            }
            else {
                lnFundoNomeVacina.setBackgroundResource(R.drawable.cartao_de_vacina_vacinas_marcar);
                vacina.setFoiTomada(false);
            }
        }

        lnFundoNomeVacina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String gotas = vacina.getNomevacina();
                if (Utils.getVacinaStatus(ctx, vacina)) {
                    if(gotas == "Rotavírus Humano - Dose 1"|| gotas == "Rotavírus Humano - Dose 2" || gotas == "VIP/VOP - Dose 1" || gotas == "VIP/VOP - Dose 2" || gotas == "VIP/VOP - Dose 3" || gotas == "VIP/VOP - Primeiro Reforço com VOP")
                    {
                     //   lnFundoNomeVacina.setBackgroundResource(R.drawable.cartao_de_vacina_vacinas_gotinha_marcar);
                        lnFundoNomeVacina.setBackgroundResource(R.drawable.cartao_de_vacina_vacinas_marcar);
                    }
                    else{
                        lnFundoNomeVacina.setBackgroundResource(R.drawable.cartao_de_vacina_vacinas_marcar);
                    }
                    Utils.setTomouVacina(ctx, vacina, false);
                } else {
                    if(gotas == "Rotavírus Humano - Dose 1"|| gotas == "Rotavírus Humano - Dose 2" || gotas == "VIP/VOP - Dose 1" || gotas == "VIP/VOP - Dose 2" || gotas == "VIP/VOP - Dose 3" || gotas == "VIP/VOP - Primeiro Reforço com VOP")
                    {
                       // lnFundoNomeVacina.setBackgroundResource(R.drawable.cartao_de_vacina_vacinas_gotinha_marcado);
                        lnFundoNomeVacina.setBackgroundResource(R.drawable.cartao_de_vacina_vacinas_marcado);
                        Utils.setTomouVacina(ctx, vacina, true);
                    }
                    else {
                        lnFundoNomeVacina.setBackgroundResource(R.drawable.cartao_de_vacina_vacinas_marcado);
                        Utils.setTomouVacina(ctx, vacina, true);
                    }
                }
            }
        });
        return convertView;
    }

}

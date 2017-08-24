package br.com.immunize.navigationdrawer.NAVI.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import br.com.immunize.navigationdrawer.NAVI.Objects.Alimentacao;
import br.com.immunize.navigationdrawer.NAVI.Utils.Utils;
import br.com.immunize.navigationdrawer.R;

/**
 * Created by Marla on 30/07/2016.
 */
public class AlimentacaoAdapter extends BaseAdapter {

    private List<Alimentacao> alimentos;
    private Context ctx;

    public AlimentacaoAdapter(Context ctx, List<Alimentacao> alimentos)
    {
        this.ctx = ctx;
        this.alimentos = alimentos;
    }

    public int getCount()
    {
        return alimentos.size();
    }

    public Object getItem(int position)
    {
        return alimentos.get(position);
    }

    public long getItemId(int position)
    {
        return alimentos.get(position).getId();
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        Alimentacao p = alimentos.get(position);

        convertView = LayoutInflater.from(ctx).inflate(R.layout.alimentoitemlayout, null);
        TextView txtNome = (TextView)convertView.findViewById(R.id.txtNome);
        txtNome.setText(p.getPeriodo());

        final Alimentacao alimento2 = alimentos.get(position);
        final LinearLayout lnFundoNomeVacina = (LinearLayout)convertView.findViewById(R.id.marcar_alimentacao);

        /*if(Utils.getComeuStatus(ctx,alimento2))
        {
            lnFundoNomeVacina.setBackgroundResource(R.drawable.alimentacao_marcado);
            alimento2.setComeu(true);
        }
        else
        {
            lnFundoNomeVacina.setBackgroundResource(R.drawable.alimentacao_marcar);
            alimento2.setComeu(false);
        }*/

        lnFundoNomeVacina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.getComeuStatus(ctx, alimento2)) {
                    lnFundoNomeVacina.setBackgroundResource(R.drawable.alimentacao_marcar);
                    //   Utils.setComeu(ctx, alimento2, false);
                } else {
                    lnFundoNomeVacina.setBackgroundResource(R.drawable.alimentacao_marcado);
                    // Utils.setComeu(ctx, alimento2, true);
                }
            }
        });
        return convertView;
    }
}

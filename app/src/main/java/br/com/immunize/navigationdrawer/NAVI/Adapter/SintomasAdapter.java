package br.com.immunize.navigationdrawer.NAVI.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import br.com.immunize.navigationdrawer.NAVI.Objects.Sintomas;
import br.com.immunize.navigationdrawer.NAVI.Utils.Utils;
import br.com.immunize.navigationdrawer.R;


/**
 * Created by Marla on 30/07/2016.
 */
public class SintomasAdapter extends BaseAdapter {



    private List<Sintomas> sintomas;
    private Context ctx;

    public SintomasAdapter(Context ctx, List<Sintomas> sintomas)
    {
        this.ctx = ctx;
        this.sintomas = sintomas;
    }

    public int getCount()
    {
        return sintomas.size();
    }

    public Object getItem(int position)
    {
        return sintomas.get(position);
    }

    public long getItemId(int position)
    {
        return sintomas.get(position).getId();
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        Sintomas p = sintomas.get(position);

        convertView = LayoutInflater.from(ctx).inflate(R.layout.sintomasitemlayout, null);
        TextView txtNome = (TextView)convertView.findViewById(R.id.txtNome);
        txtNome.setTypeface(setFonte(ctx));
        txtNome.setText(p.getPeriodo());

        final Sintomas sint = sintomas.get(position);
      //  final LinearLayout lnFundoNomeVacina = (LinearLayout)convertView.findViewById(R.id.marcar_sintoma);

        /*if(Utils.getSintomaStatus(ctx, sint))
        {
            lnFundoNomeVacina.setBackgroundResource(R.drawable.sintomas_marcado);
            //sint.setSintomas(true);
        }
        else
        {
            lnFundoNomeVacina.setBackgroundResource(R.drawable.sintomas_marcar);
            //sint.setSintomas(false);
        }

        lnFundoNomeVacina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.getSintomaStatus(ctx, sint)) {
                    lnFundoNomeVacina.setBackgroundResource(R.drawable.sintomas_marcar);

                    //remover no banco aqui


                    Utils.setSintoma(ctx, sint, false);
                } else {
                    lnFundoNomeVacina.setBackgroundResource(R.drawable.sintomas_marcado);

                    //adicionar do banco aqui

                    Utils.setSintoma(ctx, sint, true);
                }
            }
        });*/
        return convertView;
    }

    public static Typeface setFonte(Context context)
    {
        return Typeface.createFromAsset(context.getAssets(),"ARLRDBD.TTF");
    }
}
package br.com.immunize.navigationdrawer.NAVI.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.immunize.navigationdrawer.NAVI.Objects.Cartao;
import br.com.immunize.navigationdrawer.R;

/**
 * Created by Marla on 17/08/2016.
 */
public class CartaoAdapter extends BaseAdapter {

    private List<Cartao> cartoes;
    private Context ctx;

    public CartaoAdapter(Context ctx, List<Cartao> cartoes)
    {
        this.ctx = ctx;
        this.cartoes = cartoes;
    }

    public static Typeface setFonte(Context context)
    {
        return Typeface.createFromAsset(context.getAssets(),"ARLRDBD.TTF");
    }

    public int getCount()
    {
        return cartoes.size();
    }

    public Object getItem(int position)
    {
        return cartoes.get(position);
    }

    public long getItemId(int position)
    {
        return cartoes.get(position).getId();
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {

        Cartao p = cartoes.get(position);

        View convertView1 = LayoutInflater.from(ctx).inflate(R.layout.cartaoitemlayout, null);
        View convertView2 = LayoutInflater.from(ctx).inflate(R.layout.cartaoitemlayoutazul, null);
        View convertView3 = LayoutInflater.from(ctx).inflate(R.layout.cartaoitemlayoutlaranja, null);
        View convertView4 = LayoutInflater.from(ctx).inflate(R.layout.cartaoitemlayoutverde, null);

        TextView[] nome = new TextView[9];

        if(position == 0 || position == 4 || position == 8) {
            nome[position] = (TextView) convertView3.findViewById(R.id.text3);
            nome[position].setText(p.getPeriodo());
            nome[position].setTypeface(setFonte(ctx));
            return convertView3;
        }
        else if(position == 1 || position == 5) {
            nome[position] = (TextView) convertView1.findViewById(R.id.text1);
            nome[position].setText(p.getPeriodo());
            nome[position].setTypeface(setFonte(ctx));
            return convertView1;
        }
        else if(position == 2 || position == 6) {
            nome[position] = (TextView) convertView2.findViewById(R.id.text2);
            nome[position].setTypeface(setFonte(ctx));
            nome[position].setText(p.getPeriodo());
            return convertView2;
        }
       else  if(position == 3 || position == 7) {
            nome[position] = (TextView) convertView4.findViewById(R.id.text4);
            nome[position].setTypeface(setFonte(ctx));
            nome[position].setText(p.getPeriodo());
            return convertView4;
        }
        return convertView;
    }
}

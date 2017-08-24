package br.com.immunize.navigationdrawer.NAVI.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.immunize.navigationdrawer.NAVI.Objects.Cartao;
import br.com.immunize.navigationdrawer.NAVI.Objects.Peso;
import br.com.immunize.navigationdrawer.R;

/**
 * Created by Marla on 18/12/2016.
 */
public class oAdapter extends BaseAdapter {
    private List<Peso> pesos;
    private Context ctx;

    public oAdapter(Context ctx, List<Peso> cartoes)
    {
        this.ctx = ctx;
        this.pesos = cartoes;
    }

    public int getCount()
    {
        return pesos.size();
    }

    public Object getItem(int position)
    {
        return pesos.get(position);
    }

    public long getItemId(int position)
    {
        return pesos.get(position).getId();
    }
    public View getView(int position, View convertView, ViewGroup parent)
    {

        Peso p = pesos.get(position);


        return convertView;
    }
}

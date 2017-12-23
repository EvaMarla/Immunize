package br.com.immunize.navigationdrawer.NAVI.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.immunize.navigationdrawer.NAVI.Activities.AgendaActivity;
import br.com.immunize.navigationdrawer.NAVI.Objects.Agenda;
import br.com.immunize.navigationdrawer.NAVI.Utils.Utils;
import br.com.immunize.navigationdrawer.R;

/**
 * Created by Marla on 19/08/2016.
 */
public class AgendaAdapter extends BaseAdapter {

    private List<Agenda> agenda;
    private Context ctx;
    ImageView imgCaderninho;

    public AgendaAdapter(Context ctx, List<Agenda> agenda)
    {
        this.ctx = ctx;
        this.agenda = agenda;
    }

    public int getCount()
    {
        return agenda.size();
    }

    public Object getItem(int position)
    {
        return agenda.get(position);
    }

    public long getItemId(int position)
    {
        return agenda.get(position).getId();
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        Agenda p = agenda.get(position);

        convertView = LayoutInflater.from(ctx).inflate(R.layout.agendaitemlayout, null);
        TextView txtNome = (TextView)convertView.findViewById(R.id.txtNome);
        txtNome.setTypeface(setFonte(ctx));
        txtNome.setText(p.getPeriodo());
        imgCaderninho = (ImageView) convertView.findViewById(R.id.imgCaderninho);

        if(p.getPeriodo() == "Escrever"){
            imgCaderninho.setBackgroundResource(R.drawable.escrever);
        }else if(p.getPeriodo() == "Alimentação")
        {
            imgCaderninho.setBackgroundResource(R.drawable.alimentacao);
        }else if(p.getPeriodo() == "Sintomas")
        {
            imgCaderninho.setBackgroundResource(R.drawable.sintomas);
        }else if(p.getPeriodo() == "Remédios")
        {
            imgCaderninho.setBackgroundResource(R.drawable.remedios);
        }else if(p.getPeriodo() == "Peso")
        {
            imgCaderninho.setBackgroundResource(R.drawable.peso);
        }else if(p.getPeriodo() == "Temperatura")
        {
            imgCaderninho.setBackgroundResource(R.drawable.temperatura);
        }

        final Agenda agendaa = agenda.get(position);
        return convertView;
    }

    public static Typeface setFonte(Context context)
    {
        return Typeface.createFromAsset(context.getAssets(),"ARLRDBD.TTF");
    }
}

package br.com.immunize.navigationdrawer.NAVI.Activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.util.Pair;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDialog;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.os.Handler;

import br.com.immunize.navigationdrawer.NAVI.Adapter.CalendarAdapter;
import br.com.immunize.navigationdrawer.NAVI.Banco.BD;
import br.com.immunize.navigationdrawer.NAVI.Banco.BDCore;
import br.com.immunize.navigationdrawer.R;

/**
 * Created by Marla on 18/10/2016.
 */
public class CalendarioActivity extends AppCompatActivity implements View.OnClickListener{

    BDCore myBD = new BDCore(this);
    BD bdDeletar = new BD(this);

    public SQLiteDatabase database;
    private int mYear;
    private int mMonth;
    private int mDay;
    static final int DATE_PICKER_ID = 1111;
    CalendarView calendarView;
    TextView texto;
    ImageButton btnApagar;

    ImageButton btnIrCaderninho;
    TextView titulo;

    String data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    /*    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); // Para o layout preencher toda tela do cel (remover a barra de tit.)
        getSupportActionBar().hide();
*/
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario_);
        database = myBD.getReadableDatabase();
        calendarView  = (CalendarView) findViewById(R.id.calendarView);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setBackgroundDrawable(getResources().getDrawable(R.drawable.action_bar_transparente));

        texto = (TextView)findViewById(R.id.textViewMostraDados);
        texto.setTypeface(setFonte(this));

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        //titulo = (TextView) findViewById(R.id.toolbar);
       // titulo.setTypeface(setFonte(this));
        //titulo.setText(data);

        btnApagar = (ImageButton) findViewById(R.id.btnApagarDados);
       // btnIrCaderninho = (ImageButton) findViewById(R.id.btnIrCaderninho);
      /*  btnIrCaderninho.setOnClickListener(new View.OnClickListener() {

        public void onClick(View v) {
        irCaderninho();
        }
        });*/

        btnApagar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(CalendarioActivity.this, R.style.myDialog));

                builder.setMessage("Tem certeza que deseja excluir todos os dados do dia selecionado?")
                        .setPositiveButton("Excluir",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                                        String selectedDate = sdf.format(new Date(calendarView.getDate()));
                                        bdDeletar.deletarPorData(selectedDate);
                                        texto.setText("");
                                    }
                                })
                        .setNegativeButton("Cancelar", null)
                        .show();

                }
        });

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                String temp = "";
                month +=1;
                String strMonth = "" + month;
                data = "" + dayOfMonth + "/"+ strMonth + "/" + year;
                
                if(myBD.getDataInfo("alimentacao", data ) == null)
                {
                    return;
                }
                else
                {
                    temp += myBD.getDataInfo("alimentacao", data) + "\n";
                    temp += myBD.getDataInfo("pesos", data)+ "\n";
                    temp += myBD.getDataInfo("sintoma", data)+ "\n";
                    temp += myBD.getDataInfo("temperaturas", data)+ "\n";
                    temp += myBD.getDataInfo("escrever", data) + "\n";
                    temp += myBD.getDataInfo("remedios", data) + "\n";
                    texto.setText(temp);
                   // temp = texto.getText().toString();
                }
            }
        });
    }

    public static Typeface setFonte(Context context)
    {
        return Typeface.createFromAsset(context.getAssets(),"ARLRDBD.TTF");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.calendar_menu, menu);
        return true;
    }

    public boolean irCaderninho( MenuItem menuItem){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String selectedDate = sdf.format(new Date(calendarView.getDate()));

        Intent it = new Intent(getApplicationContext(), AgendaActivity.class);
        it.putExtra("data", selectedDate);
        startActivity(it);
        return true;
    }

    @Override
    protected Dialog onCreateDialog(int id){
        switch (id) {

            case DATE_PICKER_ID:
                // set date picker as current date
                return new DatePickerDialog(this, pickerListener, mYear, mMonth, mDay);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener pickerListener = new DatePickerDialog.OnDateSetListener() {
        // when dialog box is closed, below method will be called.
        @Override
        public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
            mDay = selectedDay;
            mMonth = selectedMonth;
            mYear = selectedYear;
            Calendar c = Calendar.getInstance();
            c.set(mYear, mMonth, mDay);
            SimpleDateFormat df = new SimpleDateFormat("MMM-dd-yyyy");
            String selectedDate = df.format(c.getTime());
            //   calendarView.setText(selectedDate);
        }
    };

    @Override
    public void onClick(View v) {

    }
}
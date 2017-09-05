package br.com.immunize.navigationdrawer.NAVI.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import android.os.Handler;

import br.com.immunize.navigationdrawer.NAVI.Adapter.CalendarAdapter;
import br.com.immunize.navigationdrawer.NAVI.Banco.BD;
import br.com.immunize.navigationdrawer.NAVI.push_notification.MainActivity;
import br.com.immunize.navigationdrawer.R;

/**
 * Created by Marla on 18/10/2016.
 */
public class CalendarioActivity extends AppCompatActivity {

    BD myBD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario);
        CalendarView calendarView = (CalendarView) findViewById(R.id.calendarView);

        Calendar calendar = Calendar.getInstance();

        myBD = new BD(this);

        calendarView.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {

            @Override
            public void onDateChanged(DatePicker datePicker, int year, int month, int dayOfMonth) {
                TextView txtMostraDados = (TextView) findViewById(R.id.textViewMostraDados);

                txtMostraDados.setText(myBD.getDataInfo(datePicker));

                //OLHAR ESSE LINK: https://stackoverflow.com/questions/23040790/android-calendar-view-date-picker
            }
        });

        dp.callOnClick()
        dp.setOnDateChangedListener(DatePicker.OnDateChangedListener onDateChangedListener()
        {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {

                //pegar dados do banco conforme data marcada
                //marcar as datas que possuem dados
                //mostrar no textview

                //TextView txtMostraDados = (TextView) findViewById(R.id.textViewMostraDados);

                TextView txtMostraDados = (TextView) findViewById(R.id.textViewMostraDados);

                txtMostraDados.setText(myBD.getDataInfo(view));
            }
        });
    }
}

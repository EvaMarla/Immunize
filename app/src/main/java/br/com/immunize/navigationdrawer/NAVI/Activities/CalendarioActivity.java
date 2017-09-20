package br.com.immunize.navigationdrawer.NAVI.Activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.util.Pair;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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


    BDCore myBD= new BDCore(this);

    public SQLiteDatabase database;
    private int mYear;
    private int mMonth;
    private int mDay;
    static final int DATE_PICKER_ID = 1111;
    CalendarView calendarView;
    TextView texto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario_);
        database = myBD.getReadableDatabase();
        calendarView  = (CalendarView) findViewById(R.id.calendarView);
        texto = (TextView)findViewById(R.id.textViewMostraDados);

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {
                String data = " " + dayOfMonth + "/"+ month + "/" + year;

                texto.setText(myBD.getDataInfo("alimentacao", data ));
            }
        });
    }

    public void getDataBD (String data){
        String sql = "SELECT data FROM alimentacao)" +
                "WHERE data = "+ data;
        Cursor cursor = database.rawQuery(sql, null);
        if(cursor != null && cursor.moveToNext()){
            do {
                this.texto.setText(cursor.getString(0));
            }while (cursor.moveToNext());
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.calendar_menu, menu);
        return true;
    }

    public boolean irCaderninho(MenuItem menuItem){
     startActivity(new Intent(getApplicationContext(), AgendaActivity.class));
     return true;
    }

//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.irCaderninho:
//                startActivity(new Intent(getApplicationContext(), AgendaActivity.class));
//
//                break;
//        }
//    }

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
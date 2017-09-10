package br.com.immunize.navigationdrawer.NAVI.NAVI;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import android.widget.TextView;

import java.util.Calendar;

import br.com.immunize.navigationdrawer.NAVI.Activities.AgendaActivity;
import br.com.immunize.navigationdrawer.NAVI.Activities.CadastroActivity;
import br.com.immunize.navigationdrawer.NAVI.Activities.CalendarioActivity;
import br.com.immunize.navigationdrawer.NAVI.Activities.CartaoActivity;
import br.com.immunize.navigationdrawer.NAVI.Activities.DiarioAcitivity;
import br.com.immunize.navigationdrawer.NAVI.Activities.GMapsActivity;
import br.com.immunize.navigationdrawer.NAVI.Objects.Vacina;
import br.com.immunize.navigationdrawer.NAVI.Diario.MainActivityFoto;
import br.com.immunize.navigationdrawer.NAVI.Utils.Utils;
import br.com.immunize.navigationdrawer.R;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    long cont;
    java.text.SimpleDateFormat formatter;
    boolean temCrianca;
    TextView txtContador;
    TextView txtProximaVacina;
    private ImageView img;
    private Bitmap bitmap;
    public static String fotoCriancaId = "FOTO_CRIANCA_ID";
    ImageView mImageView;
    ImageView imgViewContador;
    ImageView imgViewVacina;
    Button btnFoto;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtContador = (TextView) findViewById(R.id.txtContador);
        txtProximaVacina = (TextView) findViewById(R.id.txtNomeProximaVacina);
        temCrianca = Utils.temCrianca(this);
        imgViewContador = (ImageView) findViewById(R.id.imgViewContador);
        imgViewVacina = (ImageView) findViewById(R.id.imgViewVacina);
        btnFoto = (Button) findViewById(R.id.btnFoto);

        if (temCrianca) {
            temCrianca = true;
            atualizaContador();
            //nomeCrianca.setText(Utils.getNomeCrianca(this));
            if (getSupportActionBar() != null) {
                getSupportActionBar().setBackgroundDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.action_bar, null));
                getSupportActionBar().setTitle("Immunize");
            }
        } else {
            startActivity(new Intent(this, CadastroActivity.class));
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        atualizaContador();
    }

    @Override
    protected void onStart(){
        super.onStart();
        atualizaContador();
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        atualizaContador();
    }

    @Override
    protected void onPause(){
        super.onPause();
        atualizaContador();
    }

    @Override
    protected void onStop(){
        super.onStop();
        atualizaContador();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        atualizaContador();
    }

    public void TirarFoto(View v){
        startActivity(new Intent(this, MainActivityFoto.class));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void onClickPasta(View v){
        startActivity(new Intent(getApplicationContext(), CartaoActivity.class));
    }
    public void onClickAvatar(View v) {
        startActivity(new Intent(getApplicationContext(), DiarioAcitivity.class));
    }
    public void onClickMaps(View v) {
       startActivity(new Intent(getApplicationContext(), GMapsActivity.class));
    }

    public void onClickCalendario(View v) {
      //  Toast.makeText(this, "Esta função não está disponível!", Toast.LENGTH_SHORT).show();
   startActivity(new Intent(getApplicationContext(), AgendaActivity.class));
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();
        FragmentManager fragmentManager = getSupportFragmentManager();

        if (id == R.id.nav_alterarDados) {
            startActivity(new Intent(getApplicationContext(), CadastroActivity.class));

        } else if (id == R.id.nav_creditos) {
            startActivity(new Intent(getApplicationContext(), CalendarioActivity.class));

        }else if (id == R.id.nav_ajuda) {

            startActivity(new Intent(getApplicationContext(), br.com.immunize.navigationdrawer.NAVI.MenuAjuda.MainActivity.class));
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private View.OnClickListener btFoto = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(getApplicationContext(), br.com.immunize.navigationdrawer.NAVI.MenuAjuda.MainActivity.class));

        }
    };

    public static Typeface setFonte(Context context)
    {
        return Typeface.createFromAsset(context.getAssets(),"ARLRDBD.TTF");
    }

    public void atualizaContador(){

        Calendar dataAtual = Calendar.getInstance();
        Calendar dataNascimento = Utils.getCriancaBornDate(this);

        Vacina vacina = Utils.getProximaVacinaASerTomada(this);

        //DATA QUE ELE VAI TOMAR
        Calendar vacinarCalendar = (Calendar) dataNascimento.clone();
        vacinarCalendar.add(Calendar.MONTH, (int) vacina.getIdCartao());

        long timeContador = vacinarCalendar.getTimeInMillis() - dataAtual.getTimeInMillis();

        final long days = timeContador / 86400000;

        if(days > 30){
          /*  imgViewContador.setBackgroundResource(R.drawable.tela_inicial_nenhuma_vacinacao);
            txtProximaVacina.setVisibility(View.INVISIBLE);
            txtContador.setVisibility(View.INVISIBLE);
            imgViewVacina.setVisibility(View.INVISIBLE);*/

            txtContador.setTypeface(setFonte(getApplicationContext()));
            txtProximaVacina.setText(vacina.getNomevacina());
            txtContador.setText(String.valueOf(days));

        } else if (days <= 30){
            NotificationManager notif=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
            Notification notify = new Notification.Builder
                    (getApplicationContext()).setContentTitle("Immunize").setContentText("Leve seu bebê ao posto de vacinação mais próximo!").
                    setContentTitle("Proteja seu bebê!").setSmallIcon(R.drawable.icone).build();

            notify.flags |= Notification.FLAG_AUTO_CANCEL;
            notif.notify(0, notify);

            txtContador.setTypeface(setFonte(this));
            txtProximaVacina.setText(vacina.getNomevacina());
            txtContador.setText(String.valueOf(days));
            //txtContador.setText("24");
        }
        else{
            txtContador.setTypeface(setFonte(this));
            txtProximaVacina.setText(vacina.getNomevacina());
            txtContador.setText(String.valueOf(days));
            //txtContador.setText("24");
        }
    }
}
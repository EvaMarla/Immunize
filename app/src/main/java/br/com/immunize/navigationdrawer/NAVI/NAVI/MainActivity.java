package br.com.immunize.navigationdrawer.NAVI.NAVI;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;

import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.TypefaceSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import android.widget.TextView;

import java.io.File;
import java.util.Calendar;

import br.com.immunize.navigationdrawer.NAVI.Activities.AgendaActivity;
import br.com.immunize.navigationdrawer.NAVI.Activities.CadastroActivity;
import br.com.immunize.navigationdrawer.NAVI.Activities.CalendarioActivity;
import br.com.immunize.navigationdrawer.NAVI.Activities.CartaoActivity;
import br.com.immunize.navigationdrawer.NAVI.Activities.CreditosActivity;
import br.com.immunize.navigationdrawer.NAVI.Activities.DiarioAcitivity;
import br.com.immunize.navigationdrawer.NAVI.Activities.GMapsActivity;
import br.com.immunize.navigationdrawer.NAVI.Activities.VideoActivity;
import br.com.immunize.navigationdrawer.NAVI.Diario.Util;
import br.com.immunize.navigationdrawer.NAVI.Objects.Vacina;
import br.com.immunize.navigationdrawer.NAVI.Diario.MainActivityFoto;
import br.com.immunize.navigationdrawer.NAVI.Utils.CustomTypefaceSpan;
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

    ImageView btnFoto;
    File mCaminhoFoto;
    ImageView mImageViewFoto;
    CarregarImageTask mTask;


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String caminhoFoto = Util.carregarUltimaMidia(getApplicationContext(), Util.MIDIA_FOTO);

        if (caminhoFoto != null){
            mCaminhoFoto = new File(caminhoFoto);
        } else{
            carregarImagem();
        }

        carregarImagem();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        txtContador = (TextView) findViewById(R.id.txtContador);
        txtProximaVacina = (TextView) findViewById(R.id.txtNomeProximaVacina);
        temCrianca = Utils.temCrianca(this);
        imgViewContador = (ImageView) findViewById(R.id.imgViewContador);
        imgViewVacina = (ImageView) findViewById(R.id.imgViewVacina);
        //btnFoto = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.btnFoto);
        mImageViewFoto = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.imgFoto);

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


      /*  btnFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //dispatchTakePictureIntent();
                mCaminhoFoto = Util.novaMidia(Util.MIDIA_FOTO);
                TirarFoto();
            }
        });*/

        Menu m = navigationView.getMenu();
        for (int i=0;i<m.size();i++) {
            MenuItem mi = m.getItem(i);

            //for aapplying a font to subMenu ...
            SubMenu subMenu = mi.getSubMenu();
            if (subMenu!=null && subMenu.size() >0 ) {
                for (int j=0; j <subMenu.size();j++) {
                    MenuItem subMenuItem = subMenu.getItem(j);
                    applyFontToMenuItem(subMenuItem);
                }
            }
            applyFontToMenuItem(mi);
        }
    }

 /*   private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, 1);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            mImageViewFoto.setImageBitmap(imageBitmap);
        }
    }*/

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

    public void TirarFoto(){
        Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        it.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mCaminhoFoto));

        startActivityForResult(it, Util.REQUESTCODE_FOTO);
//        startActivity(new Intent(this, MainActivityFoto.class));
        //carregarImagem();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Activity.RESULT_OK && requestCode == Util.REQUESTCODE_FOTO){
            carregarImagem();
        }
    }

    private void carregarImagem(){
        if(mCaminhoFoto != null && mCaminhoFoto.exists()){
            if(mTask == null || mTask.getStatus() != AsyncTask.Status.RUNNING){
                mTask = new CarregarImageTask();
                mTask.execute();
            }
        }
    }

    class CarregarImageTask extends AsyncTask<Void, Void, Bitmap>{

        @Override
        protected Bitmap doInBackground(Void... voids){
            return Util.carregarImagem(mCaminhoFoto, 1800, 1800);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap){
            super.onPostExecute(bitmap);

            if(bitmap != null){
                mImageViewFoto.setImageBitmap(bitmap);
                Util.salvarUltimaMidia(getApplicationContext(), Util.MIDIA_FOTO, mCaminhoFoto.getAbsolutePath());
            }
        }
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
       startActivity(new Intent(getApplicationContext(), CalendarioActivity.class));
    }

/*
    @Override
*/
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        Typeface face = Typeface.createFromAsset(this.getAssets(),"ARLRDBD.TTF");
        SpannableStringBuilder title = new SpannableStringBuilder(this.getString(R.string.alterardados));
        SpannableStringBuilder title2 = new SpannableStringBuilder(this.getString(R.string.creditos));
        SpannableStringBuilder title3 = new SpannableStringBuilder(this.getString(R.string.ajuda));

        title.setSpan(face, 0, title.length(), 0);
        title2.setSpan(face, 0, title2.length(), 0);
        title3.setSpan(face, 0, title3.length(), 0);
/*
        menu.add(Menu.NONE, R.id.nav_alterarDados, 0, title);
*/
        MenuItem menuItem = menu.findItem(R.id.nav_alterarDados);
        MenuItem menuItem2 = menu.findItem(R.id.nav_creditos);
        MenuItem menuItem3 = menu.findItem(R.id.nav_ajuda);
        menuItem.setTitle(title);
        menuItem.setTitle(title2);
        menuItem.setTitle(title3);
       /* super.onCreateOptionsMenu(menu, inflater);*/
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();
        FragmentManager fragmentManager = getSupportFragmentManager();

        if (id == R.id.nav_alterarDados) {
            startActivity(new Intent(getApplicationContext(), CadastroActivity.class));

        } else if (id == R.id.nav_creditos) {
            startActivity(new Intent(getApplicationContext(), CreditosActivity.class));

        }else if (id == R.id.nav_ajuda) {
            startActivity(new Intent(getApplicationContext(), br.com.immunize.navigationdrawer.NAVI.MenuAjuda.MainActivity.class));
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void applyFontToMenuItem(MenuItem mi) {
        Typeface font = Typeface.createFromAsset(getAssets(), "ARLRDBD.TTF");
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("" , font), 0 , mNewTitle.length(),  Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
    }


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

        Integer i = (int) (long) days;

        if(i > 30 || i < 0){

            imgViewContador.setBackgroundResource(R.drawable.tela_inicial_nenhuma_vacinacao);
            txtProximaVacina.setVisibility(View.INVISIBLE);
            txtContador.setVisibility(View.INVISIBLE);
            imgViewVacina.setVisibility(View.INVISIBLE);

        } else if (i <= 30 && i >= 1){

            NotificationManager notif=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
            Notification notify = new Notification.Builder
                    (getApplicationContext()).setContentTitle("Immunize").setContentText("Vá ao posto de vacinação mais próximo").
                    setContentTitle("Proteja seu bebê!").setLargeIcon(BitmapFactory.decodeResource(this.getResources(),
                    R.drawable.notificacoes)).setSmallIcon(R.drawable.icone).build();

            notify.flags |= Notification.FLAG_AUTO_CANCEL;
            notif.notify(0, notify);

            txtProximaVacina.setTypeface(setFonte(this));
            txtContador.setTypeface(setFonte(this));
            txtProximaVacina.setText(vacina.getNomevacina());
            txtContador.setText(String.valueOf(days));
        }
        else {
            txtProximaVacina.setTypeface(setFonte(this));
            txtContador.setTypeface(setFonte(this));
            txtProximaVacina.setText(vacina.getNomevacina());
            txtContador.setText(String.valueOf(days));
        }
    }
}
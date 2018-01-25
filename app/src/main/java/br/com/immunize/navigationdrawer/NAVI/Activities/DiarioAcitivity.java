package br.com.immunize.navigationdrawer.NAVI.Activities;

import android.Manifest;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.provider.MediaStore;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;

import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;

import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;

import java.util.Date;

import br.com.immunize.navigationdrawer.NAVI.Diario.CameraFotoFragment;
import br.com.immunize.navigationdrawer.NAVI.Diario.GravarAudioFragment;
import br.com.immunize.navigationdrawer.NAVI.Diario.MainActivityFoto;
import br.com.immunize.navigationdrawer.NAVI.Diario.Util;
import br.com.immunize.navigationdrawer.NAVI.Diario.Util2;
import br.com.immunize.navigationdrawer.NAVI.NAVI.MainActivity;
import br.com.immunize.navigationdrawer.NAVI.Utils.App;
import br.com.immunize.navigationdrawer.NAVI.Utils.UtilMidia;
import br.com.immunize.navigationdrawer.R;

import android.app.FragmentTransaction;
import android.widget.MediaController;
import android.widget.VideoView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.drive.Drive;

/**
 * Created by Marla on 22/08/2017.
 */
public class DiarioAcitivity extends AppCompatActivity implements View.OnClickListener{

    //TEXTO
    public EditText edtNomeResponsavel;
    public EditText edtPirmeiraPalavra;
    SharedPreferences prefs;

    //FOTO
    ImageView imgFoto;
    ImageButton btnFoto;
    File mCaminhoFoto;
    int mLarguraImage;
    int mAlturaImage;
    String caminhoFoto;
    CarregarImageTask mTask;
    ImageButton apagarFoto;
    boolean fotoApagada;
    String apagarCaminho;

    //VIDEO
    VideoView mVideoView;
    ImageButton btRecordaVideo;
    int posicao;
    boolean mExecutando;
    Uri mVideoUri;
    String nomeMidia;

    //AUDIO
    ImageButton btnGravar, btnPlay;
    Chronometer chronometer;
    MediaRecorder mediaRecorder;
    MediaPlayer mediaPlayer;
    File mCaminhoaudio;
    boolean mGravando, mTocando;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_diario);
        super.onCreate(savedInstanceState);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setBackgroundDrawable(getResources().getDrawable(R.drawable.action_bar_transparente));

        //TEXTO
        edtNomeResponsavel = (EditText) findViewById(R.id.edtNomeResponsavel);
        edtPirmeiraPalavra = (EditText) findViewById(R.id.edtPrimeiraPalavra);

        edtNomeResponsavel.setTypeface(setFonte(this));
        edtPirmeiraPalavra.setTypeface(setFonte(this));

        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        edtNomeResponsavel.setText(prefs.getString("nomeResponsavel", ""));
        edtPirmeiraPalavra.setText(prefs.getString("primeiraPalavra", ""));

        edtPirmeiraPalavra.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                prefs.edit().putString("primeiraPalavra", s.toString()).commit();
            }
        });

        edtNomeResponsavel.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                prefs.edit().putString("nomeResponsavel", s.toString()).commit();
            }
        });

        //VIDEO
        btRecordaVideo = (ImageButton) findViewById(R.id.btRecordaVideo);
        mVideoView = (VideoView) this.findViewById(R.id.vvVideo);
        mVideoView.setMediaController(new MediaController(this));

        String caminhoVideo = this.getSharedPreferences("midia_video_prefs", Context.MODE_PRIVATE).getString("ULTIMO_VIDEO", null);
        if(caminhoVideo != null){
            mVideoUri = Uri.parse(caminhoVideo);
            carregarVideo();
        }

        //AUDIO
        btnPlay = (ImageButton) findViewById(R.id.btnPlay);
        btnGravar = (ImageButton) findViewById(R.id.btnGravar);
        chronometer = (Chronometer) findViewById(R.id.chronometer);

        btnGravar.setOnClickListener(this);
        btnPlay.setOnClickListener(this);

        String caminhoaudio = this.getSharedPreferences("midia_audio_prefs", Context.MODE_PRIVATE).getString("ULTIMO_AUDIO", null);
        if(caminhoaudio != null){
            mCaminhoaudio = new File(caminhoaudio);
        }

        btnGravar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                btnGravarClick();
            }
        });
        btnPlay.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                btnPlayClick();
            }
        });

       //foto
        imgFoto = (ImageView) findViewById(R.id.imgFoto);
        btnFoto = (ImageButton) findViewById(R.id.btnFoto);
        caminhoFoto  = this.getSharedPreferences("midia_fotodiario_prefs", Context.MODE_PRIVATE).getString("ULTIMA_FOTO_DIARIO", null);
       // apagarFoto = (ImageButton) findViewById(R.id.btnApagarFoto);

      /*  apagarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(DiarioAcitivity.this, R.style.myDialog));

                builder.setMessage("Tem certeza que deseja excluir permanentemente esta foto?")
                        .setPositiveButton("Excluir",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        imgFoto.setImageBitmap(null);
                                        apagarCaminho = mCaminhoFoto.getAbsolutePath();
                                        mCaminhoFoto = null;
                                        fotoApagada = true;

                                    }
                                })
                        .setNegativeButton("Cancelar", null)
                        .show();
            }
        });
*/

        if (caminhoFoto != null){
            mCaminhoFoto = new File(caminhoFoto);
           // if(fotoApagada == true && apagarCaminho == mCaminhoFoto.getAbsolutePath()){

           // } else {
            carregarImagem(); }
        // }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnGravar:
             //   btnGravarClick();
                break;
            case R.id.btnPlay:
              //  btnPlayClick();
                break;
            case R.id.btRecordaVideo:
             //   novoVideo();
                break;
            case R.id.btnFoto:
                novaFoto(view);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_diario, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public static Typeface setFonte(Context context)
    {
        return Typeface.createFromAsset(context.getAssets(),"ARLRDBD.TTF");
    }

   //AUDIO
    private void btnPlayClick(){
        chronometer.stop();

        if(mTocando){
            pararDeTocar();
        } else if (mCaminhoaudio != null && mCaminhoaudio.exists()){
            try {
                mediaPlayer = new MediaPlayer();
                mediaPlayer.setDataSource(mCaminhoaudio.getAbsolutePath());
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mTocando = false;
                        chronometer.stop();
                        atualizarBotoes();
                    }
                });
                pararDeGravar();
                //mediaPlayer = new MediaPlayer();
              //  mediaPlayer.setDataSource(mCaminhoaudio.getAbsolutePath());
                mediaPlayer.prepare();
                mediaPlayer.start();
                chronometer.setBase(SystemClock.elapsedRealtime());
                chronometer.start();
                mTocando = true;
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        atualizarBotoes();
    }

    private void btnGravarClick(){

        chronometer.stop();

        if(mGravando){
            pararDeGravar();
        } else {

            novoAudio();

            mediaRecorder = new MediaRecorder();
            if (ActivityCompat.checkSelfPermission(DiarioAcitivity.this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(DiarioAcitivity.this, new String[]{Manifest.permission.RECORD_AUDIO}, 3);

            } else {

                mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                mediaRecorder.setOutputFile(mCaminhoaudio.getAbsolutePath());
                mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            }
            try{
                mediaRecorder.prepare();
                mediaRecorder.start();
                chronometer.setBase(SystemClock.elapsedRealtime());
                chronometer.start();
                mGravando = true;
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        atualizarBotoes();
    }

    private void atualizarBotoes(){
        btnGravar.setImageResource(mGravando ? R.drawable.botao_pause : R.drawable.botao_seis);
        btnGravar.setEnabled(!mTocando);
        btnPlay.setImageResource(mTocando ? R.drawable.botao_pause : R.drawable.botao_cinco);
        btnPlay.setEnabled(!mGravando);
    }

    private void pararDeTocar(){
        if(mediaPlayer != null && mTocando){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
            mTocando = false;
        }
    }

    private void pararDeGravar(){

        if (mediaRecorder != null && mGravando){
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
            mGravando = false;

            SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("midia_audio_prefs", Context.MODE_PRIVATE);

            sharedPreferences.edit().putString("ULTIMO_AUDIO", mCaminhoaudio.getAbsolutePath()).commit();

            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);

            Uri contentUri = Uri.parse(mCaminhoaudio.getAbsolutePath());

            mediaScanIntent.setData(contentUri);

            getApplicationContext().sendBroadcast(mediaScanIntent);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Activity.RESULT_OK && requestCode == 1){
            mLarguraImage = imgFoto.getWidth();
            mAlturaImage = imgFoto.getHeight();
            carregarImagem();
        }
        if(requestCode == 2 && resultCode == Activity.RESULT_OK){
            mVideoUri = data.getData();
            carregarVideo();
        }
    }

    public void novaFoto(View view){
        nomeMidia = DateFormat.format("yyyy-MM-dd_hhmmss", new Date()).toString() + "foto_diario";

        File dirMidia = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "Immunize");

        if (!dirMidia.exists()) {

            dirMidia.mkdirs();
        }

        mCaminhoFoto = new File(dirMidia, "midia" + nomeMidia + ".jpg");

        Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        it.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mCaminhoFoto));
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(DiarioAcitivity.this, new String[] {Manifest.permission.CAMERA}, 1);
        }
        startActivityForResult(it, 1);
    }

    public void novoAudio(){
        nomeMidia = DateFormat.format("yyyy-MM-dd_hhmmss", new Date()).toString() + "audio";

        File dirMidia = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "Immunize");

        if (!dirMidia.exists()) {

            dirMidia.mkdirs();
        }

        mCaminhoaudio = new File(dirMidia, "midia" + nomeMidia + ".3gp");
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
            return Util.carregarImagem(mCaminhoFoto, 1800, 2500);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap){
            super.onPostExecute(bitmap);

            if(bitmap != null){
                imgFoto.setImageBitmap(bitmap);

                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("midia_fotodiario_prefs", Context.MODE_PRIVATE);

                sharedPreferences.edit().putString("ULTIMA_FOTO_DIARIO", mCaminhoFoto.getAbsolutePath()).commit();

                Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);

                Uri contentUri = Uri.parse(mCaminhoFoto.getAbsolutePath());

                mediaScanIntent.setData(contentUri);

                getApplicationContext().sendBroadcast(mediaScanIntent);
            }
        }
    }
    public void novoVideo(View view){
        nomeMidia = DateFormat.format("yyyy-MM-dd_hhmmss", new Date()).toString() + "video";

        File dirMidia = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "Immunize");

        if (!dirMidia.exists()) {

            dirMidia.mkdirs();
        }
        File caminhoVideo = new File(dirMidia, "midia" + nomeMidia + ".mp4");

        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        takeVideoIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(caminhoVideo));
        if (takeVideoIntent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(takeVideoIntent, 2);
        }
    }

    private void carregarVideo(){
        if(mVideoUri != null){
            mVideoView = (VideoView) this.findViewById(R.id.vvVideo);
            mVideoView.setVideoURI(mVideoUri);
            mVideoView.seekTo(posicao);

            if (!mExecutando) {
                mVideoView.start();
            }
            SharedPreferences sharedPreferences = this.getSharedPreferences("midia_video_prefs", Context.MODE_PRIVATE);

            sharedPreferences.edit().putString("ULTIMO_VIDEO", mVideoUri.toString()).commit();

            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);

            Uri contentUri = Uri.parse(mVideoUri.toString());

            mediaScanIntent.setData(contentUri);

            this.sendBroadcast(mediaScanIntent);
        }
    }

    @Override
    public void onPause(){
        super.onPause();
        pararDeGravar();
        pararDeTocar();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        mExecutando = mVideoView.isPlaying();
        posicao = mVideoView.getCurrentPosition();
       /* prefs.edit().putString("nomeResponsavel", edtNomeResponsavel.getText().toString()).commit();
        prefs.edit().putString("primeiraPalavra", edtPirmeiraPalavra.getText().toString()).commit();*/

        if(posicao == mVideoView.getDuration()){
            posicao = 0;
        }
    }
}

package br.com.immunize.navigationdrawer.NAVI.Activities;

import android.Manifest;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
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
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.provider.MediaStore;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;

import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.net.URI;

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
    static final int REQUEST_IMAGE_CAPTURE = 1;
    ImageView imgFoto;
    ImageButton btnFoto;
    File mCaminhoFoto;
    int mLarguraImage;
    int mAlturaImage;
    String caminhoFoto;

    //VIDEO
    static final int REQUEST_VIDEO_CAPTURE = 1;
    private VideoView videoView;
    private Button btRecordaVideo;
    String caminhoVideo;

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

        //audio
        btnPlay = (ImageButton) findViewById(R.id.btnPlay);
        btnGravar = (ImageButton) findViewById(R.id.btnGravar);

        btnGravar.setOnClickListener(this);
        btnPlay.setOnClickListener(this);

        String caminhoaudio = Util.carregarUltimaMidia(this, Util.MIDIA_AUDIO);
        if(caminhoaudio != null){
            mCaminhoaudio = new File(caminhoaudio);
        }

        chronometer = (Chronometer) findViewById(R.id.chronometer);

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
        caminhoFoto  = UtilMidia.carregarUltimaMidia(this, UtilMidia.MIDIA_FOTO);

        imgFoto = (ImageView) findViewById(R.id.imgFoto);
        btnFoto = (ImageButton) findViewById(R.id.btnFoto);

        if(caminhoFoto != null){
            mCaminhoFoto = new File(caminhoFoto);

        }

        btnFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCaminhoFoto = UtilMidia.novaMidia(UtilMidia.MIDIA_FOTO);

                Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                it.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mCaminhoFoto));
                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
                    ActivityCompat.requestPermissions(DiarioAcitivity.this, new String[] {Manifest.permission.CAMERA}, 1);
                }
                startActivityForResult(it, Util.REQUESTCODE_FOTO);
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnGravar:
                btnGravarClick();
                break;
            case R.id.btnPlay:
                btnPlayClick();
                break;
        }
    }

    private void carregarImagem(){
        if(mCaminhoFoto != null && mCaminhoFoto.exists()){
            Bitmap bp =  Util.carregarImagem(mCaminhoFoto, mLarguraImage, mAlturaImage);
            if(bp != null)
            imgFoto.setImageBitmap(bp);
            Util.salvarUltimaMidia(this, Util.MIDIA_FOTO, mCaminhoFoto.getAbsolutePath());

        }

        //VIDEO
        videoView = (VideoView) findViewById(R.id.vvVideo);
        btRecordaVideo = (Button) findViewById(R.id.btRecordaVideo);
        btRecordaVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakeVideoIntent();
            }
        });
        caminhoVideo  = UtilMidia.carregarUltimaMidia(this, UtilMidia.MIDIA_VIDEO);



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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_diario, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void dispatchTakeVideoIntent() {
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Util.REQUESTCODE_FOTO && resultCode == Activity.RESULT_OK) {
//            Uri videoUri = data.getData();
            /*videoView.setVideoURI(videoUri);
            videoView.requestFocus();
            videoView.start();*/
            mLarguraImage = imgFoto.getWidth();
            mAlturaImage = imgFoto.getHeight();
            carregarImagem();

        }
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
                        chronometer.setBase(SystemClock.elapsedRealtime());
                        chronometer.start();
                        mTocando = true;
                    }
                });
                mediaPlayer.prepare();
                mediaPlayer.start();
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
            mCaminhoaudio = Util.novaMidia(Util.MIDIA_AUDIO, "audio_diario");

            mediaRecorder = new MediaRecorder();
            if (PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.RECORD_AUDIO))
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mediaRecorder.setOutputFile(mCaminhoaudio.getAbsolutePath());
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

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
        btnGravar.setImageResource(mGravando ? android.R.drawable.ic_media_pause : android.R.drawable.ic_btn_speak_now);
        btnGravar.setEnabled(!mTocando);

        btnPlay.setImageResource(mTocando ? android.R.drawable.ic_media_pause : android.R.drawable.ic_media_play);
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
            Util.salvarUltimaMidia(this, Util.MIDIA_AUDIO, mCaminhoaudio.getAbsolutePath());
        }
    }
}

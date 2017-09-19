package br.com.immunize.navigationdrawer.NAVI.Activities;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
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
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
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
public class DiarioAcitivity extends AppCompatActivity implements View.OnClickListener {

    public EditText edtNomeResponsavel;
    public EditText edtPirmeiraPalavra;
    SharedPreferences prefs;
    Toolbar tbr;

    //Audio
    ImageButton btnGravar;
    ImageButton btnPlay;
    Chronometer mChronometer;

    MediaRecorder mMediaRecorder;
    MediaPlayer mMediaPlayer;
    File mCaminhoAudio;
    boolean mGravando;
    boolean mTocando;

    Button btnFoto;
    File mCaminhoFoto;
    ImageView mImageViewFoto;
    CarregarImageTask mTask;

    View lt;

    Button btRecordaVideo;
    VideoView videoView;
    Uri mVideoUri;
    int mPosicao;
    boolean mExecutando;

    private static final int CONTENT_VIEW_ID = 10101010;
    private GoogleApiClient client;
    static final int REQUEST_VIDEO_CAPTURE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_diario);
        super.onCreate(savedInstanceState);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setBackgroundDrawable(getResources().getDrawable(R.drawable.action_bar));

        String caminhoFoto = Util.carregarUltimaMidiaResponsavel(getApplicationContext(), Util.MIDIA_FOTO);

        if (caminhoFoto != null){
            mCaminhoFoto = new File(caminhoFoto);
        }

        mImageViewFoto = (ImageView) findViewById(R.id.imgFoto);
        btnFoto = (Button) findViewById(R.id.btnFoto);
        carregarImagem();

        btnFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //dispatchTakePictureIntent();
                mCaminhoFoto = Util.novaMidiaResponsavel(Util.MIDIA_FOTO);
                TirarFoto();
            }
        });


        client = new GoogleApiClient.Builder(this)
                .addApi(Drive.API)
                .addScope(Drive.SCOPE_FILE)
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(@Nullable Bundle bundle) {

                    }

                    @Override
                    public void onConnectionSuspended(int i) {

                    }
                })
                .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                    }
                }).build();

        lt = new View(this);
        edtNomeResponsavel = (EditText) findViewById(R.id.edtNomeResponsavel);
        edtPirmeiraPalavra = (EditText) findViewById(R.id.edtPrimeiraPalavra);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        edtNomeResponsavel.setText(prefs.getString("nomeResponsavel", ""));
        edtPirmeiraPalavra.setText(prefs.getString("primeiraPalavra", ""));

        btRecordaVideo = (Button) findViewById(R.id.btRecordaVideo);
        videoView = (VideoView) findViewById(R.id.vvVideo);

        //Audio
        String caminhoAudio = Util.carregarUltimaMidia(this, Util.MIDIA_AUDIO);

        String caminhoVideo = Util.carregarUltimaMidia(this, Util.MIDIA_VIDEO);

        if (caminhoAudio != null) {
            mCaminhoAudio = new File(caminhoAudio);
        }

        if (caminhoVideo != null) {
            mVideoUri = Uri.parse(caminhoVideo);
        }

        videoView.setMediaController(new MediaController(this));

        btnGravar = (ImageButton) findViewById(R.id.btnGravar);
        btnPlay = (ImageButton) findViewById(R.id.btnPlay);

        mChronometer = (Chronometer) findViewById(R.id.chronometer);

        btnGravar.setOnClickListener(this);
        btnPlay.setOnClickListener(this);

        /*//Foto
        String caminhoFoto = Util2.carregarUltimaMidia(this, Util2.MIDIA_FOTO);

        if (caminhoFoto != null){
            mCaminhoFoto = new File(caminhoFoto);
        }
        btnFoto = (Button) findViewById(R.id.btnFoto);
        btnFoto.setOnClickListener(this);
        mImageViewFoto = (ImageView) findViewById(R.id.imgFoto);

        lt.getViewTreeObserver().addOnGlobalLayoutListener(this);*/

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

        btRecordaVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakeVideoIntent();
            }
        });
    }

    public void TirarFoto(){
        Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        it.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mCaminhoFoto));

        startActivityForResult(it, Util.REQUESTCODE_FOTO);
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
            return Util.carregarImagemResponsavel(mCaminhoFoto, 1800, 1800);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap){
            super.onPostExecute(bitmap);

            if(bitmap != null){

                Util.salvarUltimaMidiaResponsavel(getApplicationContext(), Util.MIDIA_FOTO, mCaminhoFoto.getAbsolutePath());
                mImageViewFoto.setImageBitmap(bitmap);
            }
        }
    }

    public void onDestroyView(){
        mExecutando = videoView.isPlaying();
        mPosicao = videoView.getCurrentPosition();

        if(mPosicao == videoView.getDuration()){
            mPosicao = 0;
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        onDestroyView();
    }

    private void dispatchTakeVideoIntent() {
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
        }
    }

   /* @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {
            Uri videoUri = data.getData();
            videoView.setVideoURI(videoUri);
            videoView.requestFocus();
            videoView.start();
            carregarVideo();
        }
    }

    private void carregarVideo(){
        if(mVideoUri != null){
            videoView.setVideoURI(mVideoUri);
            videoView.seekTo(mPosicao);
            if (mExecutando){
                videoView.start();
            } Util.salvarUltimaMidia(this, Util.MIDIA_VIDEO, mVideoUri.toString());
        }
    }*/


    @Override
    public void onPause() {
        super.onPause();
        pararDeGravar();
        pararDeTocar();
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
/*
        if(view.getId() == R.id.btnFoto){
            mCaminhoFoto = Util2.novaMidia(Util2.MIDIA_FOTO);

            Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            it.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mCaminhoFoto));

            startActivityForResult(it, Util2.REQUESTCODE_FOTO);
        }*/
        File caminhoVideo = Util.novaMidia(Util.MIDIA_VIDEO);
        Intent takeVideoIntent= new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        takeVideoIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(caminhoVideo));
        startActivityForResult(takeVideoIntent, Util.REQUESTCODE_VIDEO);
    }

    private void btnPlayClick() {
        mChronometer.stop();
        if (mTocando) {
            pararDeTocar();
        } else if (mCaminhoAudio != null && mCaminhoAudio.exists()) {

            try {
                mMediaPlayer = new MediaPlayer();
                mMediaPlayer.setDataSource(mCaminhoAudio.getAbsolutePath());
                mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {

                        mTocando = false;
                        mChronometer.stop();
                        atualizarBotoes();
                    }
                });

                mMediaPlayer.prepare();
                mMediaPlayer.start();
                mChronometer.setBase(SystemClock.elapsedRealtime());
                mChronometer.start();
                mTocando = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        atualizarBotoes();
    }

    private void btnGravarClick() {

        mChronometer.stop();

        if (mGravando) {
            pararDeGravar();
        } else {
            mCaminhoAudio = Util.novaMidia(Util.MIDIA_AUDIO);

            mMediaRecorder = new MediaRecorder();
            mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mMediaRecorder.setOutputFile(mCaminhoAudio.getAbsolutePath());
            mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

            try {
                mMediaRecorder.prepare();
                mMediaRecorder.start();
                mChronometer.setBase(SystemClock.elapsedRealtime());
                mChronometer.start();
                mGravando = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
            atualizarBotoes();
        }
    }

    public void atualizarBotoes() {
        btnGravar.setImageResource(mGravando ? android.R.drawable.ic_media_pause : android.R.drawable.ic_btn_speak_now);

        btnGravar.setEnabled(!mTocando);

        btnPlay.setImageResource(mTocando ? android.R.drawable.ic_media_pause : android.R.drawable.ic_media_play);

        btnPlay.setEnabled(!mGravando);
    }

    public void pararDeTocar() {
        if (mMediaPlayer != null && mTocando) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
            mTocando = false;
        }
    }

    private void pararDeGravar() {
        if (mMediaRecorder != null && mGravando) {
            mMediaRecorder.stop();
            mMediaRecorder.release();
            mMediaRecorder = null;
            mGravando = false;

            Util.salvarUltimaMidia(this, Util.MIDIA_AUDIO, mCaminhoAudio.getAbsolutePath());
        }
    }



    /*@Override
    public void onStart() {
        super.onStart();

        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "DiarioAcitivity Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://br.com.immunize.navigationdrawer.NAVI.Activities/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "DiarioAcitivity Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://br.com.immunize.navigationdrawer.NAVI.Activities/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }*/

  /*  //Foto
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Activity.RESULT_OK && requestCode == Util2.REQUESTCODE_FOTO){
            carregarImagem();
        }
    }

    @Override
    public void onGlobalLayout(){
        if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1){
            lt.getViewTreeObserver().removeOnGlobalLayoutListener(this);
        } else{
            lt.getViewTreeObserver().removeOnGlobalLayoutListener(this);
        }

        mLarguraImage = mImageViewFoto.getWidth();
        mAlturaImage = mImageViewFoto.getHeight();
        carregarImagem();
    }

 *//*   @Override
    public  void onClick(View v){
        if(v.getId() == R.id.btnFoto){
            mCaminhoFoto = Util.novaMidia(Util.MIDIA_FOTO);

            Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            it.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mCaminhoFoto));

            startActivityForResult(it, Util.REQUESTCODE_FOTO);
        }
    }*//*

    private void carregarImagem(){
        if(mCaminhoFoto != null && mCaminhoFoto.exists()){
            if(mTask == null || mTask.getStatus() != AsyncTask.Status.RUNNING){
                mTask = new CarregarImageTask();
                mTask.execute();
            }
        }
    }

    class CarregarImageTask extends  AsyncTask<Void, Void, Bitmap>{

        @Override

        protected Bitmap doInBackground(Void... voids){
            return Util2.carregarImagem(mCaminhoFoto, mLarguraImage, mAlturaImage);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap){
            super.onPostExecute(bitmap);
            Context c = App.getContext();

            if(bitmap != null){
                mImageViewFoto.setImageBitmap(bitmap);
                Util2.salvarUltimaMidia(getApplicationContext(), Util2.MIDIA_FOTO, mCaminhoFoto.getAbsolutePath());
            }
        }
    }*/
}

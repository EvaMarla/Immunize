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
    ImageView mImageViewFoto;

    View lt;

    Button btRecordaVideo;
    VideoView videoView;
    Uri mVideoUri;
    int mPosicao;
    boolean mExecutando;

    private static final int CONTENT_VIEW_ID = 10101010;
    private GoogleApiClient client;
    static final int REQUEST_VIDEO_CAPTURE = 1;
    static final int REQUEST_IMAGE_CAPTURE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_diario);
        super.onCreate(savedInstanceState);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setBackgroundDrawable(getResources().getDrawable(R.drawable.action_bar));

        mImageViewFoto = (ImageView) findViewById(R.id.imgFoto);
        btnFoto = (Button) findViewById(R.id.btnFoto);

        btnFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
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
        //videoView = (VideoView) findViewById(R.id.vvVideo);

        //Audio
        String caminhoAudio = Util.carregarUltimaMidia(this, Util.MIDIA_AUDIO);

        if (caminhoAudio != null) {
            mCaminhoAudio = new File(caminhoAudio);
        }

        btnGravar = (ImageButton) findViewById(R.id.btnGravar);
        btnPlay = (ImageButton) findViewById(R.id.btnPlay);

        mChronometer = (Chronometer) findViewById(R.id.chronometer);

        btnGravar.setOnClickListener(this);
        btnPlay.setOnClickListener(this);

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
//                startActivity(new Intent(getApplicationContext(), VideoActivity.class));
            }
        });
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mImageViewFoto.setImageBitmap(imageBitmap);
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
}

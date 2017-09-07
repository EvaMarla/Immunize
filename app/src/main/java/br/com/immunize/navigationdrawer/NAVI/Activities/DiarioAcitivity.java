package br.com.immunize.navigationdrawer.NAVI.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Chronometer;
import android.widget.EditText;
import android.support.v4.app.FragmentActivity;
import android.widget.ImageButton;

import java.io.File;
import java.io.IOException;

import br.com.immunize.navigationdrawer.NAVI.Diario.GravarAudioFragment;
import br.com.immunize.navigationdrawer.NAVI.Diario.MainActivityFoto;
import br.com.immunize.navigationdrawer.NAVI.Diario.Util;
import br.com.immunize.navigationdrawer.R;

/**
 * Created by Marla on 22/08/2017.
 */
public class DiarioAcitivity extends  AppCompatActivity implements View.OnClickListener{

    public EditText edtNomeResponsavel;
    public EditText edtPirmeiraPalavra;
    SharedPreferences prefs;
    Toolbar tbr;

    ImageButton btnGravar;
    ImageButton btnPlay;
    Chronometer mChronometer;

    MediaRecorder mMediaRecorder;
    MediaPlayer mMediaPlayer;
    File mCaminhoAudio;
    boolean mGravando;
    boolean mTocando;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_diario);
        super.onCreate(savedInstanceState);

        edtNomeResponsavel = (EditText) findViewById(R.id.edtNomeResponsavel);
        edtPirmeiraPalavra = (EditText) findViewById(R.id.edtNomeResponsavel);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        edtNomeResponsavel.setText(prefs.getString("nomeResponsavel", ""));
        edtPirmeiraPalavra.setText(prefs.getString("primeiraPalavra", ""));

        String caminhoAudio = Util.carregarUltimaMidia(this, Util.MIDIA_AUDIO);

        if (caminhoAudio != null) {
            mCaminhoAudio = new File(caminhoAudio);
        }

        btnGravar = (ImageButton)findViewById(R.id.btnGravar);
        btnPlay = (ImageButton) findViewById(R.id.btnPlay);

        mChronometer = (Chronometer) findViewById(R.id.chronometer);

        btnGravar.setOnClickListener(this);
        btnPlay.setOnClickListener(this);

        edtPirmeiraPalavra.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count)
            {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after)
            {
            }

            @Override
            public void afterTextChanged(Editable s)
            {
                prefs.edit().putString("primeiraPalavra", s.toString()).commit();
            }
        });

        edtNomeResponsavel.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count)
            {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after)
            {
            }

            @Override
            public void afterTextChanged(Editable s)
            {
                prefs.edit().putString("nomeResponsavel", s.toString()).commit();
            }
        });
    }

    public void TirarFoto(View view){
        startActivity(new Intent(getApplicationContext(), MainActivityFoto.class));

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

    public void atualizarBotoes(){
        btnGravar.setImageResource(mGravando? android.R.drawable.ic_media_pause : android.R.drawable.ic_btn_speak_now );

        btnGravar.setEnabled(!mTocando);

        btnPlay.setImageResource(mTocando? android.R.drawable.ic_media_pause : android.R.drawable.ic_media_play);

        btnPlay.setEnabled(!mGravando);
    }

    public void pararDeTocar(){
        if(mMediaPlayer != null && mTocando){
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
            mTocando = false;
        }
    }

    private void pararDeGravar(){
        if(mMediaRecorder != null && mGravando){
            mMediaRecorder.stop();
            mMediaRecorder.release();
            mMediaRecorder = null;
            mGravando = false;

            Util.salvarUltimaMidia(this, Util.MIDIA_AUDIO, mCaminhoAudio.getAbsolutePath());
        }
    }
}

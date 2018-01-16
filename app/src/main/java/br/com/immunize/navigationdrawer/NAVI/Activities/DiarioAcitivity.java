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
public class DiarioAcitivity extends AppCompatActivity {

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


    //Video
    static final int REQUEST_VIDEO_CAPTURE = 1;

    private VideoView videoView;
    private Button btRecordaVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_diario);
        super.onCreate(savedInstanceState);

        //foto
        final String caminhoFoto = UtilMidia.carregarUltimaMidia(this, UtilMidia.MIDIA_FOTO);

        imgFoto = (ImageView) findViewById(R.id.imgFoto);
        btnFoto = (ImageButton) findViewById(R.id.btnFoto);

        if(caminhoFoto != null){
            mCaminhoFoto = new File(caminhoFoto);
            Util.salvarUltimaMidia(this, Util.MIDIA_FOTO, mCaminhoFoto.getAbsolutePath());
        }

        btnFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAlturaImage = imgFoto.getHeight();
                mLarguraImage = imgFoto.getWidth();

                mCaminhoFoto = UtilMidia.novaMidia(UtilMidia.MIDIA_FOTO);

                Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                it.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mCaminhoFoto));
                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
                    ActivityCompat.requestPermissions(DiarioAcitivity.this, new String[] {Manifest.permission.CAMERA}, 1);
                }
                startActivityForResult(it, Util.REQUESTCODE_FOTO);
            }
        });

        Bitmap bp =  Util.carregarImagem(mCaminhoFoto, mLarguraImage, mAlturaImage);
        imgFoto.setImageBitmap(bp);

        videoView = (VideoView) findViewById(R.id.vvVideo);
        btRecordaVideo = (Button) findViewById(R.id.btRecordaVideo);
        btRecordaVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakeVideoIntent();
            }
        });

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setBackgroundDrawable(getResources().getDrawable(R.drawable.action_bar_transparente));

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

    /*private void carregarImagem(){
        if(mCaminhoFoto != null && mCaminhoFoto.exists()){
            if (mTask == null || mTask.getStatus() != )
        }
    }

    Class CarregarImagemTask extends AsyncTask<Void, Void, Bitmap>{
        @Override
                protected Bitmap doInBackground(Void voids){

        }
    }
*/
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

    // ELE VEM DIRETO PRA CÁ, MAS ESSE É DO VÍDEO, TEM QUE TEM UM PRA FOTO
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == Util.REQUESTCODE_FOTO) {
            Util.carregarImagem(mCaminhoFoto, mLarguraImage,mAlturaImage);
        }
    }

    public static Typeface setFonte(Context context)
    {
        return Typeface.createFromAsset(context.getAssets(),"ARLRDBD.TTF");
    }
}

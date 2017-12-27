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
public class DiarioAcitivity extends AppCompatActivity {

    public EditText edtNomeResponsavel;
    public EditText edtPirmeiraPalavra;
    SharedPreferences prefs;

    //FOTO
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private ImageView imgFoto;
    private Button btnFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_diario);
        super.onCreate(savedInstanceState);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setBackgroundDrawable(getResources().getDrawable(R.drawable.action_bar));

        edtNomeResponsavel = (EditText) findViewById(R.id.edtNomeResponsavel);
        edtPirmeiraPalavra = (EditText) findViewById(R.id.edtPrimeiraPalavra);

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

        imgFoto = (ImageView) findViewById(R.id.imgFoto);
        btnFoto = (Button) findViewById(R.id.btnFoto);

        btnFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
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
            imgFoto.setImageBitmap(imageBitmap);
        }
    }
}

package br.com.immunize.navigationdrawer.NAVI.Activities;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

import br.com.immunize.navigationdrawer.NAVI.NAVI.MainActivity;
import br.com.immunize.navigationdrawer.R;

/**
 * Created by Marla on 26/10/2016.
 */
public class testegif extends ActionBarActivity {

    private ImageView img;
 //   private static int GIF_CARREGAMENTO_TIME = 9000;


    protected void onCreate(Bundle savedinstanceState){
        super.onCreate(savedinstanceState);
        setContentView(R.layout.testando);

        img = (ImageView) findViewById(R.id.imgAnim);

        img.post(new Runnable() {
            @Override
            public void run() {
                ((AnimationDrawable) img.getBackground()).start();
            }
        });

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent();
                intent.setClass(testegif.this, MainActivity.class);
                startActivity(intent);
            }
        }, 4500);
    }
}

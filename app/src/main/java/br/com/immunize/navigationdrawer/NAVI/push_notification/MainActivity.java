package br.com.immunize.navigationdrawer.NAVI.push_notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import br.com.immunize.navigationdrawer.R;

public class MainActivity extends AppCompatActivity {

    private static final int NOTIFICACAO_SIMPLES = 1;

    EditText mEdtTxt;
    MeuReceiver meuReciver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.push_main);

        mEdtTxt = (EditText) findViewById(R.id.edtTxt);

        meuReciver = new MeuReceiver();
        registerReceiver(meuReciver, new IntentFilter(NotificationUtils.ACAO_DELETE));
        registerReceiver(meuReciver, new IntentFilter(NotificationUtils.ACAO_NOTIFICACAO));
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        unregisterReceiver(meuReciver);
    }

    public void criaNotificacaoSimples(){

        //NotificationUtils.criarNotificacaoSimples(this, mEdtTxt.getText().toString(),NOTIFICACAO_SIMPLES);
        NotificationUtils.criarNotificacaoSimples(this, "Atenção! Falta 1 dia para próxima vacina!", NOTIFICACAO_SIMPLES);
    }

    class MeuReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(MainActivity.this, intent.getAction(), Toast.LENGTH_SHORT).show();

        }
    }
}

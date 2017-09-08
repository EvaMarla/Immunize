package br.com.immunize.navigationdrawer.NAVI.push_notification;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import br.com.immunize.navigationdrawer.R;

/**
 * Created by Marla on 15/08/2017.
 */
public class NotificationUtils {

    public static final String ACAO_DELETE = "testes.push_notification.Push_Notification.DELETE_NOTIFICACAO";
    public static final String ACAO_NOTIFICACAO = "testes.push_notification.Push_Notification.ACAO_NOTIFICACAO";

    private static PendingIntent criarPendingIntent(Context ctx, String texto, int id){

        Intent resultIntent = new Intent(ctx, DetalheActivity.class);
        resultIntent.putExtra(DetalheActivity.EXTRA_TEXTO, texto);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(ctx);
        stackBuilder.addParentStack(DetalheActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        return stackBuilder.getPendingIntent(id, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public static void criarNotificacaoSimples(Context ctx, String texto, int id){

        PendingIntent resultPedingIntent = criarPendingIntent(ctx, texto, id);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(ctx).setDefaults(Notification.DEFAULT_ALL).setSmallIcon(R.drawable.icone).setContentTitle("Vacinar seu filho " + id).setContentText(texto).setContentIntent(resultPedingIntent);

        NotificationManagerCompat nm = NotificationManagerCompat.from(ctx);

        nm.notify(id, mBuilder.build());
    }


}

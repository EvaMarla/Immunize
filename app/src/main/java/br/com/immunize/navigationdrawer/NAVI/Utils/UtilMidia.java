package br.com.immunize.navigationdrawer.NAVI.Utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.text.format.DateFormat;

import java.io.File;
import java.util.Date;

/**
 * Created by Marla on 27/12/2017.
 */

public class UtilMidia {

    public final static int MIDIA_FOTO = 0;
    public final static int MIDIA_VIDEO = 1;
    public final static int MIDIA_AUDIO = 2;

    public final static int REQUESTCODE_FOTO = 1;
    public final static int REQUESTCODE_VIDEO = 2;
    public final static int REQUESTCODE_AUDIO = 3;

    private static final String ULTIMA_FOTO = "ultima_foto";
    private static final String ULTIMO_VIDEO = "ultimo_video";
    private static final String ULTIMO_AUDIO = "ultimo_audio";

    private static final String PREFERENCIA_MIDIA = "midia_prefs";
    private static final String PASTA_MIDIA = "Immunize";

    private static final String[] EXTENSOES = new String[]{".jpg", ".mp4", ".3gp"};
    private static final String[] CHAVES_PREF = new String[]{ULTIMA_FOTO, ULTIMO_VIDEO, ULTIMO_AUDIO};

    //gera um nome utilizando a data
    public static File novaMidia(int tipo){

        String nomeMidia = DateFormat.format("yyyy-MM-hhmmss", new Date()).toString();

        File dirMidia = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), PASTA_MIDIA);

        if(!dirMidia.exists()){
            dirMidia.mkdirs();
        }
        return new File(dirMidia, nomeMidia + EXTENSOES[tipo]);
    }

    //armazena o caminho em um shared
    public static void salvarUltimaMidia(Context ctx, int tipo, String midia){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(PREFERENCIA_MIDIA, Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(CHAVES_PREF[tipo], midia).commit();

        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);

        Uri contentUri = Uri.parse(midia);
        mediaScanIntent.setData(contentUri);
        ctx.sendBroadcast(mediaScanIntent);
    }

    //carrega o que salvou
    public static String carregarUltimaMidia(Context ctx, int tipo){

        return ctx.getSharedPreferences(PREFERENCIA_MIDIA, Context.MODE_PRIVATE).getString(CHAVES_PREF[tipo], null);
    }

    public static Bitmap carregarImagem(File imagem, int largura, int altura){

        if(largura == 0 || altura == 0){
            return null;
        }

        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imagem.getAbsolutePath(), bmOptions);

        int larguraFoto = bmOptions.outWidth;
        int alturaFoto = bmOptions.outHeight;
        int escala = Math.min(larguraFoto/largura, alturaFoto/altura);

        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = escala;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(imagem.getAbsolutePath(), bmOptions);
        return bitmap;
    }
}

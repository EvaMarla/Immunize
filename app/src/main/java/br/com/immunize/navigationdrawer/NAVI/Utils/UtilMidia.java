package br.com.immunize.navigationdrawer.NAVI.Utils;

import java.io.File;
import java.sql.Date;
import java.text.DateFormat;


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

    /*public static File novaMidia(int tipo){

        String nomeMidia = DateFormat.format("yyyy-MM-hhmmss", new Date()).toString();
    }*/
}

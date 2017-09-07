package br.com.immunize.navigationdrawer.NAVI.Utils;

import android.app.Application;
import android.content.Context;

/**
 * Created by Marla on 07/09/2017.
 */
public class App extends Application {

    private static Context mContext;

    public static Context getContext() {
        return mContext;
    }

    public static void setContext(Context mContext) {
        mContext = mContext;
    }
}

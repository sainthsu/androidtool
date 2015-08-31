package org.flakor.androidtool;

import android.app.Application;
import android.content.Context;

/**
 * Created by saint on 18/5/15.
 */
public class App extends Application
{
    static Context appContext;

    @Override
    public void onCreate() {
        appContext = this;
        super.onCreate();

        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(getApplicationContext());
    }
}

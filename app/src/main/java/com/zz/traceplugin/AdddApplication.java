package com.zz.traceplugin;

import android.app.Application;
import android.util.Log;

/**
 * @author zz
 * @date 2023/4/10
 * @describe
 **/
public class AdddApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("ceshi", "onCreate: ");
    }
}

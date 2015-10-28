package com.superlity.strongservice;

import android.app.Application;
import android.content.Context;

/**
 * Created by lion on 15-9-29.
 */
public class TempApplication extends Application {

    private static TempApplication context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    public static Context getContext() {
        return context;
    }
}

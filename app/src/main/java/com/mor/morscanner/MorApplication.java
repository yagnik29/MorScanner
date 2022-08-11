package com.mor.morscanner;

import android.app.Application;

import timber.log.Timber;

public class MorApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }
}

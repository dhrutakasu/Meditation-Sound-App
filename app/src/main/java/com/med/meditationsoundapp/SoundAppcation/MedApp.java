package com.med.meditationsoundapp.SoundAppcation;

import android.app.Application;

public class MedApp extends Application {
    public static MedApp App = null;

    public static MedApp getInstance() {
        return App;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        App = this;
    }
}

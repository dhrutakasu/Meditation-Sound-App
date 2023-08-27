package com.med.meditationsoundapp.SoundAppcation;

import android.app.Application;

public class MedApp extends Application {
//    https://www.figma.com/file/PipEJluDafMjKehoIXyCVS/sleeping-sounds?type=design&node-id=228-1545&mode=design&t=L34F9c09UqH4N5AU-0
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

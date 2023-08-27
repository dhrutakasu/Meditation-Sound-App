package com.med.meditationsoundapp.SoundUi.MedActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.collection.ArraySet;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;

import com.med.meditationsoundapp.R;
import com.med.meditationsoundapp.SoundConstants.MedConstants;
import com.med.meditationsoundapp.SoundModel.PlyerModel;
import com.med.meditationsoundapp.SoundModel.SoundModel;

import java.io.IOException;
import java.util.ArrayList;

public class MedSplashActivity extends AppCompatActivity {

    private long seconds = 1;
    private long secondsRemaining;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_med_splash);
        MedInitViews();
        MedInitListeners();
        MedInitActions();
    }

    private void MedInitViews() {

    }

    private void MedInitListeners() {

    }

    private void MedInitActions() {

        CountDownTimer countDownTimer = new CountDownTimer(seconds * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                secondsRemaining = ((millisUntilFinished / 1000) + 1);
            }

            @Override
            public void onFinish() {
                secondsRemaining = 0;
                GotoMainActivity();
            }
        };
        countDownTimer.start();
    }

    private void GotoMainActivity() {
//        if (!new MyAppPref(SplashActivity.this).getBoolean(MyAppPref.APP_WALKTHROUGH, false)) {
//            startActivity(new Intent(this, WalkthroughActivity.class));
//            new MyAppPref(SplashActivity.this).putBoolean(MyAppPref.APP_WALKTHROUGH, true);
//        } else {
        startActivity(new Intent(this, MedMainActivity.class));
//        }
        finish();
    }
}
package com.med.meditationsoundapp.SoundUi.MedActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;

import com.med.meditationsoundapp.R;

public class MedMainActivity extends AppCompatActivity {

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_med_main);
        MedInitViews();
        MedInitListeners();
        MedInitActions();
    }

    private void MedInitViews() {
        context=this;

    }

    private void MedInitListeners() {

    }

    private void MedInitActions() {

    }
}
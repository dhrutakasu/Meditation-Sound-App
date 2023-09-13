package com.med.meditationsoundapp.SoundUi.MedActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdSize;
import com.med.meditationsoundapp.R;
import com.med.meditationsoundapp.SoundAds.MedAd_Banner;
import com.med.meditationsoundapp.SoundConstants.MedConstants;
import com.med.meditationsoundapp.SoundUtils.MedPref;

public class PrivacyActivity extends AppCompatActivity {

    private Context context;

    private ImageView IvBack;
    private TextView TvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy);
        initViews();
        initListeners();
        initActions();
    }

    private void initViews() {
        context = this;
        IvBack = (ImageView) findViewById(R.id.IvBack);
        TvTitle = (TextView) findViewById(R.id.TvTitle);
    }

    private void initListeners() {
        IvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initActions() {
        TvTitle.setText("Policy Policy");
        if (MedConstants.isConnectingToInternet(context)) {
            MedAd_Banner.getMedInstance().showBannerAds(PrivacyActivity.this, AdSize.LARGE_BANNER, (RelativeLayout) findViewById(R.id.RlMedBannerAdView), (RelativeLayout) findViewById(R.id.RlMedBannerAd));
        }
        if (new MedPref(context).getBoolean(MedPref.BOOL_NIGHT, false)) {
            Window window = getWindow();
            window.setStatusBarColor(ContextCompat.getColor(context, R.color.purple_200_dark));
        } else {
            Window window = getWindow();
            window.setStatusBarColor(ContextCompat.getColor(context, R.color.purple_200));
        }
    }
}
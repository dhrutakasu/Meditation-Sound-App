package com.med.meditationsoundapp.SoundUi.MedActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.WindowManager;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.appopen.AppOpenAd;
import com.med.meditationsoundapp.R;
import com.med.meditationsoundapp.SoundAds.MedAd_Interstitial;
import com.med.meditationsoundapp.SoundAds.MedAd_Native;
import com.med.meditationsoundapp.SoundAds.MedAdsJsonPass;
import com.med.meditationsoundapp.SoundConstants.MedConstants;
import com.med.meditationsoundapp.SoundUtils.MedPref;

import org.json.JSONException;
import org.json.JSONObject;

public class MedSplashActivity extends AppCompatActivity {

    private long seconds = 0;
    private Context context;
    private String url = "https://anshinfotech.in/sleeping_sounds/sound.json";
//    private String url = "https://anshinfotech.in/api/soundapi.json";
    String MedAdShow, MedAppOpen, MedBannerAdId, MedInterstitialFullScreen = "", MedNativeAd;
    private long IntervalSeconds;
    private AppOpenAd.AppOpenAdLoadCallback MedLoadCallback;
    private AppOpenAd MedAppOpenAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        }
        setContentView(R.layout.activity_med_splash);
        MedInitActions();
    }

    private void MedInitViews() {
        MedLoadCallback = new AppOpenAd.AppOpenAdLoadCallback() {
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                MedAppOpenAd = null;
                GotoMainActivity();
            }

            @Override
            public void onAdLoaded(@NonNull AppOpenAd appOpenAds) {
                MedAppOpenAd = appOpenAds;
                if (MedAppOpenAd != null) {
                    MedAppOpenAd.show(MedSplashActivity.this);
                    MedAppOpenAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            GotoMainActivity();
                        }

                        @Override
                        public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                            MedAppOpenAd = null;
                            GotoMainActivity();
                        }

                        @Override
                        public void onAdImpression() {
                            super.onAdImpression();
                        }

                        @Override
                        public void onAdShowedFullScreenContent() {
                            super.onAdShowedFullScreenContent();
                        }
                    });
                }
            }
        };
        AdRequest request = new AdRequest.Builder().build();
        AppOpenAd.load(this, new MedPref(context).getString(MedPref.MED_AD_OPEN, ""), request, AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT, MedLoadCallback);
    }

    private void createHandler(long seconds) {
        if (MedConstants.isConnectingToInternet(context)) {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    response2 -> {
                        try {
                            JSONObject response = new JSONObject(response2);

                            MedAppOpen = response.getString(MedConstants.MEDOPENAD);
                            MedBannerAdId = response.getString(MedConstants.MEDBANNERAD);
                            MedInterstitialFullScreen = response.getString(MedConstants.MEDINTERSTITALAD);
                            MedNativeAd = response.getString(MedConstants.MEDNATIVEAD);
                            MedAdShow = response.getString(MedConstants.MEDSHOWAD);

                            new MedPref(MedSplashActivity.this).putString(MedPref.MED_SHOW, MedAdShow);
                            new MedPref(MedSplashActivity.this).putString(MedPref.MED_AD_BANNER, MedBannerAdId);
                            new MedPref(MedSplashActivity.this).putString(MedPref.MED_AD_INTER, MedInterstitialFullScreen);
                            new MedPref(MedSplashActivity.this).putString(MedPref.MED_AD_NATIVE, MedNativeAd);
                            new MedPref(MedSplashActivity.this).putString(MedPref.MED_AD_OPEN, MedAppOpen);

                            MedAd_Native.getMedInstance().LoadNativeAds(MedSplashActivity.this);
                            MedAd_Interstitial.getMedInstance().loadInterstitialAds(MedSplashActivity.this);

                            if (MedAdShow.equalsIgnoreCase("yes")) {
                                MedInitViews();
                            } else {
                                CountDownTimer timer = new CountDownTimer(seconds * 1000, 1000) {
                                    @Override
                                    public void onTick(long millisUntilFinished) {
                                        IntervalSeconds = ((millisUntilFinished / 1000) + 1);
                                    }

                                    @Override
                                    public void onFinish() {
                                        IntervalSeconds = 0;
                                        GotoMainActivity();
                                    }
                                };
                                timer.start();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    },
                    error -> {
                    }) {
            };
            MedAdsJsonPass.getMedInstance(getApplicationContext()).addToMedRequestQueue(stringRequest);
        } else {
            Toast.makeText(context, "Please turn on your internet connection...", Toast.LENGTH_LONG).show();
            finish();
        }
    }


    private void MedInitActions() {
        context = this;
        createHandler(3);
    }

    private void GotoMainActivity() {
        startActivity(new Intent(this, MedMainActivity.class));
        finish();
    }
}
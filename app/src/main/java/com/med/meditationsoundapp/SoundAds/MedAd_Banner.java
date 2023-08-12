package com.med.meditationsoundapp.SoundAds;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.med.meditationsoundapp.SoundConstants.MedPref;

public class MedAd_Banner {

    private static MedAd_Banner medAdBanner;

    public static MedAd_Banner getMedInstance() {
        if (medAdBanner == null) {
            medAdBanner = new MedAd_Banner();
        }
        return medAdBanner;
    }

    public void showBannerAds(Activity activitys, AdSize size, RelativeLayout RlBannerV, RelativeLayout RlBannerP) {
        String Bannerstring = new MedPref(activitys).getString(MedPref.MED_AD_BANNER, "");
        AdView view = new AdView(activitys);
        view.setAdSize(size);
        view.setAdUnitId(Bannerstring);
        view.loadAd(new AdRequest.Builder().build());
        view.setAdListener(new AdListener() {

            @Override
            public void onAdClicked() {
            }

            @Override
            public void onAdClosed() {
            }

            @Override
            public void onAdFailedToLoad(LoadAdError loadAdError) {
                RlBannerV.setVisibility(View.INVISIBLE);
            }

            @Override

            public void onAdLoaded() {
            }

            @Override
            public void onAdOpened() {
            }
        });
        RlBannerV.addView(view);
        String show = new MedPref(activitys).getString(MedPref.MED_SHOW, "no");

        if (show.equalsIgnoreCase("yes")) {
            RlBannerP.setVisibility(View.VISIBLE);
        } else {
            RlBannerP.setVisibility(View.INVISIBLE);
        }
    }

    private AdSize getBannerAdSize(Activity activitys) {
        Display defaultDisplay = activitys.getWindowManager().getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        defaultDisplay.getMetrics(displayMetrics);
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(activitys, (int) (((float) displayMetrics.widthPixels) / displayMetrics.density));
    }
}

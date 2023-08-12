package com.med.meditationsoundapp.SoundAds;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.med.meditationsoundapp.SoundConstants.MedPref;

public class MedAd_Interstitial {
    static int OnClickCount = 1;
    private static MedAd_Interstitial medAdInterstitial;
    public InterstitialAd interstitialAd;
    MedCallback medCallback;

    public interface MedCallback {
        void AppCallback();
    }

    public static MedAd_Interstitial getMedInstance() {
        if (medAdInterstitial == null) {
            medAdInterstitial = new MedAd_Interstitial();
        }
        return medAdInterstitial;
    }

    public void loadInterstitialAds(final Activity activitys) {
        MobileAds.initialize(activitys, new OnInitializationCompleteListener() {

            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        InterstitialAd.load(activitys, new MedPref(activitys).getString(MedPref.MED_AD_INTER, ""), new AdRequest.Builder().build(), new InterstitialAdLoadCallback() {

            public void onAdLoaded(InterstitialAd interstitialAd) {
                MedAd_Interstitial.this.interstitialAd = interstitialAd;
                MedAd_Interstitial.this.interstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                    @Override
                    public void onAdDismissedFullScreenContent() {
                        MedAd_Interstitial.this.interstitialAd = null;
                        loadInterstitialAds(activitys);
                        if (medCallback != null) {
                            medCallback.AppCallback();
                            medCallback = null;
                        }
                    }
                    @Override
                    public void onAdFailedToShowFullScreenContent(AdError adError) {

                        MedAd_Interstitial.this.interstitialAd = null;
                        loadInterstitialAds(activitys);
                    }
                });
            }
            @Override
            public void onAdFailedToLoad(LoadAdError loadAdError) {
                interstitialAd = null;
            }
        });
    }



    public void showInterstitialAds(Activity activity, MedCallback medCallback2) {
        this.medCallback = medCallback2;
        int integer =new MedPref(activity).getInt(MedPref.MED_CLICK, 1);
        int i = OnClickCount;

        String string2 = new MedPref(activity).getString(MedPref.MED_SHOW, "no");

        if (string2.equalsIgnoreCase("yes")) {
            if (i == integer) {
                OnClickCount = 1;
                InterstitialAd interstitialAd = this.interstitialAd;
                if (interstitialAd != null) {
                    interstitialAd.show(activity);
                    return;
                }

                MedCallback medCallback3 = this.medCallback;
                if (medCallback3 != null) {
                    medCallback3.AppCallback();
                    this.medCallback = null;
                    return;
                }
                return;
            }
            OnClickCount = i + 1;
        } else {
            MedCallback medCallback4 = this.medCallback;
            if (medCallback4 != null) {
                medCallback4.AppCallback();
                this.medCallback = null;
            }
            return;
        }
    }

    public void showInterstitialAdsBack(Activity activity, MedCallback medCallback2) {
        this.medCallback = medCallback2;
        int integer = new MedPref(activity).getInt(MedPref.MED_AD_BACK, 0);
        int integer2 = new MedPref(activity).getInt(MedPref.MED_CLICK, 1);
        if (integer == 0) {
            int i = OnClickCount;
            if (i == integer2) {
                OnClickCount = 1;
                InterstitialAd interstitialAd = this.interstitialAd;
                if (interstitialAd != null) {
                    interstitialAd.show(activity);
                    return;
                }
                MedCallback medCallback3 = this.medCallback;
                if (medCallback3 != null) {
                    medCallback3.AppCallback();
                    this.medCallback = null;
                    return;
                }
                return;
            }
            OnClickCount = i + 1;
            MedCallback medCallback4 = this.medCallback;
            if (medCallback4 != null) {
                medCallback4.AppCallback();
                this.medCallback = null;
                return;
            }
            return;
        }
        MedCallback medCallback5 = this.medCallback;
        if (medCallback5 != null) {
            medCallback5.AppCallback();
            this.medCallback = null;
        }
    }

    public boolean isInternetOn(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTED || connectivityManager.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTING || connectivityManager.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTING || connectivityManager.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTED) {
            return true;
        }
        if (connectivityManager.getNetworkInfo(0).getState() != NetworkInfo.State.DISCONNECTED) {
            connectivityManager.getNetworkInfo(1).getState();
            NetworkInfo.State state = NetworkInfo.State.DISCONNECTED;
        }
        return false;
    }

    public static void alertBox(Activity activity) {
        AlertDialog.Builder build = new AlertDialog.Builder(activity);
        build.setTitle("Internet Alert");
        build.setMessage("You need to internet connection");
        build.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        build.show();
    }
}

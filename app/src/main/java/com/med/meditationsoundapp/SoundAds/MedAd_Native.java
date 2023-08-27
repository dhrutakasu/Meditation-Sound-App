package com.med.meditationsoundapp.SoundAds;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.nativead.MediaView;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdOptions;
import com.google.android.gms.ads.nativead.NativeAdView;
import com.med.meditationsoundapp.R;
import com.med.meditationsoundapp.SoundUtils.MedPref;

public class MedAd_Native {
    private static MedAd_Native medAdNative;
    private NativeAd ad;

    public static MedAd_Native getMedInstance() {
        if (medAdNative == null) {
            medAdNative = new MedAd_Native();
        }
        return medAdNative;
    }

    public void LoadNativeAds(final Activity activityS) {
        AdLoader.Builder ALertBuilder = new AdLoader.Builder(activityS, new MedPref(activityS).getString(MedPref.MED_AD_NATIVE, ""));
        ALertBuilder.forNativeAd(new NativeAd.OnNativeAdLoadedListener() {

            @Override
            public void onNativeAdLoaded(NativeAd ad) {
                MedAd_Native.this.ad = ad;
            }
        });
        ALertBuilder.withNativeAdOptions(new NativeAdOptions.Builder().setVideoOptions(new VideoOptions.Builder().setStartMuted(true).build()).build());
        ALertBuilder.withAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(LoadAdError error) {
            }
        }).build().loadAd(new AdRequest.Builder().build());
    }

    public void showNativeAdsMedium(Activity activityS, FrameLayout layout) {
        if (ad != null) {
            NativeAdView adView = (NativeAdView) activityS.getLayoutInflater().inflate(R.layout.layout_ads_item_ad_unified, (ViewGroup) null);
            UnifiedNativeAdView(ad, adView);
            layout.removeAllViews();
            layout.addView(adView);
        }
        LoadNativeAds(activityS);
    }

    public void showNativeAdsBig(Activity activityS, FrameLayout layout) {
        if (ad != null) {
            NativeAdView inflate = (NativeAdView) activityS.getLayoutInflater().inflate(R.layout.layout_ads_item_ad_unified, (ViewGroup) null);
            UnifiedNativeAdView(ad, inflate);
            layout.removeAllViews();
            layout.addView(inflate);

        }
        LoadNativeAds(activityS);
    }
    public void showNativeAds250(Activity activityS, FrameLayout layout) {
        if (ad != null) {
            NativeAdView inflate = (NativeAdView) activityS.getLayoutInflater().inflate(R.layout.layout_ads_item_big_native_layout, (ViewGroup) null);
            UnifiedNativeAdView(ad, inflate);
            layout.removeAllViews();
            String string2 = new MedPref(activityS).getString(MedPref.MED_SHOW, "no");

            if (string2.equalsIgnoreCase("yes") ) {
                layout.setVisibility(View.VISIBLE);
                layout.addView(inflate);
            } else {
                layout.setVisibility(View.INVISIBLE);
            }

        }
        LoadNativeAds(activityS);
    }

    public void showNativeAds55(Activity activityS, FrameLayout layout) {
        if (ad != null) {
            NativeAdView inflate = (NativeAdView) activityS.getLayoutInflater().inflate(R.layout.layout_ads_item_ad_unified, (ViewGroup) null);
            UnifiedNativeAdView100(ad, inflate);
            layout.removeAllViews();
            layout.addView(inflate);
        }
        LoadNativeAds(activityS);
    }

    @SuppressLint("WrongConstant")
    private void UnifiedNativeAdView(NativeAd ad, NativeAdView nativeAdView) {
        nativeAdView.setMediaView((MediaView) nativeAdView.findViewById(R.id.MvAdMedia));
        nativeAdView.setHeadlineView(nativeAdView.findViewById(R.id.TvAdHeadline));
        nativeAdView.setBodyView(nativeAdView.findViewById(R.id.TvAdBody));
        Button button = nativeAdView.findViewById(R.id.BtnAdCallToAction);
        nativeAdView.setCallToActionView(button);
        nativeAdView.setIconView(nativeAdView.findViewById(R.id.IvAdIcon));
        nativeAdView.setPriceView(nativeAdView.findViewById(R.id.TvAdPrice));
        nativeAdView.setStarRatingView(nativeAdView.findViewById(R.id.RbAdStars));
        nativeAdView.setStoreView(nativeAdView.findViewById(R.id.TvAdStore));
        nativeAdView.setAdvertiserView(nativeAdView.findViewById(R.id.TvAdAdvertiser));
        ((TextView) nativeAdView.getHeadlineView()).setText(ad.getHeadline());
        if (ad.getBody() == null) {
            nativeAdView.getBodyView().setVisibility(4);
        } else {
            nativeAdView.getBodyView().setVisibility(0);
            ((TextView) nativeAdView.getBodyView()).setText(ad.getBody());
        }
        if (ad.getCallToAction() == null) {
            nativeAdView.getCallToActionView().setVisibility(4);
        } else {
            nativeAdView.getCallToActionView().setVisibility(0);
            ((Button) nativeAdView.getCallToActionView()).setText(ad.getCallToAction());
        }
        if (ad.getIcon() == null) {
            nativeAdView.getIconView().setVisibility(8);
        } else {
            ((ImageView) nativeAdView.getIconView()).setImageDrawable(ad.getIcon().getDrawable());
            nativeAdView.getIconView().setVisibility(0);
        }
        if (ad.getPrice() == null) {
            nativeAdView.getPriceView().setVisibility(4);
        } else {
            nativeAdView.getPriceView().setVisibility(0);
            ((TextView) nativeAdView.getPriceView()).setText(ad.getPrice());
        }
        if (ad.getStore() == null) {
            nativeAdView.getStoreView().setVisibility(4);
        } else {
            nativeAdView.getStoreView().setVisibility(0);
            ((TextView) nativeAdView.getStoreView()).setText(ad.getStore());
        }
        if (ad.getStarRating() == null) {
            nativeAdView.getStarRatingView().setVisibility(4);
        } else {
            ((RatingBar) nativeAdView.getStarRatingView()).setRating(ad.getStarRating().floatValue());
            nativeAdView.getStarRatingView().setVisibility(0);
        }
        if (ad.getAdvertiser() == null) {
            nativeAdView.getAdvertiserView().setVisibility(4);
        } else {
            ((TextView) nativeAdView.getAdvertiserView()).setText(ad.getAdvertiser());
            nativeAdView.getAdvertiserView().setVisibility(0);
        }
        nativeAdView.setNativeAd(ad);
    }

    @SuppressLint("WrongConstant")
    private void UnifiedNativeAdView100(NativeAd nativeAd, NativeAdView adView) {
        adView.setHeadlineView(adView.findViewById(R.id.TvAdHeadline));
        adView.setBodyView(adView.findViewById(R.id.TvAdBody));
        Button button = adView.findViewById(R.id.BtnAdCallToAction);
        adView.setCallToActionView(button);
        adView.setIconView(adView.findViewById(R.id.IvAdIcon));
        adView.setStarRatingView(adView.findViewById(R.id.RbAdStars));
        ((TextView) adView.getHeadlineView()).setText(nativeAd.getHeadline());
        if (nativeAd.getBody() == null) {
            adView.getBodyView().setVisibility(4);
        } else {
            adView.getBodyView().setVisibility(0);
            ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
        }
        if (nativeAd.getCallToAction() == null) {
            adView.getCallToActionView().setVisibility(4);
        } else {
            adView.getCallToActionView().setVisibility(0);
            ((Button) adView.getCallToActionView()).setText(nativeAd.getCallToAction());
        }
        if (nativeAd.getIcon() == null) {
            adView.getIconView().setVisibility(8);
        } else {
            ((ImageView) adView.getIconView()).setImageDrawable(nativeAd.getIcon().getDrawable());
            adView.getIconView().setVisibility(0);
        }
        if (nativeAd.getStarRating() == null) {
            adView.getStarRatingView().setVisibility(4);
        } else {
            ((RatingBar) adView.getStarRatingView()).setRating(nativeAd.getStarRating().floatValue());
            adView.getStarRatingView().setVisibility(0);
        }
        adView.setNativeAd(nativeAd);
    }
}

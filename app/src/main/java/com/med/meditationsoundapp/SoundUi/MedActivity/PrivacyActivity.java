package com.med.meditationsoundapp.SoundUi.MedActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
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
    private WebView WebPrivacy  ;
    private View ViewProgress;

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
        WebPrivacy = (WebView) findViewById(R.id.WebPrivacy);
        ViewProgress = (View) findViewById(R.id.ViewProgress);
    }

    private void initListeners() {
        IvBack.setOnClickListener(v -> finish());
    }

    private void initActions() {
        TvTitle.setText("Policy Policy");
        if (MedConstants.isConnectingToInternet(context)) {
            MedAd_Banner.getMedInstance().showBannerAds(PrivacyActivity.this, AdSize.LARGE_BANNER, (RelativeLayout) findViewById(R.id.RlMedBannerAdView), (RelativeLayout) findViewById(R.id.RlMedBannerAd));
        }

        WebPrivacy.setInitialScale(100);
        WebSettings webPrivacySettings = WebPrivacy.getSettings();
        webPrivacySettings.setJavaScriptEnabled(true);
        webPrivacySettings.setTextZoom(webPrivacySettings.getTextZoom() + 70);
        WebPrivacy.loadUrl("https://sites.google.com/view/sleeping-sound/home");
        WebPrivacy.setWebViewClient(new AppWebViewClients(ViewProgress));
        if (new MedPref(context).getBoolean(MedPref.BOOL_NIGHT, false)) {
            Window window = getWindow();
            window.setStatusBarColor(ContextCompat.getColor(context, R.color.purple_200_dark));
        } else {
            Window window = getWindow();
            window.setStatusBarColor(ContextCompat.getColor(context, R.color.purple_200));
        }
    }

    private class AppWebViewClients extends WebViewClient {

        private final View progressBar;

        public AppWebViewClients(View progressBar) {
            this.progressBar=progressBar;
            progressBar.setVisibility(View.VISIBLE);
        }
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.GONE);
        }
    }
}
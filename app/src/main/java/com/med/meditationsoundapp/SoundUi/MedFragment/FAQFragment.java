package com.med.meditationsoundapp.SoundUi.MedFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.google.android.gms.ads.AdSize;
import com.med.meditationsoundapp.R;
import com.med.meditationsoundapp.SoundAds.MedAd_Banner;
import com.med.meditationsoundapp.SoundConstants.MedConstants;

import androidx.fragment.app.Fragment;

public class FAQFragment extends Fragment {
    private View FAQView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FAQView = inflater.inflate(R.layout.fragment_faq, container, false);

        initViews();
        initActions();
        return FAQView;
    }

    private void initViews() {

    }

    private void initActions() {
        if (MedConstants.isConnectingToInternet(getContext())) {
            MedAd_Banner.getMedInstance().showBannerAds(getActivity(), AdSize.LARGE_BANNER, (RelativeLayout) FAQView.findViewById(R.id.RlMedBannerAdView), (RelativeLayout) FAQView.findViewById(R.id.RlMedBannerAd));
        }
    }

}

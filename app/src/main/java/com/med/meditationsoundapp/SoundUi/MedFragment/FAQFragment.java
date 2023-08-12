package com.med.meditationsoundapp.SoundUi.MedFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.med.meditationsoundapp.R;

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

    }

}

package com.med.meditationsoundapp.SoundUi.MedFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.med.meditationsoundapp.R;

public class PrivacyPolicyFragment extends Fragment {

    private View PolicyView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        PolicyView = inflater.inflate(R.layout.fragment_privacy_policy, container, false);

        initViews();
        initActions();
        return PolicyView;
    }

    private void initViews() {

    }

    private void initActions() {

    }
}
package com.med.meditationsoundapp.SoundDialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.widget.SwitchCompat;

import com.med.meditationsoundapp.R;
import com.med.meditationsoundapp.SoundAds.MedAd_Native;
import com.med.meditationsoundapp.SoundUi.MedActivity.MedMainActivity;

public class SoundSettingDialog extends Dialog {
    private final MedMainActivity activity;

    public SoundSettingDialog(MedMainActivity activity, Context context) {
        super(context);
        this.activity = activity;
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.dialog_setting);
        SwitchCompat switchPower = (SwitchCompat) findViewById(R.id.SwitchPower);
        SwitchCompat switchNight = (SwitchCompat) findViewById(R.id.SwitchNight);
        SwitchCompat switchDevice = (SwitchCompat) findViewById(R.id.SwitchDevice);
        Spinner spinnerCountdown = (Spinner) findViewById(R.id.SpinnerCountdown);
        Spinner spinnerDefaultVolume = (Spinner) findViewById(R.id.SpinnerDefaultVolume);
        Spinner spinnerTimerInterface = (Spinner) findViewById(R.id.SpinnerTimerInterface);
        ImageView IvOkDialogSetting = (ImageView) findViewById(R.id.IvOkDialogSetting);
        TextView TvCancelDialogSetting = (TextView) findViewById(R.id.TvCancelDialogSetting);


        IvOkDialogSetting.setOnClickListener(view -> {
            dismiss();
        });
        TvCancelDialogSetting.setOnClickListener(view -> {
            dismiss();
        });
    }
}

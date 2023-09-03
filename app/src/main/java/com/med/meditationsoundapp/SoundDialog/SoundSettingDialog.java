package com.med.meditationsoundapp.SoundDialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.med.meditationsoundapp.R;
import com.med.meditationsoundapp.SoundAds.MedAd_Native;
import com.med.meditationsoundapp.SoundConstants.MedConstants;
import com.med.meditationsoundapp.SoundUi.MedActivity.MedMainActivity;
import com.med.meditationsoundapp.SoundUtils.MedPref;

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
        SwitchCompat switchNight = (SwitchCompat) findViewById(R.id.SwitchNight);
        SwitchCompat switchDevice = (SwitchCompat) findViewById(R.id.SwitchDevice);
        Spinner spinnerCountdown = (Spinner) findViewById(R.id.SpinnerCountdown);
        Spinner spinnerDefaultVolume = (Spinner) findViewById(R.id.SpinnerDefaultVolume);
        Spinner spinnerTimerInterface = (Spinner) findViewById(R.id.SpinnerTimerInterface);
        ImageView IvOkDialogSetting = (ImageView) findViewById(R.id.IvOkDialogSetting);
        TextView TvCancelDialogSetting = (TextView) findViewById(R.id.TvCancelDialogSetting);
        String[] CountDown = {"20s", "40s", "60s"};
        ArrayAdapter arrayAdapter = new ArrayAdapter(activity, android.R.layout.simple_spinner_item, CountDown);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCountdown.setAdapter(arrayAdapter);

        String[] DefaultVolume = {"25", "50", "75", "100"};
        ArrayAdapter arrayDefaultVolumeAdapter = new ArrayAdapter(activity, android.R.layout.simple_spinner_item, DefaultVolume);
        arrayDefaultVolumeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDefaultVolume.setAdapter(arrayDefaultVolumeAdapter);

        String[] TimerInterface = {"Clock", "Buttons"};
        ArrayAdapter arrayTimerInterfaceAdapter = new ArrayAdapter(activity, android.R.layout.simple_spinner_item, TimerInterface);
        arrayTimerInterfaceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTimerInterface.setAdapter(arrayTimerInterfaceAdapter);

        spinnerCountdown.setSelection(new MedPref(getContext()).getInt(MedPref.INT_COUNT_DOWN, 0));
        spinnerDefaultVolume.setSelection(new MedPref(getContext()).getInt(MedPref.INT_DEAFULT_VOLUME, 1));
        spinnerTimerInterface.setSelection(new MedPref(getContext()).getInt(MedPref.INT_TIMER_INTERFACE, 0));
        switchNight.setChecked(new MedPref(getContext()).getBoolean(MedPref.BOOL_NIGHT, false));
        switchDevice.setChecked(new MedPref(getContext()).getBoolean(MedPref.BOOL_DEVICE, true));

        switchNight.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }

            activity.recreate();
        });
        IvOkDialogSetting.setOnClickListener(view -> {
            new MedPref(getContext()).putInt(MedPref.INT_COUNT_DOWN, spinnerCountdown.getSelectedItemPosition());
            new MedPref(getContext()).putInt(MedPref.INT_DEAFULT_VOLUME, spinnerDefaultVolume.getSelectedItemPosition());
            new MedPref(getContext()).putInt(MedPref.INT_TIMER_INTERFACE, spinnerTimerInterface.getSelectedItemPosition());
            new MedPref(getContext()).putBoolean(MedPref.BOOL_NIGHT, switchNight.isChecked());
            new MedPref(getContext()).putBoolean(MedPref.BOOL_DEVICE, switchDevice.isChecked());

            Intent intent = new Intent(MedConstants.BROADCAST_SETTING);
            LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);
            dismiss();
        });
        TvCancelDialogSetting.setOnClickListener(view -> {
            dismiss();
        });
    }
}

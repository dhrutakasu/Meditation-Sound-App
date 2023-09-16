package com.med.meditationsoundapp.SoundDialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
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
        ConstraintLayout ConstSetting = (ConstraintLayout) findViewById(R.id.ConstSetting);
        ImageView IvSettingTitle = (ImageView) findViewById(R.id.IvSettingTitle);
        ImageView IvOkDialogSetting = (ImageView) findViewById(R.id.IvOkDialogSetting);
        TextView TvNightMode = (TextView) findViewById(R.id.TvNightMode);
        TextView TvDeviceVolume = (TextView) findViewById(R.id.TvDeviceVolume);
        TextView TvFadingCountdown = (TextView) findViewById(R.id.TvFadingCountdown);
        TextView TvDefaultVolume = (TextView) findViewById(R.id.TvDefaultVolume);
        TextView TvTimerInterface = (TextView) findViewById(R.id.TvTimerInterface);
        TextView TvCancelDialogSetting = (TextView) findViewById(R.id.TvCancelDialogSetting);

        String[] CountDown = {"20s", "40s", "60s"};
        ArrayAdapter arrayAdapter = new ArrayAdapter(activity, android.R.layout.simple_spinner_item, CountDown) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                if (new MedPref(getContext()).getBoolean(MedPref.BOOL_NIGHT, false)) {
                    ((TextView) v).setTextColor(getContext().getResources().getColorStateList(R.color.black_dark));
                } else {
                    ((TextView) v).setTextColor(getContext().getResources().getColorStateList(R.color.black));
                }
                return v;
            }
        };
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCountdown.setAdapter(arrayAdapter);

        String[] DefaultVolume = {"25", "50", "75", "100"};
        ArrayAdapter arrayDefaultVolumeAdapter = new ArrayAdapter(activity, android.R.layout.simple_spinner_item, DefaultVolume) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                if (new MedPref(getContext()).getBoolean(MedPref.BOOL_NIGHT, false)) {
                    ((TextView) v).setTextColor(getContext().getResources().getColorStateList(R.color.black_dark));
                } else {
                    ((TextView) v).setTextColor(getContext().getResources().getColorStateList(R.color.black));
                }
                return v;
            }
        };
        arrayDefaultVolumeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDefaultVolume.setAdapter(arrayDefaultVolumeAdapter);

        String[] TimerInterface = {"Clock", "Buttons"};
        ArrayAdapter arrayTimerInterfaceAdapter = new ArrayAdapter(activity, android.R.layout.simple_spinner_item, TimerInterface) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                if (new MedPref(getContext()).getBoolean(MedPref.BOOL_NIGHT, false)) {
                    ((TextView) v).setTextColor(getContext().getResources().getColorStateList(R.color.black_dark));
                } else {
                    ((TextView) v).setTextColor(getContext().getResources().getColorStateList(R.color.black));
                }
                return v;
            }
        };
        arrayTimerInterfaceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTimerInterface.setAdapter(arrayTimerInterfaceAdapter);

        spinnerCountdown.setSelection(new MedPref(getContext()).getInt(MedPref.INT_COUNT_DOWN, 0));
        spinnerDefaultVolume.setSelection(new MedPref(getContext()).getInt(MedPref.INT_DEAFULT_VOLUME, 1));
        spinnerTimerInterface.setSelection(new MedPref(getContext()).getInt(MedPref.INT_TIMER_INTERFACE, 0));
        switchNight.setChecked(new MedPref(getContext()).getBoolean(MedPref.BOOL_NIGHT, false));
        switchDevice.setChecked(new MedPref(getContext()).getBoolean(MedPref.BOOL_DEVICE, true));
        if (new MedPref(getContext()).getBoolean(MedPref.BOOL_NIGHT, false)) {

            ConstSetting.setBackgroundColor(getContext().getResources().getColor(R.color.black));
            spinnerTimerInterface.setBackgroundResource(R.drawable.spinner_bg_dark);
            spinnerCountdown.setBackgroundResource(R.drawable.spinner_bg_dark);
            spinnerDefaultVolume.setBackgroundResource(R.drawable.spinner_bg_dark);
            IvSettingTitle.setImageResource(R.drawable.ic_setting_title_dark);
            IvOkDialogSetting.setImageResource(R.drawable.ic_ok_btn_dark);
            TvCancelDialogSetting.setTextColor(ContextCompat.getColor(getContext(), R.color.black_dark));
            TvNightMode.setTextColor(ContextCompat.getColor(getContext(), R.color.black_dark));
            TvDeviceVolume.setTextColor(ContextCompat.getColor(getContext(), R.color.black_dark));
            TvFadingCountdown.setTextColor(ContextCompat.getColor(getContext(), R.color.black_dark));
            TvDefaultVolume.setTextColor(ContextCompat.getColor(getContext(), R.color.black_dark));
            TvTimerInterface.setTextColor(ContextCompat.getColor(getContext(), R.color.black_dark));
            switchDevice.setTrackResource(R.drawable.track_selector_dark);
            switchNight.setTrackResource(R.drawable.track_selector_dark);
            switchDevice.setThumbResource(R.drawable.thumb_selector_dark);
            switchNight.setThumbResource(R.drawable.thumb_selector_dark);
        } else {
            IvSettingTitle.setImageResource(R.drawable.ic_setting_title);
            ConstSetting.setBackgroundColor(getContext().getResources().getColor(R.color.black_dark));
            IvOkDialogSetting.setImageResource(R.drawable.ic_ok_btn);
            TvCancelDialogSetting.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
            TvNightMode.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
            TvDeviceVolume.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
            TvFadingCountdown.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
            TvDefaultVolume.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
            TvTimerInterface.setTextColor(ContextCompat.getColor(getContext(), R.color.black));

            spinnerTimerInterface.setBackgroundResource(R.drawable.spinner_bg);
            spinnerCountdown.setBackgroundResource(R.drawable.spinner_bg);
            spinnerDefaultVolume.setBackgroundResource(R.drawable.spinner_bg);
            switchDevice.setTrackResource(R.drawable.track_selector);
            switchNight.setTrackResource(R.drawable.track_selector);
            switchDevice.setThumbResource(R.drawable.thumb_selector);
            switchNight.setThumbResource(R.drawable.thumb_selector);
        }
        IvOkDialogSetting.setOnClickListener(view ->
        {
            new MedPref(getContext()).putInt(MedPref.INT_COUNT_DOWN, spinnerCountdown.getSelectedItemPosition());
            new MedPref(getContext()).putInt(MedPref.INT_DEAFULT_VOLUME, spinnerDefaultVolume.getSelectedItemPosition());
            new MedPref(getContext()).putInt(MedPref.INT_TIMER_INTERFACE, spinnerTimerInterface.getSelectedItemPosition());
            new MedPref(getContext()).putBoolean(MedPref.BOOL_NIGHT, switchNight.isChecked());
            new MedPref(getContext()).putBoolean(MedPref.BOOL_DEVICE, switchDevice.isChecked());

            Intent intent = new Intent(MedConstants.BROADCAST_SETTING);
            LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);
            dismiss();
            Intent MainIntent = new Intent(MedConstants.BROADCAST_THEME);
            LocalBroadcastManager.getInstance(getContext()).sendBroadcast(MainIntent);
        });
        TvCancelDialogSetting.setOnClickListener(view ->
        {
            dismiss();
        });
    }
}

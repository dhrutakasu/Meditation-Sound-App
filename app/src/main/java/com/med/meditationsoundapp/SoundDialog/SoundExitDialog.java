package com.med.meditationsoundapp.SoundDialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.TextView;

import com.med.meditationsoundapp.R;
import com.med.meditationsoundapp.SoundAds.MedAd_Native;
import com.med.meditationsoundapp.SoundUi.MedActivity.MedMainActivity;
import com.med.meditationsoundapp.SoundUtils.MedPref;

import androidx.core.content.ContextCompat;

public class SoundExitDialog extends Dialog {
    private final MedMainActivity activity;
    public ExitListener exitListener;

    public interface ExitListener {

        void onExit();
    }

    public SoundExitDialog(MedMainActivity activity, Context context, ExitListener exitListener) {
        super(context);
        this.activity = activity;
        this.exitListener = exitListener;
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.dialog_exit);
        TextView TvExitMsg = (TextView) findViewById(R.id.TvExitMsg);
        TextView TvDialogExit = (TextView) findViewById(R.id.IvExit);
        TextView TvDialogNotExit = (TextView) findViewById(R.id.TvDialogNotExit);
        MedAd_Native.getMedInstance().showNativeAds250(activity, findViewById(R.id.FlNativeExit));
        if (new MedPref(getContext()).getBoolean(MedPref.BOOL_NIGHT, false)) {
            TvExitMsg.setTextColor(ContextCompat.getColor(getContext(), R.color.black_dark));
            TvDialogExit.setTextColor(ContextCompat.getColor(getContext(), R.color.app_main_color_dark));
            TvDialogNotExit.setTextColor(ContextCompat.getColor(getContext(), R.color.app_main_color_dark));
        } else {
            TvExitMsg.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
            TvDialogExit.setTextColor(ContextCompat.getColor(getContext(), R.color.app_main_color));
            TvDialogNotExit.setTextColor(ContextCompat.getColor(getContext(), R.color.app_main_color));
        }
        TvDialogExit.setOnClickListener(view -> {
            exitListener.onExit();
        });
        TvDialogNotExit.setOnClickListener(view -> {
            dismiss();
        });
    }
}

package com.med.meditationsoundapp.SoundDialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.TextView;

import com.med.meditationsoundapp.R;
import com.med.meditationsoundapp.SoundAds.MedAd_Native;
import com.med.meditationsoundapp.SoundUtils.MedPref;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

public class SoundMaxDialog extends Dialog {
    private final DialogDismiss dialogDismiss;
    private final Activity activity;

    public SoundMaxDialog(Activity context, DialogDismiss dismiss) {
        super(context);
        activity=context;
        this.dialogDismiss = dismiss;
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.dialog_max_sound);
        ConstraintLayout ConstMaximum = (ConstraintLayout) findViewById(R.id.ConstMaximum);
        TextView TvMaxSoundsMsg = (TextView) findViewById(R.id.TvMaxSoundsMsg);
        TextView TvMaxSoundsCancelDialog = (TextView) findViewById(R.id.TvMaxSoundsCancelDialog);
        MedAd_Native.getMedInstance().showNativeAds250(activity, findViewById(R.id.FlNativeMax));
        if (new MedPref(getContext()).getBoolean(MedPref.BOOL_NIGHT, false)) {
            ConstMaximum.getBackground().setColorFilter(ContextCompat.getColor(getContext(), R.color.black), PorterDuff.Mode.SRC_IN);
            TvMaxSoundsCancelDialog.setTextColor(ContextCompat.getColor(getContext(), R.color.black_dark));
            TvMaxSoundsMsg.setTextColor(ContextCompat.getColor(getContext(), R.color.black_dark));
        } else {
            TvMaxSoundsMsg.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
            TvMaxSoundsCancelDialog.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
            ConstMaximum.getBackground().setColorFilter(ContextCompat.getColor(getContext(), R.color.black_dark), PorterDuff.Mode.SRC_IN);
        }
        TvMaxSoundsCancelDialog.setOnClickListener(view -> {
            dialogDismiss.DismissListener(this);
        });
    }

    public interface DialogDismiss {
        void DismissListener(SoundMaxDialog soundMaxDialog);
    }
}

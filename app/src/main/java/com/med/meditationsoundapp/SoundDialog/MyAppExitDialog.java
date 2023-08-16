package com.med.meditationsoundapp.SoundDialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.TextView;

import com.med.meditationsoundapp.R;
import com.med.meditationsoundapp.SoundAds.MedAd_Native;
import com.med.meditationsoundapp.SoundUi.MedActivity.MedMainActivity;

public class MyAppExitDialog extends Dialog {
    private final MedMainActivity activity;
    public ExitListener exitListener;

    public interface ExitListener {

        void onExit();
    }

    public MyAppExitDialog(MedMainActivity activity, Context context, ExitListener exitListener) {
        super(context);
        this.activity = activity;
        this.exitListener = exitListener;
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.dialog_exit);
        TextView TvDialogExit = (TextView) findViewById(R.id.IvExit);
        TextView TvDialogNotExit = (TextView) findViewById(R.id.TvDialogNotExit);
        MedAd_Native.getMedInstance().showNativeAds250(activity, findViewById(R.id.FlNativeExit));
        TvDialogExit.setOnClickListener(view -> {
            exitListener.onExit();
        });
        TvDialogNotExit.setOnClickListener(view -> {
            dismiss();
        });
    }
}

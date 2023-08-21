package com.med.meditationsoundapp.SoundDialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.med.meditationsoundapp.R;
import com.med.meditationsoundapp.SoundUi.MedActivity.MedMainActivity;

public class SoundReminderDialog extends Dialog {
    private final MedMainActivity activity;
    private final DialogDismiss dialogDismiss;

    public SoundReminderDialog(MedMainActivity activity, Context context,DialogDismiss dismiss) {
        super(context);
        this.activity = activity;
        this.dialogDismiss = dismiss;
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.dialog_reminder);
        TimePicker TimePickerReminder = (TimePicker) findViewById(R.id.TimePickerReminder);
        TextView TvSetTimer = (TextView) findViewById(R.id.TvSetTimer);
        TextView TvStopTimer = (TextView) findViewById(R.id.TvStopTimer);
        ImageView IvChangeInterface = (ImageView) findViewById(R.id.IvChangeInterface);
        ImageView IvOkDialog = (ImageView) findViewById(R.id.IvOkDialog);
        TextView TvCancelDialog = (TextView) findViewById(R.id.TvCancelDialog);
        IvOkDialog.setOnClickListener(view -> {
            dialogDismiss.DismissListener(this);
        });
        TvCancelDialog.setOnClickListener(view -> {
            dialogDismiss.DismissListener(this);
        });
    }

    public interface DialogDismiss {
        void DismissListener(SoundReminderDialog soundReminderDialog);
    }
}

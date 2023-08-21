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
import com.med.meditationsoundapp.SoundUi.MedAdapter.SelectSoundAdapter;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SoundSelectDialog extends Dialog {
    private final MedMainActivity activity;
    private final DialogDismiss dialogDismiss;

    public SoundSelectDialog(MedMainActivity activity, Context context, DialogDismiss dismiss) {
        super(context);
        this.activity = activity;
        this.dialogDismiss = dismiss;
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.dialog_sounds);
        RecyclerView RvSelectSound = (RecyclerView) findViewById(R.id.RvSelectSound);
        TextView TvCancelDialogSound = (TextView) findViewById(R.id.TvCancelDialogSound);
        String[] ListOfCategory = activity.getResources().getStringArray(R.array.CategoryList);
        RvSelectSound.setLayoutManager(new LinearLayoutManager(activity, RecyclerView.VERTICAL, false));
        RvSelectSound.setAdapter(new SelectSoundAdapter(activity, ListOfCategory, position -> dialogDismiss.SoundListener(SoundSelectDialog.this, position)));
        TvCancelDialogSound.setOnClickListener(view -> {
            dialogDismiss.DismissListener(this);
        });
    }

    public interface DialogDismiss {
        void SoundListener(SoundSelectDialog soundSelectDialog, int SoundPos);

        void DismissListener(SoundSelectDialog soundSelectDialog);
    }
}

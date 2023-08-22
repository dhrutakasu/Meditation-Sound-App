package com.med.meditationsoundapp.SoundDialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.med.meditationsoundapp.R;
import com.med.meditationsoundapp.SoundUi.MedActivity.MedMainActivity;
import com.med.meditationsoundapp.SoundUi.MedAdapter.SelectSoundAdapter;

public class SoundMaxDialog extends Dialog {
    private final DialogDismiss dialogDismiss;

    public SoundMaxDialog(Context context, DialogDismiss dismiss) {
        super(context);
        this.dialogDismiss = dismiss;
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.dialog_max_sound);
        TextView TvMaxSoundsCancelDialog = (TextView) findViewById(R.id.TvMaxSoundsCancelDialog);
        TvMaxSoundsCancelDialog.setOnClickListener(view -> {
            dialogDismiss.DismissListener(this);
        });
    }

    public interface DialogDismiss {
          void DismissListener(SoundMaxDialog soundMaxDialog);
    }
}

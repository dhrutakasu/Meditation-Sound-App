package com.med.meditationsoundapp.SoundDialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.med.meditationsoundapp.R;
import com.med.meditationsoundapp.SoundUtils.MedPref;

public class SoundPlayAllDialog extends Dialog {
    private final DialogDismiss dialogDismiss;

    public SoundPlayAllDialog(Context context, DialogDismiss dismiss) {
        super(context);
        this.dialogDismiss = dismiss;
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.dialog_play_all);
        TextView TvPlaySoundsCancelDialog = (TextView) findViewById(R.id.TvPlaySoundsCancelDialog);
        TextView TvPlaySoundsDialog = (TextView) findViewById(R.id.TvPlaySoundsDialog);
        Spinner SpinnerPlayAll = (Spinner) findViewById(R.id.SpinnerPlayAll);

        String[] CountDown = {"2 Minutes", "3 Minutes", "5 Minutes", "10 Minutes", "15 Minutes", "20 Minutes", "30 Minutes"};
        SpinnerPlayAll.setSelection(new MedPref(getContext()).getInt(MedPref.INT_DURATION, 1));
        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, CountDown);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerPlayAll.setAdapter(arrayAdapter);

        TvPlaySoundsDialog.setOnClickListener(view -> {
            dialogDismiss.PlayListener(SpinnerPlayAll.getSelectedItemPosition(), this);
        });
        TvPlaySoundsCancelDialog.setOnClickListener(view -> {
            dialogDismiss.DismissListener(this);
        });
    }

    public interface DialogDismiss {
        void PlayListener(int selectedItemPosition, SoundPlayAllDialog playAllDialog);

        void DismissListener(SoundPlayAllDialog playAllDialog);
    }
}

package com.med.meditationsoundapp.SoundDialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.med.meditationsoundapp.R;
import com.med.meditationsoundapp.SoundUtils.MedPref;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

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
        ConstraintLayout ConstSelectAmbience = (ConstraintLayout) findViewById(R.id.ConstSelectAmbience);
        TextView TvPlaySoundsCancelDialog = (TextView) findViewById(R.id.TvPlaySoundsCancelDialog);
        ImageView IvSelectAmbienceTitle = (ImageView) findViewById(R.id.IvSelectAmbienceTitle);
        TextView TvPlaySoundsDialog = (TextView) findViewById(R.id.TvPlaySoundsDialog);
        Spinner SpinnerPlayAll = (Spinner) findViewById(R.id.SpinnerPlayAll);
        String[] CountDown = {"2 Minutes", "3 Minutes", "5 Minutes", "10 Minutes", "15 Minutes", "20 Minutes", "30 Minutes"};
        SpinnerPlayAll.setSelection(new MedPref(getContext()).getInt(MedPref.INT_DURATION, 1));
        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, CountDown) {
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
        SpinnerPlayAll.setAdapter(arrayAdapter);
        if (new MedPref(getContext()).getBoolean(MedPref.BOOL_NIGHT, false)) {
            ConstSelectAmbience.getBackground().setColorFilter(ContextCompat.getColor(getContext(), R.color.black), PorterDuff.Mode.SRC_IN);
            IvSelectAmbienceTitle.setImageResource(R.drawable.ic_select_ambience_title_dark);
            SpinnerPlayAll.setBackgroundResource(R.drawable.spinner_bg_dark);
            TvPlaySoundsCancelDialog.setTextColor(ContextCompat.getColor(getContext(), R.color.black_dark));
            TvPlaySoundsDialog.setTextColor(ContextCompat.getColor(getContext(), R.color.black_dark));
        } else {
            IvSelectAmbienceTitle.setImageResource(R.drawable.ic_select_ambience_title);
            SpinnerPlayAll.setBackgroundResource(R.drawable.spinner_bg);
            TvPlaySoundsCancelDialog.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
            TvPlaySoundsDialog.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
            ConstSelectAmbience.getBackground().setColorFilter(ContextCompat.getColor(getContext(), R.color.black_dark), PorterDuff.Mode.SRC_IN);
        }

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

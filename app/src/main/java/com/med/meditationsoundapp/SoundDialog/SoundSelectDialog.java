package com.med.meditationsoundapp.SoundDialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.med.meditationsoundapp.R;
import com.med.meditationsoundapp.SoundModel.FavModel;
import com.med.meditationsoundapp.SoundUi.MedActivity.MedMainActivity;
import com.med.meditationsoundapp.SoundUi.MedAdapter.SelectFavSoundAdapter;
import com.med.meditationsoundapp.SoundUtils.MedPref;

import java.util.ArrayList;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SoundSelectDialog extends Dialog {
    private final MedMainActivity activity;
    private final DialogDismiss dialogDismiss;
    private final ArrayList<FavModel> models;

    public SoundSelectDialog(MedMainActivity activity, Context context, ArrayList<FavModel> modelArrayList, DialogDismiss dismiss) {
        super(context);
        this.activity = activity;
        this.dialogDismiss = dismiss;
        this.models = modelArrayList;
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.dialog_sounds);
        ConstraintLayout ConstSelectSound = (ConstraintLayout) findViewById(R.id.ConstSelectSound);
        ImageView IvSelectSoundTitle = (ImageView) findViewById(R.id.IvSelectSoundTitle);
        RecyclerView RvSelectSound = (RecyclerView) findViewById(R.id.RvSelectSound);
        TextView TvCancelDialogSound = (TextView) findViewById(R.id.TvCancelDialogSound);
        if (new MedPref(getContext()).getBoolean(MedPref.BOOL_NIGHT, false)) {
            ConstSelectSound.getBackground().setColorFilter(ContextCompat.getColor(getContext(), R.color.black), PorterDuff.Mode.SRC_IN);
            IvSelectSoundTitle.setImageResource(R.drawable.ic_select_sounds_title_dark);
            TvCancelDialogSound.setTextColor(ContextCompat.getColor(getContext(), R.color.black_dark));
        } else {
            IvSelectSoundTitle.setImageResource(R.drawable.ic_select_sounds_title);
            TvCancelDialogSound.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
            ConstSelectSound.getBackground().setColorFilter(ContextCompat.getColor(getContext(), R.color.black_dark), PorterDuff.Mode.SRC_IN);
        }
        RvSelectSound.setLayoutManager(new LinearLayoutManager(activity, RecyclerView.VERTICAL, false));
        RvSelectSound.setAdapter(new SelectFavSoundAdapter(activity, models, position -> dialogDismiss.SoundListener(SoundSelectDialog.this, position)));
        TvCancelDialogSound.setOnClickListener(view -> {
            dialogDismiss.DismissListener(this);
        });
    }

    public interface DialogDismiss {
        void SoundListener(SoundSelectDialog soundSelectDialog, int SoundPos);

        void DismissListener(SoundSelectDialog soundSelectDialog);
    }
}

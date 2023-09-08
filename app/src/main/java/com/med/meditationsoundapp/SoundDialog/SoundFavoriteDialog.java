package com.med.meditationsoundapp.SoundDialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.med.meditationsoundapp.R;
import com.med.meditationsoundapp.SoundAds.MedAd_Native;
import com.med.meditationsoundapp.SoundUtils.MedPref;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

public class SoundFavoriteDialog extends Dialog {
    private final DialogDismiss dialogDismiss;
    private final Activity activity;

    public SoundFavoriteDialog(Activity context, DialogDismiss dismiss) {
        super(context);
        activity = context;
        this.dialogDismiss = dismiss;
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.dialog_favourite);
        ConstraintLayout ConstSelectAmbience = (ConstraintLayout) findViewById(R.id.ConstSelectAmbience);
        ImageView IvSelectAmbienceTitle = (ImageView) findViewById(R.id.IvSelectAmbienceTitle);
        TextView TvSoundsFavCancelDialog = (TextView) findViewById(R.id.TvSoundsFavCancelDialog);
        EditText EdtPlayFavSounds = (EditText) findViewById(R.id.EdtPlayFavSounds);
        ImageView IvOkDialogFav = (ImageView) findViewById(R.id.IvOkDialogFav);
        MedAd_Native.getMedInstance().showNativeAds250(activity, findViewById(R.id.FlNativeFav));
        if (new MedPref(getContext()).getBoolean(MedPref.BOOL_NIGHT, false)) {
            ConstSelectAmbience.getBackground().setColorFilter(ContextCompat.getColor(getContext(), R.color.black), PorterDuff.Mode.SRC_IN);
            EdtPlayFavSounds.getBackground().setColorFilter(ContextCompat.getColor(getContext(), R.color.purple_200_dark), PorterDuff.Mode.SRC_IN);
            IvSelectAmbienceTitle.setImageResource(R.drawable.ic_select_favourite_title_dark);
            EdtPlayFavSounds.setHintTextColor(ContextCompat.getColor(getContext(), R.color.black_dark));
            EdtPlayFavSounds.setTextColor(ContextCompat.getColor(getContext(), R.color.black_dark));
            IvOkDialogFav.setImageResource(R.drawable.ic_ok_btn_dark);
            TvSoundsFavCancelDialog.setTextColor(ContextCompat.getColor(getContext(), R.color.black_dark));
        } else {
            TvSoundsFavCancelDialog.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
            ConstSelectAmbience.getBackground().setColorFilter(ContextCompat.getColor(getContext(), R.color.black_dark), PorterDuff.Mode.SRC_IN);
            EdtPlayFavSounds.getBackground().setColorFilter(ContextCompat.getColor(getContext(), R.color.purple_200), PorterDuff.Mode.SRC_IN);
            IvSelectAmbienceTitle.setImageResource(R.drawable.ic_select_favourite_title);
            EdtPlayFavSounds.setHintTextColor(ContextCompat.getColor(getContext(), R.color.black));
            EdtPlayFavSounds.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
            IvOkDialogFav.setImageResource(R.drawable.ic_ok_btn);
        }
        IvOkDialogFav.setOnClickListener(view -> {
            if (EdtPlayFavSounds.getText().toString().equalsIgnoreCase("")) {
                Toast.makeText(getContext(), "Please Enter List Name..", Toast.LENGTH_SHORT).show();
            } else {
                dialogDismiss.DismissListener(this, EdtPlayFavSounds.getText().toString());
            }
        });
        TvSoundsFavCancelDialog.setOnClickListener(view -> {
            dismiss();
        });
    }

    public interface DialogDismiss {
        void DismissListener(SoundFavoriteDialog soundFavoriteDialog, String EdtName);
    }
}

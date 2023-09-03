package com.med.meditationsoundapp.SoundDialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.med.meditationsoundapp.R;

public class SoundFavoriteDialog extends Dialog {
    private final DialogDismiss dialogDismiss;
    public SoundFavoriteDialog(Context context, DialogDismiss dismiss) {
        super(context);
        this.dialogDismiss = dismiss;
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.dialog_favourite);
        TextView TvSoundsFavCancelDialog = (TextView) findViewById(R.id.TvSoundsFavCancelDialog);
        EditText EdtPlayFavSounds = (EditText) findViewById(R.id.EdtPlayFavSounds);
        ImageView IvOkDialogFav = (ImageView) findViewById(R.id.IvOkDialogFav);

        IvOkDialogFav.setOnClickListener(view -> {
         if (EdtPlayFavSounds.getText().toString().equalsIgnoreCase("")){
             Toast.makeText(getContext(), "Please Enter List Name..", Toast.LENGTH_SHORT).show();
         }else {
             dialogDismiss.DismissListener(this,EdtPlayFavSounds.getText().toString());
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

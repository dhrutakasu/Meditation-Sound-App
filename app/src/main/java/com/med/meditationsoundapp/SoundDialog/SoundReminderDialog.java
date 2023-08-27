package com.med.meditationsoundapp.SoundDialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.med.meditationsoundapp.R;
import com.med.meditationsoundapp.SoundConstants.MedConstants;
import com.med.meditationsoundapp.SoundModel.ButtonsModel;
import com.med.meditationsoundapp.SoundUi.MedActivity.MedMainActivity;
import com.med.meditationsoundapp.SoundUi.MedAdapter.ButtonsAdapter;
import com.med.meditationsoundapp.SoundUtils.MedPref;

import java.util.ArrayList;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SoundReminderDialog extends Dialog {
    private final MedMainActivity activity;
    private final DialogDismiss dialogDismiss;
    private ButtonsAdapter buttonsAdapter;

    public SoundReminderDialog(MedMainActivity activity, Context context, DialogDismiss dismiss) {
        super(context);
        this.activity = activity;
        this.dialogDismiss = dismiss;
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.dialog_reminder);
        LinearLayout LlTimerOption = (LinearLayout) findViewById(R.id.LlTimerOption);
        TimePicker TimePickerReminder = (TimePicker) findViewById(R.id.TimePickerReminder);
        RecyclerView RvButtonsInterface = (RecyclerView) findViewById(R.id.RvButtonsInterface);
        TextView TvSetTimer = (TextView) findViewById(R.id.TvSetTimer);
        TextView TvStopTimer = (TextView) findViewById(R.id.TvStopTimer);
        ImageView IvChangeInterface = (ImageView) findViewById(R.id.IvChangeInterface);
        ImageView IvMinus = (ImageView) findViewById(R.id.IvMinus);
        ImageView IvPlus = (ImageView) findViewById(R.id.IvPlus);
        TextView TvTime = (TextView) findViewById(R.id.TvTime);
        ImageView IvOkDialog = (ImageView) findViewById(R.id.IvOkDialog);
        TextView TvCancelDialog = (TextView) findViewById(R.id.TvCancelDialog);

        TimePickerReminder.setIs24HourView(Boolean.TRUE);
        if (new MedPref(getContext()).getInt(MedPref.INT_TIMER_INTERFACE, 0) == 0) {
            TimePickerReminder.setVisibility(View.VISIBLE);
            LlTimerOption.setVisibility(View.VISIBLE);
            RvButtonsInterface.setVisibility(View.GONE);
        } else {
            TimePickerReminder.setVisibility(View.GONE);
            LlTimerOption.setVisibility(View.GONE);
            RvButtonsInterface.setVisibility(View.VISIBLE);
        }
        RvButtonsInterface.setLayoutManager(new GridLayoutManager(getContext(), 3));
        ArrayList<ButtonsModel> buttonsModelArrayList = new ArrayList<>();
        ButtonsModel buttonsModel = new ButtonsModel(15, 0);
        buttonsModelArrayList.add(buttonsModel);
        buttonsModel = new ButtonsModel(30, 0);
        buttonsModelArrayList.add(buttonsModel);
        buttonsModel = new ButtonsModel(45, 0);
        buttonsModelArrayList.add(buttonsModel);
        buttonsModel = new ButtonsModel(1, 0);
        buttonsModelArrayList.add(buttonsModel);
        buttonsModel = new ButtonsModel(2, 0);
        buttonsModelArrayList.add(buttonsModel);
        buttonsModel = new ButtonsModel(3, 0);
        buttonsModelArrayList.add(buttonsModel);
        buttonsModel = new ButtonsModel(4, 0);
        buttonsModelArrayList.add(buttonsModel);
        buttonsModel = new ButtonsModel(5, 0);
        buttonsModelArrayList.add(buttonsModel);
        buttonsModel = new ButtonsModel(6, 0);
        buttonsModelArrayList.add(buttonsModel);

        buttonsAdapter = new ButtonsAdapter(getContext(), buttonsModelArrayList, position -> {
            for (int i = 0; i < buttonsModelArrayList.size(); i++) {
                if (i == position) {
                    buttonsModelArrayList.set(position, new ButtonsModel(buttonsModelArrayList.get(position).getButtons(), 1));
                } else {
                    buttonsModelArrayList.set(position, new ButtonsModel(buttonsModelArrayList.get(position).getButtons(), 0));
                }
            }
            buttonsAdapter.notifyDataSetChanged();
            String minute;
            if (position > 2) {
                minute = String.valueOf(buttonsModelArrayList.get(position).getButtons() * 60);
            } else {
                minute = String.valueOf(buttonsModelArrayList.get(position).getButtons());
            }

            TvTime.setText(MedConstants.convertToTimeString(minute));
        });
        RvButtonsInterface.setAdapter(buttonsAdapter);

        IvMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        IvPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TvTime.getText().toString().equalsIgnoreCase("")) {
                }
            }
        });
        IvChangeInterface.setOnClickListener(view -> {
            dismiss();
            SoundSettingDialog settingDialog = new SoundSettingDialog(activity, getContext());
            settingDialog.show();
            WindowManager.LayoutParams params = settingDialog.getWindow().getAttributes();
            Window dialogWindow = settingDialog.getWindow();
            params.copyFrom(dialogWindow.getAttributes());
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            params.gravity = Gravity.CENTER;
            dialogWindow.setAttributes(params);
        });

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

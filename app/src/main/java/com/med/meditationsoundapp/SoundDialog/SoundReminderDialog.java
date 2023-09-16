package com.med.meditationsoundapp.SoundDialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.med.meditationsoundapp.R;
import com.med.meditationsoundapp.SoundConstants.MedConstants;
import com.med.meditationsoundapp.SoundModel.ButtonsModel;
import com.med.meditationsoundapp.SoundUi.MedActivity.MedMainActivity;
import com.med.meditationsoundapp.SoundUi.MedAdapter.ButtonsAdapter;
import com.med.meditationsoundapp.SoundUtils.MedPref;

import java.util.ArrayList;
import java.util.Calendar;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SoundReminderDialog extends Dialog {
    private final MedMainActivity activity;
    private final DialogDismiss dialogDismiss;
    private ButtonsAdapter buttonsAdapter;
    private int TxEdt = 0, TxColor = 0;
    private int TimerButtons;

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
        RelativeLayout LlButtonTypes = (RelativeLayout) findViewById(R.id.LlButtonTypes);
        TimePicker TimePickerReminder = (TimePicker) findViewById(R.id.TimePickerReminder);
        TimePicker TimePickerReminderDark = (TimePicker) findViewById(R.id.TimePickerReminderDark);
        RecyclerView RvButtonsInterface = (RecyclerView) findViewById(R.id.RvButtonsInterface);
        ConstraintLayout ConstSetTimer = (ConstraintLayout) findViewById(R.id.ConstSetTimer);
        TextView TvSetTimer = (TextView) findViewById(R.id.TvSetTimer);
        TextView TvStopTimer = (TextView) findViewById(R.id.TvStopTimer);
        ImageView IvChangeInterface = (ImageView) findViewById(R.id.IvChangeInterface);
        ImageView IvSetTimer = (ImageView) findViewById(R.id.IvSetTimer);
        ImageView IvMinus = (ImageView) findViewById(R.id.IvMinus);
        ImageView IvPlus = (ImageView) findViewById(R.id.IvPlus);
        TextView TvTime = (TextView) findViewById(R.id.TvTime);
        ImageView IvOkDialog = (ImageView) findViewById(R.id.IvOkDialog);
        TextView TvCancelDialog = (TextView) findViewById(R.id.TvCancelDialog);
        if (new MedPref(getContext()).getBoolean(MedPref.BOOL_NIGHT, false)) {
            IvSetTimer.setImageResource(R.drawable.ic_set_timer_title_dark);
            TvSetTimer.setBackgroundColor(getContext().getResources().getColor(R.color.purple_light_dark));
            TvStopTimer.setBackgroundColor(getContext().getResources().getColor(R.color.app_main_color_dark));
            IvMinus.getBackground().setColorFilter(ContextCompat.getColor(getContext(), R.color.app_main_color_center_dark), PorterDuff.Mode.SRC_IN);
            IvPlus.getBackground().setColorFilter(ContextCompat.getColor(getContext(), R.color.app_main_color_center_dark), PorterDuff.Mode.SRC_IN);
            TvTime.getBackground().setColorFilter(ContextCompat.getColor(getContext(), R.color.app_main_color_light10_dark), PorterDuff.Mode.SRC_IN);
            ConstSetTimer.getBackground().setColorFilter(ContextCompat.getColor(getContext(), R.color.black), PorterDuff.Mode.SRC_IN);
            IvOkDialog.setImageResource(R.drawable.ic_ok_btn_dark);
            TvCancelDialog.setTextColor(ContextCompat.getColor(getContext(), R.color.black_dark));
            TimePickerReminderDark.setVisibility(View.VISIBLE);
            TimePickerReminder.setVisibility(View.GONE);
        } else {
            IvSetTimer.setImageResource(R.drawable.ic_set_timer_title);
            ConstSetTimer.getBackground().setColorFilter(ContextCompat.getColor(getContext(), R.color.black_dark), PorterDuff.Mode.SRC_IN);
            IvOkDialog.setImageResource(R.drawable.ic_ok_btn);
            TvCancelDialog.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
            TvSetTimer.setBackgroundColor(getContext().getResources().getColor(R.color.purple_light));
            TvStopTimer.setBackgroundColor(getContext().getResources().getColor(R.color.app_main_color));
            IvMinus.getBackground().setColorFilter(ContextCompat.getColor(getContext(), R.color.app_main_color_center), PorterDuff.Mode.SRC_IN);
            IvPlus.getBackground().setColorFilter(ContextCompat.getColor(getContext(), R.color.app_main_color_center), PorterDuff.Mode.SRC_IN);
            TvTime.getBackground().setColorFilter(ContextCompat.getColor(getContext(), R.color.app_main_color_light10), PorterDuff.Mode.SRC_IN);

            TimePickerReminderDark.setVisibility(View.GONE);
            TimePickerReminder.setVisibility(View.VISIBLE);
        }
        TimePickerReminder.setIs24HourView(Boolean.TRUE);
        TimePickerReminderDark.setIs24HourView(Boolean.TRUE);
        if (new MedPref(getContext()).getInt(MedPref.INT_TIMER_INTERFACE, 0) == 0) {
            if (new MedPref(getContext()).getBoolean(MedPref.BOOL_NIGHT, false)) {
                TimePickerReminderDark.setVisibility(View.VISIBLE);
                TimePickerReminder.setVisibility(View.GONE);
            } else {
                TimePickerReminderDark.setVisibility(View.GONE);
                TimePickerReminder.setVisibility(View.VISIBLE);
            }
            LlTimerOption.setVisibility(View.VISIBLE);
            RvButtonsInterface.setVisibility(View.GONE);
            LlButtonTypes.setVisibility(View.GONE);
        } else {
            TimePickerReminder.setVisibility(View.GONE);
            TimePickerReminderDark.setVisibility(View.GONE);
            LlTimerOption.setVisibility(View.GONE);
            RvButtonsInterface.setVisibility(View.VISIBLE);
            LlButtonTypes.setVisibility(View.VISIBLE);
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
        TxColor = new MedPref(getContext()).getInt(MedPref.START_TIMER, 0);
        if (new MedPref(getContext()).getBoolean(MedPref.BOOL_NIGHT, false)) {
            if (TxColor == 0) {
                TvSetTimer.setBackgroundColor(getContext().getResources().getColor(R.color.purple_light_dark));
                TvStopTimer.setBackgroundColor(getContext().getResources().getColor(R.color.app_main_color_dark));
            } else {
                TvStopTimer.setBackgroundColor(getContext().getResources().getColor(R.color.purple_light_dark));
                TvSetTimer.setBackgroundColor(getContext().getResources().getColor(R.color.app_main_color_dark));
            }
        } else {
            if (TxColor == 0) {
                TvSetTimer.setBackgroundColor(getContext().getResources().getColor(R.color.purple_light));
                TvStopTimer.setBackgroundColor(getContext().getResources().getColor(R.color.app_main_color));
            } else {
                TvStopTimer.setBackgroundColor(getContext().getResources().getColor(R.color.purple_light));
                TvSetTimer.setBackgroundColor(getContext().getResources().getColor(R.color.app_main_color));
            }
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            TimePickerReminder.setHour(new MedPref(getContext()).getInt(MedPref.HOUR, 0));
            TimePickerReminder.setMinute(new MedPref(getContext()).getInt(MedPref.MINUTE, 0));
            TimePickerReminderDark.setHour(new MedPref(getContext()).getInt(MedPref.HOUR, 0));
            TimePickerReminderDark.setMinute(new MedPref(getContext()).getInt(MedPref.MINUTE, 0));
        }
        TxEdt = new MedPref(getContext()).getInt(MedPref.TIMER, 0);
        TvTime.setText(MedConstants.convertToTimeString(String.valueOf(TxEdt)));
        int Adap = new MedPref(getContext()).getInt(MedPref.TIMER_BUTTON, -1);
        new MedPref(getContext()).putInt(MedPref.TIMER_BUTTON, Adap);
        for (int i = 0; i < buttonsModelArrayList.size(); i++) {
            if (Adap == buttonsModelArrayList.get(i).getButtons()) {
                buttonsModelArrayList.set(i, new ButtonsModel(buttonsModelArrayList.get(i).getButtons(), 1));
            } else {
                buttonsModelArrayList.set(i, new ButtonsModel(buttonsModelArrayList.get(i).getButtons(), 0));
            }
        }

        buttonsAdapter = new ButtonsAdapter(getContext(), buttonsModelArrayList, position -> {
            String minute;
            int point = buttonsModelArrayList.get(position).getButtons();
            if (buttonsModelArrayList.get(position).getSelected() == 0) {
                if (position > 2) {
                    minute = String.valueOf(point * 60);
                } else {
                    minute = String.valueOf(point);
                }
                for (int i = 0; i < buttonsModelArrayList.size(); i++) {
                    buttonsModelArrayList.set(i, new ButtonsModel(buttonsModelArrayList.get(i).getButtons(), 0));
                }
                buttonsModelArrayList.set(position, new ButtonsModel(buttonsModelArrayList.get(position).getButtons(), 1));
                TimerButtons = point;
            } else {
                for (int i = 0; i < buttonsModelArrayList.size(); i++) {
                    buttonsModelArrayList.set(position, new ButtonsModel(buttonsModelArrayList.get(position).getButtons(), 0));
                }
                buttonsModelArrayList.set(position, new ButtonsModel(buttonsModelArrayList.get(position).getButtons(), 0));
                minute = String.valueOf(0);
                TimerButtons = -1;
            }
            buttonsAdapter.notifyDataSetChanged();
            TxEdt = Integer.parseInt(minute);
            TvTime.setText(MedConstants.convertToTimeString(minute));
        });
        RvButtonsInterface.setAdapter(buttonsAdapter);

        IvMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TxEdt > 5) {
                    if (TxEdt >= 360) {
                        TxEdt = TxEdt - 15;
                    } else {
                        TxEdt = TxEdt - 5;
                    }
                } else if (TxEdt > 0) {
                    TxEdt = TxEdt - 1;
                }
                TvTime.setText(MedConstants.convertToTimeString(String.valueOf(TxEdt)));
            }
        });
        IvPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TxEdt >= 5) {
                    if (TxEdt >= 360) {
                        TxEdt = TxEdt + 15;
                    } else {
                        TxEdt = TxEdt + 5;
                    }
                } else {
                    TxEdt++;
                }
                TvTime.setText(MedConstants.convertToTimeString(String.valueOf(TxEdt)));
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

        TvSetTimer.setOnClickListener(view -> {
            TxColor = 0;
            if (new MedPref(getContext()).getBoolean(MedPref.BOOL_NIGHT, false)) {
                TvSetTimer.setBackgroundColor(getContext().getResources().getColor(R.color.purple_light_dark));
                TvStopTimer.setBackgroundColor(getContext().getResources().getColor(R.color.app_main_color_dark));
            } else {
                TvSetTimer.setBackgroundColor(getContext().getResources().getColor(R.color.purple_light));
                TvStopTimer.setBackgroundColor(getContext().getResources().getColor(R.color.app_main_color));
            }
        });

        TvStopTimer.setOnClickListener(view -> {
            TxColor = 1;
            if (new MedPref(getContext()).getBoolean(MedPref.BOOL_NIGHT, false)) {
                TvStopTimer.setBackgroundColor(getContext().getResources().getColor(R.color.purple_light_dark));
                TvSetTimer.setBackgroundColor(getContext().getResources().getColor(R.color.app_main_color_dark));
            } else {
                TvStopTimer.setBackgroundColor(getContext().getResources().getColor(R.color.purple_light));
                TvSetTimer.setBackgroundColor(getContext().getResources().getColor(R.color.app_main_color));
            }
        });

        IvOkDialog.setOnClickListener(view -> {
            new MedPref(getContext()).putInt(MedPref.START_TIMER, TxColor);
            new MedPref(getContext()).putInt(MedPref.TIMER, TxEdt);
            new MedPref(getContext()).putInt(MedPref.TIMER_BUTTON, TimerButtons);
            new MedPref(getContext()).putBoolean(MedPref.SET_TIMER, true);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (new MedPref(getContext()).getBoolean(MedPref.BOOL_NIGHT, false)) {
                    new MedPref(getContext()).putInt(MedPref.HOUR, TimePickerReminderDark.getHour());
                    new MedPref(getContext()).putInt(MedPref.MINUTE, TimePickerReminderDark.getMinute());
                } else {
                    new MedPref(getContext()).putInt(MedPref.HOUR, TimePickerReminder.getHour());
                    new MedPref(getContext()).putInt(MedPref.MINUTE, TimePickerReminder.getMinute());
                }
            }
            dialogDismiss.DismissListener(this);
        });
        TvCancelDialog.setOnClickListener(view -> {
            dismiss();
        });
    }

    public interface DialogDismiss {
        void DismissListener(SoundReminderDialog soundReminderDialog);
    }
}

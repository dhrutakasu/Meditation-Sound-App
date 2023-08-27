package com.med.meditationsoundapp.SoundUi.MedAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.med.meditationsoundapp.R;
import com.med.meditationsoundapp.SoundModel.ButtonsModel;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class ButtonsAdapter extends RecyclerView.Adapter<ButtonsAdapter.MyViewHolder> {
    private final Context context;
    private final ArrayList<ButtonsModel> buttonsModels;
    private final setButtonsListeners setButtonsListeners;
    public static int IsSelected=-1;

    public ButtonsAdapter(Context context, ArrayList<ButtonsModel> timerArr, setButtonsListeners setButtonsListeners) {
        this.context = context;
        this.buttonsModels = timerArr;
        this.setButtonsListeners = setButtonsListeners;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.layout_item_buttons, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if (position > 2) {
            holder.TvButtonTitle.setText(buttonsModels.get(position).getButtons() + "h");
        } else {
            holder.TvButtonTitle.setText(buttonsModels.get(position).getButtons() + "m");
        }
        if (IsSelected == position) {
            holder.ConsButton.setBackgroundTintList(context.getResources().getColorStateList(R.color.app_main_color));
        } else {
            holder.ConsButton.setBackgroundTintList(context.getResources().getColorStateList(R.color.app_main_color_center));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setButtonsListeners.ButtonsClick(position);
                IsSelected=position;
            }
        });
    }

    public interface setButtonsListeners {
        void ButtonsClick(int position);
    }

    @Override
    public int getItemCount() {
        return buttonsModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final ConstraintLayout ConsButton;
        private final TextView TvButtonTitle;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ConsButton = itemView.findViewById(R.id.ConsButton);
            TvButtonTitle = itemView.findViewById(R.id.TvButtonTitle);
        }
    }
}

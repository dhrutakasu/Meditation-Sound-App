package com.med.meditationsoundapp.SoundUi.MedAdapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.med.meditationsoundapp.R;
import com.med.meditationsoundapp.SoundUtils.MedPref;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class CustomSoundAdapter extends RecyclerView.Adapter<CustomSoundAdapter.MyViewHolder> {
    private final Context context;
    private final ArrayList<String> soundModelsList;
    private final setCustomSoundPlay selectionSoundPlay;

    public CustomSoundAdapter(Context context, ArrayList<String> soundModelsList, setCustomSoundPlay customSoundPlay) {
        this.context = context;
        this.soundModelsList = soundModelsList;
        this.selectionSoundPlay = customSoundPlay;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.layout_item_sound_select, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.TvCategoryTitle.setText(soundModelsList.get(position).toString());
        holder.TvCategoryTitle.setGravity(Gravity.CENTER_VERTICAL);
        holder.TvCategoryTitle.setSelected(true);
        if (new MedPref(context).getBoolean(MedPref.BOOL_NIGHT, false)) {
            holder.ConstSound.setBackgroundColor(context.getResources().getColor(R.color.app_main_color_light20_dark));
            holder.TvCategoryTitle.setTextColor(context.getResources().getColor(R.color.black_dark));
        } else {
            holder.TvCategoryTitle.setTextColor(context.getResources().getColor(R.color.black));
            holder.ConstSound.setBackgroundColor(context.getResources().getColor(R.color.app_main_color_light10));
        }
        holder.itemView.setOnClickListener(view -> selectionSoundPlay.CustomSoundSelect(position));
    }

    public interface setCustomSoundPlay {
        void CustomSoundSelect(int position);
    }

    @Override
    public int getItemCount() {
        return soundModelsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView TvCategoryTitle;
        private final ConstraintLayout ConstSound;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            TvCategoryTitle = itemView.findViewById(R.id.TvCategoryTitle);
            ConstSound = itemView.findViewById(R.id.ConstSound);
        }
    }
}

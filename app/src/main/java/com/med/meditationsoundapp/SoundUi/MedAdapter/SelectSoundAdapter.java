package com.med.meditationsoundapp.SoundUi.MedAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.med.meditationsoundapp.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SelectSoundAdapter extends RecyclerView.Adapter<SelectSoundAdapter.MyViewHolder> {
    private final Context context;
    private final String[] soundModelsList;
    private final setSelectionSoundPlay selectionSoundPlay;

    public SelectSoundAdapter(Context context, String[] soundModelsList, setSelectionSoundPlay soundPlay) {
        this.context = context;
        this.soundModelsList = soundModelsList;
        this.selectionSoundPlay = soundPlay;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.layout_item_sound_select, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.TvCategoryTitle.setText(soundModelsList[position].toString());

        holder.itemView.setOnClickListener(view -> selectionSoundPlay.SoundSelect(position));
    }

    public interface setSelectionSoundPlay {
        void SoundSelect(int position);
    }

    @Override
    public int getItemCount() {
        return soundModelsList.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView TvCategoryTitle;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            TvCategoryTitle = itemView.findViewById(R.id.TvCategoryTitle);
        }
    }
}

package com.med.meditationsoundapp.SoundUi.MedAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.med.meditationsoundapp.R;
import com.med.meditationsoundapp.SoundModel.SoundModel;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
    private final Context context;
    private final ArrayList<SoundModel> soundModelsList;
    private final setSoundPlay setSoundPlay;

    public CustomAdapter(Context context, ArrayList<SoundModel> soundModelsList, setSoundPlay setSoundPlay) {
        this.context = context;
        this.soundModelsList = soundModelsList;
        this.setSoundPlay = setSoundPlay;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.layout_item_custom, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.TvCustomTitle.setText(soundModelsList.get(position).getSoundTitle());
        holder.SeekCategoryVolume.setMax(100);
        System.out.println("-- -- -- - - title : "+soundModelsList.get(position).getSoundMp3Checked()+" -- vol : "+soundModelsList.get(position).getSoundVolume());
        if (soundModelsList.get(position).getSoundMp3Checked() != 0) {
            holder.IvCategoryImgChecked.setVisibility(View.VISIBLE);
            holder.TvCategoryVolume.setText(String.valueOf(soundModelsList.get(position).getSoundVolume()).toString());
            holder.SeekCategoryVolume.setProgress(soundModelsList.get(position).getSoundVolume());
            holder.TvCategoryVolume.setVisibility(View.VISIBLE);
            holder.SeekCategoryVolume.setVisibility(View.VISIBLE);
            holder.TvCustomTitle.setSelected(true);
        } else {
            holder.IvCategoryImgChecked.setVisibility(View.GONE);
            holder.TvCategoryVolume.setVisibility(View.GONE);
            holder.SeekCategoryVolume.setVisibility(View.GONE);
            holder.TvCustomTitle.setSelected(false);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSoundPlay.SoundPlays(position, soundModelsList.get(position).getSoundPos());
            }
        });

        holder.SeekCategoryVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                System.out.println("----- - - -seek : "+seekBar.getProgress());
                setSoundPlay.SoundPlaysVolume(position, soundModelsList.get(position).getSoundPos(), seekBar.getProgress());
            }
        });
    }

    public interface setSoundPlay {
        void SoundPlays(int position, int PlayerPos);

        void SoundPlaysVolume(int position, int PlayerPos, int PlayerVolume);
    }

    @Override
    public int getItemCount() {
        return soundModelsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final ImageView IvCategoryImgChecked;
        private final TextView TvCustomTitle,TvCategoryVolume;
        private final SeekBar SeekCategoryVolume;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            TvCustomTitle = itemView.findViewById(R.id.TvCustomTitle);
            IvCategoryImgChecked = itemView.findViewById(R.id.IvCategoryImgChecked);
            TvCategoryVolume = itemView.findViewById(R.id.TvCategoryVolume);
            SeekCategoryVolume = itemView.findViewById(R.id.SeekCategoryVolume);
        }
    }
}

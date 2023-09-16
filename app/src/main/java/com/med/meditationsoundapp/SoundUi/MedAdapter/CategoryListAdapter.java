package com.med.meditationsoundapp.SoundUi.MedAdapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.med.meditationsoundapp.R;
import com.med.meditationsoundapp.SoundModel.SoundModel;
import com.med.meditationsoundapp.SoundUtils.MedPref;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.MyViewHolder> {
    private final Context context;
    private final ArrayList<SoundModel> soundModelsList;
    private final setSoundPlay setSoundPlay;

    public CategoryListAdapter(Context context, ArrayList<SoundModel> soundModelsList, setSoundPlay setSoundPlay) {
        this.context = context;
        this.soundModelsList = soundModelsList;
        this.setSoundPlay = setSoundPlay;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.layout_item_category, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.IvCategoryImg.setImageResource(soundModelsList.get(position).getSoundIcon());
        holder.SeekCategoryVolume.setMax(100);
        if (soundModelsList.get(position).getSoundMp3Checked() != 0) {
            holder.IvCategoryImgChecked.setVisibility(View.VISIBLE);
            holder.TvCategoryVolume.setText(String.valueOf(soundModelsList.get(position).getSoundVolume()).toString());
            holder.SeekCategoryVolume.setProgress(soundModelsList.get(position).getSoundVolume());
            holder.TvCategoryVolume.setVisibility(View.VISIBLE);
            holder.SeekCategoryVolume.setVisibility(View.VISIBLE);
        } else {
            holder.IvCategoryImgChecked.setVisibility(View.GONE);
            holder.TvCategoryVolume.setVisibility(View.GONE);
            holder.SeekCategoryVolume.setVisibility(View.GONE);
        }
        if (new MedPref(context).getBoolean(MedPref.BOOL_NIGHT, false)) {
            holder.IvCategoryImgChecked.setImageResource(R.drawable.ic_checked_dark);
            holder.SeekCategoryVolume.setProgressTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.purple_200_dark)));
            holder.SeekCategoryVolume.setThumb(context.getDrawable(R.drawable.seek_thumb_selector_dark));
            holder.TvCategoryVolume.setTextColor(context.getResources().getColor(R.color.black_dark));
        } else {
            holder.TvCategoryVolume.setTextColor(context.getResources().getColor(R.color.black));
            holder.SeekCategoryVolume.setProgressTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.purple_200)));
            holder.IvCategoryImgChecked.setImageResource(R.drawable.ic_checked);
            holder.SeekCategoryVolume.setThumb(context.getDrawable(R.drawable.seek_thumb_selector));
        }
        holder.IvCategoryImg.setOnClickListener(new View.OnClickListener() {
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
        private final ImageView IvCategoryImg, IvCategoryImgChecked;
        private final TextView TvCategoryVolume;
        private final SeekBar SeekCategoryVolume;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            IvCategoryImg = itemView.findViewById(R.id.IvCategoryImg);
            IvCategoryImgChecked = itemView.findViewById(R.id.IvCategoryImgChecked);
            TvCategoryVolume = itemView.findViewById(R.id.TvCategoryVolume);
            SeekCategoryVolume = itemView.findViewById(R.id.SeekCategoryVolume);
        }
    }
}

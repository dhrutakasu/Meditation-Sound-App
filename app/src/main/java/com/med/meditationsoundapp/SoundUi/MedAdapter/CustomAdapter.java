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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
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
        if (new MedPref(context).getBoolean(MedPref.BOOL_NIGHT, false)) {
            holder.ConstCutomText.setBackgroundColor(context.getResources().getColor(R.color.purple_light_dark));
            holder.SeekCategoryVolume.setProgressTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.purple_200_dark)));
            holder.SeekCategoryVolume.setThumb(context.getDrawable(R.drawable.seek_thumb_selector_dark));
            holder.TvCustomTitle.setTextColor(context.getResources().getColor(R.color.black_dark));
            holder.IvCategoryImgChecked.setImageResource(R.drawable.ic_checked_dark);
        } else {
            holder.IvCategoryImgChecked.setImageResource(R.drawable.ic_checked);
            holder.TvCustomTitle.setTextColor(context.getResources().getColor(R.color.black));
            holder.ConstCutomText.setBackgroundColor(context.getResources().getColor(R.color.app_main_color_light10));
            holder.SeekCategoryVolume.setThumb(context.getDrawable(R.drawable.seek_thumb_selector));
            holder.SeekCategoryVolume.setProgressTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.purple_200)));
        }

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
        private final TextView TvCustomTitle, TvCategoryVolume;
        private final SeekBar SeekCategoryVolume;
        private final ConstraintLayout ConstCutomText;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            TvCustomTitle = itemView.findViewById(R.id.TvCustomTitle);
            IvCategoryImgChecked = itemView.findViewById(R.id.IvCategoryImgChecked);
            TvCategoryVolume = itemView.findViewById(R.id.TvCategoryVolume);
            SeekCategoryVolume = itemView.findViewById(R.id.SeekCategoryVolume);
            ConstCutomText = itemView.findViewById(R.id.ConstCutomText);
        }
    }
}

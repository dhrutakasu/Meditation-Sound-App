package com.med.meditationsoundapp.SoundUi.MedAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.med.meditationsoundapp.R;
import com.med.meditationsoundapp.SoundModel.SoundModel;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.MyViewHolder> {
    private final Context context;
    private final ArrayList<SoundModel> soundModelsList;
    private final setSoundPlay setSoundPlay;

    public CategoryListAdapter(Context context, ArrayList<SoundModel> soundModelsList,setSoundPlay setSoundPlay) {
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
        System.out.println("---- - -pos : " + position + " -- - : " + soundModelsList.get(position).getSoundMp3Checked());
        if (soundModelsList.get(position).getSoundMp3Checked() != 0) {
            holder.IvCategoryImgChecked.setVisibility(View.VISIBLE);
        } else {
            holder.IvCategoryImgChecked.setVisibility(View.GONE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSoundPlay.SoundPlays(position);
            }
        });
    }

    public interface setSoundPlay {
        void SoundPlays(int pos);
    }

    @Override
    public int getItemCount() {
        return soundModelsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final ImageView IvCategoryImg, IvCategoryImgChecked;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            IvCategoryImg = itemView.findViewById(R.id.IvCategoryImg);
            IvCategoryImgChecked = itemView.findViewById(R.id.IvCategoryImgChecked);
        }
    }
}

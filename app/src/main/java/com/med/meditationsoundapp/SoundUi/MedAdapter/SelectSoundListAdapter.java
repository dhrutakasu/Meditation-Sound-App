package com.med.meditationsoundapp.SoundUi.MedAdapter;

import android.content.Context;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.med.meditationsoundapp.R;
import com.med.meditationsoundapp.SoundModel.PlyerModel;
import com.med.meditationsoundapp.SoundUtils.MedPref;

import java.util.ArrayList;

public class SelectSoundListAdapter extends RecyclerView.Adapter<SelectSoundListAdapter.MyViewHolder> {
    private final Context context;
    private final ArrayList<PlyerModel> plyerModelArrayList;
    private final setSoundPlay setSoundPlay;

    public SelectSoundListAdapter(Context context, ArrayList<PlyerModel> plyerModels, setSoundPlay setSoundPlay) {
        this.context = context;
        this.plyerModelArrayList = plyerModels;
        this.setSoundPlay = setSoundPlay;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.layout_item_sound_selected, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.TvSoundTitle.setText(plyerModelArrayList.get(position).getPlayerName());
        if (new MedPref(context).getBoolean(MedPref.BOOL_NIGHT, false)) {
            holder.ConstSelectSound.getBackground().setColorFilter(ContextCompat.getColor(context, R.color.black), PorterDuff.Mode.SRC_IN);;
            holder.TvSoundTitle.setTextColor(context.getResources().getColor(R.color.black_dark));
            holder.IvSoundImgCancel.setImageResource(R.drawable.icon_close_dark);
        } else {
            holder.IvSoundImgCancel.setImageResource(R.drawable.icon_close);
            holder.TvSoundTitle.setTextColor(context.getResources().getColor(R.color.black));
            holder.ConstSelectSound.getBackground().setColorFilter(ContextCompat.getColor(context, R.color.black_dark), PorterDuff.Mode.SRC_IN);;
        }
        holder.IvSoundImgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSoundPlay.SoundPlays(position);
            }
        });
    }

    public interface setSoundPlay {
        void SoundPlays(int position);
    }

    @Override
    public int getItemCount() {
        return plyerModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView TvSoundTitle;
        private final ImageView IvSoundImgCancel;
        private final ConstraintLayout ConstSelectSound;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            TvSoundTitle = itemView.findViewById(R.id.TvSoundTitle);
            IvSoundImgCancel = itemView.findViewById(R.id.IvSoundImgCancel);
            ConstSelectSound = itemView.findViewById(R.id.ConstSelectSound);
        }
    }
}

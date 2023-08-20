package com.med.meditationsoundapp.SoundUi.MedFragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.med.meditationsoundapp.R;
import com.med.meditationsoundapp.SoundConstants.MedConstants;
import com.med.meditationsoundapp.SoundModel.SoundModel;
import com.med.meditationsoundapp.SoundUi.MedAdapter.CategoryListAdapter;

import java.io.IOException;
import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static android.content.Context.MODE_PRIVATE;

public class CategoryFragment extends Fragment {

    private Context context;
    private int Cate_Pos;
    private String Cate_Str;
    private View CategoryView;
    private TextView TvCategoryList;
    private RecyclerView RvCategoryList;
    private ArrayList<SoundModel> SoundModelsList;
    private CategoryListAdapter categoryListAdapter;
    private int currentAudioIndex = -1;
    private MediaPlayer mediaPlayer;

    public static CategoryFragment newInstance(String category, int position) {
        CategoryFragment fragment = new CategoryFragment();
        Bundle args = new Bundle();
        args.putInt("FRAG_POSITION", position);
        args.putString("FRAG_STR", category);
        fragment.setArguments(args);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

            Cate_Pos = getArguments().getInt("FRAG_POSITION");
            Cate_Str = getArguments().getString("FRAG_STR");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        CategoryView = inflater.inflate(R.layout.fragment_category, container, false);
        if (CategoryView != null) {
            initViews();
            initListeners();
            initActions();
        }

        return CategoryView;
    }

    private void initViews() {
        context = getContext();
        TvCategoryList = CategoryView.findViewById(R.id.TvCategoryList);
        RvCategoryList = CategoryView.findViewById(R.id.RvCategoryList);
    }

    private void initListeners() {

    }

    private void initActions() {
        String[] Titles = getContext().getResources().getStringArray(R.array.CategoryList);
        TvCategoryList.setText(Titles[Cate_Pos]);
        RvCategoryList.setLayoutManager(new GridLayoutManager(context, 3));
        SoundModelsList = new ArrayList<>();
        if (Cate_Pos == 0) {
            SoundModelsList.addAll(MedConstants.RainSounds());
        } else if (Cate_Pos == 1) {
            SoundModelsList.addAll(MedConstants.NatureSounds());
        } else if (Cate_Pos == 2) {
            SoundModelsList.addAll(MedConstants.WindSounds());
        } else if (Cate_Pos == 3) {
            SoundModelsList.addAll(MedConstants.WaterSounds());
        } else if (Cate_Pos == 4) {
            SoundModelsList.addAll(MedConstants.CitySounds());
        } else if (Cate_Pos == 5) {
            SoundModelsList.addAll(MedConstants.CountrySounds());
        } else if (Cate_Pos == 6) {
            SoundModelsList.addAll(MedConstants.NightSounds());
        } else if (Cate_Pos == 7) {
            SoundModelsList.addAll(MedConstants.HomeSounds());
        } else if (Cate_Pos == 8) {
            SoundModelsList.addAll(MedConstants.RelaxingSounds());
        } else if (Cate_Pos == 9) {
            SoundModelsList.addAll(MedConstants.NoiseSounds());
        } else if (Cate_Pos == 10) {
            SoundModelsList.addAll(MedConstants.BinauralSounds());
        } else if (Cate_Pos == 11) {
            RvCategoryList.setVisibility(View.GONE);
        }
        ArrayList<MediaPlayer> mediaPlayerArrayList = new ArrayList<>();
        categoryListAdapter = new CategoryListAdapter(context, SoundModelsList, new CategoryListAdapter.setSoundPlay() {
            @Override
            public void SoundPlays(int position) {
//                    MediaPlayer mediaPlayer = new MediaPlayer();
//                    mediaPlayer.setDataSource(SoundModelsList.get(position).getSoundMp3());
//                    mediaPlayer.setLooping(true);
//                    if (SoundModelsList.get(position).getSoundMp3Checked() == 0) {
//                        mediaPlayer.prepare();
//                        mediaPlayer.start();
//                        SoundModelsList.get(position).setSoundMp3Checked(1);
//                        mediaPlayerArrayList.add(mediaPlayer);
//                    } else {
//                        mediaPlayer.stop();
//                        SoundModelsList.get(position).setSoundMp3Checked(0);
//                        mediaPlayerArrayList.remove( mediaPlayer);
//                    }
                if (currentAudioIndex == position) {
                    // Toggle play/pause for the same item
                    if (mediaPlayer != null) {
                        if (mediaPlayer.isPlaying()) {
                            mediaPlayer.pause();
                            SoundModelsList.get(position).setSoundMp3Checked(0);
                        } else {
                            mediaPlayer.start();
                            SoundModelsList.get(position).setSoundMp3Checked(1);
                        }
                    }
                } else {
                    currentAudioIndex = position;
                    releaseMediaPlayer();
                    SoundModelsList.get(position).setSoundMp3Checked(1);
                    initializeMediaPlayer(SoundModelsList.get(currentAudioIndex).getSoundMp3());
                }

                System.out.println("--- - -- - MP# : " + SoundModelsList.get(position).getSoundMp3());

                categoryListAdapter.notifyDataSetChanged();

                Intent intent = new Intent(MedConstants.BROADCAST_MAIN);
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
            }
        });
        RvCategoryList.setAdapter(categoryListAdapter);
    }

    private void initializeMediaPlayer(String audioUrl) {
        releaseMediaPlayer();
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(audioUrl);
            mediaPlayer.setLooping(true);
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(mp -> {
                if (mediaPlayer != null) {
                    mediaPlayer.start();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void releaseMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}

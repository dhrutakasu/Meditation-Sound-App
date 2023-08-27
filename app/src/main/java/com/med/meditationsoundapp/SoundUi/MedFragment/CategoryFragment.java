package com.med.meditationsoundapp.SoundUi.MedFragment;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.med.meditationsoundapp.R;
import com.med.meditationsoundapp.SoundConstants.MedConstants;
import com.med.meditationsoundapp.SoundDialog.SoundMaxDialog;
import com.med.meditationsoundapp.SoundDialog.SoundReminderDialog;
import com.med.meditationsoundapp.SoundModel.SoundModel;
import com.med.meditationsoundapp.SoundUi.MedActivity.MedMainActivity;
import com.med.meditationsoundapp.SoundUi.MedAdapter.CategoryListAdapter;
import com.med.meditationsoundapp.SoundUi.MedAdapter.CustomAdapter;
import com.med.meditationsoundapp.SoundUtils.MedPref;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CategoryFragment extends Fragment {

    private Context context;
    private int Cate_Pos;
    private String Cate_Str;
    private View CategoryView;
    private TextView TvCategoryList, TvEmptyList;
    private RecyclerView RvCategoryList, RvCustomCategoryList;
    private ArrayList<SoundModel> SoundModelsList;
    private CategoryListAdapter categoryListAdapter;
    private CustomAdapter customAdapter;

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
        TvEmptyList = CategoryView.findViewById(R.id.TvEmptyList);
        RvCategoryList = CategoryView.findViewById(R.id.RvCategoryList);
        RvCustomCategoryList = CategoryView.findViewById(R.id.RvCustomCategoryList);
    }

    private void initListeners() {
    }

    private void initActions() {
        LocalBroadcastManager.getInstance(context).registerReceiver(FragmentReceiver, new IntentFilter(MedConstants.BROADCAST_FRAGMENT));
        String[] Titles = getContext().getResources().getStringArray(R.array.CategoryList);
        TvCategoryList.setText(Titles[Cate_Pos]);
        RvCategoryList.setLayoutManager(new GridLayoutManager(context, 3));
        RvCustomCategoryList.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
        initArrays();
        customAdapter = new CustomAdapter(context, SoundModelsList, new CustomAdapter.setSoundPlay() {
            @Override
            public void SoundPlays(int position, int PlayerPos) {
                try {

                    if (MedConstants.SelectedPlayerArrayList.size() <= 9) {
//                    try {
                        if (SoundModelsList.get(position).getSoundMp3Checked() == 0) {
                            System.out.println("--- 0-0 - -- : " + position + " pos :- - " + SoundModelsList.get(position).getSoundMp3Checked());
                            SoundModelsList.get(position).setSoundMp3Checked(1);
                            if (MedConstants.mediaPlayerArrayList.get(PlayerPos).getPlayer().isPlaying()) {
                                MedConstants.mediaPlayerArrayList.get(PlayerPos).getPlayer().stop();
                            }
                            for (int i = 0; i < MedConstants.SelectedPlayerArrayList.size(); i++) {
                                if (MedConstants.SelectedPlayerArrayList.get(i).getPlayerPos() == MedConstants.mediaPlayerArrayList.get(PlayerPos).getPlayerPos()) {
                                    MedConstants.SelectedPlayerArrayList.remove(i);
                                }
                            }
                            MediaPlayer mediaPlayer = new MediaPlayer();
                            float volume = (float) (1 - (Math.log(100 - SoundModelsList.get(position).getSoundVolume()) / Math.log(100)));
                            mediaPlayer.setVolume(volume, volume);
                            mediaPlayer.setDataSource(SoundModelsList.get(position).getSoundMp3());
                            mediaPlayer.setLooping(true);
                            MedConstants.mediaPlayerArrayList.get(PlayerPos).setPlayer(mediaPlayer);
                            MedConstants.mediaPlayerArrayList.get(PlayerPos).getPlayer().prepareAsync();
                            MedConstants.mediaPlayerArrayList.get(PlayerPos).getPlayer().setOnPreparedListener(mp ->
                                    MedConstants.mediaPlayerArrayList.get(PlayerPos).getPlayer().start());
                            MedConstants.mediaPlayerArrayList.get(PlayerPos).getPlayer().setOnCompletionListener(mp ->
                                    MedConstants.mediaPlayerArrayList.get(PlayerPos).getPlayer().start());
                            MedConstants.SelectedPlayerArrayList.add(MedConstants.mediaPlayerArrayList.get(PlayerPos));
                        } else {
                            SoundModelsList.get(position).setSoundMp3Checked(0);
                            MedConstants.mediaPlayerArrayList.get(PlayerPos).getPlayer().stop();
                            MedConstants.SelectedPlayerArrayList.remove(MedConstants.mediaPlayerArrayList.get(PlayerPos));
                        }

                        customAdapter.notifyDataSetChanged();

                        Intent intent = new Intent(MedConstants.BROADCAST_MAIN);
                        intent.putExtra(MedConstants.SelectedSounds, MedConstants.SelectedPlayerArrayList.size());
                        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
                    } else {
                        SoundMaxDialog reminderDialog = new SoundMaxDialog(context, (SoundMaxDialog soundMaxDialog) -> {
                            soundMaxDialog.dismiss();
                        });
                        reminderDialog.show();
                        WindowManager.LayoutParams lp = reminderDialog.getWindow().getAttributes();
                        Window window = reminderDialog.getWindow();
                        lp.copyFrom(window.getAttributes());
                        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                        lp.gravity = Gravity.CENTER;
                        window.setAttributes(lp);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void SoundPlaysVolume(int position, int PlayerPos, int PlayerVolume) {
                try {
                    System.out.println("*********b * * : " + PlayerVolume);
                    MedConstants.mediaPlayerArrayList.get(PlayerPos).getPlayer().stop();
                    SoundModelsList.get(position).setSoundVolume(PlayerVolume);
                    MediaPlayer mediaPlayer = new MediaPlayer();
                    float volume = (float) (1 - (Math.log(100 - PlayerVolume) / Math.log(100)));
                    mediaPlayer.setVolume(volume, volume);
                    mediaPlayer.setDataSource(SoundModelsList.get(position).getSoundMp3());
                    mediaPlayer.setLooping(true);
                    MedConstants.mediaPlayerArrayList.get(PlayerPos).setPlayer(mediaPlayer);
                    MedConstants.mediaPlayerArrayList.get(PlayerPos).getPlayer().prepareAsync();
                    MedConstants.mediaPlayerArrayList.get(PlayerPos).getPlayer().setOnPreparedListener(mp ->
                            MedConstants.mediaPlayerArrayList.get(PlayerPos).getPlayer().start());
                    for (int i = 0; i < MedConstants.SelectedPlayerArrayList.size(); i++) {
                        if (PlayerPos == MedConstants.SelectedPlayerArrayList.get(i).getPlayerPos()) {
                            MedConstants.SelectedPlayerArrayList.get(i).setPlayerVolume(PlayerVolume);
                        }
                    }
                    customAdapter.notifyDataSetChanged();


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        RvCustomCategoryList.setAdapter(customAdapter);
        categoryListAdapter = new CategoryListAdapter(context, SoundModelsList, new CategoryListAdapter.setSoundPlay() {
            @Override
            public void SoundPlays(int position, int PlayerPos) {
                try {

                    if (MedConstants.SelectedPlayerArrayList.size() <= 9) {
//                    try {
                        if (SoundModelsList.get(position).getSoundMp3Checked() == 0) {
                            System.out.println("--- 0-0 - -- : " + position + " pos :- - " + SoundModelsList.get(position).getSoundMp3Checked());
                            SoundModelsList.get(position).setSoundMp3Checked(1);
                            if (MedConstants.mediaPlayerArrayList.get(PlayerPos).getPlayer().isPlaying()) {
                                MedConstants.mediaPlayerArrayList.get(PlayerPos).getPlayer().stop();
                            }
                            for (int i = 0; i < MedConstants.SelectedPlayerArrayList.size(); i++) {
                                if (MedConstants.SelectedPlayerArrayList.get(i).getPlayerPos() == MedConstants.mediaPlayerArrayList.get(PlayerPos).getPlayerPos()) {
                                    MedConstants.SelectedPlayerArrayList.remove(i);
                                }
                            }
                            MediaPlayer mediaPlayer = new MediaPlayer();
                            float volume = (float) (1 - (Math.log(100 - SoundModelsList.get(position).getSoundVolume()) / Math.log(100)));
                            mediaPlayer.setVolume(volume, volume);
                            mediaPlayer.setDataSource(SoundModelsList.get(position).getSoundMp3());
                            mediaPlayer.setLooping(true);
                            MedConstants.mediaPlayerArrayList.get(PlayerPos).setPlayer(mediaPlayer);
                            MedConstants.mediaPlayerArrayList.get(PlayerPos).getPlayer().prepareAsync();
                            MedConstants.mediaPlayerArrayList.get(PlayerPos).getPlayer().setOnPreparedListener(mp ->
                                    MedConstants.mediaPlayerArrayList.get(PlayerPos).getPlayer().start());
                            MedConstants.mediaPlayerArrayList.get(PlayerPos).getPlayer().setOnCompletionListener(mp ->
                                    MedConstants.mediaPlayerArrayList.get(PlayerPos).getPlayer().start());
                            MedConstants.SelectedPlayerArrayList.add(MedConstants.mediaPlayerArrayList.get(PlayerPos));
                        } else {
                            SoundModelsList.get(position).setSoundMp3Checked(0);
                            MedConstants.mediaPlayerArrayList.get(PlayerPos).getPlayer().stop();
                            MedConstants.SelectedPlayerArrayList.remove(MedConstants.mediaPlayerArrayList.get(PlayerPos));
                        }

                        categoryListAdapter.notifyDataSetChanged();

                        Intent intent = new Intent(MedConstants.BROADCAST_MAIN);
                        intent.putExtra(MedConstants.SelectedSounds, MedConstants.SelectedPlayerArrayList.size());
                        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
                    } else {
                        SoundMaxDialog reminderDialog = new SoundMaxDialog(context, (SoundMaxDialog soundMaxDialog) -> {
                            soundMaxDialog.dismiss();
                        });
                        reminderDialog.show();
                        WindowManager.LayoutParams lp = reminderDialog.getWindow().getAttributes();
                        Window window = reminderDialog.getWindow();
                        lp.copyFrom(window.getAttributes());
                        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                        lp.gravity = Gravity.CENTER;
                        window.setAttributes(lp);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void SoundPlaysVolume(int position, int PlayerPos, int PlayerVolume) {
                try {
                    System.out.println("*********b * * : " + PlayerVolume);
                    MedConstants.mediaPlayerArrayList.get(PlayerPos).getPlayer().stop();
                    SoundModelsList.get(position).setSoundVolume(PlayerVolume);
                    MediaPlayer mediaPlayer = new MediaPlayer();
                    float volume = (float) (1 - (Math.log(100 - PlayerVolume) / Math.log(100)));
                    mediaPlayer.setVolume(volume, volume);
                    mediaPlayer.setDataSource(SoundModelsList.get(position).getSoundMp3());
                    mediaPlayer.setLooping(true);
                    MedConstants.mediaPlayerArrayList.get(PlayerPos).setPlayer(mediaPlayer);
                    MedConstants.mediaPlayerArrayList.get(PlayerPos).getPlayer().prepareAsync();
                    MedConstants.mediaPlayerArrayList.get(PlayerPos).getPlayer().setOnPreparedListener(mp ->
                            MedConstants.mediaPlayerArrayList.get(PlayerPos).getPlayer().start());
                    for (int i = 0; i < MedConstants.SelectedPlayerArrayList.size(); i++) {
                        if (PlayerPos == MedConstants.SelectedPlayerArrayList.get(i).getPlayerPos()) {
                            MedConstants.SelectedPlayerArrayList.get(i).setPlayerVolume(PlayerVolume);
                            break;
                        }
                    }
                    categoryListAdapter.notifyDataSetChanged();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        RvCategoryList.setAdapter(categoryListAdapter);
    }

    private void initArrays() {
        SoundModelsList = new ArrayList<>();
        RvCustomCategoryList.setVisibility(View.GONE);
        TvEmptyList.setVisibility(View.GONE);
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
            RvCustomCategoryList.setVisibility(View.VISIBLE);
            SoundModelsList.addAll(MedConstants.getSoundDefault());
        }
        if (SoundModelsList.size() == 0) {
            TvEmptyList.setVisibility(View.VISIBLE);
            RvCategoryList.setVisibility(View.GONE);
            RvCategoryList.setVisibility(View.GONE);
        }
        for (int i = 0; i < MedConstants.SelectedPlayerArrayList.size(); i++) {
            for (int j = 0; j < SoundModelsList.size(); j++) {
                SoundModel soundModel = SoundModelsList.get(j);
                if (SoundModelsList.get(j).getSoundPos() == MedConstants.SelectedPlayerArrayList.get(i).getPlayerPos()) {
                    soundModel.setSoundMp3Checked(1);
                    soundModel.setSoundVolume(MedConstants.SelectedPlayerArrayList.get(i).getPlayerVolume());
//                } else {
//                    soundModel.setSoundMp3Checked(0);
//                    soundModel.setSoundVolume(soundModel.getSoundVolume());
                    SoundModelsList.set(j, soundModel);
                }
                System.out.println("-------- hhh : " + SoundModelsList.get(j).getSoundVolume());
            }
        }
    }

    private BroadcastReceiver FragmentReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getStringExtra(MedConstants.FRAGMENT_CLICK).equalsIgnoreCase("Stop")) {
                for (int j = 0; j < SoundModelsList.size(); j++) {
                    SoundModel soundModel = SoundModelsList.get(j);
                    soundModel.setSoundMp3Checked(0);
                    soundModel.setSoundVolume(MedConstants.SoundDefaultVolume());
                    SoundModelsList.set(j, soundModel);
                }
            } else if (intent.getStringExtra(MedConstants.FRAGMENT_CLICK).equalsIgnoreCase("Cancel")) {
               /* for (int i = 0; i < MedConstants.SelectedPlayerArrayList.size(); i++) {
                    for (int j = 0; j < SoundModelsList.size(); j++) {
                        SoundModel soundModel = SoundModelsList.get(j);
                        if (SoundModelsList.get(j).getSoundPos() == MedConstants.SelectedPlayerArrayList.get(i).getPlayerPos()) {
                            soundModel.setSoundVolume(MedConstants.SelectedPlayerArrayList.get(i).getPlayerVolume());
                            soundModel.setSoundMp3Checked(1);
                            SoundModelsList.set(j, soundModel);
                        } else {
                            SoundModel soundModel = SoundModelsList.get(j);
                            soundModel.setSoundMp3Checked(0);
                            soundModel.setSoundVolume(MedConstants.SelectedPlayerArrayList.get(i).getPlayerVolume());
                            SoundModelsList.set(j, soundModel);
                        }
                    }
                }*/
                for (int j = 0; j < SoundModelsList.size(); j++) {
                    SoundModel soundModel = SoundModelsList.get(j);
                    soundModel.setSoundVolume(MedConstants.SoundDefaultVolume());

                    for (int i = 0; i < MedConstants.SelectedPlayerArrayList.size(); i++) {
                        if (MedConstants.SelectedPlayerArrayList.get(i).getPlayerPos() == SoundModelsList.get(j).getSoundPos()) {
                            soundModel.setSoundVolume(MedConstants.SelectedPlayerArrayList.get(i).getPlayerVolume());
                            break;
                        }
                    }

                    SoundModelsList.set(j, soundModel);
                }
            } else if (intent.getStringExtra(MedConstants.FRAGMENT_CLICK).equalsIgnoreCase("ArraySet")) {
                for (int j = 0; j < SoundModelsList.size(); j++) {
                    SoundModel soundModel = SoundModelsList.get(j);
                    soundModel.setSoundVolume(MedConstants.SoundDefaultVolume());

                    for (int i = 0; i < MedConstants.SelectedPlayerArrayList.size(); i++) {
                        if (MedConstants.SelectedPlayerArrayList.get(i).getPlayerPos() == SoundModelsList.get(j).getSoundPos()) {
                            soundModel.setSoundVolume(MedConstants.SelectedPlayerArrayList.get(i).getPlayerVolume());
                            break;
                        }
                    }

                    SoundModelsList.set(j, soundModel);
                }
            }

            categoryListAdapter.notifyDataSetChanged();
            customAdapter.notifyDataSetChanged();
        }

    };
}

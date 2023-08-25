package com.med.meditationsoundapp.SoundUi.MedFragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.med.meditationsoundapp.R;
import com.med.meditationsoundapp.SoundConstants.MedConstants;
import com.med.meditationsoundapp.SoundDialog.SoundMaxDialog;
import com.med.meditationsoundapp.SoundDialog.SoundReminderDialog;
import com.med.meditationsoundapp.SoundModel.SoundModel;
import com.med.meditationsoundapp.SoundUi.MedActivity.MedMainActivity;
import com.med.meditationsoundapp.SoundUi.MedAdapter.CategoryListAdapter;

import java.io.IOException;
import java.util.ArrayList;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CategoryFragment extends Fragment {

    private Context context;
    private int Cate_Pos;
    private String Cate_Str;
    private View CategoryView;
    private TextView TvCategoryList;
    private RecyclerView RvCategoryList;
    private ArrayList<SoundModel> SoundModelsList;
    private CategoryListAdapter categoryListAdapter;

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
        LocalBroadcastManager.getInstance(context).registerReceiver(FragmentReceiver, new IntentFilter(MedConstants.BROADCAST_FRAGMENT));
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
        for (int i = 0; i < MedConstants.SelectedPlayerArrayList.size(); i++) {
            for (int j = 0; j < SoundModelsList.size(); j++) {
                if (SoundModelsList.get(j).getSoundPos() == MedConstants.SelectedPlayerArrayList.get(i).getPlayerPos()) {
                    SoundModel soundModel = SoundModelsList.get(j);
                    soundModel.setSoundMp3Checked(1);
                    SoundModelsList.set(j, soundModel);
                }
            }
        }
        categoryListAdapter = new CategoryListAdapter(context, SoundModelsList, new CategoryListAdapter.setSoundPlay() {
            @Override
            public void SoundPlays(int position, int PlayerPos) {
                if (MedConstants.SelectedPlayerArrayList.size() <= 9) {
                    try {
                        if (SoundModelsList.get(position).getSoundMp3Checked() == 0) {
                            SoundModelsList.get(position).setSoundMp3Checked(1);

                            MedConstants.mediaPlayerArrayList.get(PlayerPos).getPlayer().prepare();
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
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
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
            }

            @Override
            public void SoundPlaysVolume(int position, int PlayerPos, int PlayerVolume) {
//                try {
                    SoundModelsList.get(position).setSoundVolume(PlayerVolume);
                    float volume = (float) (1 - (Math.log(100 - PlayerVolume) / Math.log(100)));
                    MedConstants.mediaPlayerArrayList.get(PlayerPos).getPlayer().setVolume(volume, volume);
                    MedConstants.mediaPlayerArrayList.get(PlayerPos).getPlayer().start();
//                );
                    categoryListAdapter.notifyDataSetChanged();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
            }
        });
        RvCategoryList.setAdapter(categoryListAdapter);
    }

    private BroadcastReceiver FragmentReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getStringExtra(MedConstants.FRAGMENT_CLICK).equalsIgnoreCase("Stop")) {
                for (int j = 0; j < SoundModelsList.size(); j++) {
                    SoundModel soundModel = SoundModelsList.get(j);
                    soundModel.setSoundMp3Checked(0);
                    SoundModelsList.set(j, soundModel);
                }
            } else if (intent.getStringExtra(MedConstants.FRAGMENT_CLICK).equalsIgnoreCase("Cancel")) {
                for (int i = 0; i < MedConstants.SelectedPlayerArrayList.size(); i++) {
                    for (int j = 0; j < SoundModelsList.size(); j++) {
                        if (SoundModelsList.get(j).getSoundPos() == MedConstants.SelectedPlayerArrayList.get(i).getPlayerPos()) {
                            SoundModel soundModel = SoundModelsList.get(j);
                            soundModel.setSoundMp3Checked(1);
                            SoundModelsList.set(j, soundModel);
                        }else {
                            SoundModel soundModel = SoundModelsList.get(j);
                            soundModel.setSoundMp3Checked(0);
                            SoundModelsList.set(j, soundModel);
                        }
                    }
                }
            }
            categoryListAdapter.notifyDataSetChanged();
        }
    };
}

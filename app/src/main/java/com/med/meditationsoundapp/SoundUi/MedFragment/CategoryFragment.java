package com.med.meditationsoundapp.SoundUi.MedFragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.med.meditationsoundapp.R;
import com.med.meditationsoundapp.SoundConstants.MedConstants;
import com.med.meditationsoundapp.SoundDialog.SoundMaxDialog;
import com.med.meditationsoundapp.SoundModel.SoundModel;
import com.med.meditationsoundapp.SoundService.MediaPlayerService;
import com.med.meditationsoundapp.SoundUi.MedAdapter.CategoryListAdapter;
import com.med.meditationsoundapp.SoundUi.MedAdapter.CustomAdapter;
import com.med.meditationsoundapp.SoundUtils.MedPref;

import java.io.IOException;
import java.util.ArrayList;

import androidx.constraintlayout.widget.ConstraintLayout;
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
    private ImageView IvLineBar;
    private ConstraintLayout ConstCategoryFarg;
//    private View ViewProgress;
//    private ProgressBar ProgressDialog;

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
        ConstCategoryFarg = CategoryView.findViewById(R.id.ConstCategoryFarg);
        IvLineBar = CategoryView.findViewById(R.id.IvLineBar);
        TvEmptyList = CategoryView.findViewById(R.id.TvEmptyList);
        RvCategoryList = CategoryView.findViewById(R.id.RvCategoryList);
        RvCustomCategoryList = CategoryView.findViewById(R.id.RvCustomCategoryList);
//        ViewProgress = CategoryView.findViewById(R.id.ViewProgress);
//        ProgressDialog = CategoryView.findViewById(R.id.ProgressDialog);
    }

    private void initListeners() {
    }

    private void initActions() {
        if (new MedPref(context).getBoolean(MedPref.BOOL_NIGHT, false)) {
            IvLineBar.setImageResource(R.drawable.ic_title_bar_dark);
            TvCategoryList.setTextColor(ContextCompat.getColor(context, R.color.black_dark));
            TvEmptyList.setTextColor(ContextCompat.getColor(context, R.color.black_dark));
            ConstCategoryFarg.setBackgroundColor(ContextCompat.getColor(context, R.color.black));
//            ProgressDialog.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(context, R.color.purple_200_dark), PorterDuff.Mode.SRC_IN);
        } else {
//            ProgressDialog.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(context, R.color.purple_200), PorterDuff.Mode.SRC_IN);
            ConstCategoryFarg.setBackgroundColor(ContextCompat.getColor(context, R.color.black_dark));
            TvCategoryList.setTextColor(ContextCompat.getColor(context, R.color.black));
            TvEmptyList.setTextColor(ContextCompat.getColor(context, R.color.black));
            IvLineBar.setImageResource(R.drawable.ic_title_bar);
        }
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
                        Intent intentS = new Intent(MedConstants.BROADCAST_PROGRESS);
                        intentS.putExtra("IsProgress", true);
                        LocalBroadcastManager.getInstance(context).sendBroadcast(intentS);

                        if (SoundModelsList.get(position).getSoundMp3Checked() == 0) {
                            SoundModelsList.get(position).setSoundMp3Checked(1);
                            if (MedConstants.mediaPlayerArrayList.get(PlayerPos).getPlayer().isPlaying()) {
                                MedConstants.mediaPlayerArrayList.get(PlayerPos).getPlayer().stop();
                            }

                            Intent serviceIntent = new Intent(context, MediaPlayerService.class);
                            MedConstants.FAVOURITESONG = "";
                            MedConstants.NOTIFICATION_PLAYPAUSE_ICON = "Pause";
                            serviceIntent.putExtra(MedConstants.IsNotificationFavoriteTitle, "");
                            serviceIntent.putExtra(MedConstants.NOTIFICATION_PLAYPAUSE_ICON, "Pause");
                            context.startService(serviceIntent);
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
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                Intent intentS = new Intent(MedConstants.BROADCAST_PROGRESS);
                                intentS.putExtra("IsProgress", false);
                                LocalBroadcastManager.getInstance(context).sendBroadcast(intentS);
                            }
                        },1000);

                        Intent intent = new Intent(MedConstants.BROADCAST_MAIN);
                        intent.putExtra(MedConstants.SelectedSounds, MedConstants.SelectedPlayerArrayList.size());
                        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                    } else {
                        SoundMaxDialog reminderDialog = new SoundMaxDialog(getActivity(), (SoundMaxDialog soundMaxDialog) -> {
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

                    Intent intentS = new Intent(MedConstants.BROADCAST_PROGRESS);
                    intentS.putExtra("IsProgress", true);
                    LocalBroadcastManager.getInstance(context).sendBroadcast(intentS);
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

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            Intent intentS = new Intent(MedConstants.BROADCAST_PROGRESS);
                            intentS.putExtra("IsProgress", false);
                            LocalBroadcastManager.getInstance(context).sendBroadcast(intentS);
                        }
                    },1000);
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

                        Intent intentS = new Intent(MedConstants.BROADCAST_PROGRESS);
                        intentS.putExtra("IsProgress", true);
                        LocalBroadcastManager.getInstance(context).sendBroadcast(intentS);
                        if (SoundModelsList.get(position).getSoundMp3Checked() == 0) {
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
                            Intent serviceIntent = new Intent(context, MediaPlayerService.class);
                            MedConstants.FAVOURITESONG = "";
                            MedConstants.NOTIFICATION_PLAYPAUSE_ICON = "Pause";
                            serviceIntent.putExtra(MedConstants.IsNotificationFavoriteTitle, "");
                            context.startService(serviceIntent);
                        } else {
                            SoundModelsList.get(position).setSoundMp3Checked(0);
                            MedConstants.mediaPlayerArrayList.get(PlayerPos).getPlayer().stop();
                            MedConstants.SelectedPlayerArrayList.remove(MedConstants.mediaPlayerArrayList.get(PlayerPos));
                        }

                        categoryListAdapter.notifyDataSetChanged();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                Intent intentS = new Intent(MedConstants.BROADCAST_PROGRESS);
                                intentS.putExtra("IsProgress", false);
                                LocalBroadcastManager.getInstance(context).sendBroadcast(intentS);
                            }
                        },1000);

                        Intent intent = new Intent(MedConstants.BROADCAST_MAIN);
                        intent.putExtra(MedConstants.SelectedSounds, MedConstants.SelectedPlayerArrayList.size());
                        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                    } else {
                        SoundMaxDialog reminderDialog = new SoundMaxDialog(getActivity(), (SoundMaxDialog soundMaxDialog) -> {
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

                    Intent intentS = new Intent(MedConstants.BROADCAST_PROGRESS);
                    intentS.putExtra("IsProgress", true);
                    LocalBroadcastManager.getInstance(context).sendBroadcast(intentS);

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
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            Intent intentS = new Intent(MedConstants.BROADCAST_PROGRESS);
                            intentS.putExtra("IsProgress", false);
                            LocalBroadcastManager.getInstance(context).sendBroadcast(intentS);
                        }
                    },1000);

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
            SoundModelsList.addAll(MedConstants.RainSounds(context));
        } else if (Cate_Pos == 1) {
            SoundModelsList.addAll(MedConstants.NatureSounds(context));
        } else if (Cate_Pos == 2) {
            SoundModelsList.addAll(MedConstants.WindSounds(context));
        } else if (Cate_Pos == 3) {
            SoundModelsList.addAll(MedConstants.WaterSounds(context));
        } else if (Cate_Pos == 4) {
            SoundModelsList.addAll(MedConstants.CitySounds(context));
        } else if (Cate_Pos == 5) {
            SoundModelsList.addAll(MedConstants.CountrySounds(context));
        } else if (Cate_Pos == 6) {
            SoundModelsList.addAll(MedConstants.NightSounds(context));
        } else if (Cate_Pos == 7) {
            SoundModelsList.addAll(MedConstants.HomeSounds(context));
        } else if (Cate_Pos == 8) {
            SoundModelsList.addAll(MedConstants.RelaxingSounds(context));
        } else if (Cate_Pos == 9) {
            SoundModelsList.addAll(MedConstants.NoiseSounds(context));
        } else if (Cate_Pos == 10) {
            SoundModelsList.addAll(MedConstants.BinauralSounds(context));
        } else if (Cate_Pos == 11) {
            RvCategoryList.setVisibility(View.GONE);
            RvCustomCategoryList.setVisibility(View.VISIBLE);
            SoundModelsList.addAll(MedConstants.getSoundDefault(context));
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
                    SoundModelsList.set(j, soundModel);
                }
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

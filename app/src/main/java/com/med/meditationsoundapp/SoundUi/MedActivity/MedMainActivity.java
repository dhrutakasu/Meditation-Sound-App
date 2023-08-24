package com.med.meditationsoundapp.SoundUi.MedActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PorterDuff;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.med.meditationsoundapp.R;
import com.med.meditationsoundapp.SoundConstants.MedConstants;
import com.med.meditationsoundapp.SoundDialog.SoundReminderDialog;
import com.med.meditationsoundapp.SoundDialog.SoundSelectDialog;
import com.med.meditationsoundapp.SoundDialog.SoundSettingDialog;
import com.med.meditationsoundapp.SoundModel.PlyerModel;
import com.med.meditationsoundapp.SoundModel.SoundModel;
import com.med.meditationsoundapp.SoundUi.MedAdapter.CategoryAdapter;
import com.med.meditationsoundapp.SoundUi.MedAdapter.SelectSoundAdapter;
import com.med.meditationsoundapp.SoundUi.MedAdapter.SelectSoundListAdapter;

import java.io.IOException;
import java.util.ArrayList;

public class MedMainActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    private Context context;
    private ImageView IvBack, IvCategoryImg, IvCategoryNext, IvCategoryPrevious, IvDrawer, IvSetting, IvUpload;
    private TextView TvTitle;
    private ImageView IvFav, IvSoundPlayPause, IvSoundStop, IvPreview, IvFavoriteTab, IvReminderTab, IvHomeTab, IvPagesTab, IvCustomTab;
    private TextView TvCountSounds, TvVolume;
    private SeekBar SeekVolume;
    private ViewPager PagerCategory;
    private TabLayout TabCategory;
    private ConstraintLayout ConsVolume;
    private LinearLayout LlButtonView, LlSelectedSoundsList;
    private DrawerLayout DrawerMain;
    private ActionBarDrawerToggle DrawerToggle;
    private NavigationView NavMain;
    private String[] ListOfCategory;
    private AudioManager audioManager;
    private boolean isStartPlayer = false;
    private RecyclerView RvSelectedSoundsList, RvSelectSound;
    private ConstraintLayout LlSelectSoundPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_med_main);
        MedInitViews();
        MedInitListeners();
        MedInitActions();
    }

    private void MedInitViews() {
        context = this;
        IvBack = (ImageView) findViewById(R.id.IvBack);
        IvCategoryImg = (ImageView) findViewById(R.id.IvCategoryImg);
        IvCategoryNext = (ImageView) findViewById(R.id.IvCategoryNext);
        IvCategoryPrevious = (ImageView) findViewById(R.id.IvCategoryPrevious);
        IvDrawer = (ImageView) findViewById(R.id.IvDrawer);
        IvDrawer = (ImageView) findViewById(R.id.IvDrawer);
        TvTitle = (TextView) findViewById(R.id.TvTitle);
        IvSetting = (ImageView) findViewById(R.id.IvSetting);
        IvUpload = (ImageView) findViewById(R.id.IvUpload);
        DrawerMain = (DrawerLayout) findViewById(R.id.DrawerMain);
        IvFav = (ImageView) findViewById(R.id.IvFav);
        IvSoundPlayPause = (ImageView) findViewById(R.id.IvSoundPlayPause);
        IvSoundStop = (ImageView) findViewById(R.id.IvSoundStop);
        IvPreview = (ImageView) findViewById(R.id.IvPreview);
        TvCountSounds = (TextView) findViewById(R.id.TvCountSounds);
        IvFavoriteTab = (ImageView) findViewById(R.id.IvFavoriteTab);
        IvReminderTab = (ImageView) findViewById(R.id.IvReminderTab);
        IvHomeTab = (ImageView) findViewById(R.id.IvHomeTab);
        IvPagesTab = (ImageView) findViewById(R.id.IvPagesTab);
        IvCustomTab = (ImageView) findViewById(R.id.IvCustomTab);
        SeekVolume = (SeekBar) findViewById(R.id.SeekVolume);
        TvVolume = (TextView) findViewById(R.id.TvVolume);
        PagerCategory = (ViewPager) findViewById(R.id.PagerCategory);
        TabCategory = (TabLayout) findViewById(R.id.TabCategory);
        ConsVolume = (ConstraintLayout) findViewById(R.id.ConsVolume);
        LlButtonView = (LinearLayout) findViewById(R.id.LlButtonView);
        LlSelectedSoundsList = (LinearLayout) findViewById(R.id.LlSelectedSoundsList);
        NavMain = (NavigationView) findViewById(R.id.NavMain);
        RvSelectedSoundsList = (RecyclerView) findViewById(R.id.RvSelectedSoundsList);
        LlSelectSoundPage = (ConstraintLayout) findViewById(R.id.LlSelectSoundPage);
        RvSelectSound = (RecyclerView) findViewById(R.id.RvSelectSound);
        NavMain.setNavigationItemSelectedListener(this);
    }

    private void MedInitListeners() {
        IvFavoriteTab.setOnClickListener(this);
        IvReminderTab.setOnClickListener(this);
        IvHomeTab.setOnClickListener(this);
        IvPagesTab.setOnClickListener(this);
        IvCustomTab.setOnClickListener(this);
        IvSetting.setOnClickListener(this);
        IvUpload.setOnClickListener(this);
        IvDrawer.setOnClickListener(this);
        IvCategoryNext.setOnClickListener(this);
        IvCategoryPrevious.setOnClickListener(this);
        IvSoundPlayPause.setOnClickListener(this);
        IvSoundStop.setOnClickListener(this);
        IvPreview.setOnClickListener(this);
    }

    private void MedInitActions() {
        IvDrawer.setVisibility(View.VISIBLE);
        IvSetting.setVisibility(View.VISIBLE);
        IvUpload.setVisibility(View.VISIBLE);
        IvBack.setVisibility(View.GONE);
        RvSelectedSoundsList.setVisibility(View.GONE);
        LlSelectedSoundsList.setVisibility(View.GONE);
        TvTitle.setText(getString(R.string.app_name));
        LlButtonView.setVisibility(View.GONE);
        LlSelectSoundPage.setVisibility(View.GONE);
        RvSelectSound.setVisibility(View.GONE);
        IvCategoryImg.setVisibility(View.VISIBLE);
        PagerCategory.setVisibility(View.VISIBLE);
        ConsVolume.setVisibility(View.VISIBLE);

        IvFavoriteTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), PorterDuff.Mode.SRC_IN);
        IvReminderTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), PorterDuff.Mode.SRC_IN);
        IvHomeTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color), PorterDuff.Mode.SRC_IN);
        IvPagesTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), PorterDuff.Mode.SRC_IN);
        IvCustomTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), PorterDuff.Mode.SRC_IN);

        DrawerToggle = new ActionBarDrawerToggle(this, DrawerMain, R.string.nav_open, R.string.nav_close);
        DrawerMain.addDrawerListener(DrawerToggle);
        DrawerToggle.syncState();
        MedConstants.mediaPlayerArrayList = new ArrayList<>();
        MedConstants.SelectedPlayerArrayList = new ArrayList<>();

        ArrayList<SoundModel> SoundModelsList = new ArrayList<SoundModel>();
        SoundModelsList.addAll(MedConstants.RainSounds());
        SoundModelsList.addAll(MedConstants.NatureSounds());
        SoundModelsList.addAll(MedConstants.WindSounds());
        SoundModelsList.addAll(MedConstants.WaterSounds());
        SoundModelsList.addAll(MedConstants.CitySounds());
        SoundModelsList.addAll(MedConstants.CountrySounds());
        SoundModelsList.addAll(MedConstants.NightSounds());
        SoundModelsList.addAll(MedConstants.HomeSounds());
        SoundModelsList.addAll(MedConstants.RelaxingSounds());
        SoundModelsList.addAll(MedConstants.NoiseSounds());
        SoundModelsList.addAll(MedConstants.BinauralSounds());
        for (int i = 0; i < SoundModelsList.size(); i++) {
            try {
                MediaPlayer mediaPlayer = new MediaPlayer();
//                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                float volume = (float) (1 - (Math.log(100 - SoundModelsList.get(i).getSoundVolume()) / Math.log(100)));
                mediaPlayer.setVolume(volume, volume);
                mediaPlayer.setDataSource(SoundModelsList.get(i).getSoundMp3());
                mediaPlayer.setLooping(true);

                PlyerModel plyerModel = new PlyerModel();
                plyerModel.setPlayerPos(i);
                plyerModel.setPlayer(mediaPlayer);
                plyerModel.setPlayerName(SoundModelsList.get(i).getSoundTitle());
                MedConstants.mediaPlayerArrayList.add(plyerModel);
            } catch (IOException e) {

            }
        }
        ListOfCategory = getResources().getStringArray(R.array.CategoryList);
        for (int i = 0; i < ListOfCategory.length; i++) {
            TabCategory.addTab(TabCategory.newTab().setText(ListOfCategory[i].toString()));
        }

        PagerCategory.setAdapter(new CategoryAdapter(getSupportFragmentManager(), context, TabCategory.getSelectedTabPosition(), ListOfCategory));
        TabCategory.setupWithViewPager(PagerCategory);

        if (PagerCategory.getCurrentItem() == 0) {
            IvCategoryPrevious.setVisibility(View.GONE);
        } else {
            IvCategoryPrevious.setVisibility(View.VISIBLE);
        }
        RvSelectedSoundsList.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
        RvSelectedSoundsList.setAdapter(new SelectSoundListAdapter(context, MedConstants.SelectedPlayerArrayList, position -> {
            for (int i = 0; i < MedConstants.mediaPlayerArrayList.size(); i++) {
                if (MedConstants.mediaPlayerArrayList.get(i).getPlayerPos() == MedConstants.SelectedPlayerArrayList.get(position).getPlayerPos()) {
                    if (MedConstants.mediaPlayerArrayList.get(i).getPlayer().isPlaying()) {
                        MedConstants.mediaPlayerArrayList.get(i).getPlayer().stop();
                    }
                }
            }
            for (int i = 0; i < MedConstants.SelectedPlayerArrayList.size(); i++) {
                if (MedConstants.SelectedPlayerArrayList.get(i).getPlayerPos() == MedConstants.SelectedPlayerArrayList.get(position).getPlayerPos()) {
                    if (MedConstants.SelectedPlayerArrayList.get(i).getPlayer().isPlaying()) {
                        MedConstants.SelectedPlayerArrayList.get(i).getPlayer().stop();
                    }
                }
            }
            MedConstants.SelectedPlayerArrayList.remove(position);
            Intent intent = new Intent(MedConstants.BROADCAST_FRAGMENT);
            intent.putExtra(MedConstants.FRAGMENT_CLICK, "Cancel");
            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
        }));

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        int volume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        TvVolume.setText(volume + "");
        SeekVolume.setProgress(volume);
        SeekVolume.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
        SeekVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int newVolume, boolean b) {
                TvVolume.setText(newVolume + "");
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, newVolume, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        LocalBroadcastManager.getInstance(context).registerReceiver(MainReceiver, new IntentFilter(MedConstants.BROADCAST_MAIN));
    }

    private BroadcastReceiver MainReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            TvCountSounds.setText(String.valueOf(intent.getIntExtra(MedConstants.SelectedSounds, 0)).toString());
            if (intent.getIntExtra(MedConstants.SelectedSounds, 0) >= 0) {
                LlButtonView.setVisibility(View.VISIBLE);
                IvSoundPlayPause.setImageResource(R.drawable.icon_pause);
                isStartPlayer = true;
            } else {
                IvSoundPlayPause.setImageResource(R.drawable.icon_play);
                LlButtonView.setVisibility(View.GONE);
                isStartPlayer = false;
            }
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_UP:
                audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_RAISE, AudioManager.FLAG_PLAY_SOUND | AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
                int volumeUp = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                TvVolume.setText(volumeUp + "");
                SeekVolume.setProgress(volumeUp);
                return true;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_LOWER, AudioManager.FLAG_PLAY_SOUND | AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
                int volumeDown = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                TvVolume.setText(volumeDown + "");
                SeekVolume.setProgress(volumeDown);
                return true;
            default:
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.IvFavoriteTab:
                if (DrawerMain.isOpen()) {
                    DrawerMain.closeDrawer(GravityCompat.START);
                }

                LlSelectSoundPage.setVisibility(View.GONE);
                RvSelectSound.setVisibility(View.GONE);
                IvCategoryImg.setVisibility(View.VISIBLE);
                PagerCategory.setVisibility(View.VISIBLE);
                IvFavoriteTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color), android.graphics.PorterDuff.Mode.SRC_IN);
                IvReminderTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), android.graphics.PorterDuff.Mode.SRC_IN);
                IvHomeTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), android.graphics.PorterDuff.Mode.SRC_IN);
                IvPagesTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), android.graphics.PorterDuff.Mode.SRC_IN);
                IvCustomTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), android.graphics.PorterDuff.Mode.SRC_IN);
                break;
            case R.id.IvReminderTab:
                if (DrawerMain.isOpen()) {
                    DrawerMain.closeDrawer(GravityCompat.START);
                }

                LlSelectSoundPage.setVisibility(View.GONE);
                RvSelectSound.setVisibility(View.GONE);
                IvCategoryImg.setVisibility(View.VISIBLE);
                PagerCategory.setVisibility(View.VISIBLE);
                SoundReminderDialog reminderDialog = new SoundReminderDialog(MedMainActivity.this, context, (SoundReminderDialog soundReminderDialog) -> {
                    soundReminderDialog.dismiss();
                    IvFavoriteTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), android.graphics.PorterDuff.Mode.SRC_IN);
                    IvReminderTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), android.graphics.PorterDuff.Mode.SRC_IN);
                    IvHomeTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color), android.graphics.PorterDuff.Mode.SRC_IN);
                    IvPagesTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), android.graphics.PorterDuff.Mode.SRC_IN);
                    IvCustomTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), android.graphics.PorterDuff.Mode.SRC_IN);

                });
                reminderDialog.show();
                WindowManager.LayoutParams lp = reminderDialog.getWindow().getAttributes();
                Window window = reminderDialog.getWindow();
                lp.copyFrom(window.getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                lp.gravity = Gravity.CENTER;
                window.setAttributes(lp);
                IvFavoriteTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), android.graphics.PorterDuff.Mode.SRC_IN);
                IvReminderTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color), android.graphics.PorterDuff.Mode.SRC_IN);
                IvHomeTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), android.graphics.PorterDuff.Mode.SRC_IN);
                IvPagesTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), android.graphics.PorterDuff.Mode.SRC_IN);
                IvCustomTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), android.graphics.PorterDuff.Mode.SRC_IN);
                break;
            case R.id.IvHomeTab:
                if (DrawerMain.isOpen()) {
                    DrawerMain.closeDrawer(GravityCompat.START);
                }

                LlSelectSoundPage.setVisibility(View.GONE);
                RvSelectSound.setVisibility(View.GONE);
                IvCategoryImg.setVisibility(View.VISIBLE);
                PagerCategory.setVisibility(View.VISIBLE);
                IvFavoriteTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), android.graphics.PorterDuff.Mode.SRC_IN);
                IvReminderTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), android.graphics.PorterDuff.Mode.SRC_IN);
                IvHomeTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color), android.graphics.PorterDuff.Mode.SRC_IN);
                IvPagesTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), android.graphics.PorterDuff.Mode.SRC_IN);
                IvCustomTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), android.graphics.PorterDuff.Mode.SRC_IN);
                break;
            case R.id.IvPagesTab:
                if (DrawerMain.isOpen()) {
                    DrawerMain.closeDrawer(GravityCompat.START);
                }

                LlSelectSoundPage.setVisibility(View.VISIBLE);
                RvSelectSound.setVisibility(View.VISIBLE);
                IvCategoryImg.setVisibility(View.GONE);
                PagerCategory.setVisibility(View.GONE);
                RvSelectSound.setLayoutManager(new GridLayoutManager(context, 2));
                RvSelectSound.setAdapter(new SelectSoundAdapter(context, ListOfCategory, position -> {
                    PagerCategory.setCurrentItem(position);

                    LlSelectSoundPage.setVisibility(View.GONE);
                    RvSelectSound.setVisibility(View.GONE);
                    IvCategoryImg.setVisibility(View.VISIBLE);
                    PagerCategory.setVisibility(View.VISIBLE);

                    IvFavoriteTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), android.graphics.PorterDuff.Mode.SRC_IN);
                    IvReminderTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), android.graphics.PorterDuff.Mode.SRC_IN);
                    IvHomeTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color), android.graphics.PorterDuff.Mode.SRC_IN);
                    IvPagesTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), android.graphics.PorterDuff.Mode.SRC_IN);
                    IvCustomTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), android.graphics.PorterDuff.Mode.SRC_IN);

                }));

                IvFavoriteTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), android.graphics.PorterDuff.Mode.SRC_IN);
                IvReminderTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), android.graphics.PorterDuff.Mode.SRC_IN);
                IvHomeTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), android.graphics.PorterDuff.Mode.SRC_IN);
                IvPagesTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color), android.graphics.PorterDuff.Mode.SRC_IN);
                IvCustomTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), android.graphics.PorterDuff.Mode.SRC_IN);
                break;
            case R.id.IvCustomTab:
                if (DrawerMain.isOpen()) {
                    DrawerMain.closeDrawer(GravityCompat.START);
                }

                LlSelectSoundPage.setVisibility(View.GONE);
                RvSelectSound.setVisibility(View.GONE);
                IvCategoryImg.setVisibility(View.VISIBLE);
                PagerCategory.setVisibility(View.VISIBLE);

                IvFavoriteTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), android.graphics.PorterDuff.Mode.SRC_IN);
                IvReminderTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), android.graphics.PorterDuff.Mode.SRC_IN);
                IvHomeTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), android.graphics.PorterDuff.Mode.SRC_IN);
                IvPagesTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), android.graphics.PorterDuff.Mode.SRC_IN);
                IvCustomTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color), android.graphics.PorterDuff.Mode.SRC_IN);
                break;
            case R.id.IvSetting:
                if (DrawerMain.isOpen()) {
                    DrawerMain.closeDrawer(GravityCompat.START);
                }

                LlSelectSoundPage.setVisibility(View.GONE);
                RvSelectSound.setVisibility(View.GONE);
                IvCategoryImg.setVisibility(View.VISIBLE);
                PagerCategory.setVisibility(View.VISIBLE);

                SoundSettingDialog settingDialog = new SoundSettingDialog(MedMainActivity.this, context);
                settingDialog.show();
                WindowManager.LayoutParams params = settingDialog.getWindow().getAttributes();
                Window dialogWindow = settingDialog.getWindow();
                params.copyFrom(dialogWindow.getAttributes());
                params.width = WindowManager.LayoutParams.MATCH_PARENT;
                params.height = WindowManager.LayoutParams.WRAP_CONTENT;
                params.gravity = Gravity.CENTER;
                dialogWindow.setAttributes(params);
                break;
            case R.id.IvUpload:
                if (DrawerMain.isOpen()) {
                    DrawerMain.closeDrawer(GravityCompat.START);
                }

                LlSelectSoundPage.setVisibility(View.GONE);
                RvSelectSound.setVisibility(View.GONE);
                IvCategoryImg.setVisibility(View.VISIBLE);
                PagerCategory.setVisibility(View.VISIBLE);
                break;
            case R.id.IvDrawer:
                if (DrawerMain.isOpen()) {
                    DrawerMain.closeDrawer(GravityCompat.START);
                } else {
                    DrawerMain.openDrawer(GravityCompat.START);
                }

                LlSelectSoundPage.setVisibility(View.GONE);
                RvSelectSound.setVisibility(View.GONE);
                IvCategoryImg.setVisibility(View.VISIBLE);
                PagerCategory.setVisibility(View.VISIBLE);
                break;
            case R.id.IvCategoryNext:
                if (DrawerMain.isOpen()) {
                    DrawerMain.closeDrawer(GravityCompat.START);
                }
                if ((PagerCategory.getCurrentItem() + 1) == (ListOfCategory.length - 1)) {
                    IvCategoryNext.setVisibility(View.GONE);
                } else {
                    IvCategoryNext.setVisibility(View.VISIBLE);
                }
                IvCategoryPrevious.setVisibility(View.VISIBLE);

                if (PagerCategory.getCurrentItem() != (ListOfCategory.length - 1)) {
                    PagerCategory.setCurrentItem(PagerCategory.getCurrentItem() + 1);
                }

                LlSelectSoundPage.setVisibility(View.GONE);
                RvSelectSound.setVisibility(View.GONE);
                IvCategoryImg.setVisibility(View.VISIBLE);
                PagerCategory.setVisibility(View.VISIBLE);
                break;
            case R.id.IvCategoryPrevious:
                if (DrawerMain.isOpen()) {
                    DrawerMain.closeDrawer(GravityCompat.START);
                }
                if ((PagerCategory.getCurrentItem() - 1) == 0) {
                    IvCategoryPrevious.setVisibility(View.GONE);
                } else {
                    IvCategoryPrevious.setVisibility(View.VISIBLE);
                }
                IvCategoryNext.setVisibility(View.VISIBLE);
                if (PagerCategory.getCurrentItem() != 0) {
                    PagerCategory.setCurrentItem(PagerCategory.getCurrentItem() - 1);
                }

                LlSelectSoundPage.setVisibility(View.GONE);
                RvSelectSound.setVisibility(View.GONE);
                IvCategoryImg.setVisibility(View.VISIBLE);
                PagerCategory.setVisibility(View.VISIBLE);
                break;
            case R.id.IvSoundPlayPause:
                try {
                    if (DrawerMain.isOpen()) {
                        DrawerMain.closeDrawer(GravityCompat.START);
                    }
                    if (!isStartPlayer) {
                        if (MedConstants.SelectedPlayerArrayList.size() >= 0) {
                            for (int i = 0; i < MedConstants.SelectedPlayerArrayList.size(); i++) {
                                MedConstants.SelectedPlayerArrayList.get(i).getPlayer().prepare();
                                int finalI = i;
                                MedConstants.SelectedPlayerArrayList.get(i).getPlayer().setOnPreparedListener(mp ->
                                        MedConstants.SelectedPlayerArrayList.get(finalI).getPlayer().start());
                                MedConstants.SelectedPlayerArrayList.get(i).getPlayer().setOnCompletionListener(mp ->
                                        MedConstants.SelectedPlayerArrayList.get(finalI).getPlayer().start());
                            }
                        } else {
                            Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
                        }
                        IvSoundPlayPause.setImageResource(R.drawable.icon_pause);
                        isStartPlayer = true;
                    } else {
                        isStartPlayer = false;
                        for (int i = 0; i < MedConstants.mediaPlayerArrayList.size(); i++) {
                            if (MedConstants.mediaPlayerArrayList.get(i).getPlayer().isPlaying()) {
                                MedConstants.mediaPlayerArrayList.get(i).getPlayer().stop();
                            }
                        }
                        for (int i = 0; i < MedConstants.SelectedPlayerArrayList.size(); i++) {
                            if (MedConstants.SelectedPlayerArrayList.get(i).getPlayer().isPlaying()) {
                                MedConstants.SelectedPlayerArrayList.get(i).getPlayer().stop();
                            }
                        }
                        IvSoundPlayPause.setImageResource(R.drawable.icon_play);
                    }

                    LlSelectSoundPage.setVisibility(View.GONE);
                    RvSelectSound.setVisibility(View.GONE);
                    IvCategoryImg.setVisibility(View.VISIBLE);
                    PagerCategory.setVisibility(View.VISIBLE);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;
            case R.id.IvSoundStop:
                if (DrawerMain.isOpen()) {
                    DrawerMain.closeDrawer(GravityCompat.START);
                }
                IvSoundPlayPause.setImageResource(R.drawable.icon_play);
                LlButtonView.setVisibility(View.GONE);
                for (int i = 0; i < MedConstants.mediaPlayerArrayList.size(); i++) {
                    if (MedConstants.mediaPlayerArrayList.get(i).getPlayer().isPlaying()) {
                        MedConstants.mediaPlayerArrayList.get(i).getPlayer().stop();
                    }
                }
                for (int i = 0; i < MedConstants.SelectedPlayerArrayList.size(); i++) {
                    if (MedConstants.SelectedPlayerArrayList.get(i).getPlayer().isPlaying()) {
                        MedConstants.SelectedPlayerArrayList.get(i).getPlayer().stop();
                    }
                }
                MedConstants.SelectedPlayerArrayList = new ArrayList<>();
                TvCountSounds.setText("0");
                Intent intent = new Intent(MedConstants.BROADCAST_FRAGMENT);
                intent.putExtra(MedConstants.FRAGMENT_CLICK, "Stop");
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

                LlSelectSoundPage.setVisibility(View.GONE);
                RvSelectSound.setVisibility(View.GONE);
                IvCategoryImg.setVisibility(View.VISIBLE);
                PagerCategory.setVisibility(View.VISIBLE);
                break;
            case R.id.IvPreview:
                if (DrawerMain.isOpen()) {
                    DrawerMain.closeDrawer(GravityCompat.START);
                }

                if (MedConstants.SelectedPlayerArrayList.size() > 0) {
                    if (LlSelectedSoundsList.getVisibility() == View.VISIBLE) {
                        LlSelectedSoundsList.setVisibility(View.GONE);
                    } else {
                        LlSelectedSoundsList.setVisibility(View.VISIBLE);
                    }
                }

                break;
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.NavMail:
                if (DrawerMain.isOpen()) {
                    DrawerMain.closeDrawer(GravityCompat.START);
                }
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
                emailIntent.putExtra(Intent.EXTRA_TEXT, "body of email");
                emailIntent.setData(Uri.parse("mailto:"));
                try {
                    startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(context, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.NavFAQ:
                if (DrawerMain.isOpen()) {
                    DrawerMain.closeDrawer(GravityCompat.START);
                }
                break;
            case R.id.NavRating:
                if (DrawerMain.isOpen()) {
                    DrawerMain.closeDrawer(GravityCompat.START);
                }
                Uri uri = Uri.parse("market://details?id=" + getPackageName());
                Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    startActivity(myAppLinkToMarket);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(context, " unable to find market app", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.NavPolicy:
                if (DrawerMain.isOpen()) {
                    DrawerMain.closeDrawer(GravityCompat.START);
                }
                break;
        }
        return false;
    }
}
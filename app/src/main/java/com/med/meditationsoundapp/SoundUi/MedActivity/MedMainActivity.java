package com.med.meditationsoundapp.SoundUi.MedActivity;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.med.meditationsoundapp.R;
import com.med.meditationsoundapp.SoundConstants.MedConstants;
import com.med.meditationsoundapp.SoundDatabase.MedDatabaseHelper;
import com.med.meditationsoundapp.SoundDialog.SoundFavoriteDialog;
import com.med.meditationsoundapp.SoundDialog.SoundPlayAllDialog;
import com.med.meditationsoundapp.SoundDialog.SoundReminderDialog;
import com.med.meditationsoundapp.SoundDialog.SoundSelectDialog;
import com.med.meditationsoundapp.SoundDialog.SoundSettingDialog;
import com.med.meditationsoundapp.SoundModel.FavModel;
import com.med.meditationsoundapp.SoundModel.PlyerModel;
import com.med.meditationsoundapp.SoundModel.SoundModel;
import com.med.meditationsoundapp.SoundService.MediaPlayerService;
import com.med.meditationsoundapp.SoundUi.MedAdapter.CategoryAdapter;
import com.med.meditationsoundapp.SoundUi.MedAdapter.CustomSoundAdapter;
import com.med.meditationsoundapp.SoundUi.MedAdapter.SelectSoundAdapter;
import com.med.meditationsoundapp.SoundUi.MedAdapter.SelectSoundListAdapter;
import com.med.meditationsoundapp.SoundUtils.MedPref;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
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

public class MedMainActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    private Context context;
    private ImageView IvBack, IvCategoryImg, IvCategoryNext, IvCategoryPrevious, IvDrawer, IvSetting, IvUpload;
    private TextView TvTitle, TvReminderTime;
    private ImageView IvFav, IvSoundPlayPause, IvSoundStop, IvPreview, IvFavoriteTab, IvReminderTab, IvHomeTab, IvPagesTab, IvCustomBar, IvCustomTab, IvVolume, IvPlayAll;
    private TextView TvCountSounds, TvVolume, TvSelectSound, TvPlayAll;
    private SeekBar SeekVolume;
    private ViewPager PagerCategory;
    private TabLayout TabCategory;
    private ConstraintLayout ConsVolume;
    private LinearLayout LlButtonView, LlSelectedSoundsList, LlPlayAll;
    private DrawerLayout DrawerMain;
    private ActionBarDrawerToggle DrawerToggle;
    private NavigationView NavMain;
    private String[] ListOfCategory;
    private AudioManager audioManager;
    private boolean isStartPlayer = false;
    private RecyclerView RvSelectedSoundsList, RvSelectSound;
    private ConstraintLayout LlSelectSoundPage;
    private int PlayerPos;
    private View ViewProgress;
    private ArrayList<SoundModel> CustomSoundModels = new ArrayList<>();
    private int CustomPos;
    private CountDownTimer countDownTimer, countDownReminder;
    private ArrayList<SoundModel> SoundModelsList;
    private CategoryAdapter adapter;
    private ConstraintLayout content_main;
    private MedDatabaseHelper helper;
    private RelativeLayout RlTopBar, RlToolBottom;
    private ProgressBar ProgressDialog;
    private LinearLayout ConsBottom;
    private LinearLayout LlHeaderImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_med_main);
    }

    private void MedInitViews() {
        context = this;
        helper = new MedDatabaseHelper(context);
        IvBack = (ImageView) findViewById(R.id.IvBack);
        IvCategoryImg = (ImageView) findViewById(R.id.IvCategoryImg);
        IvCategoryNext = (ImageView) findViewById(R.id.IvCategoryNext);
        RlTopBar = (RelativeLayout) findViewById(R.id.RlTopBar);
        RlToolBottom = (RelativeLayout) findViewById(R.id.RlToolBottom);
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
        IvCustomBar = (ImageView) findViewById(R.id.IvCustomBar);
        IvVolume = (ImageView) findViewById(R.id.IvVolume);
        SeekVolume = (SeekBar) findViewById(R.id.SeekVolume);
        TvVolume = (TextView) findViewById(R.id.TvVolume);
        IvPlayAll = (ImageView) findViewById(R.id.IvPlayAll);
        TvReminderTime = (TextView) findViewById(R.id.TvReminderTime);
        TvSelectSound = (TextView) findViewById(R.id.TvSelectSound);
        TvPlayAll = (TextView) findViewById(R.id.TvPlayAll);
        PagerCategory = (ViewPager) findViewById(R.id.PagerCategory);
        TabCategory = (TabLayout) findViewById(R.id.TabCategory);
        ConsVolume = (ConstraintLayout) findViewById(R.id.ConsVolume);
        LlButtonView = (LinearLayout) findViewById(R.id.LlButtonView);
        LlSelectedSoundsList = (LinearLayout) findViewById(R.id.LlSelectedSoundsList);
        LlPlayAll = (LinearLayout) findViewById(R.id.LlPlayAll);
        NavMain = (NavigationView) findViewById(R.id.NavMain);
        RvSelectedSoundsList = (RecyclerView) findViewById(R.id.RvSelectedSoundsList);
        LlSelectSoundPage = (ConstraintLayout) findViewById(R.id.LlSelectSoundPage);
        RvSelectSound = (RecyclerView) findViewById(R.id.RvSelectSound);
        content_main = (ConstraintLayout) findViewById(R.id.content_main);
        ViewProgress = (View) findViewById(R.id.ViewProgress);
        ProgressDialog = (ProgressBar) findViewById(R.id.ProgressDialog);
        ConsBottom = (LinearLayout) findViewById(R.id.ConsBottom);
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
        LlPlayAll.setOnClickListener(this);
        content_main.setOnClickListener(this);
        IvFav.setOnClickListener(this);
        PagerCategory.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (new MedPref(context).getBoolean(MedPref.BOOL_NIGHT, false)) {
                    switch (position) {
                        case 0:
                            IvCategoryImg.setImageResource(R.drawable.ic_rain_img_dark);
                            break;
                        case 1:
                            IvCategoryImg.setImageResource(R.drawable.ic_nature_img_dark);
                            break;
                        case 2:
                            IvCategoryImg.setImageResource(R.drawable.ic_wind_img_dark);
                            break;
                        case 3:
                            IvCategoryImg.setImageResource(R.drawable.ic_water_img_dark);
                            break;
                        case 4:
                            IvCategoryImg.setImageResource(R.drawable.ic_city_img_dark);
                            break;
                        case 5:
                            IvCategoryImg.setImageResource(R.drawable.ic_country_img_dark);
                            break;
                        case 6:
                            IvCategoryImg.setImageResource(R.drawable.ic_night_img_dark);
                            break;
                        case 7:
                            IvCategoryImg.setImageResource(R.drawable.ic_home_img_dark);
                            break;
                        case 8:
                            IvCategoryImg.setImageResource(R.drawable.ic_relaxing_img_dark);
                            break;
                        case 9:
                            IvCategoryImg.setImageResource(R.drawable.ic_noise_img_dark);
                            break;
                        case 10:
                            IvCategoryImg.setImageResource(R.drawable.ic_binaural_img_dark);
                            break;
                        case 11:
                            IvCategoryImg.setImageResource(R.drawable.ic_binaural_img_dark);
                            break;
                    }
                } else {
                    switch (position) {
                        case 0:
                            IvCategoryImg.setImageResource(R.drawable.ic_rain_img);
                            break;
                        case 1:
                            IvCategoryImg.setImageResource(R.drawable.ic_nature_img);
                            break;
                        case 2:
                            IvCategoryImg.setImageResource(R.drawable.ic_wind_img);
                            break;
                        case 3:
                            IvCategoryImg.setImageResource(R.drawable.ic_water_img);
                            break;
                        case 4:
                            IvCategoryImg.setImageResource(R.drawable.ic_city_img);
                            break;
                        case 5:
                            IvCategoryImg.setImageResource(R.drawable.ic_country_img);
                            break;
                        case 6:
                            IvCategoryImg.setImageResource(R.drawable.ic_night_img);
                            break;
                        case 7:
                            IvCategoryImg.setImageResource(R.drawable.ic_home_img);
                            break;
                        case 8:
                            IvCategoryImg.setImageResource(R.drawable.ic_relaxing_img);
                            break;
                        case 9:
                            IvCategoryImg.setImageResource(R.drawable.ic_noise_img);
                            break;
                        case 10:
                            IvCategoryImg.setImageResource(R.drawable.ic_binaural_img);
                            break;
                        case 11:
                            IvCategoryImg.setImageResource(R.drawable.ic_binaural_img);
                            break;
                    }
                }
                if (position == 0) {
                    IvCategoryPrevious.setVisibility(View.GONE);
                } else {
                    IvCategoryPrevious.setVisibility(View.VISIBLE);
                }
                if ((position + 1) == (ListOfCategory.length)) {
                    IvCategoryNext.setVisibility(View.GONE);
                } else {
                    IvCategoryNext.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void MedInitActions() {
        IvDrawer.setVisibility(View.VISIBLE);
        IvSetting.setVisibility(View.VISIBLE);
        IvUpload.setVisibility(View.VISIBLE);
        IvBack.setVisibility(View.GONE);
        RvSelectedSoundsList.setVisibility(View.VISIBLE);
        LlSelectedSoundsList.setVisibility(View.GONE);
        TvTitle.setText(getString(R.string.app_name));
//        LlButtonView.setVisibility(View.GONE);
        LlSelectSoundPage.setVisibility(View.GONE);
        RvSelectSound.setVisibility(View.GONE);
        ViewProgress.setVisibility(View.GONE);
        TvReminderTime.setVisibility(View.GONE);
        IvCategoryImg.setVisibility(View.VISIBLE);
        PagerCategory.setVisibility(View.VISIBLE);
        if (new MedPref(context).getBoolean(MedPref.BOOL_DEVICE, true)) {
            ConsVolume.setVisibility(View.VISIBLE);
        } else {
            ConsVolume.setVisibility(View.GONE);
        }
        new MedPref(context).putBoolean(MedPref.SET_TIMER, false);
        if (new MedPref(context).getBoolean(MedPref.BOOL_NIGHT, false)) {
            IvFavoriteTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), PorterDuff.Mode.SRC_IN);
            IvReminderTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), PorterDuff.Mode.SRC_IN);
            IvHomeTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_dark), PorterDuff.Mode.SRC_IN);
            IvPagesTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), PorterDuff.Mode.SRC_IN);
            IvCustomTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), PorterDuff.Mode.SRC_IN);
        } else {
            IvFavoriteTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), PorterDuff.Mode.SRC_IN);
            IvReminderTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), PorterDuff.Mode.SRC_IN);
            IvHomeTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color), PorterDuff.Mode.SRC_IN);
            IvPagesTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), PorterDuff.Mode.SRC_IN);
            IvCustomTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), PorterDuff.Mode.SRC_IN);
        }
        DrawerToggle = new ActionBarDrawerToggle(this, DrawerMain, R.string.nav_open, R.string.nav_close);
        DrawerMain.addDrawerListener(DrawerToggle);
        DrawerToggle.syncState();

        ListOfCategory = getResources().getStringArray(R.array.CategoryList);
        for (int i = 0; i < ListOfCategory.length; i++) {
            TabCategory.addTab(TabCategory.newTab().setText(ListOfCategory[i].toString()));
        }


        if (PagerCategory.getCurrentItem() == 0) {
            IvCategoryPrevious.setVisibility(View.GONE);
        } else {
            IvCategoryPrevious.setVisibility(View.VISIBLE);
        }

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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            String s = Manifest.permission.READ_MEDIA_AUDIO;
            String s1 = Manifest.permission.POST_NOTIFICATIONS;
            Dexter.withActivity(this)
                    .withPermissions(s, s1)
                    .withListener(new MultiplePermissionsListener() {
                        public void onPermissionsChecked(MultiplePermissionsReport report) {
                            if (report.areAllPermissionsGranted()) {
                                MedConstants.getSoundDefault(context);
                                MedConstants.SelectedPlayerArrayList = new ArrayList<>();
                                initArrays();
                            }
                            if (report.isAnyPermissionPermanentlyDenied()) {
                                MedConstants.showSettingsDialog(MedMainActivity.this);
                            }
                        }

                        public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken permissionToken) {
                            MedConstants.showPermissionDialog(MedMainActivity.this, permissionToken);
                        }
                    })
                    .onSameThread()
                    .check();
        } else {
            String s = Manifest.permission.READ_EXTERNAL_STORAGE;
            Dexter.withActivity(this)
                    .withPermissions(s)
                    .withListener(new MultiplePermissionsListener() {
                        public void onPermissionsChecked(MultiplePermissionsReport report) {
                            if (report.areAllPermissionsGranted()) {
                                MedConstants.getSoundDefault(context);
                                MedConstants.SelectedPlayerArrayList = new ArrayList<>();
                                initArrays();
                            }
                            if (report.isAnyPermissionPermanentlyDenied()) {
                                MedConstants.showSettingsDialog(MedMainActivity.this);
                            }
                        }

                        public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken permissionToken) {
                            MedConstants.showPermissionDialog(MedMainActivity.this, permissionToken);
                        }
                    })
                    .onSameThread()
                    .check();
        }
        LocalBroadcastManager.getInstance(context).registerReceiver(MainReceiver, new IntentFilter(MedConstants.BROADCAST_MAIN));
        LocalBroadcastManager.getInstance(context).registerReceiver(ThemeReceiver, new IntentFilter(MedConstants.BROADCAST_THEME));
        LocalBroadcastManager.getInstance(context).registerReceiver(MainSettingReceiver, new IntentFilter(MedConstants.BROADCAST_SETTING));
    }

    private void initArrays() {
        MedConstants.mediaPlayerArrayList = new ArrayList<>();

        SoundModelsList = new ArrayList<SoundModel>();
        SoundModelsList.addAll(MedConstants.RainSounds(context));
        SoundModelsList.addAll(MedConstants.NatureSounds(context));
        SoundModelsList.addAll(MedConstants.WindSounds(context));
        SoundModelsList.addAll(MedConstants.WaterSounds(context));
        SoundModelsList.addAll(MedConstants.CitySounds(context));
        SoundModelsList.addAll(MedConstants.CountrySounds(context));
        SoundModelsList.addAll(MedConstants.NightSounds(context));
        SoundModelsList.addAll(MedConstants.HomeSounds(context));
        SoundModelsList.addAll(MedConstants.RelaxingSounds(context));
        SoundModelsList.addAll(MedConstants.NoiseSounds(context));
        SoundModelsList.addAll(MedConstants.BinauralSounds(context));
        SoundModelsList.addAll(MedConstants.getSoundDefault(context));
        for (int i = 0; i < SoundModelsList.size(); i++) {
            try {
                MediaPlayer mediaPlayer = new MediaPlayer();
                float volume = (float) (1 - (Math.log(100 - SoundModelsList.get(i).getSoundVolume()) / Math.log(100)));
                mediaPlayer.setVolume(volume, volume);
                mediaPlayer.setDataSource(SoundModelsList.get(i).getSoundMp3());
                mediaPlayer.setLooping(true);

                PlyerModel plyerModel = new PlyerModel();
                plyerModel.setPlayerPos(i);
                plyerModel.setPlayerVolume(SoundModelsList.get(i).getSoundVolume());
                plyerModel.setPlayer(mediaPlayer);
                plyerModel.setPlayerName(SoundModelsList.get(i).getSoundTitle());
                MedConstants.mediaPlayerArrayList.add(plyerModel);
            } catch (IOException e) {

            }
        }
        adapter = new CategoryAdapter(getSupportFragmentManager(), context, TabCategory.getSelectedTabPosition(), ListOfCategory);
        PagerCategory.setAdapter(adapter);
        TabCategory.setupWithViewPager(PagerCategory);

        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();

        MedInitViews();
        MedInitListeners();
        MedInitActions();
        if (new MedPref(context).getBoolean(MedPref.BOOL_NIGHT, false)) {
            Window window = getWindow();
            window.setStatusBarColor(ContextCompat.getColor(context, R.color.purple_200_dark));

            RlTopBar.setBackgroundResource(R.drawable.ic_top_bar_dark);
            RlToolBottom.setBackgroundResource(R.drawable.ic_top_inner_bar_dark);
            TvReminderTime.setBackgroundResource(R.drawable.ic_setting_title_dark);
            IvFav.setImageResource(R.drawable.icon_fav_dark);
            IvSoundPlayPause.setImageResource(R.drawable.icon_pause_dark);
            IvSoundStop.setImageResource(R.drawable.icon_stop_dark);
            IvPreview.setImageResource(R.drawable.icon_preview_dark);
            IvVolume.setImageResource(R.drawable.ic_volume_dark);
            IvPlayAll.setImageResource(R.drawable.ic_play_all_dark);
            IvCustomBar.setImageResource(R.drawable.ic_title_bar_dark);
            TvSelectSound.setTextColor(getResources().getColor(R.color.black_dark));
            TvPlayAll.setTextColor(getResources().getColor(R.color.black_dark));
            View header = NavMain.getHeaderView(0);
            LlHeaderImage = (LinearLayout) header.findViewById(R.id.LlHeaderImage);
            LlHeaderImage.setBackgroundResource(R.drawable.ic_header_img_dark);
            NavMain.setBackgroundColor(getResources().getColor(R.color.black));
            NavMain.getItemBackground().setColorFilter(ContextCompat.getColor(context, R.color.black), PorterDuff.Mode.SRC_IN);
            NavMain.setItemTextColor(ColorStateList.valueOf(R.color.black_dark));
            ConsVolume.setBackgroundResource(R.drawable.ic_volume_bar_dark);
            TvVolume.setTextColor(ContextCompat.getColor(context, R.color.black_dark));
            DrawerMain.setBackgroundColor(ContextCompat.getColor(context, R.color.black));
            SeekVolume.getProgressDrawable().setColorFilter(ContextCompat.getColor(context, R.color.purple_200_dark), PorterDuff.Mode.SRC_IN);
            TvCountSounds.getBackground().setColorFilter(ContextCompat.getColor(context, R.color.purple_200_dark), PorterDuff.Mode.SRC_IN);
            ProgressDialog.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(context, R.color.purple_200_dark), PorterDuff.Mode.SRC_IN);
            ConsBottom.setBackgroundColor(getResources().getColor(R.color.app_main_color_light10));
        } else {
            Window window = getWindow();
            window.setStatusBarColor(ContextCompat.getColor(context, R.color.purple_200));

            ConsVolume.setBackgroundResource(R.drawable.ic_volume_bar);
            ConsBottom.setBackgroundColor(getResources().getColor(R.color.app_main_color_light10));
            RlTopBar.setBackgroundResource(R.drawable.ic_top_bar);
            RlToolBottom.setBackgroundResource(R.drawable.ic_top_inner_bar);
            TvReminderTime.setBackgroundResource(R.drawable.ic_setting_title);
            IvFav.setImageResource(R.drawable.icon_fav);
            IvSoundPlayPause.setImageResource(R.drawable.icon_pause);
            IvSoundStop.setImageResource(R.drawable.icon_stop);
            TvSelectSound.setTextColor(getResources().getColor(R.color.black));
            TvPlayAll.setTextColor(getResources().getColor(R.color.black));
            IvPreview.setImageResource(R.drawable.icon_preview);
            IvVolume.setImageResource(R.drawable.ic_volume);
            IvCustomBar.setImageResource(R.drawable.ic_title_bar_dark);
            View header = NavMain.getHeaderView(0);
            LlHeaderImage = (LinearLayout) header.findViewById(R.id.LlHeaderImage);
            LlHeaderImage.setBackgroundResource(R.drawable.ic_header_img);
            NavMain.setBackgroundColor(getResources().getColor(R.color.black_dark));
            NavMain.setItemTextColor(ColorStateList.valueOf(R.color.black));
            NavMain.getItemBackground().setColorFilter(ContextCompat.getColor(context, R.color.black_dark), PorterDuff.Mode.SRC_IN);
            IvPlayAll.setImageResource(R.drawable.ic_play_all);
            TvVolume.setTextColor(ContextCompat.getColor(context, R.color.black));
            DrawerMain.setBackgroundColor(ContextCompat.getColor(context, R.color.black_dark));
            SeekVolume.getProgressDrawable().setColorFilter(ContextCompat.getColor(context, R.color.purple_200), PorterDuff.Mode.SRC_IN);
            TvCountSounds.getBackground().setColorFilter(ContextCompat.getColor(context, R.color.purple_200), PorterDuff.Mode.SRC_IN);
            ProgressDialog.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(context, R.color.purple_200), PorterDuff.Mode.SRC_IN);
        }
    }

    private BroadcastReceiver MainReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            TvCountSounds.setText(String.valueOf(intent.getIntExtra(MedConstants.SelectedSounds, 0)).toString());

            if (intent.getIntExtra(MedConstants.SelectedSounds, 0) > 0) {
                LlButtonView.setVisibility(View.VISIBLE);
                IvSoundPlayPause.setImageResource(R.drawable.icon_pause);
                isStartPlayer = true;
            } else {
                IvSoundPlayPause.setImageResource(R.drawable.icon_play);
                LlButtonView.setVisibility(View.GONE);
                isStartPlayer = false;
            }
            PagerCategory.getAdapter().notifyDataSetChanged();
            onResume();
        }
    };

    private BroadcastReceiver ThemeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            PagerCategory.getAdapter().notifyDataSetChanged();
            onResume();
        }
    };

    private BroadcastReceiver MainSettingReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (new MedPref(context).getBoolean(MedPref.BOOL_DEVICE, true)) {
                ConsVolume.setVisibility(View.VISIBLE);
            } else {
                ConsVolume.setVisibility(View.GONE);
            }

            Intent intentS = new Intent(MedConstants.BROADCAST_FRAGMENT);
            intentS.putExtra(MedConstants.FRAGMENT_CLICK, "ArraySet");
            LocalBroadcastManager.getInstance(context).sendBroadcast(intentS);
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
                if (new MedPref(context).getBoolean(MedPref.BOOL_DEVICE, true)) {
                    ConsVolume.setVisibility(View.VISIBLE);
                } else {
                    ConsVolume.setVisibility(View.VISIBLE);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ConsVolume.setVisibility(View.GONE);
                        }
                    }, 2000);
                }
                return true;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_LOWER, AudioManager.FLAG_PLAY_SOUND | AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
                int volumeDown = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                TvVolume.setText(volumeDown + "");
                SeekVolume.setProgress(volumeDown);
                if (new MedPref(context).getBoolean(MedPref.BOOL_DEVICE, true)) {
                    ConsVolume.setVisibility(View.VISIBLE);
                } else {
                    ConsVolume.setVisibility(View.VISIBLE);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ConsVolume.setVisibility(View.GONE);
                        }
                    }, 2000);
                }
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

                ArrayList<FavModel> favModels = helper.getGroupByUniqueIdList();
                if (favModels.size() > 0) {
                    SoundSelectDialog soundSelectDialog = new SoundSelectDialog(MedMainActivity.this, context, favModels, new SoundSelectDialog.DialogDismiss() {
                        @Override
                        public void SoundListener(SoundSelectDialog soundSelectDialog, int SoundPos) {
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

                            ArrayList<Integer> integers = helper.getGroupByFavList(Integer.parseInt(favModels.get(SoundPos).getId()));
                            MedConstants.SelectedPlayerArrayList = new ArrayList<>();
                            TvReminderTime.setVisibility(View.VISIBLE);
                            TvReminderTime.setText(favModels.get(SoundPos).getName());
//                            if (MedConstants.isServiceRunning(context, MediaPlayerService.class)) {
//                                Intent serviceIntent = new Intent(context, MediaPlayerService.class);
//                                context.stopService(serviceIntent);
//                            }
//                            MedConstants.NOTIFICATION_PLAYPAUSE_ICON = "Pause";
//                            Intent serviceIntent = new Intent(context, MediaPlayerService.class);
//                            MedConstants.FAVOURITESONG = favModels.get(SoundPos).getName();
//                            serviceIntent.putExtra(MedConstants.IsNotificationFavoriteTitle, favModels.get(SoundPos).getName());
////                            serviceIntent.putExtra(MedConstants.NOTIFICATION_PLAYPAUSE_ICON,"Pause");
//                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                                context.startForegroundService(serviceIntent);
//                            } else {
//                                context.startService(serviceIntent);
//                            }
                            new FavAsynkTask(integers).execute();

                            soundSelectDialog.dismiss();
                        }

                        @Override
                        public void DismissListener(SoundSelectDialog soundSelectDialog) {
                            soundSelectDialog.dismiss();
                        }
                    });
                    soundSelectDialog.show();
                    WindowManager.LayoutParams attribute = soundSelectDialog.getWindow().getAttributes();
                    Window soundSelectDialogWindow = soundSelectDialog.getWindow();
                    attribute.copyFrom(soundSelectDialogWindow.getAttributes());
                    attribute.width = WindowManager.LayoutParams.MATCH_PARENT;
                    attribute.height = WindowManager.LayoutParams.WRAP_CONTENT;
                    attribute.gravity = Gravity.CENTER;
                    soundSelectDialogWindow.setAttributes(attribute);

                    LlSelectSoundPage.setVisibility(View.GONE);
                    RvSelectSound.setVisibility(View.GONE);
                    IvCategoryImg.setVisibility(View.VISIBLE);
                    PagerCategory.setVisibility(View.VISIBLE);
                    IvCategoryPrevious.setVisibility(View.VISIBLE);
                    IvCategoryNext.setVisibility(View.VISIBLE);
                    if (new MedPref(context).getBoolean(MedPref.BOOL_NIGHT, false)) {
                        IvFavoriteTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_dark), android.graphics.PorterDuff.Mode.SRC_IN);
                        IvReminderTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), android.graphics.PorterDuff.Mode.SRC_IN);
                        IvHomeTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), android.graphics.PorterDuff.Mode.SRC_IN);
                        IvPagesTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), android.graphics.PorterDuff.Mode.SRC_IN);
                        IvCustomTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), android.graphics.PorterDuff.Mode.SRC_IN);
                    } else {
                        IvFavoriteTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color), android.graphics.PorterDuff.Mode.SRC_IN);
                        IvReminderTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), android.graphics.PorterDuff.Mode.SRC_IN);
                        IvHomeTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), android.graphics.PorterDuff.Mode.SRC_IN);
                        IvPagesTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), android.graphics.PorterDuff.Mode.SRC_IN);
                        IvCustomTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), android.graphics.PorterDuff.Mode.SRC_IN);
                    }
                    if (LlSelectedSoundsList.getVisibility() == View.VISIBLE) {
                        LlSelectedSoundsList.setVisibility(View.GONE);
                    }
                } else {
                    Toast.makeText(context, "No favourite items..", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.IvReminderTab:
                if (DrawerMain.isOpen()) {
                    DrawerMain.closeDrawer(GravityCompat.START);
                }
                boolean Isreminder = new MedPref(context).getBoolean(MedPref.SET_TIMER, false);
                if (Isreminder) {
                    new MedPref(context).putBoolean(MedPref.SET_TIMER, false);
                    IvReminderTab.setImageResource(R.drawable.ic_reminder_non);
                    countDownReminder.cancel();
                    TvReminderTime.setVisibility(View.GONE);
                } else {
                    LlSelectSoundPage.setVisibility(View.GONE);
                    RvSelectSound.setVisibility(View.GONE);
                    IvCategoryImg.setVisibility(View.VISIBLE);
                    PagerCategory.setVisibility(View.VISIBLE);
                    IvCategoryPrevious.setVisibility(View.VISIBLE);
                    IvCategoryNext.setVisibility(View.VISIBLE);
                    SoundReminderDialog reminderDialog = new SoundReminderDialog(MedMainActivity.this, context, (SoundReminderDialog soundReminderDialog) -> {
                        int Hour = new MedPref(context).getInt(MedPref.HOUR, 0);
                        int Minute = new MedPref(context).getInt(MedPref.MINUTE, 0);
                        if (Hour > 0) {
                            Minute = 1;
                        }

                        TvReminderTime.setVisibility(View.VISIBLE);

                        int duration;
                        if (new MedPref(context).getInt(MedPref.START_TIMER, 0) != 0) {
                            int hours = 24;
                            int minutes = 0;
                            int seconds = 0;

                            long totalTimeInSeconds = hours * 3600 + minutes * 60 + seconds;
                            long totalTimeInMillis = totalTimeInSeconds * 1000;
                            long curentInseconds = (Hour * 3600) + (Minute) + 0;
                            long curentMillis = curentInseconds * 1000;
                            duration = (int) (totalTimeInMillis - curentMillis);
                        } else {
                            duration = Hour * 60 + Minute * (60 * 1000);
                        }
                        System.out.println("----- -- - +++ : " + Hour + " - - : " + Minute);
//                        duration = (Hour * 60 + Minute - 5);
                        System.out.println("----- -- - +++ duration: " + duration);
                        countDownReminder = new CountDownTimer((long) duration, 1000) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                System.out.println("----- -- - +++ : " + millisUntilFinished);
                                long seconds = millisUntilFinished / 1000;
                                long minutes = seconds / 60;
                                long hour = minutes / 60;
                                seconds %= 60;

                                String timeLeftFormatted = String.format("%02d:%02d:%02d", hour, minutes, seconds);
                                TvReminderTime.setText(timeLeftFormatted);
                            }

                            @Override
                            public void onFinish() {
                                TvReminderTime.setVisibility(View.GONE);
                                finishAffinity();
                            }
                        };
                        countDownReminder.start();
                        IvReminderTab.setImageResource(R.drawable.ic_reminder_non);
                        if (new MedPref(context).getBoolean(MedPref.BOOL_NIGHT, false)) {
                            IvFavoriteTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), android.graphics.PorterDuff.Mode.SRC_IN);
                            IvReminderTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), android.graphics.PorterDuff.Mode.SRC_IN);
                            IvHomeTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_dark), android.graphics.PorterDuff.Mode.SRC_IN);
                            IvPagesTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), android.graphics.PorterDuff.Mode.SRC_IN);
                            IvCustomTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), android.graphics.PorterDuff.Mode.SRC_IN);
                        } else {
                            IvFavoriteTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), android.graphics.PorterDuff.Mode.SRC_IN);
                            IvReminderTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), android.graphics.PorterDuff.Mode.SRC_IN);
                            IvHomeTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color), android.graphics.PorterDuff.Mode.SRC_IN);
                            IvPagesTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), android.graphics.PorterDuff.Mode.SRC_IN);
                            IvCustomTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), android.graphics.PorterDuff.Mode.SRC_IN);
                        }
                        soundReminderDialog.dismiss();
                    });
                    reminderDialog.show();
                    WindowManager.LayoutParams lp = reminderDialog.getWindow().getAttributes();
                    Window window = reminderDialog.getWindow();
                    lp.copyFrom(window.getAttributes());
                    lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                    lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                    lp.gravity = Gravity.CENTER;
                    window.setAttributes(lp);
                    IvReminderTab.setImageResource(R.drawable.ic_reminder);
                    if (new MedPref(context).getBoolean(MedPref.BOOL_NIGHT, false)) {
                        IvFavoriteTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), android.graphics.PorterDuff.Mode.SRC_IN);
                        IvReminderTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_dark), android.graphics.PorterDuff.Mode.SRC_IN);
                        IvHomeTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), android.graphics.PorterDuff.Mode.SRC_IN);
                        IvPagesTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), android.graphics.PorterDuff.Mode.SRC_IN);
                        IvCustomTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), android.graphics.PorterDuff.Mode.SRC_IN);
                    } else {
                        IvFavoriteTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), android.graphics.PorterDuff.Mode.SRC_IN);
                        IvReminderTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color), android.graphics.PorterDuff.Mode.SRC_IN);
                        IvHomeTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), android.graphics.PorterDuff.Mode.SRC_IN);
                        IvPagesTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), android.graphics.PorterDuff.Mode.SRC_IN);
                        IvCustomTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), android.graphics.PorterDuff.Mode.SRC_IN);
                    }
                }
                if (LlSelectedSoundsList.getVisibility() == View.VISIBLE) {
                    LlSelectedSoundsList.setVisibility(View.GONE);
                }
                break;
            case R.id.IvHomeTab:
                if (DrawerMain.isOpen()) {
                    DrawerMain.closeDrawer(GravityCompat.START);
                }

                LlSelectSoundPage.setVisibility(View.GONE);
                RvSelectSound.setVisibility(View.GONE);
                IvCategoryImg.setVisibility(View.VISIBLE);
                PagerCategory.setVisibility(View.VISIBLE);
                IvCategoryPrevious.setVisibility(View.VISIBLE);
                IvCategoryNext.setVisibility(View.VISIBLE);
                if (new MedPref(context).getBoolean(MedPref.BOOL_NIGHT, false)) {
                    IvFavoriteTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), android.graphics.PorterDuff.Mode.SRC_IN);
                    IvReminderTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), android.graphics.PorterDuff.Mode.SRC_IN);
                    IvHomeTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_dark), android.graphics.PorterDuff.Mode.SRC_IN);
                    IvPagesTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), android.graphics.PorterDuff.Mode.SRC_IN);
                    IvCustomTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), android.graphics.PorterDuff.Mode.SRC_IN);
                } else {
                    IvFavoriteTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), android.graphics.PorterDuff.Mode.SRC_IN);
                    IvReminderTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), android.graphics.PorterDuff.Mode.SRC_IN);
                    IvHomeTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color), android.graphics.PorterDuff.Mode.SRC_IN);
                    IvPagesTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), android.graphics.PorterDuff.Mode.SRC_IN);
                    IvCustomTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), android.graphics.PorterDuff.Mode.SRC_IN);
                }
                if (LlSelectedSoundsList.getVisibility() == View.VISIBLE) {
                    LlSelectedSoundsList.setVisibility(View.GONE);
                }
                break;
            case R.id.IvPagesTab:
                if (DrawerMain.isOpen()) {
                    DrawerMain.closeDrawer(GravityCompat.START);
                }

                LlSelectSoundPage.setVisibility(View.VISIBLE);
                RvSelectSound.setVisibility(View.VISIBLE);
                LlPlayAll.setVisibility(View.GONE);
                IvCategoryImg.setVisibility(View.GONE);
                PagerCategory.setVisibility(View.GONE);
                IvCategoryPrevious.setVisibility(View.GONE);
                IvCategoryNext.setVisibility(View.GONE);
                TvSelectSound.setText(getResources().getString(R.string.select_sound));
                RvSelectSound.setLayoutManager(new GridLayoutManager(context, 2));
                RvSelectSound.setAdapter(new SelectSoundAdapter(context, ListOfCategory, position -> {
                    PagerCategory.setCurrentItem(position);

                    LlSelectSoundPage.setVisibility(View.GONE);
                    RvSelectSound.setVisibility(View.GONE);
                    LlPlayAll.setVisibility(View.GONE);
                    IvCategoryImg.setVisibility(View.VISIBLE);
                    PagerCategory.setVisibility(View.VISIBLE);
                    IvCategoryPrevious.setVisibility(View.VISIBLE);
                    IvCategoryNext.setVisibility(View.VISIBLE);
                    if (new MedPref(context).getBoolean(MedPref.BOOL_NIGHT, false)) {
                        IvFavoriteTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), android.graphics.PorterDuff.Mode.SRC_IN);
                        IvReminderTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), android.graphics.PorterDuff.Mode.SRC_IN);
                        IvHomeTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_dark), android.graphics.PorterDuff.Mode.SRC_IN);
                        IvPagesTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), android.graphics.PorterDuff.Mode.SRC_IN);
                        IvCustomTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), android.graphics.PorterDuff.Mode.SRC_IN);
                    } else {
                        IvFavoriteTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), android.graphics.PorterDuff.Mode.SRC_IN);
                        IvReminderTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), android.graphics.PorterDuff.Mode.SRC_IN);
                        IvHomeTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color), android.graphics.PorterDuff.Mode.SRC_IN);
                        IvPagesTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), android.graphics.PorterDuff.Mode.SRC_IN);
                        IvCustomTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), android.graphics.PorterDuff.Mode.SRC_IN);
                    }
                }));
                if (new MedPref(context).getBoolean(MedPref.BOOL_NIGHT, false)) {
                    IvFavoriteTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), android.graphics.PorterDuff.Mode.SRC_IN);
                    IvReminderTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), android.graphics.PorterDuff.Mode.SRC_IN);
                    IvHomeTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), android.graphics.PorterDuff.Mode.SRC_IN);
                    IvPagesTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_dark), android.graphics.PorterDuff.Mode.SRC_IN);
                    IvCustomTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), android.graphics.PorterDuff.Mode.SRC_IN);
                } else {
                    IvFavoriteTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), android.graphics.PorterDuff.Mode.SRC_IN);
                    IvReminderTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), android.graphics.PorterDuff.Mode.SRC_IN);
                    IvHomeTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), android.graphics.PorterDuff.Mode.SRC_IN);
                    IvPagesTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color), android.graphics.PorterDuff.Mode.SRC_IN);
                    IvCustomTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), android.graphics.PorterDuff.Mode.SRC_IN);
                }
                if (LlSelectedSoundsList.getVisibility() == View.VISIBLE) {
                    LlSelectedSoundsList.setVisibility(View.GONE);
                }
                break;
            case R.id.IvCustomTab:
                if (DrawerMain.isOpen()) {
                    DrawerMain.closeDrawer(GravityCompat.START);
                }

                LlSelectSoundPage.setVisibility(View.VISIBLE);
                RvSelectSound.setVisibility(View.VISIBLE);
                LlPlayAll.setVisibility(View.VISIBLE);
                IvCategoryImg.setVisibility(View.GONE);
                PagerCategory.setVisibility(View.GONE);
                IvCategoryPrevious.setVisibility(View.GONE);
                IvCategoryNext.setVisibility(View.GONE);

                TvSelectSound.setText(getResources().getString(R.string.select_ambience));
                RvSelectSound.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
                RvSelectSound.setAdapter(new CustomSoundAdapter(context, MedConstants.PlaylistSounds(), position -> {
                    PagerCategory.setCurrentItem(0);

                    LlSelectSoundPage.setVisibility(View.GONE);
                    RvSelectSound.setVisibility(View.GONE);
                    LlPlayAll.setVisibility(View.GONE);
                    IvCategoryImg.setVisibility(View.VISIBLE);
                    PagerCategory.setVisibility(View.VISIBLE);
                    if (new MedPref(context).getBoolean(MedPref.BOOL_NIGHT, false)) {
                        IvFavoriteTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), android.graphics.PorterDuff.Mode.SRC_IN);
                        IvReminderTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), android.graphics.PorterDuff.Mode.SRC_IN);
                        IvHomeTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_dark), android.graphics.PorterDuff.Mode.SRC_IN);
                        IvPagesTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), android.graphics.PorterDuff.Mode.SRC_IN);
                        IvCustomTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), android.graphics.PorterDuff.Mode.SRC_IN);
                    } else {
                        IvFavoriteTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), android.graphics.PorterDuff.Mode.SRC_IN);
                        IvReminderTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), android.graphics.PorterDuff.Mode.SRC_IN);
                        IvHomeTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color), android.graphics.PorterDuff.Mode.SRC_IN);
                        IvPagesTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), android.graphics.PorterDuff.Mode.SRC_IN);
                        IvCustomTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), android.graphics.PorterDuff.Mode.SRC_IN);
                    }
                    CustomPos = position;
                    if (countDownTimer != null) {
                        countDownTimer.cancel();
                    }
                    new CustomSoundAsynkTask(position).execute();
                }));
                if (new MedPref(context).getBoolean(MedPref.BOOL_NIGHT, false)) {
                    IvFavoriteTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), android.graphics.PorterDuff.Mode.SRC_IN);
                    IvReminderTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), android.graphics.PorterDuff.Mode.SRC_IN);
                    IvHomeTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), android.graphics.PorterDuff.Mode.SRC_IN);
                    IvPagesTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), android.graphics.PorterDuff.Mode.SRC_IN);
                    IvCustomTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_dark), android.graphics.PorterDuff.Mode.SRC_IN);
                } else {
                    IvFavoriteTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), android.graphics.PorterDuff.Mode.SRC_IN);
                    IvReminderTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), android.graphics.PorterDuff.Mode.SRC_IN);
                    IvHomeTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), android.graphics.PorterDuff.Mode.SRC_IN);
                    IvPagesTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), android.graphics.PorterDuff.Mode.SRC_IN);
                    IvCustomTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color), android.graphics.PorterDuff.Mode.SRC_IN);
                }
                if (LlSelectedSoundsList.getVisibility() == View.VISIBLE) {
                    LlSelectedSoundsList.setVisibility(View.GONE);
                }
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
                if (LlSelectedSoundsList.getVisibility() == View.VISIBLE) {
                    LlSelectedSoundsList.setVisibility(View.GONE);
                }
                break;
            case R.id.IvUpload:
                if (DrawerMain.isOpen()) {
                    DrawerMain.closeDrawer(GravityCompat.START);
                }

                LlSelectSoundPage.setVisibility(View.GONE);
                RvSelectSound.setVisibility(View.GONE);
                IvCategoryImg.setVisibility(View.VISIBLE);
                PagerCategory.setVisibility(View.VISIBLE);
                if (LlSelectedSoundsList.getVisibility() == View.VISIBLE) {
                    LlSelectedSoundsList.setVisibility(View.GONE);
                }
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
                if (LlSelectedSoundsList.getVisibility() == View.VISIBLE) {
                    LlSelectedSoundsList.setVisibility(View.GONE);
                }
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
                if (LlSelectedSoundsList.getVisibility() == View.VISIBLE) {
                    LlSelectedSoundsList.setVisibility(View.GONE);
                }
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
                if (LlSelectedSoundsList.getVisibility() == View.VISIBLE) {
                    LlSelectedSoundsList.setVisibility(View.GONE);
                }
                break;
            case R.id.IvSoundPlayPause:
                if (DrawerMain.isOpen()) {
                    DrawerMain.closeDrawer(GravityCompat.START);
                }

                if (!isStartPlayer) {
                    new PlayPauseAsynkTask().execute();
                    IvSoundPlayPause.setImageResource(R.drawable.icon_pause);
                    isStartPlayer = true;
                } else {
                    if (countDownTimer != null) {
                        countDownTimer.cancel();
                    }
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
                if (LlSelectedSoundsList.getVisibility() == View.VISIBLE) {
                    LlSelectedSoundsList.setVisibility(View.GONE);
                }
                break;
            case R.id.IvSoundStop:
                if (DrawerMain.isOpen()) {
                    DrawerMain.closeDrawer(GravityCompat.START);
                }
                if (countDownTimer != null) {
                    countDownTimer.cancel();
                }
//                if (MedConstants.isServiceRunning(context, MediaPlayerService.class)) {
//                    Intent serviceIntent = new Intent(context, MediaPlayerService.class);
//                    context.stopService(serviceIntent);
//                }
                IvSoundPlayPause.setImageResource(R.drawable.icon_play);
                LlButtonView.setVisibility(View.GONE);
                TvReminderTime.setVisibility(View.GONE);
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
                if (LlSelectedSoundsList.getVisibility() == View.VISIBLE) {
                    LlSelectedSoundsList.setVisibility(View.GONE);
                }
                break;
            case R.id.IvPreview:
                if (DrawerMain.isOpen()) {
                    DrawerMain.closeDrawer(GravityCompat.START);
                }

                RvSelectedSoundsList.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
                RvSelectedSoundsList.setAdapter(new SelectSoundListAdapter(context, MedConstants.SelectedPlayerArrayList, position -> {
                    System.out.println("-- - -- - pos : " + position + " = = " + MedConstants.SelectedPlayerArrayList.get(position).getPlayerPos());
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
                    Intent FagIntent = new Intent(MedConstants.BROADCAST_FRAGMENT);
                    FagIntent.putExtra(MedConstants.FRAGMENT_CLICK, "Cancel");
                    LocalBroadcastManager.getInstance(context).sendBroadcast(FagIntent);

                    Intent MainIntent = new Intent(MedConstants.BROADCAST_MAIN);
                    MainIntent.putExtra(MedConstants.SelectedSounds, MedConstants.SelectedPlayerArrayList.size());
                    LocalBroadcastManager.getInstance(context).sendBroadcast(MainIntent);
                    RvSelectedSoundsList.getAdapter().notifyDataSetChanged();
                    PagerCategory.getAdapter().notifyDataSetChanged();
                }));
                System.out.println("=== selele : " + MedConstants.SelectedPlayerArrayList.size());
                if (MedConstants.SelectedPlayerArrayList.size() > 0) {
                    if (LlSelectedSoundsList.getVisibility() == View.VISIBLE) {
                        LlSelectedSoundsList.setVisibility(View.GONE);
                    } else if (LlSelectedSoundsList.getVisibility() == View.GONE) {
                        RvSelectedSoundsList.setBackgroundResource(R.drawable.sound_dialog_bg);

                        LlSelectedSoundsList.setVisibility(View.VISIBLE);
                    }
                }
                break;
            case R.id.content_main:
                if (DrawerMain.isOpen()) {
                    DrawerMain.closeDrawer(GravityCompat.START);
                }
                if (MedConstants.SelectedPlayerArrayList.size() > 0) {
                    if (LlSelectedSoundsList.getVisibility() == View.VISIBLE) {
                        LlSelectedSoundsList.setVisibility(View.GONE);
                    }
                }
                break;
            case R.id.IvFav:
                if (DrawerMain.isOpen()) {
                    DrawerMain.closeDrawer(GravityCompat.START);
                }
                System.out.println("==== COUNTTT : " + helper.getGroupByUniqueId());
                if (MedConstants.SelectedPlayerArrayList.size() >= 1) {
                    SoundFavoriteDialog favoriteDialog = new SoundFavoriteDialog(MedMainActivity.this, new SoundFavoriteDialog.DialogDismiss() {
                        @Override
                        public void DismissListener(SoundFavoriteDialog soundFavoriteDialog, String EdtName) {
                            soundFavoriteDialog.dismiss();
                            IvFavoriteTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), android.graphics.PorterDuff.Mode.SRC_IN);
                            IvReminderTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), android.graphics.PorterDuff.Mode.SRC_IN);
                            IvHomeTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color), android.graphics.PorterDuff.Mode.SRC_IN);
                            IvPagesTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), android.graphics.PorterDuff.Mode.SRC_IN);
                            IvCustomTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), android.graphics.PorterDuff.Mode.SRC_IN);
//todo insert0
                            int U_ID = helper.getGroupByUniqueId() + 1;
                            for (int i = 0; i < MedConstants.SelectedPlayerArrayList.size(); i++) {
                                helper.InsertWidget(EdtName, U_ID, MedConstants.SelectedPlayerArrayList.get(i).getPlayerPos(), MedConstants.SelectedPlayerArrayList.get(i).getPlayerVolume());
                            }
                        }
                    });
                    favoriteDialog.show();
                    WindowManager.LayoutParams layoutParams = favoriteDialog.getWindow().getAttributes();
                    Window window = favoriteDialog.getWindow();
                    layoutParams.copyFrom(window.getAttributes());
                    layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
                    layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
                    layoutParams.gravity = Gravity.CENTER;
                    window.setAttributes(layoutParams);
                }

//                String json = preferences.getString(FAVORITE_MEDIA_PLAYERS_KEY, null);
//
//                if (json != null) {
//                    Gson gson = new Gson();
//                    Type type = new TypeToken<List<MediaPlayerModel>>(){}.getType();
//                    return gson.fromJson(json, type);
//                } else {
//                    return new ArrayList<>();
//                }

                if (MedConstants.SelectedPlayerArrayList.size() > 0) {
                    if (LlSelectedSoundsList.getVisibility() == View.VISIBLE) {
                        LlSelectedSoundsList.setVisibility(View.GONE);
                    }
                }
                break;
            case R.id.LlPlayAll:
                if (DrawerMain.isOpen()) {
                    DrawerMain.closeDrawer(GravityCompat.START);
                }
                SoundPlayAllDialog soundPlayAllDialog = new SoundPlayAllDialog(context, new SoundPlayAllDialog.DialogDismiss() {
                    @Override
                    public void PlayListener(int selectedItemPosition, SoundPlayAllDialog playAllDialog) {
                        playAllDialog.dismiss();
                        new MedPref(context).putInt(MedPref.INT_DURATION, selectedItemPosition);
                        int duration = 0;
                        switch (selectedItemPosition) {
                            case 0:
                                duration = 2;
                                break;
                            case 1:
                                duration = 3;
                                break;
                            case 2:
                                duration = 5;
                                break;
                            case 3:
                                duration = 10;
                                break;
                            case 4:
                                duration = 15;
                                break;
                            case 5:
                                duration = 20;
                                break;
                            case 6:
                                duration = 30;
                                break;
                        }
                        CustomPos = 0;
                        PagerCategory.setCurrentItem(0);

                        LlSelectSoundPage.setVisibility(View.GONE);
                        RvSelectSound.setVisibility(View.GONE);
                        LlPlayAll.setVisibility(View.GONE);
                        IvCategoryImg.setVisibility(View.VISIBLE);
                        PagerCategory.setVisibility(View.VISIBLE);
                        if (new MedPref(context).getBoolean(MedPref.BOOL_NIGHT, false)) {
                            IvFavoriteTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), android.graphics.PorterDuff.Mode.SRC_IN);
                            IvReminderTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), android.graphics.PorterDuff.Mode.SRC_IN);
                            IvHomeTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_dark), android.graphics.PorterDuff.Mode.SRC_IN);
                            IvPagesTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), android.graphics.PorterDuff.Mode.SRC_IN);
                            IvCustomTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), android.graphics.PorterDuff.Mode.SRC_IN);
                        } else {
                            IvFavoriteTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), android.graphics.PorterDuff.Mode.SRC_IN);
                            IvReminderTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), android.graphics.PorterDuff.Mode.SRC_IN);
                            IvHomeTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color), android.graphics.PorterDuff.Mode.SRC_IN);
                            IvPagesTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), android.graphics.PorterDuff.Mode.SRC_IN);
                            IvCustomTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), android.graphics.PorterDuff.Mode.SRC_IN);
                        }
                        new CustomSoundAsynkTask(CustomPos).execute();
                        GotoCountDown(duration);
                    }

                    @Override
                    public void DismissListener(SoundPlayAllDialog playAllDialog) {
                        playAllDialog.dismiss();
                    }
                });
                soundPlayAllDialog.show();
                WindowManager.LayoutParams attributes = soundPlayAllDialog.getWindow().getAttributes();
                Window playAllDialogWindow = soundPlayAllDialog.getWindow();
                attributes.copyFrom(playAllDialogWindow.getAttributes());
                attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
                attributes.height = WindowManager.LayoutParams.WRAP_CONTENT;
                attributes.gravity = Gravity.CENTER;
                playAllDialogWindow.setAttributes(attributes);
                break;
        }
    }

    private void GotoCountDown(int duration) {
//        countDownTimer = new CountDownTimer((5 * 1000), 1000) {
        countDownTimer = new CountDownTimer(duration * (60 * 1000), 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                System.out.println("---- - -- TIICKKC : " + millisUntilFinished);
            }

            @Override
            public void onFinish() {
                CustomPos++;
                if (CustomPos <= (MedConstants.PlaylistSounds().size() - 1)) {
                    System.out.println("---- - -- onFinish : " + CustomPos + " -- " + MedConstants.PlaylistSounds().size());
                    new CustomSoundAsynkTask(CustomPos).execute();
                    GotoCountDown(duration);
                } else {
                    System.out.println("---- - -- onFinish Up : " + CustomPos + " -- " + MedConstants.PlaylistSounds().size());
                    IvSoundPlayPause.setImageResource(R.drawable.icon_play);
                    LlButtonView.setVisibility(View.GONE);

                    MedConstants.SelectedPlayerArrayList = new ArrayList<>();
                    TvCountSounds.setText("0");
                    Intent intent = new Intent(MedConstants.BROADCAST_FRAGMENT);
                    intent.putExtra(MedConstants.FRAGMENT_CLICK, "Stop");
                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

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
                    LlSelectSoundPage.setVisibility(View.GONE);
                    RvSelectSound.setVisibility(View.GONE);
                    IvCategoryImg.setVisibility(View.VISIBLE);
                    PagerCategory.setVisibility(View.VISIBLE);
                }
            }
        };
        countDownTimer.start();
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

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    private class PlayPauseAsynkTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ViewProgress.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if (MedConstants.SelectedPlayerArrayList.size() > 0) {
                for (int i = 0; i < MedConstants.SelectedPlayerArrayList.size(); i++) {
                    MedConstants.SelectedPlayerArrayList.get(i).getPlayer().prepareAsync();
                }
            } else {
                Toast.makeText(context, "No Sounds play now.", Toast.LENGTH_SHORT).show();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {

            ViewProgress.setVisibility(View.GONE);
            for (int i = 0; i < MedConstants.SelectedPlayerArrayList.size(); i++) {
                int finalI = i;
                MedConstants.SelectedPlayerArrayList.get(i).getPlayer().setOnPreparedListener(mp ->
                        MedConstants.SelectedPlayerArrayList.get(finalI).getPlayer().start());
                MedConstants.SelectedPlayerArrayList.get(i).getPlayer().setOnCompletionListener(mp ->
                        MedConstants.SelectedPlayerArrayList.get(finalI).getPlayer().start());
            }
            super.onPostExecute(unused);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    private class CustomSoundAsynkTask extends AsyncTask<Void, Void, Void> {
        private final int Pos;

        public CustomSoundAsynkTask(int position) {
            Pos = position;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ViewProgress.setVisibility(View.VISIBLE);
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
            Intent intentFrag = new Intent(MedConstants.BROADCAST_FRAGMENT);
            intentFrag.putExtra(MedConstants.FRAGMENT_CLICK, "Stop");
            LocalBroadcastManager.getInstance(context).sendBroadcast(intentFrag);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            switch (Pos) {
                case 0:
                    CustomSoundModels = MedConstants.RelaxingCoffeeSounds(context);
                    System.out.println("--- - -- : " + CustomSoundModels.size());
                    break;
                case 1:
                    CustomSoundModels = MedConstants.JourneySounds(context);
                    break;
                case 2:
                    CustomSoundModels = MedConstants.BeachSounds(context);
                    break;
                case 3:
                    CustomSoundModels = MedConstants.StormSounds(context);
                    break;
                case 4:
                    CustomSoundModels = MedConstants.ChildrenSounds(context);
                    break;
                case 5:
                    CustomSoundModels = MedConstants.ContactSounds(context);
                    break;
                case 6:
                    CustomSoundModels = MedConstants.AsianSounds(context);
                    break;
                case 7:
                    CustomSoundModels = MedConstants.CaveSounds(context);
                    break;
                case 8:
                    CustomSoundModels = MedConstants.SpringSounds(context);
                    break;
                case 9:
                    CustomSoundModels = MedConstants.DreamSounds(context);
                    break;
                case 10:
                    CustomSoundModels = MedConstants.MeditationNatureSounds(context);
                    break;
                case 11:
                    CustomSoundModels = MedConstants.MeditationTempleSounds(context);
                    break;
                case 12:
                    CustomSoundModels = MedConstants.CabinSounds(context);
                    break;
                case 13:
                    CustomSoundModels = MedConstants.DockSounds(context);
                    break;
                case 14:
                    CustomSoundModels = MedConstants.PeaceSounds(context);
                    break;
                case 15:
                    CustomSoundModels = MedConstants.RainySounds(context);
                    break;
                case 16:
                    CustomSoundModels = MedConstants.RelaxRainSounds(context);
                    break;
                case 17:
                    CustomSoundModels = MedConstants.RealxBeachSounds(context);
                    break;
                case 18:
                    CustomSoundModels = MedConstants.RuralSounds(context);
                    break;
                case 19:
                    CustomSoundModels = MedConstants.DolphinsSounds(context);
                    break;
                case 20:
                    CustomSoundModels = MedConstants.IndiaSounds(context);
                    break;
                case 21:
                    CustomSoundModels = MedConstants.WildSounds(context);
                    break;
            }
            try {
                for (int i = 0; i < CustomSoundModels.size(); i++) {
                    if (CustomSoundModels.get(i).getSoundMp3Checked() == 0) {
                        PlayerPos = CustomSoundModels.get(i).getSoundPos();
                        System.out.println("--- - -- : " + PlayerPos + " -- " + CustomSoundModels.get(i).getSoundVolume());
                        MediaPlayer mediaPlayer = new MediaPlayer();
                        float volume = (float) (1 - (Math.log(100 - CustomSoundModels.get(i).getSoundVolume()) / Math.log(100)));
                        mediaPlayer.setVolume(volume, volume);
                        mediaPlayer.setDataSource(CustomSoundModels.get(i).getSoundMp3());
                        mediaPlayer.setLooping(true);
                        MedConstants.mediaPlayerArrayList.get(PlayerPos).setPlayer(mediaPlayer);
                        MedConstants.mediaPlayerArrayList.get(PlayerPos).setPlayerVolume(CustomSoundModels.get(i).getSoundVolume());

                        MedConstants.mediaPlayerArrayList.get(PlayerPos).getPlayer().prepareAsync();

                        MedConstants.SelectedPlayerArrayList.add(MedConstants.mediaPlayerArrayList.get(PlayerPos));
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);

            ViewProgress.setVisibility(View.GONE);
            for (int i = 0; i < MedConstants.SelectedPlayerArrayList.size(); i++) {
                int Pos = MedConstants.SelectedPlayerArrayList.get(i).getPlayerPos();
                MedConstants.mediaPlayerArrayList.get(Pos).getPlayer().setOnPreparedListener(mp ->
                        MedConstants.mediaPlayerArrayList.get(Pos).getPlayer().start()
                );
                MedConstants.mediaPlayerArrayList.get(Pos).getPlayer().setOnCompletionListener(mp ->
                        MedConstants.mediaPlayerArrayList.get(Pos).getPlayer().start());
            }

            Intent intent = new Intent(MedConstants.BROADCAST_MAIN);
            intent.putExtra(MedConstants.SelectedSounds, MedConstants.SelectedPlayerArrayList.size());
            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

            Intent intentFrag = new Intent(MedConstants.BROADCAST_FRAGMENT);
            intentFrag.putExtra(MedConstants.FRAGMENT_CLICK, "Cancel");
            LocalBroadcastManager.getInstance(context).sendBroadcast(intentFrag);
        }
    }

    private class FavAsynkTask extends AsyncTask<Void, Void, Void> {
        private final ArrayList<Integer> integers;

        public FavAsynkTask(ArrayList<Integer> integers) {
            this.integers = integers;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                for (int i = 0; i < MedConstants.mediaPlayerArrayList.size(); i++) {
                    for (int j = 0; j < integers.size(); j++) {
                        if (MedConstants.mediaPlayerArrayList.get(i).getPlayerPos() == integers.get(j)) {
                            MedConstants.SelectedPlayerArrayList.add(MedConstants.mediaPlayerArrayList.get(i));
                            System.out.println("=== --- : " + SoundModelsList.get(i).getSoundMp3());
                            MediaPlayer mediaPlayer = new MediaPlayer();
                            float volume = (float) (1 - (Math.log(100 - SoundModelsList.get(i).getSoundVolume()) / Math.log(100)));
                            mediaPlayer.setVolume(volume, volume);
                            mediaPlayer.setDataSource(SoundModelsList.get(i).getSoundMp3());
                            mediaPlayer.setLooping(true);
                            MedConstants.mediaPlayerArrayList.get(PlayerPos).setPlayer(mediaPlayer);
                            MedConstants.mediaPlayerArrayList.get(PlayerPos).getPlayer().prepareAsync();
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            for (int i = 0; i < MedConstants.SelectedPlayerArrayList.size(); i++) {
                int Pos = MedConstants.SelectedPlayerArrayList.get(i).getPlayerPos();
                MedConstants.mediaPlayerArrayList.get(Pos).getPlayer().setOnPreparedListener(mp ->
                        MedConstants.mediaPlayerArrayList.get(Pos).getPlayer().start()
                );
                MedConstants.mediaPlayerArrayList.get(Pos).getPlayer().setOnCompletionListener(mp ->
                        MedConstants.mediaPlayerArrayList.get(Pos).getPlayer().start());
            }
            PagerCategory.getAdapter().notifyDataSetChanged();
            Intent intent = new Intent(MedConstants.BROADCAST_MAIN);
            intent.putExtra(MedConstants.SelectedSounds, MedConstants.SelectedPlayerArrayList.size());
            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
            LlButtonView.setVisibility(View.VISIBLE);
            IvReminderTab.setImageResource(R.drawable.ic_reminder_non);
            IvFavoriteTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), android.graphics.PorterDuff.Mode.SRC_IN);
            IvReminderTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), android.graphics.PorterDuff.Mode.SRC_IN);
            IvHomeTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color), android.graphics.PorterDuff.Mode.SRC_IN);
            IvPagesTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), android.graphics.PorterDuff.Mode.SRC_IN);
            IvCustomTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), android.graphics.PorterDuff.Mode.SRC_IN);
        }
    }
}
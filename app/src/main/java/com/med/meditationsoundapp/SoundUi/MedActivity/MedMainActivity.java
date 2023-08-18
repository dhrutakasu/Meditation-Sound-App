package com.med.meditationsoundapp.SoundUi.MedActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
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
import android.util.Log;
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
import com.med.meditationsoundapp.SoundDialog.SoundSettingDialog;
import com.med.meditationsoundapp.SoundUi.MedAdapter.CategoryAdapter;

import java.io.IOException;

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
    private LinearLayout LlButtonView;
    private DrawerLayout DrawerMain;
    private ActionBarDrawerToggle DrawerToggle;
    private NavigationView NavMain;
    private String[] ListOfCategory;
    private AudioManager audioManager;

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
        NavMain = (NavigationView) findViewById(R.id.NavMain);
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
    }

    private void MedInitActions() {
        IvDrawer.setVisibility(View.VISIBLE);
        IvSetting.setVisibility(View.VISIBLE);
        IvUpload.setVisibility(View.VISIBLE);
        IvBack.setVisibility(View.GONE);
        TvTitle.setText(getString(R.string.app_name));
        LlButtonView.setVisibility(View.GONE);
        ConsVolume.setVisibility(View.VISIBLE);

        IvFavoriteTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), PorterDuff.Mode.SRC_IN);
        IvReminderTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), PorterDuff.Mode.SRC_IN);
        IvHomeTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color), PorterDuff.Mode.SRC_IN);
        IvPagesTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), PorterDuff.Mode.SRC_IN);
        IvCustomTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), PorterDuff.Mode.SRC_IN);

        DrawerToggle = new ActionBarDrawerToggle(this, DrawerMain, R.string.nav_open, R.string.nav_close);
        DrawerMain.addDrawerListener(DrawerToggle);
        DrawerToggle.syncState();

        ListOfCategory = getResources().getStringArray(R.array.CategoryList);
        for (int i = 0; i < ListOfCategory.length; i++) {
            TabCategory.addTab(TabCategory.newTab().setText(ListOfCategory[i].toString()));
            System.out.println("---- -- - TABB : " + ListOfCategory[i].toString());
        }

        PagerCategory.setAdapter(new CategoryAdapter(getSupportFragmentManager(), context, TabCategory.getSelectedTabPosition(), ListOfCategory));
        TabCategory.setupWithViewPager(PagerCategory);

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
        LocalBroadcastManager.getInstance(this).registerReceiver(
                MainReceiver, new IntentFilter(MedConstants.BROADCAST_MAIN));
    }

    private BroadcastReceiver MainReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            LlButtonView.setVisibility(View.VISIBLE);
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_UP:
                audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_RAISE, AudioManager.FLAG_PLAY_SOUND | AudioManager.FLAG_SHOW_UI);
                int volumeUp = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                TvVolume.setText(volumeUp + "");
                SeekVolume.setProgress(volumeUp);
                return true;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_LOWER, AudioManager.FLAG_PLAY_SOUND | AudioManager.FLAG_SHOW_UI);
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
                SoundReminderDialog reminderDialog = new SoundReminderDialog(MedMainActivity.this, context);
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
                break;
            case R.id.IvDrawer:
                if (DrawerMain.isOpen()) {
                    DrawerMain.closeDrawer(GravityCompat.START);
                } else {
                    DrawerMain.openDrawer(GravityCompat.START);
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

                System.out.println("---- - - next : " + (PagerCategory.getCurrentItem() + 1) + " - size : " + ListOfCategory.length);
                if (PagerCategory.getCurrentItem() != (ListOfCategory.length - 1)) {
                    PagerCategory.setCurrentItem(PagerCategory.getCurrentItem() + 1);
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
                System.out.println("---- - - previous : " + (PagerCategory.getCurrentItem() - 1) + " - size : " + ListOfCategory.length);
                if (PagerCategory.getCurrentItem() != 0) {
                    PagerCategory.setCurrentItem(PagerCategory.getCurrentItem() - 1);
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
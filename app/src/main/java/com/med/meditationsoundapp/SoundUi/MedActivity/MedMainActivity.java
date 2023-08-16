package com.med.meditationsoundapp.SoundUi.MedActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
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
import com.med.meditationsoundapp.SoundDialog.SoundSettingDialog;

import java.io.IOException;

public class MedMainActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    private Context context;
    private ImageView IvBack, IvCategoryImg, IvCategoryNext, IvDrawer, IvSetting, IvUpload;
    private TextView TvTitle;
    private TextView TvCategoryTitle;
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
        IvDrawer = (ImageView) findViewById(R.id.IvDrawer);
        IvDrawer = (ImageView) findViewById(R.id.IvDrawer);
        TvTitle = (TextView) findViewById(R.id.TvTitle);
        IvSetting = (ImageView) findViewById(R.id.IvSetting);
        IvUpload = (ImageView) findViewById(R.id.IvUpload);
        DrawerMain = (DrawerLayout) findViewById(R.id.DrawerMain);
        TvCategoryTitle = (TextView) findViewById(R.id.TvCategoryTitle);
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
    }

    private void MedInitActions() {
        IvDrawer.setVisibility(View.VISIBLE);
        IvSetting.setVisibility(View.VISIBLE);
        IvUpload.setVisibility(View.VISIBLE);
        IvBack.setVisibility(View.GONE);
        TvTitle.setText(getString(R.string.app_name));
        LlButtonView.setVisibility(View.GONE);
        ConsVolume.setVisibility(View.GONE);

        IvFavoriteTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), PorterDuff.Mode.SRC_IN);
        IvReminderTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), PorterDuff.Mode.SRC_IN);
        IvHomeTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color), PorterDuff.Mode.SRC_IN);
        IvPagesTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), PorterDuff.Mode.SRC_IN);
        IvCustomTab.setColorFilter(ContextCompat.getColor(context, R.color.app_main_color_gray), PorterDuff.Mode.SRC_IN);

        DrawerToggle = new ActionBarDrawerToggle(this, DrawerMain, R.string.nav_open, R.string.nav_close);
        DrawerMain.addDrawerListener(DrawerToggle);
        DrawerToggle.syncState();

        MediaPlayer mp = new MediaPlayer();
        try {
            mp.setDataSource("https://anshinfotech.in/sound/sound_air_bamboo.ogg");
            mp.prepare();
            mp.start();
        } catch (IOException e) {
            Log.e("LOG_TAG", "prepare() failed");
        }
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
                WindowManager.LayoutParams lp = settingDialog.getWindow().getAttributes();
                Window window = settingDialog.getWindow();
                lp.copyFrom(window.getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                lp.gravity = Gravity.CENTER;
                window.setAttributes(lp);
                break;
            case R.id.IvUpload:
                if (DrawerMain.isOpen()) {
                    DrawerMain.closeDrawer(GravityCompat.START);
                }
                break;
            case R.id.IvDrawer:
                if (DrawerMain.isOpen()) {
                    DrawerMain.closeDrawer(GravityCompat.START);
                }else {
                    DrawerMain.openDrawer(GravityCompat.START);
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
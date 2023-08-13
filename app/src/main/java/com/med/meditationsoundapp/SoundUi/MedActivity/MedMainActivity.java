package com.med.meditationsoundapp.SoundUi.MedActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.med.meditationsoundapp.R;

public class MedMainActivity extends AppCompatActivity {

    private Context context;
    private ImageView IvCategoryImg,IvCategoryNext, IvDrawer, IvSetting, IvUpload;
    private TextView TvTitle;
    private TextView TvCategoryTitle;
    private ImageView IvFav,IvSoundPlayPause,IvSoundStop,IvPreview,IvFavoriteTab,IvReminderTab,IvHomeTab,IvPagesTab,IvCustomTab;
    private TextView TvCountSounds,TvVolume;
    private SeekBar SeekVolume;
    private ViewPager PagerCategory;
    private TabLayout TabCategory;

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
        IvCategoryImg = (ImageView) findViewById(R.id.IvCategoryImg);
        IvCategoryNext = (ImageView) findViewById(R.id.IvCategoryNext);
        IvDrawer = (ImageView) findViewById(R.id.IvDrawer);
        TvTitle = (TextView) findViewById(R.id.TvTitle);
        IvSetting = (ImageView) findViewById(R.id.IvSetting);
        IvUpload = (ImageView) findViewById(R.id.IvUpload);
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

    }

    private void MedInitListeners() {

    }

    private void MedInitActions() {

    }
}
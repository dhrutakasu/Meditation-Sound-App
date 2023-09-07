package com.med.meditationsoundapp.SoundUi.MedAdapter;

import android.content.Context;
import android.view.View;

import com.med.meditationsoundapp.SoundUi.MedFragment.CategoryFragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

public class CategoryAdapter extends FragmentPagerAdapter {
    private final Context context;
    private final int selectedTabPosition;
    private final String[] listOfCategory;

    public CategoryAdapter(FragmentManager supportFragmentManager, Context context, int selectedTabPosition, String[] listOfCategory) {
        super(supportFragmentManager);
        this.context = context;
        this.selectedTabPosition = selectedTabPosition;
        this.listOfCategory = listOfCategory;
    }

    @Override
    public int getCount() {
        return listOfCategory.length;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return CategoryFragment.newInstance(listOfCategory[position], position);
    }
    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}

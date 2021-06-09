package com.example.mybroadcast.tabexample;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class MyTabAdapter extends FragmentPagerAdapter {
    private Context context;
    int totalTabs;

    public MyTabAdapter(Context context, FragmentManager fm,int totalTabs){
        super(fm);
        this.context=context;
        this.totalTabs=totalTabs;
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                HomeFragment homeFragment=new HomeFragment();
                return homeFragment;
            case 1:
                SportFragment sportFragment=new SportFragment();
                return sportFragment;
            case 2:
                MovieFragment movieFragment=new MovieFragment();
                return movieFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}

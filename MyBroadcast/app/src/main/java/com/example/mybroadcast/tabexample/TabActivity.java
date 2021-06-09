package com.example.mybroadcast.tabexample;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.view.View;
import android.view.ViewPropertyAnimator;

import com.example.mybroadcast.R;
import com.google.android.material.tabs.TabLayout;

public class TabActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
      tabLayout=(TabLayout) findViewById(R.id.tabLayout);
      viewPager=(ViewPager) findViewById(R.id.viewPager);

      tabLayout.addTab(tabLayout.newTab().setText("Home"));
      tabLayout.addTab(tabLayout.newTab().setText("Sports"));
      tabLayout.addTab(tabLayout.newTab().setText("Movie"));

      tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

      final MyTabAdapter adapter=new MyTabAdapter(this,getSupportFragmentManager(),tabLayout.getTabCount());
      viewPager.setAdapter(adapter);

      viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
      tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
          @Override
          public void onTabSelected(TabLayout.Tab tab) {
              viewPager.setCurrentItem(tab.getPosition());
          }

          @Override
          public void onTabUnselected(TabLayout.Tab tab) {

          }

          @Override
          public void onTabReselected(TabLayout.Tab tab) {

          }
      });



    }
}
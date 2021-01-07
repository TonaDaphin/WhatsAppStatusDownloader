package com.example.whatsappstatusdownloader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

import com.example.whatsappstatusdownloader.Adapters.PagerAdapter;
import com.google.android.material.tabs.TabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    public ViewPager viewPager;
    public TabLayout tableLayout;

    @BindView(R.id.toolBarLayout) Toolbar toolbar;
//    @BindView(R.id.tabsLayout) TabLayout tableLayout;
//    @BindView(R.id.viewPagerLayout) ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.viewPagerLayout);
        tableLayout = findViewById(R.id.tabsLayout);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager()));
        tableLayout.setupWithViewPager(viewPager);
    }
}

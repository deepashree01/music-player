package com.example.letsplay;

import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.view.*;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;
import android.content.*;
import android.app.*;
import android.widget.*;


public class MainActivity extends AppCompatActivity {
    Toolbar mToolbar;
    PagerAdapter mPagerAdapter;
    TabLayout mTabLayout;
    TabItem musicTabItem;
    TabItem albumTabItem;
    TabItem playlistTabItem;
    ViewPager mViewPager;
    private NotificationManager mNotificationManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_audiotrack_black_24dp) // notification icon
                .setContentTitle("Simple notification") // title
                .setContentText("Hello word") // body message
                .setAutoCancel(true);
        NotificationManager mNotificationManager = (NotificationManager)
                getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(0, mBuilder.build());




        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(getString(R.string.app_name));

        mTabLayout = findViewById(R.id.tabLayout);
        musicTabItem = findViewById(R.id.musicTabItem);
        albumTabItem = findViewById(R.id.albumTabItem);
        playlistTabItem = findViewById(R.id.playlistTabItem);
        mViewPager = findViewById(R.id.pager);

        mPagerAdapter = new PagerAdapter(getSupportFragmentManager(),mTabLayout.getTabCount());
        mViewPager.setAdapter(mPagerAdapter);

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition()); // set current tab position

                if(tab.getPosition() == 1){
//                    Toast.makeText(MainActivity.this, "Album Tab Selected.", Toast.LENGTH_SHORT).show();
                }else if(tab.getPosition() == 2){
//                    Toast.makeText(MainActivity.this, "Music Tab Selected.", Toast.LENGTH_SHORT).show();
                }else {
//                    Toast.makeText(MainActivity.this, "Playlist Tab Selected.", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));





    }


}

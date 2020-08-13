package com.shrinqghana.myapplication.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.Fragment;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.shrinqghana.myapplication.Fragments.DashboardFragment;
import com.shrinqghana.myapplication.Fragments.TransactionsFragment;
import com.shrinqghana.myapplication.Fragments.ScanFragment;
import com.shrinqghana.myapplication.Inc.Util;
import com.shrinqghana.myapplication.R;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        bottomNavigation.setSelectedItemId(R.id.navigation_loyalty);

        Util.openFragment(getSupportFragmentManager(),R.id.container, DashboardFragment.newInstance("", ""), "DashboardFragment", 0);

    }


    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.navigation_loyalty:
                            Util.openFragment(getSupportFragmentManager(),R.id.container, DashboardFragment.newInstance("", ""), "DashboardFragment", 0);
                            return true;
                    }
                    return false;
                }
            };


    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if(count == 1){
            finish();
        } else {
            super.onBackPressed();
        }

    }

}

package com.shrinqghana.myapplication.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.RectF;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.shrinqghana.myapplication.Fragments.BareExampleFragment;
import com.shrinqghana.myapplication.Fragments.RedemptionFragment;
import com.shrinqghana.myapplication.Inc.Util;
import com.shrinqghana.myapplication.R;

import org.opencv.android.OpenCVLoader;

public class ScanActivity extends AppCompatActivity {
    static {

        OpenCVLoader.initDebug();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        Util.open_fragment(getSupportFragmentManager(),R.id.container, BareExampleFragment.newInstance(), "BareExampleFragment", 0);

        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getSupportFragmentManager().popBackStack();
                Util.open_fragment(getSupportFragmentManager(),R.id.container2, RedemptionFragment.newInstance("", ""), "RedemptionFragment", 3);
            }
        }, 7000);
    }


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

package com.example.shiryabdo.onesearchapp.ui;

import android.os.Bundle;
import android.view.View;

import com.example.shiryabdo.onesearchapp.R;
import com.example.shiryabdo.onesearchapp.base.BaseActivity;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        findViewById(R.id.bReadySplash).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}

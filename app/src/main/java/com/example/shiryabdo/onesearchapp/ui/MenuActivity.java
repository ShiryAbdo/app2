package com.example.shiryabdo.onesearchapp.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.shiryabdo.onesearchapp.ui.gameplay.PauseDialogFragment;
import com.example.shiryabdo.onesearchapp.ui.gameplay.Suplevel;
import com.example.shiryabdo.onesearchapp.ui.gameplay.SuplevelDialogFragment;
import com.google.android.gms.common.ConnectionResult;

import com.example.shiryabdo.onesearchapp.R;
import com.example.shiryabdo.onesearchapp.base.BaseGooglePlayServicesActivity;
import com.example.shiryabdo.onesearchapp.framework.WordSearchManager;
import com.example.shiryabdo.onesearchapp.models.GameDifficulty;
import com.example.shiryabdo.onesearchapp.models.GameMode;
import com.example.shiryabdo.onesearchapp.models.GameType;
import com.example.shiryabdo.onesearchapp.ui.gameplay.WordSearchActivity;

public class MenuActivity extends BaseGooglePlayServicesActivity implements View.OnClickListener {

    private final static String MENU_PREF_NAME = "menu_prefs";
    private final static String FIRST_TIME = "first_time";

    private final static long ROUND_TIME_IN_MS = 60000;
    private final SuplevelDialogFragment mPauseDialogFragment = new SuplevelDialogFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        categoryId = R.string.ga_menu_screen;
        // Check if first time opening app, show splash screen
        SharedPreferences prefs = getSharedPreferences(MENU_PREF_NAME, MODE_PRIVATE);
        boolean isFirstTime = prefs.getBoolean(FIRST_TIME, true);
        if (isFirstTime) {
            SharedPreferences.Editor editor = getSharedPreferences(MENU_PREF_NAME, MODE_PRIVATE).edit();
            editor.putBoolean(FIRST_TIME, false);
            editor.apply();

            Intent i = new Intent(getApplicationContext(), SplashActivity.class);
            startActivity(i);
        }

        setContentView(R.layout.activity_menu);

        findViewById(R.id.bMenuEasy).setOnClickListener(this);
        findViewById(R.id.bMenuMedium).setOnClickListener(this);
        findViewById(R.id.bMenuHard).setOnClickListener(this);
        //TODO: Reimplement advanced after more efficient way of drawing out the grid
//        findViewById(R.id.bMenuAdvanced).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.bMenuSignIn) {
            mInSignInFlow = true;
            mSignInClicked = true;
            mGoogleApiClient.connect();
            return;
        }
        String gd = null;
        int ga_button_id = -1;
        switch (view.getId()) {
            case R.id.bMenuEasy:
                gd = GameDifficulty.Easy;
                ga_button_id = R.string.ga_click_easy;
                break;
            case R.id.bMenuMedium:
                gd = GameDifficulty.Medium;
                ga_button_id = R.string.ga_click_medium;
                break;
            case R.id.bMenuHard:
                gd = GameDifficulty.Hard;
                ga_button_id = R.string.ga_click_hard;
                break;
//            case R.id.bMenuAdvanced:
//                gd = GameDifficulty.Advanced;
//                break;
        }
        analyticsTrackEvent(ga_button_id);
        WordSearchManager wsm = WordSearchManager.getInstance();
        wsm.Initialize(new GameMode(GameType.Timed, gd, ROUND_TIME_IN_MS), getApplicationContext());
        wsm.buildWordSearches();
        Intent i = new Intent(this,  Suplevel.class);
        startActivity(i);
    }

    @Override
    protected void onResume() {
        super.onResume();
        analyticsTrackScreen(getString(categoryId));
        WordSearchManager.nullify();
    }

    @Override
    public void onConnected(Bundle bundle) {
        super.onConnected(bundle);
        findViewById(R.id.bMenuSignIn).setVisibility(View.GONE);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        super.onConnectionFailed(connectionResult);
        findViewById(R.id.bMenuSignIn).setVisibility(View.VISIBLE);
        findViewById(R.id.bMenuSignIn).setOnClickListener(this);
    }
}

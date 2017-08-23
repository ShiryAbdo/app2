package com.example.shiryabdo.onesearchapp.ui.gameplay;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shiryabdo.onesearchapp.R;
import com.example.shiryabdo.onesearchapp.adapters.WordSearchPagerAdapter;
import com.example.shiryabdo.onesearchapp.base.BaseActivity;
import com.example.shiryabdo.onesearchapp.framework.WordSearchManager;
import com.example.shiryabdo.onesearchapp.models.GameState;
import com.example.shiryabdo.onesearchapp.ui.ResultsActivity;

public class WordSearchActivity extends BaseActivity implements WordSearchGridView.WordFoundListener, PauseDialogFragment.PauseDialogListener, View.OnClickListener {

    private final static boolean ON_SKIP_HIGHLIGHT_WORD = true;
    private final static long ON_SKIP_HIGHLIGHT_WORD_DELAY_IN_MS = 500;

    private final static int TIMER_GRANULARITY_IN_MS = 50;

    /**
     * Current number of grid views that have been instantiated
     * Actual fragment position is currentItem - 2
     */
    public static int currentItem;
    private final PauseDialogFragment mPauseDialogFragment = new PauseDialogFragment();
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private TextView mTimerTextView;
    private TextView mScoreTextView;
    private CountDownTimer mCountDownTimer;
    WordSearchFragment wordSearchFragment ;

    int numberQution_level_one = 5;
    int numberQution_level_two = 10;
    int numberQution_level_three =30;
    private String mGameState;
    private long mTimeRemaining;
    private long mRoundTime;
    private int mScore;
    private int mSkipped;
    private WordSearchPagerAdapter mWordSearchPagerAdapter;
    private  int numberQution ,levelInputQution  ,NUMERoflevel;
    Bundle bundle ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        numberQution=NUMERoflevel=0;
        int timeNumber =5;
        bundle =getIntent().getExtras();
        categoryId = R.string.ga_gameplay_screen;
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.wordsearch_activity);
        mGameState = GameState.START;
        findViewById(R.id.bSkip).setOnClickListener(this);
        findViewById(R.id.bPause).setOnClickListener(this);
        mTimerTextView = (TextView) findViewById(R.id.tvTimer);
        mScoreTextView = (TextView) findViewById(R.id.tvScore);
        mScoreTextView.setText("0");
        currentItem = 0;
        mScore = 0;
        mSkipped = 0;

        // Create the adapter that will return a fragment for each of the
        // primary sections of the activity.
        /*
          The {@link android.support.v4.view.PagerAdapter} that will provide
          fragments for each of the sections. We use a
          {@link FragmentPagerAdapter} derivative, which will keep every
          loaded fragment in memory. If this becomes too memory intensive, it
          may be best to switch to a
          {@link android.support.v13.app.FragmentStatePagerAdapter}.
         */
        mWordSearchPagerAdapter = new WordSearchPagerAdapter(getFragmentManager(), getApplicationContext());


        // Set up the ViewPager with the sections adapter.
        mViewPager = (WordSearchViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mWordSearchPagerAdapter);

        mRoundTime = WordSearchManager.getInstance().getGameMode().getTime();
        mTimeRemaining = mRoundTime;
        setupCountDownTimer(mTimeRemaining);
        startCountDownTimer();
          int qutionmuberLevel = bundle.getInt("qutionNumper");
        if (qutionmuberLevel!=0){
            levelInputQution= qutionmuberLevel;

            setupCountDownTimer(mTimeRemaining);
            startCountDownTimer();

        }

        int numerOfLevel = bundle.getInt("numerOflevels");
        if(numerOfLevel!=0){
             NUMERoflevel=numerOfLevel ;

         }




    }

    private void pauseGameplay() {
        if (mGameState.equals(GameState.PAUSE))
            return;
        mGameState = GameState.PAUSE;
        stopCountDownTimer();
        mPauseDialogFragment.show(getFragmentManager(), "dialog");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bSkip:
                analyticsTrackEvent(R.string.ga_click_skip);
                if (ON_SKIP_HIGHLIGHT_WORD) {
                    ((WordSearchFragment) mWordSearchPagerAdapter.getFragmentFromCurrentItem(currentItem)).highlightWord();
                    (new CountDownTimer(ON_SKIP_HIGHLIGHT_WORD_DELAY_IN_MS, TIMER_GRANULARITY_IN_MS) {

                        public void onTick(long millisUntilFinished) {
                        }

                        public void onFinish() {
                            mViewPager.setCurrentItem(currentItem);
                            mSkipped++;
                        }
                    }).start();
                } else {
                    mViewPager.setCurrentItem(currentItem);
                    mSkipped++;
                }


                break;
            case R.id.bPause:
                analyticsTrackEvent(R.string.ga_click_pause);
                pauseGameplay();
                break;
        }
    }

    @Override
    public void notifyWordFound() {
        mViewPager.setCurrentItem(currentItem);
        mScoreTextView.setText(Integer.toString(++mScore));
        numberQution++;



        if (levelInputQution <numberQution){
            mCountDownTimer.cancel();
            stopCountDownTimer();

            Toast.makeText(this, "MUHMOUD", Toast.LENGTH_SHORT).show();
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.myDialog));
            // ...Irrelevant code for customizing the buttons and title
            LayoutInflater inflater = this.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.alert_gialoge, null);
            NUMERoflevel++;

            dialogBuilder.setView(dialogView);

             Button nextlevel = ( Button) dialogView.findViewById(R.id.nextlevel);
            nextlevel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                     Intent i = new Intent(getApplicationContext(), WordSearchActivity.class);

                    i.putExtra("numerOflevels", NUMERoflevel++ );
                    if(NUMERoflevel<11){
                        i.putExtra("qutionNumper",numberQution_level_one);
                       i.putExtra("timMin" ,5);
                    }if(NUMERoflevel<21){
                        i.putExtra("qutionNumper",numberQution_level_two);
                        i.putExtra("timMin" ,10);

                    }if(NUMERoflevel<31){
                        i.putExtra("qutionNumper",numberQution_level_three);
                        i.putExtra("timMin" ,20);

                    }if(NUMERoflevel<41){

                    }
                     finish();
                      startActivity(i);


                }
            });
            TextView show =(TextView)dialogView.findViewById(R.id.show);
            show.setText(Integer.toString(NUMERoflevel));
             AlertDialog alertDialog = dialogBuilder.create();
            alertDialog.show();
        }

    }

    @Override
    public void onDialogQuit() {
        finish();
    }

    @Override
    public void onDialogResume() {
        mGameState = GameState.PLAY;
        setupCountDownTimer(mTimeRemaining);
        startCountDownTimer();
        setFullscreen();
    }

    @Override
    public void onDialogRestart() {
        mGameState = GameState.PLAY;
        restart();
    }

    private void restart() {
        mScore = 0;
        mSkipped = 0;
        mTimeRemaining = mRoundTime;
        setupCountDownTimer(mTimeRemaining);
        startCountDownTimer();
        setFullscreen();
        mScoreTextView.setText("0");
        mViewPager.setCurrentItem(currentItem);
    }

    @Override
    protected void onResume() {
        super.onResume();
        analyticsTrackScreen(getString(categoryId));
        if (mGameState.equals(GameState.START) || mGameState.equals(GameState.FINISHED))
            mGameState = GameState.PLAY;
        else
            pauseGameplay();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopCountDownTimer();
    }

    @Override
    public void onBackPressed() {
        pauseGameplay();
    }

    private void setupCountDownTimer(final long timeinMS) {
        mCountDownTimer = new CountDownTimer(timeinMS, TIMER_GRANULARITY_IN_MS) {


            public void onTick(long millisUntilFinished) {
                mTimeRemaining = millisUntilFinished;
                long secondsRemaining = mTimeRemaining / 1000 + 1;
                mTimerTextView.setText(Long.toString(secondsRemaining));
                if (secondsRemaining <= 10) {
                    mTimerTextView.setTextColor(getResources().getColor(R.color.red));
                } else {
                    mTimerTextView.setTextColor(Color.BLACK);

                }
            }

            public void onFinish() {
                mGameState = GameState.FINISHED;
                mTimerTextView.setText("0");


                ((WordSearchFragment) mWordSearchPagerAdapter.getFragmentFromCurrentItem(currentItem)).highlightWord();
                (new CountDownTimer(ON_SKIP_HIGHLIGHT_WORD_DELAY_IN_MS, TIMER_GRANULARITY_IN_MS) {

                    public void onTick(long millisUntilFinished) {
                    }

                    public void onFinish() {
                        Intent i = new Intent(getApplicationContext(), ResultsActivity.class);
                        i.putExtra("score", mScore);
                        i.putExtra("skipped", mSkipped);
                        startActivity(i);
                        finish();
                    }
                }).start();
//


            }
        };
    }

    private void startCountDownTimer() {
        if (mCountDownTimer != null)
            mCountDownTimer.start();
    }

    private void stopCountDownTimer() {
        if (mCountDownTimer != null)
            mCountDownTimer.cancel();
    }

    public long getTimeRemaining() {
        return mTimeRemaining;
    }

    public int getScore() {
        return mScore;
    }

}

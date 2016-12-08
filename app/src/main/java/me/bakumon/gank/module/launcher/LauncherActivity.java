package me.bakumon.gank.module.launcher;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import me.bakumon.gank.R;
import me.bakumon.gank.module.home.HomeActivity;

public class LauncherActivity extends AppCompatActivity implements LauncherContract.View {

    private LauncherContract.Presenter mLauncherPresenter = new LauncherPresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_loading);
    }

    @Override
    public void onResume() {
        super.onResume();
        mLauncherPresenter.subscribe();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mLauncherPresenter.unsubscribe();
    }

    @Override
    public void startAnim() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.anim_launch_item_fade_in);
        anim.setStartOffset(560);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Animation anim = AnimationUtils.loadAnimation(LauncherActivity.this,
                        R.anim.anim_launch_item_scale_in);
                View view = findViewById(R.id.launch_icon);
                view.setVisibility(View.VISIBLE);
                view.startAnimation(anim);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        findViewById(R.id.content).startAnimation(anim);
    }

    @Override
    public void toHome() {
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {

    }
}

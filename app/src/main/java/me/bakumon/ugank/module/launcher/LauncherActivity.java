package me.bakumon.ugank.module.launcher;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import me.bakumon.ugank.R;
import me.bakumon.ugank.module.home.HomeActivity;
/**
 * LauncherActivity
 * Created by bakumon on 2016/12/6 11:07.
 */
public class LauncherActivity extends AppCompatActivity implements LauncherContract.View {

    private LauncherContract.Presenter mLauncherPresenter = new LauncherPresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
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
    public void toHome() {
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {

    }
}

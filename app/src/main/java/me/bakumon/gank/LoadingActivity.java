package me.bakumon.gank;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import me.bakumon.gank.home.HomeActivity;

public class LoadingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        iconIn();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(LoadingActivity.this, HomeActivity.class));
                finish();
            }
        }, 3200);
    }


    private void iconIn() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.anim_launch_item_fade_in);
        anim.setStartOffset(560);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Animation anim = AnimationUtils.loadAnimation(LoadingActivity.this,
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

//        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(findViewById(R.id.content), "alpha", 0f, 1f);
//        alphaAnimator.setInterpolator(new LinearInterpolator());
//        alphaAnimator.setDuration(100);
////        alphaAnimator.setStartDelay(560);
//        alphaAnimator.addListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                super.onAnimationEnd(animation);
//                Animation anim = AnimationUtils.loadAnimation(LoadingActivity.this,
//                        R.anim.anim_launch_item_scale_in);
//                View view = findViewById(R.id.launch_icon);
//                view.setVisibility(View.VISIBLE);
//                view.startAnimation(anim);
//            }
//        });
//        alphaAnimator.start();
    }

    @Override
    public void onBackPressed() {

    }
}

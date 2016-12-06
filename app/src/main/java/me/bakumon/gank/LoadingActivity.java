package me.bakumon.gank;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import me.bakumon.gank.home.HomeActivity;

public class LoadingActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_loading);
        jumpToMain(200);
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                setContentView(R.layout.activity_loading);
//            }
//        }, 1000);
    }

    /**
     * 延迟去首页
     *
     * @param delayMillis 延迟时长，单位：毫秒
     */
    private void jumpToMain(long delayMillis) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(LoadingActivity.this, HomeActivity.class));
                finish();
            }
        }, delayMillis);
    }
}

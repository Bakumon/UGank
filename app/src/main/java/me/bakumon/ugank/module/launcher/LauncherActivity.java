package me.bakumon.ugank.module.launcher;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import me.bakumon.ugank.module.home.HomeActivity;

/*
* 此 Activity 存在的说明：
* 1.避免白屏
*   本来是直接给 HomeActivity 设置的透明主题，但是在 一加3T（7.0） 和 华为P9（7.0） 上按下 home 键，会返回到 Launcher 桌面的 home 页，而不是应用所在页，
*   可能是因为 7.0 手机在透明背景上按 home 键做了处理
* 2.处女座的内心其实是不情愿加这一界面的，启动速度受到了影响，但是又不知道怎样解决返回 home 页的问题，暂时先这样解决吧。
* */

/**
 * LauncherActivity
 * Created by bakumon on 2016/12/8.
 */
public class LauncherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = new Intent(LauncherActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}

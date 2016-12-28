package me.bakumon.ugank;

import android.app.Application;

/**
 * App
 * Created by bakumon on 2016/12/8 17:18.
 */
public class App extends Application {
    private static App INSTANCE;

    public static App getInstance() {
        return INSTANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        // 初始化主题色
        ThemeManage.INSTANCE.initColorPrimary(getResources().getColor(R.color.colorPrimary));
        ConfigManage.INSTANCE.initConfig(this);
    }


}

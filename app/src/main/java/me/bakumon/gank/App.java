package me.bakumon.gank;

import android.app.Application;
import android.content.SharedPreferences;

/**
 * App
 * Created by bakumon on 2016/12/8 17:18.
 */
public class App extends Application {
    private static App INSTANCE;

    public static boolean isListShowImg;

    public static App getInstance() {
        return INSTANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        // 初始化主题色
        ThemeManage.INSTANCE.initColorPrimary(getResources().getColor(R.color.colorPrimary));
        initConfig();
    }

    /**
     * 初始化配置信息
     */
    private void initConfig() {
        SharedPreferences sharedPreferences = this.getSharedPreferences("app_config", MODE_PRIVATE);
        // 列表是否显示图片
        isListShowImg = sharedPreferences.getBoolean("isListShowImg", false);
    }

}

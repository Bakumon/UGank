package me.bakumon.gank;

import android.app.Application;
import android.content.SharedPreferences;

/**
 * App
 * Created by bakumon on 2016/12/8 17:18.
 */
public class App extends Application {
    private static App INSTANCE;

    public static int colorPrimary;
    public static int colorAccent;

    public static boolean isListShowImg;

    public static App getInstance() {
        return INSTANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        // 初始化主题色
        colorPrimary = getResources().getColor(R.color.colorPrimary);
        colorAccent = getResources().getColor(R.color.colorAccent);

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

package me.bakumon.ugank;

import android.app.Application;

import org.litepal.LitePal;

import cn.bingoogolapple.swipebacklayout.BGASwipeBackManager;

//import com.squareup.leakcanary.LeakCanary;

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
        // 初始化 LeakCanary
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            // This process is dedicated to LeakCanary for heap analysis.
//            // You should not init your app in this process.
//            return;
//        }
//        LeakCanary.install(this);
        BGASwipeBackManager.getInstance().init(this);
        INSTANCE = this;
        // 初始化主题色
        ThemeManage.INSTANCE.initColorPrimary(getResources().getColor(R.color.colorPrimary));
        ConfigManage.INSTANCE.initConfig(this);
        LitePal.initialize(this);
    }


}

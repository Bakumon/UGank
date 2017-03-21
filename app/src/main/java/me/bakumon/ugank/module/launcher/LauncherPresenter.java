package me.bakumon.ugank.module.launcher;

import android.text.TextUtils;

import me.bakumon.ugank.ConfigManage;

/**
 * LauncherPresenter
 * Created by bakumon on 17-3-21.
 */

public class LauncherPresenter implements LauncherContract.Presenter {

    private LauncherContract.View mLauncherView;

    public LauncherPresenter(LauncherContract.View view) {
        mLauncherView = view;
    }

    @Override
    public void subscribe() {
        String imgCacheUrl = ConfigManage.INSTANCE.getBannerURL();
        if (TextUtils.isEmpty(imgCacheUrl)) {
            mLauncherView.goHomeActivity();
        } else {
            mLauncherView.loadImg(imgCacheUrl);
        }
    }

    @Override
    public void unsubscribe() {

    }
}

package me.bakumon.ugank.module.bigimg;

import android.support.annotation.NonNull;

import me.bakumon.ugank.ThemeManage;

/**
 * BigimgPresenter
 * Created by bakumon on 16-12-20.
 */

public class BigimgPresenter implements BigimgContract.Presenter {

    private BigimgContract.View mBigimgView;

    public BigimgPresenter(@NonNull BigimgContract.View bigimgView) {
        mBigimgView = bigimgView;

    }

    @Override
    public void subscribe() {
        mBigimgView.setToolbarBackgroundColor(ThemeManage.INSTANCE.getColorPrimary());
        mBigimgView.setLoadingColor(ThemeManage.INSTANCE.getColorPrimary());
        loadMeiziImg(mBigimgView.getMeiziImg());
        setMeiziTitle(mBigimgView.getMeiziTitle());
    }

    @Override
    public void unsubscribe() {
    }

    private void loadMeiziImg(String url) {
        if (url == null) {
            return;
        }
        mBigimgView.showLoading();
        mBigimgView.loadMeizuImg(url);
    }

    private void setMeiziTitle(String title) {
        if (title == null) {
            return;
        }
        mBigimgView.setMeiziTitle("妹子:" + title);
    }
}

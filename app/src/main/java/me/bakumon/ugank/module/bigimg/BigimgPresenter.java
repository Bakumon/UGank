package me.bakumon.ugank.module.bigimg;

import android.os.Bundle;

import me.bakumon.ugank.ThemeManage;

/**
 * BigimgPresenter
 * Created by bakumon on 16-12-20.
 */

public class BigimgPresenter implements BigimgContract.Presenter {

    private BigimgContract.View mBigimgView;

    public BigimgPresenter(BigimgContract.View bigimgView) {
        mBigimgView = bigimgView;

    }

    @Override
    public void subscribe() {
        mBigimgView.setToolbarBackgroundColor(ThemeManage.INSTANCE.getColorPrimary());
        mBigimgView.setLoadingColor(ThemeManage.INSTANCE.getColorPrimary());
    }

    @Override
    public void unsubscribe() {
    }

    @Override
    public void loadMeiziImg(Bundle data) {
        if (data == null) {
            return;
        }
        mBigimgView.showLoading();
        mBigimgView.loadMeizuImg(data.getString(BigimgActivity.MEIZI_URL));
    }

    @Override
    public void setMeiziTitle(Bundle data) {
        if (data == null) {
            return;
        }
        mBigimgView.setMeiziTitle("妹子:" + data.getString(BigimgActivity.MEIZI_TITLE));
    }
}

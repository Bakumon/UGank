package me.bakumon.gank.module.img;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;

import me.bakumon.gank.App;
import me.bakumon.gank.ThemeManage;

/**
 * BigimgPresenter
 * Created by bakumon on 16-12-20.
 */

public class BigimgPresenter implements BigimgContract.Presenter {

    private BigimgContract.View mBigimgView;
    private Activity mContext;

    public BigimgPresenter(BigimgContract.View bigimgView) {
        mBigimgView = bigimgView;

    }

    @Override
    public void subscribe() {
        mContext = mBigimgView.getBigimgContext();
        mBigimgView.setToolbarBackgroundColor(ThemeManage.INSTANCE.getColorPrimary());
        mBigimgView.setViewColorAccent(ThemeManage.INSTANCE.getColorPrimary());
        loadDate();
    }

    private void loadDate() {
        Intent intent = mContext.getIntent();
        mBigimgView.setMeiziTitle("妹子:" + intent.getStringExtra(BigimgActivity.MEIZI_TITLE));
        String meiziUrl = intent.getStringExtra(BigimgActivity.MEIZI_URL);
        if (!TextUtils.isEmpty(meiziUrl)) {
            mBigimgView.loadMeizuImg(meiziUrl);
        }
    }

    @Override
    public void unsubscribe() {
        mContext = null;
    }
}

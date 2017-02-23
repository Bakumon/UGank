package me.bakumon.ugank.module.bigimg;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;

import me.bakumon.ugank.ThemeManage;
import rx.subscriptions.CompositeSubscription;

/**
 * BigimgPresenter
 * Created by bakumon on 16-12-20.
 */

public class BigimgPresenter implements BigimgContract.Presenter {

    private BigimgContract.View mBigimgView;
    private Activity mContext;
    private String meiziUrl;

    private CompositeSubscription mSubscriptions;

    public BigimgPresenter(BigimgContract.View bigimgView) {
        mBigimgView = bigimgView;

    }

    @Override
    public void subscribe() {
        mSubscriptions = new CompositeSubscription();
        mContext = mBigimgView.getBigimgContext();
        mBigimgView.setToolbarBackgroundColor(ThemeManage.INSTANCE.getColorPrimary());
        loadDate();
    }

    private void loadDate() {
        Intent intent = mContext.getIntent();
        mBigimgView.setMeiziTitle("妹子:" + intent.getStringExtra(BigimgActivity.MEIZI_TITLE));
        meiziUrl = intent.getStringExtra(BigimgActivity.MEIZI_URL);
        if (!TextUtils.isEmpty(meiziUrl)) {
            mBigimgView.loadMeizuImg(meiziUrl);
        }
    }

    @Override
    public void unsubscribe() {
        mContext = null;
        mSubscriptions.clear();
    }

}

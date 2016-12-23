package me.bakumon.gank.module.bigimg;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

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

    class BigimgLoadCompleteistener implements RequestListener<Drawable> {
        @Override
        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
            return false;
        }

        @Override
        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
            mBigimgView.showSaveFab();
            return false;
        }
    }

}

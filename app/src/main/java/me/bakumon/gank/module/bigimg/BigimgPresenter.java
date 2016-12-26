package me.bakumon.gank.module.bigimg;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.util.Observable;

import me.bakumon.gank.ThemeManage;
import me.bakumon.gank.utills.ImageUtil;
import me.bakumon.gank.utills.ToastUtil;
import rx.Subscription;
import rx.functions.Action1;
import rx.observers.Observers;
import rx.subscriptions.CompositeSubscription;

/**
 * BigimgPresenter
 * Created by bakumon on 16-12-20.
 */

public class BigimgPresenter implements BigimgContract.Presenter {

    private BigimgContract.View mBigimgView;
    private Activity mContext;
    private String meiziUrl;
    private Bitmap mBitmap;

    @NonNull
    private CompositeSubscription mSubscriptions;

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

    @Override
    public void requestPermissionForSaveImg() {
        RxPermissions rxPermissions = new RxPermissions(mContext);
        Subscription requestPermissionSubscription = rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (aBoolean) {
                            saveImageToGallery();

                        } else {
                            ToastUtil.showToastDefault(mContext, "拒绝");
                        }
                    }
                });
        mSubscriptions.add(requestPermissionSubscription);
    }

    private void saveImageToGallery() {
        if (mBitmap != null) {
            ImageUtil.saveImageToGallery(mContext, mBitmap);
        }

//        Observers.create(new Action1<Object>() {
//            @Override
//            public void call(Object o) {
//
//            }
//        }).onNext();
    }


    class BigimgLoadCompleteistener implements RequestListener<Drawable> {
        @Override
        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
            return false;
        }

        @Override
        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
            mBitmap = ImageUtil.drawableToBitmap(resource);
            mBigimgView.showSaveFab();
            return false;
        }
    }

}

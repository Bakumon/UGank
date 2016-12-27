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

import me.bakumon.gank.ThemeManage;
import me.bakumon.gank.utills.ImageUtil;
import me.bakumon.gank.utills.ToastUtil;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
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
        mSubscriptions = new CompositeSubscription();
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
                            ToastUtil.showToastDefault(mContext, "需要权限才能保存妹子");
                        }
                    }
                });
        mSubscriptions.add(requestPermissionSubscription);
    }

    private void saveImageToGallery() {
        Subscription subscription = Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                boolean isSaveSuccess = ImageUtil.saveImageToGallery(mContext, mBitmap);
                subscriber.onNext(isSaveSuccess);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Boolean isSaveSuccess) {
                        if (isSaveSuccess) {
                            mBigimgView.showMsgSaveSuccess("图片保存成功");
                        } else {
                            mBigimgView.showMsgSaveFail("图片保存失败");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }


    class BigimgLoadCompleteistener implements RequestListener<Drawable> {
        @Override
        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
            return false;
        }

        @Override
        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
            mBitmap = ImageUtil.drawableToBitmap(resource);
            if (mBitmap != null) {
                mBigimgView.showSaveFab();
            }
            return false;
        }
    }

}

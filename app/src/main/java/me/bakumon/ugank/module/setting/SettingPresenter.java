package me.bakumon.ugank.module.setting;

import android.support.annotation.NonNull;


import me.bakumon.ugank.App;
import me.bakumon.ugank.ConfigManage;
import me.bakumon.ugank.ThemeManage;
import me.bakumon.ugank.utills.GlideCacheUtil;
import me.bakumon.ugank.utills.PackageUtil;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * SettingPresenter
 * Created by bakumon on 2016/12/15 17:08.
 */
public class SettingPresenter implements SettingContract.Presenter {

    private SettingContract.View mView;

    @NonNull
    private CompositeSubscription mSubscriptions;

    public SettingPresenter(SettingContract.View view) {
        mView = view;
    }

    @Override
    public void subscribe() {
        mSubscriptions = new CompositeSubscription();
        mView.setSwitchCompatsColor(ThemeManage.INSTANCE.getColorPrimary());
        mView.setToolbarBackgroundColor(ThemeManage.INSTANCE.getColorPrimary());
        // 初始化开关显示状态
        mView.changeSwitchState(ConfigManage.INSTANCE.isListShowImg());
        mView.setCacheSizeInTv(getCachedSize());
        mView.setAppVersionNameInTv(PackageUtil.getVersionName(App.getInstance()));
    }

    @Override
    public void unsubscribe() {
        mSubscriptions.clear();
    }

    /**
     * 获取 Glide 已经缓存的大小
     */
    private String getCachedSize() {
        return GlideCacheUtil.getInstance().getCacheSize(App.getInstance());
    }

    @Override
    public void saveIsListShowImg(boolean isListShowImg) {
        ConfigManage.INSTANCE.setListShowImg(isListShowImg);
    }

    @Override
    public void deleteImgCache() {
        Subscription subscription = Observable.create(new Observable.OnSubscribe<Object>() {
            @Override
            public void call(Subscriber<? super Object> subscriber) {
                // TODO: 2017/2/22 清理缓存 
//                Glide.get(App.getInstance()).clearDiskCache();
                subscriber.onNext(null);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showDeleteImgFail();
                    }

                    @Override
                    public void onNext(Object object) {
                        mView.showDeleteImgSuccess();
                        mView.setCacheSizeInTv(getCachedSize());
                    }
                });
        mSubscriptions.add(subscription);

    }

    @Override
    public int getColorPrimary() {
        return ThemeManage.INSTANCE.getColorPrimary();
    }
}

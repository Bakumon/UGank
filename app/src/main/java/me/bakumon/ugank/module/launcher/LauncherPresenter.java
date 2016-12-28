package me.bakumon.ugank.module.launcher;

import android.support.annotation.NonNull;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * LauncherPresenter
 * Created by bakumon on 16-12-6.
 */

public class LauncherPresenter implements LauncherContract.Presenter {

    private LauncherContract.View mLauncherView;

    @NonNull
    private CompositeSubscription mSubscriptions;

    public LauncherPresenter(LauncherContract.View launcherView) {
        mLauncherView = launcherView;
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void subscribe() {
        start();
    }

    @Override
    public void unsubscribe() {
        mSubscriptions.clear();
    }

    private void start() {

        mLauncherView.startAnim();

        Observable.just(null)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .delaySubscription(3200, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        mLauncherView.toHome();
                    }
                });
    }
}

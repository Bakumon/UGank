package me.bakumon.gank.module.android;

import android.support.annotation.NonNull;

import me.bakumon.gank.entity.AndroidResult;
import me.bakumon.gank.network.NetWork;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * AndroidPresenter
 * Created by bakumon on 2016/12/8 16:42.
 */
public class AndroidPresenter implements AndroidContract.Presenter {

    private AndroidContract.View mAndroidView;

    @NonNull
    private CompositeSubscription mSubscriptions;

    public AndroidPresenter(AndroidContract.View androidView) {
        mAndroidView = androidView;
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void subscribe() {

        getAndroidItems();
    }

    @Override
    public void unsubscribe() {
        mSubscriptions.clear();
    }

    private void getAndroidItems() {
        Subscription subscription = NetWork.getGankApi()
                .getAndroid(20, 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AndroidResult>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(AndroidResult androidResult) {
                        mAndroidView.setAndroidItems(androidResult);
                    }


                });
        mSubscriptions.add(subscription);
    }
}

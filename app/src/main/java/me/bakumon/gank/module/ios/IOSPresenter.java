package me.bakumon.gank.module.ios;

import android.support.annotation.NonNull;

import me.bakumon.gank.GlobalConfig;
import me.bakumon.gank.entity.IOSResult;
import me.bakumon.gank.network.NetWork;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * IOSPresenter
 * Created by bakumon on 16-12-9.
 */

public class IOSPresenter implements IOSContract.Presenter {

    private IOSContract.View mIOSView;

    @NonNull
    private CompositeSubscription mSubscriptions;

    public IOSPresenter(IOSContract.View iOSView) {
        mIOSView = iOSView;
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void getIOSItems(int number, int page, final boolean isRefresh) {
        Subscription subscription = NetWork.getGankApi()
                .getiOS(number, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<IOSResult>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        mIOSView.getIOSItemsFail("iOS 列表数据获取失败，请重试。201");
                    }

                    @Override
                    public void onNext(IOSResult iosResult) {
                        if (isRefresh) {
                            mIOSView.setIOSItems(iosResult);
                        } else {
                            mIOSView.addIOSItems(iosResult);
                        }

                    }


                });
        mSubscriptions.add(subscription);
    }

    @Override
    public void subscribe() {
        getIOSItems(GlobalConfig.PAGE_SIZE_IOS, 1, true);
    }

    @Override
    public void unsubscribe() {
        mSubscriptions.clear();
    }
}

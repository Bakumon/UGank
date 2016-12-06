package me.bakumon.gank.home;

import android.support.annotation.NonNull;

import me.bakumon.gank.model.GankBeautyResult;
import me.bakumon.gank.network.NetWork;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by mafei on 2016/12/6 11:07.
 *
 * @author mafei
 * @version 1.0.0
 */
class HomePresenter implements HomeContract.Presenter {

    private HomeContract.View mHomeView;

    @NonNull
    private CompositeSubscription mSubscriptions;

    HomePresenter(HomeContract.View homeView) {
        mHomeView = homeView;

        mSubscriptions = new CompositeSubscription();
//        mHomeView.setPresenter(this);
    }

    @Override
    public void subscribe() {
        getBanner();
    }

    @Override
    public void unsubscribe() {
        mSubscriptions.clear();
    }

    private void getBanner() {
        Subscription subscription = NetWork.getGankApi()
                .getBeauties(1, 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GankBeautyResult>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        mHomeView.showBannerFail("Banner 图加载失败，请重试。01 " + e.getMessage());
                    }

                    @Override
                    public void onNext(GankBeautyResult gankBeautyResult) {
                        if (gankBeautyResult != null && gankBeautyResult.beauties != null && gankBeautyResult.beauties.size() > 0 && gankBeautyResult.beauties.get(0).url != null) {
                            mHomeView.setBanner(gankBeautyResult.beauties.get(0).url);
                        } else {
                            mHomeView.showBannerFail("Banner 图加载失败，请重试。02");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }
}

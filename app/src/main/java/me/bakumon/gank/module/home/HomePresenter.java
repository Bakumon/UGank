package me.bakumon.gank.module.home;

import android.support.annotation.NonNull;

import me.bakumon.gank.entity.MeiziResult;
import me.bakumon.gank.network.NetWork;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * HomePresenter
 * Created by bakumon on 2016/12/6 11:07.
 */
public class HomePresenter implements HomeContract.Presenter {

    private HomeContract.View mHomeView;

    @NonNull
    private CompositeSubscription mSubscriptions;

    HomePresenter(HomeContract.View homeView) {
        mHomeView = homeView;
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void subscribe() {
        getBanner(1);
    }

    @Override
    public void unsubscribe() {
        mSubscriptions.clear();
    }


    @Override
    public void getRandomBanner() {
        Subscription subscription = NetWork.getGankApi()
                .getRandomBeauties(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MeiziResult>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        mHomeView.showBannerFail("Banner 图加载失败，请重试。103");
                    }

                    @Override
                    public void onNext(MeiziResult meiziResult) {
                        if (meiziResult != null && meiziResult.results != null && meiziResult.results.size() > 0 && meiziResult.results.get(0).url != null) {
                            mHomeView.setBanner(meiziResult.results.get(0).url);
                        } else {
                            mHomeView.showBannerFail("Banner 图加载失败，请重试。104");
                        }

                    }
                });
        mSubscriptions.add(subscription);
    }

    private void getBanner(int page) {
        Subscription subscription = NetWork.getGankApi()
                .getBeauties(1, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MeiziResult>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        mHomeView.showBannerFail("Banner 图加载失败，请重试。101");
                    }

                    @Override
                    public void onNext(MeiziResult meiziResult) {
                        if (meiziResult != null && meiziResult.results != null && meiziResult.results.size() > 0 && meiziResult.results.get(0).url != null) {
                            mHomeView.setBanner(meiziResult.results.get(0).url);
                        } else {
                            mHomeView.showBannerFail("Banner 图加载失败，请重试。102");
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }
}

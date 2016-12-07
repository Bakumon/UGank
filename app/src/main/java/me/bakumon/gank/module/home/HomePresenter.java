package me.bakumon.gank.module.home;

import android.support.annotation.NonNull;

import java.util.Random;

import me.bakumon.gank.entity.GankBeautyResult;
import me.bakumon.gank.network.NetWork;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by bakumon on 2016/12/6 11:07.
 *
 * @author bakumon
 * @version 1.0.0
 */
public class HomePresenter implements HomeContract.Presenter {

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
        getBanner(1);
    }

    @Override
    public void unsubscribe() {
        mSubscriptions.clear();
    }


    @Override
    public void getRandomBanner() {
        Random random = new Random();
        int randomPage = random.nextInt(394);
        getBanner(randomPage);
    }

    private void getBanner(int page) {
        Subscription subscription = NetWork.getGankApi()
                .getBeauties(1, page)
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

package me.bakumon.gank.module.category;

import android.support.annotation.NonNull;

import me.bakumon.gank.GlobalConfig;
import me.bakumon.gank.entity.CategoryResult;
import me.bakumon.gank.network.NetWork;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * CategoryPresenter
 * Created by bakumon on 2016/12/8 16:42.
 */
public class CategoryPresenter implements CategoryContract.Presenter {

    private CategoryContract.View mCategoryView;

    @NonNull
    private CompositeSubscription mSubscriptions;

    public CategoryPresenter(CategoryContract.View androidView) {
        mCategoryView = androidView;
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void subscribe() {

        getAndroidItems(GlobalConfig.PAGE_SIZE_CATEGORY, 1, true);
    }

    @Override
    public void unsubscribe() {
        mSubscriptions.clear();
    }

    @Override
    public void getAndroidItems(int number, int page, final boolean isRefresh) {
        Subscription subscription = NetWork.getGankApi()
                .getCategoryDate(mCategoryView.getCategoryName(), number, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CategoryResult>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        mCategoryView.getAndroidItemsFail(mCategoryView.getCategoryName() + " 列表数据获取失败，请重试。201");
                    }

                    @Override
                    public void onNext(CategoryResult androidResult) {
                        if (isRefresh) {
                            mCategoryView.setAndroidItems(androidResult);
                        } else {
                            mCategoryView.addAndroidItems(androidResult);
                        }

                    }


                });
        mSubscriptions.add(subscription);
    }
}

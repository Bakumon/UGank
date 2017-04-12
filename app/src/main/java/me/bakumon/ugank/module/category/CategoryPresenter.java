package me.bakumon.ugank.module.category;

import android.support.annotation.NonNull;

import me.bakumon.ugank.GlobalConfig;
import me.bakumon.ugank.entity.CategoryResult;
import me.bakumon.ugank.network.NetWork;
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

    private int mPage = 1;

    @NonNull
    private CompositeSubscription mSubscriptions;

    public CategoryPresenter(CategoryContract.View androidView) {
        mCategoryView = androidView;
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void subscribe() {
        getCategoryItems(true);
    }

    @Override
    public void unsubscribe() {
        mSubscriptions.clear();
    }

    @Override
    public void getCategoryItems(final boolean isRefresh) {
        if (isRefresh) {
            mCategoryView.showSwipeLoading();
            mPage = 1;
        } else {
            mPage += 1;
        }
        Subscription subscription = NetWork.getGankApi()
                .getCategoryDate(mCategoryView.getCategoryName(), GlobalConfig.PAGE_SIZE_CATEGORY, mPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CategoryResult>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        mCategoryView.hideSwipeLoading();
                        mCategoryView.getCategoryItemsFail(mCategoryView.getCategoryName() + " 列表数据获取失败。");
                    }

                    @Override
                    public void onNext(CategoryResult androidResult) {
                        if (isRefresh) {
                            mCategoryView.setCategoryItems(androidResult);
                            mCategoryView.hideSwipeLoading();
                            mCategoryView.setLoading();
                        } else {
                            mCategoryView.addCategoryItems(androidResult);
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }
}

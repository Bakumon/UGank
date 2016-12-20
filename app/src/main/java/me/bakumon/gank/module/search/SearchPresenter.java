package me.bakumon.gank.module.search;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import me.bakumon.gank.App;
import me.bakumon.gank.GlobalConfig;
import me.bakumon.gank.entity.SearchResult;
import me.bakumon.gank.network.NetWork;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * SearchPresenter
 * Created by bakumon on 2016/12/19 14:21.
 */
public class SearchPresenter implements SearchContract.Presenter {

    private SearchContract.View mView;

    @NonNull
    private CompositeSubscription mSubscriptions;

    public SearchPresenter(SearchContract.View view) {
        mView = view;
    }

    @Override
    public void subscribe() {
        mSubscriptions = new CompositeSubscription();
        mView.setToolbarBackgroundColor(App.colorPrimary);
        mView.hideEditClear();
    }

    @Override
    public void unsubscribe() {
        mSubscriptions.clear();
    }

    @Override
    public void search(String searchText) {
        if (TextUtils.isEmpty(searchText)) {
            mView.showSearchFail("搜索内容不能为空");
            return;
        }
        mView.showSwipLoading();
        Subscription subscription = NetWork.getGankApi()
                .getSearchResult(searchText, GlobalConfig.PAGE_SIZE_CATEGORY, 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SearchResult>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showSearchFail("搜索出错了，请重试" + e.getMessage());
                    }

                    @Override
                    public void onNext(SearchResult searchResult) {
                        mView.setSearchItems(searchResult);
                    }


                });
        mSubscriptions.add(subscription);
    }
}

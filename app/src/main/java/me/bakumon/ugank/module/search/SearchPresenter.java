package me.bakumon.ugank.module.search;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import me.bakumon.ugank.GlobalConfig;
import me.bakumon.ugank.ThemeManage;
import me.bakumon.ugank.entity.SearchResult;
import me.bakumon.ugank.network.NetWork;
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
        mView.setToolbarBackgroundColor(ThemeManage.INSTANCE.getColorPrimary());
        mView.setEditTextCursorColor(Color.WHITE);
        mView.hideEditClear();
    }

    @Override
    public void unsubscribe() {
        mSubscriptions.clear();
    }

    @Override
    public void search(final String searchText, final int page, final boolean isLoadMore) {
        if (TextUtils.isEmpty(searchText)) {
            mView.showTip("搜索内容不能为空");
            return;
        }
        if (!isLoadMore) {
            mView.showSwipLoading();
        }
        Subscription subscription = NetWork.getGankApi()
                .getSearchResult(searchText, GlobalConfig.PAGE_SIZE_CATEGORY, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SearchResult>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showSearchFail("搜索出错了，请重试", searchText, page, isLoadMore);
                        mView.hideSwipLoading();
                    }

                    @Override
                    public void onNext(SearchResult searchResult) {
                        if (!isLoadMore) {
                            if (searchResult == null || searchResult.count == 0) {
                                mView.showTip("没有搜索到结果");
                                mView.hideSwipLoading();
                                mView.setEmpty();
                                return;
                            }
                            mView.setSearchItems(searchResult);
                            mView.setLoading();
                        } else {
                            mView.addSearchItems(searchResult);
                        }
                        boolean isLastPage = searchResult.count < GlobalConfig.PAGE_SIZE_CATEGORY;
                        if (isLastPage) {
                            mView.setLoadMoreIsLastPage();
                        }

                    }


                });
        mSubscriptions.add(subscription);
    }
}

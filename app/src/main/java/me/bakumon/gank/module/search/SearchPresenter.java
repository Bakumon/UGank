package me.bakumon.gank.module.search;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import me.bakumon.gank.GlobalConfig;
import me.bakumon.gank.ThemeManage;
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
                    }

                    @Override
                    public void onNext(SearchResult searchResult) {
                        if (!isLoadMore) {
                            if (searchResult == null || searchResult.count == 0) {
                                mView.showTip("没有搜索到结果");
                                mView.hideSwipLoading();
                                return;
                            }
                            mView.setLoadMoreIsLastPage(false);
                            mView.setSearchItems(searchResult);
                        } else {
                            boolean isLastPage = searchResult.count < GlobalConfig.PAGE_SIZE_CATEGORY;
                            if (isLastPage) {
                                mView.showTip("已经是最后一页了");
                            }
                            mView.setLoadMoreIsLastPage(isLastPage);
                            mView.addSearchItems(searchResult);
                        }

                    }


                });
        mSubscriptions.add(subscription);
    }
}

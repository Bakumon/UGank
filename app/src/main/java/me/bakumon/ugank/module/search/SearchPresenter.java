package me.bakumon.ugank.module.search;

import android.graphics.Color;
import android.text.TextUtils;

import org.litepal.crud.DataSupport;

import java.util.List;

import me.bakumon.ugank.GlobalConfig;
import me.bakumon.ugank.ThemeManage;
import me.bakumon.ugank.entity.History;
import me.bakumon.ugank.entity.SearchResult;
import me.bakumon.ugank.network.NetWork;
import me.bakumon.ugank.utills.EmojiFilter;
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

    private CompositeSubscription mSubscriptions;
    private int mPage = 1;

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
    public void search(final String searchText, final boolean isLoadMore) {
        String searchTextNoEmoji = EmojiFilter.filterEmoji(searchText);
        if (TextUtils.isEmpty(searchTextNoEmoji)) {
            mView.startEmojiRain();
            return;
        }
        if (TextUtils.isEmpty(searchText)) {
            mView.showTip("搜索内容不能为空。");
            return;
        }
        mView.showSearchResult();
        saveOneHistory(searchText);
        if (!isLoadMore) {
            mView.showSwipLoading();
            mPage = 1;
        } else {
            mPage += 1;
        }
        Subscription subscription = NetWork.getGankApi()
                .getSearchResult(searchText, GlobalConfig.PAGE_SIZE_CATEGORY, mPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SearchResult>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showSearchFail("搜索出错了。");
                        mView.hideSwipLoading();
                    }

                    @Override
                    public void onNext(SearchResult searchResult) {
                        if (!isLoadMore) {
                            if (searchResult == null || searchResult.count == 0) {
                                mView.showTip("没有搜索到结果");
                                mView.hideSwipLoading();
                                mView.showSearchHistory();
                                mView.setEmpty();
                                return;
                            }
                            mView.setSearchItems(searchResult);
                            mView.showSearchResult();
                            mView.setLoading();
                        } else {
                            mView.addSearchItems(searchResult);
                            mView.showSearchResult();
                        }
                        boolean isLastPage = searchResult.count < GlobalConfig.PAGE_SIZE_CATEGORY;
                        if (isLastPage) {
                            mView.setLoadMoreIsLastPage();
                        }

                    }


                });
        mSubscriptions.add(subscription);
    }

    @Override
    public void queryHistory() {
        // 展示查询所有，需要截取、去重和排序
        List<History> historyList = DataSupport.order("createTimeMill desc").limit(10).find(History.class);
        // 将查询结果转为list对象
        if (historyList == null || historyList.size() < 1) {
            mView.showSearchResult();
        } else {
            mView.setHistory(historyList);
        }
    }

    private void saveOneHistory(String historyContent) {
        if (TextUtils.isEmpty(historyContent)) {
            return;
        }
        // 不知道 LitePal 支不支持去重
        // 先这样写吧，新增之前查询是否有相同数据，有就更新 CreateTimeMill ，没有就直接新增
        List<History> historyList = DataSupport.where("content = ?", historyContent).find(History.class);
        if (historyList == null || historyList.size() < 1) { // 不存在
            History history = new History();
            history.setCreateTimeMill(System.currentTimeMillis());
            history.setContent(historyContent);
            history.save();
        } else {
            // 更新
            History updateNews = new History();
            updateNews.setCreateTimeMill(System.currentTimeMillis());
            updateNews.updateAll("content = ?", historyContent);
        }

    }

    @Override
    public void deleteAllHistory() {
        DataSupport.deleteAll(History.class);
        mView.showSearchResult();
    }
}

package me.bakumon.gank.module.search;

import android.text.TextUtils;

import me.bakumon.gank.App;

/**
 * SearchPresenter
 * Created by bakumon on 2016/12/19 14:21.
 */
public class SearchPresenter implements SearchContract.Presenter {

    private SearchContract.View mView;

    public SearchPresenter(SearchContract.View view) {
        mView = view;
    }

    @Override
    public void subscribe() {
        mView.setToolbarBackgroundColor(App.colorPrimary);
        mView.hideEditClear();
    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void search(String searchText) {
        if (TextUtils.isEmpty(searchText)) {
            mView.showSearchFail("搜索内容不能为空");
        } else {
            mView.showSearchFail("搜索" + searchText);
        }
    }
}

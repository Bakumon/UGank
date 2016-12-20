package me.bakumon.gank.module.search;

import me.bakumon.gank.base.BasePresenter;
import me.bakumon.gank.base.BaseView;
import me.bakumon.gank.entity.SearchResult;

/**
 * SearchContract
 * Created by bakumon on 2016/12/19 14:20.
 */
public interface SearchContract {
    interface View extends BaseView {

        void setToolbarBackgroundColor(int color);

        void showEditClear();

        void hideEditClear();

        void showSearchFail(String failMsg);

        void setSearchItems(SearchResult searchResult);
    }

    interface Presenter extends BasePresenter {

        void search(String searchText);
    }
}

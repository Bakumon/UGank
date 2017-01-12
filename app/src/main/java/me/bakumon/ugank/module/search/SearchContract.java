package me.bakumon.ugank.module.search;

import me.bakumon.ugank.base.BasePresenter;
import me.bakumon.ugank.base.BaseView;
import me.bakumon.ugank.entity.SearchResult;

/**
 * SearchContract
 * Created by bakumon on 2016/12/19 14:20.
 */
public interface SearchContract {
    interface View extends BaseView {

        void setToolbarBackgroundColor(int color);

        void setEditTextCursorColor(int cursorColor);

        void showEditClear();

        void hideEditClear();

        void showSearchFail(String failMsg, String searchText, int page, boolean isLoadMore);

        void setSearchItems(SearchResult searchResult);

        void addSearchItems(SearchResult searchResult);

        void showSwipLoading();

        void hideSwipLoading();

        void showTip(String msg);

        void setLoadMoreIsLastPage();

        void setEmpty();

        void setLoading();
    }

    interface Presenter extends BasePresenter {

        void search(String searchText, int page, boolean isLoadMore);
    }
}

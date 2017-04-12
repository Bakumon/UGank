package me.bakumon.ugank.module.category;

import me.bakumon.ugank.base.BasePresenter;
import me.bakumon.ugank.base.BaseView;
import me.bakumon.ugank.entity.CategoryResult;

/**
 * CategoryContract
 * Created by bakumon on 2016/12/8.
 */
public interface CategoryContract {

    interface View extends BaseView {

        void setCategoryItems(CategoryResult categoryResult);

        void addCategoryItems(CategoryResult categoryResult);

        void getCategoryItemsFail(String failMessage);

        String getCategoryName();

        void showSwipeLoading();

        void hideSwipeLoading();

        void setLoading();
    }

    interface Presenter extends BasePresenter {

        void getCategoryItems(boolean isRefresh);
    }
}

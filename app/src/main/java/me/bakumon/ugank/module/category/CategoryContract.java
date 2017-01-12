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

        void setAndroidItems(CategoryResult categoryResult);

        void addAndroidItems(CategoryResult categoryResult);

        void getAndroidItemsFail(String failMessage, final int number, final int page, final boolean isRefresh);

        String getCategoryName();

        void showSwipLoading();

        void hideSwipLoading();

        void setLoading();
    }

    interface Presenter extends BasePresenter {

        void getAndroidItems(int number, int page, boolean isRefresh);
    }
}

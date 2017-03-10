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

        void getAndroidItemsFail(String failMessage);

        String getCategoryName();

        void showSwipLoading();

        void hideSwipLoading();

        void setLoading();
    }

    interface Presenter extends BasePresenter {

        void getAndroidItems(boolean isRefresh);
    }
}

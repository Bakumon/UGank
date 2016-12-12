package me.bakumon.gank.module.category;

import me.bakumon.gank.base.BasePresenter;
import me.bakumon.gank.base.BaseView;
import me.bakumon.gank.entity.CategoryResult;

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
    }

    interface Presenter extends BasePresenter {

        void getAndroidItems(int number, int page, boolean isRefresh);
    }
}

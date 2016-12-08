package me.bakumon.gank.module.android;

import me.bakumon.gank.base.BasePresenter;
import me.bakumon.gank.base.BaseView;
import me.bakumon.gank.entity.AndroidResult;

/**
 * AndroidContract
 * Created by bakumon on 2016/12/8.
 */
public interface AndroidContract {

    interface View extends BaseView {

        void setAndroidItems(AndroidResult androidResult);

        void addAndroidItems(AndroidResult androidResult);

        void getAndroidItemsFail(String failMessage);
    }

    interface Presenter extends BasePresenter {
        void getAndroidItems(int number, int page, boolean isRefresh);
    }
}

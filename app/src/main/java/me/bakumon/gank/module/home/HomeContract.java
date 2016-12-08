package me.bakumon.gank.module.home;

import me.bakumon.gank.base.BasePresenter;
import me.bakumon.gank.base.BaseView;

/**
 * HomeContract
 * Created by bakumon on 2016/12/6 10:48.
 */
public interface HomeContract {
    interface View extends BaseView {

        void showBannerFail(String failMessage);

        void setBanner(String imgUrl);
    }

    interface Presenter extends BasePresenter {
        void getRandomBanner();
    }
}

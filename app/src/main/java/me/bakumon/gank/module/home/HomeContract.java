package me.bakumon.gank.module.home;

import me.bakumon.gank.base.BasePresenter;
import me.bakumon.gank.base.BaseView;

/**
 * Created by mafei on 2016/12/6 10:48.
 *
 * @author mafei
 * @version 1.0.0
 * @class HomeContract
 * @describe 包装 view 和 presenter
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

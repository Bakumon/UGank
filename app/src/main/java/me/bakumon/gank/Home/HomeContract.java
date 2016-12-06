package me.bakumon.gank.home;

import me.bakumon.gank.BasePresenter;
import me.bakumon.gank.BaseView;

/**
 * Created by mafei on 2016/12/6 10:48.
 *
 * @author mafei
 * @version 1.0.0
 * @class HomeContract
 * @describe 包装 view 和 presenter
 */
interface HomeContract {
    interface View extends BaseView<Presenter> {

        void showBannerFail(String failMessage);

        void setBanner(String imgUrl);
    }

    interface Presenter extends BasePresenter {

    }
}

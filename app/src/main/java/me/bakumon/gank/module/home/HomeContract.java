package me.bakumon.gank.module.home;

import android.support.annotation.Nullable;
import android.support.v7.graphics.Palette;

import me.bakumon.gank.base.BasePresenter;
import me.bakumon.gank.base.BaseView;

/**
 * HomeContract
 * Created by bakumon on 2016/12/6 10:48.
 */
public interface HomeContract {
    interface View extends BaseView {

        void showBannerFail(String failMessage, boolean isRandom);

        void setBanner(String imgUrl);

        void startBannerLoadingAnim();

        void stopBannerLoadingAnim();

        void enableFabButton();

        void disEnableFabButton();

        void setAppBarLayoutColor(int appBarLayoutColor);

        void setFabButtonColor(int color);
    }

    interface Presenter extends BasePresenter {

        void getRandomBanner();

        void setThemeColor(@Nullable Palette palette);

        void getBanner(final boolean isRandom);
    }
}

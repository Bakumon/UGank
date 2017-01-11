package me.bakumon.ugank.module.home;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.graphics.Palette;

import me.bakumon.ugank.base.BasePresenter;
import me.bakumon.ugank.base.BaseView;

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

        Activity getBigimgContext();

        void showPermissionsTip();

        void showMsgSaveSuccess();

        void showMsgSaveFail();

        void showSavingMsgTip();

    }

    interface Presenter extends BasePresenter {

        void getRandomBanner();

        void setThemeColor(@Nullable Palette palette);

        void getBanner(final boolean isRandom);

        void saveImg(Drawable drawable);
    }
}

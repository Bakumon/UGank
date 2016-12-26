package me.bakumon.gank.module.bigimg;

import android.app.Activity;

import me.bakumon.gank.base.BasePresenter;
import me.bakumon.gank.base.BaseView;

/**
 * BigimgContract
 * Created by bakumon on 16-12-20.
 */

public interface BigimgContract {

    interface View extends BaseView {

        Activity getBigimgContext();

        void setMeiziTitle(String title);

        void loadMeizuImg(String url);

        void setToolbarBackgroundColor(int color);

        void setViewColorAccent(int color);

        void showSaveFab();

    }

    interface Presenter extends BasePresenter {
        void requestPermissionForSaveImg();
    }
}

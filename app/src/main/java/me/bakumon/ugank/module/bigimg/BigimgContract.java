package me.bakumon.ugank.module.bigimg;

import android.app.Activity;

import me.bakumon.ugank.base.BasePresenter;
import me.bakumon.ugank.base.BaseView;

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

    }

    interface Presenter extends BasePresenter {
    }
}

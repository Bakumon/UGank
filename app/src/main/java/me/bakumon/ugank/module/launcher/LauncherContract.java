package me.bakumon.ugank.module.launcher;

import me.bakumon.ugank.base.BasePresenter;
import me.bakumon.ugank.base.BaseView;

/**
 * LauncherContract
 * Created by bakumon on 17-3-21.
 */

public class LauncherContract {

    interface View extends BaseView {

        void goHomeActivity();

        void loadImg(String url);
    }

    interface Presenter extends BasePresenter {

    }
}

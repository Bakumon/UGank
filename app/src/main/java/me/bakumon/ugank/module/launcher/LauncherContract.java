package me.bakumon.ugank.module.launcher;

import me.bakumon.ugank.base.BasePresenter;
import me.bakumon.ugank.base.BaseView;

/**
 * Launcher 包装　view presenter
 * Created by bakumon on 16-12-6.
 */

public interface LauncherContract {

    interface View extends BaseView {

        void toHome();
    }

    interface Presenter extends BasePresenter {

    }

}

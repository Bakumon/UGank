package me.bakumon.gank.module.launcher;

import me.bakumon.gank.BasePresenter;
import me.bakumon.gank.BaseView;

/**
 * Launcher 包装　view presenter
 * Created by bakumon on 16-12-6.
 */

public interface LauncherContract {

    interface View extends BaseView<Presenter> {

        void startAnim();

        void toHome();
    }

    interface Presenter extends BasePresenter {

    }

}

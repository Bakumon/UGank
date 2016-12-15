package me.bakumon.gank.module.setting;

import me.bakumon.gank.base.BasePresenter;
import me.bakumon.gank.base.BaseView;

/**
 * SettingContract
 * Created by bakumon on 2016/12/15 17:05.
 */
public interface SettingContract {

    interface View extends BaseView {

        void setToolbarBackgroundColor(int color);

        void changeSwitchState(boolean isChecked);
    }

    interface Presenter extends BasePresenter {

        void saveIsListShowImg(boolean isListShowImg);

    }
}

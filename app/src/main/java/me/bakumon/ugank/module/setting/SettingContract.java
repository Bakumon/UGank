package me.bakumon.ugank.module.setting;

import me.bakumon.ugank.base.BasePresenter;
import me.bakumon.ugank.base.BaseView;

/**
 * SettingContract
 * Created by bakumon on 2016/12/15 17:05.
 */
public interface SettingContract {

    interface View extends BaseView {

        void setToolbarBackgroundColor(int color);

        void changeSwitchState(boolean isChecked);

        void setSwitchCompatsColor(int color);

        void setAppVersionNameInTv(String versionName);

        void setImageQualityChooseUnEnable();

        void setImageQualityChooseEnable();

        void setThumbnailQualityInfo(String quality);

    }

    interface Presenter extends BasePresenter {

        void saveIsListShowImg(boolean isListShowImg);

        int getColorPrimary();

        int getThumbnailQuality();

        void setThumbnailQuality(int quality);
    }
}

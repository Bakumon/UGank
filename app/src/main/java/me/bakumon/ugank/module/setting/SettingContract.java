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

        void changeIsShowLauncherImgSwitchState(boolean isChecked);

        void changeIsAlwaysShowLauncherImgSwitchState(boolean isChecked);

        void setSwitchCompatsColor(int color);

        void setAppVersionNameInTv(String versionName);

        void setImageQualityChooseUnEnable();

        void setImageQualityChooseEnable();

        void setLauncherImgProbabilityUnEnable();

        void setLauncherImgProbabilityEnable();

        void setThumbnailQualityInfo(int quality);

        void showCacheSize(String cache);

        void showSuccessTip(String msg);

        void showFailTip(String msg);

        void setShowLauncherTip(String tip);

        void setAlwaysShowLauncherTip(String tip);

    }

    interface Presenter extends BasePresenter {

        boolean isThumbnailSettingChanged();

        void saveIsListShowImg(boolean isListShowImg);

        void saveIsLauncherShowImg(boolean isLauncherShowImg);

        void saveIsLauncherAlwaysShowImg(boolean isLauncherAlwaysShowImg);

        int getColorPrimary();

        int getThumbnailQuality();

        void setThumbnailQuality(int quality);

        void cleanCache();
    }
}

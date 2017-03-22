package me.bakumon.ugank.module.setting;

import me.bakumon.ugank.App;
import me.bakumon.ugank.ConfigManage;
import me.bakumon.ugank.ThemeManage;
import me.bakumon.ugank.utills.DataCleanManager;
import me.bakumon.ugank.utills.PackageUtil;
import rx.subscriptions.CompositeSubscription;

/**
 * SettingPresenter
 * Created by bakumon on 2016/12/15 17:08.
 */
public class SettingPresenter implements SettingContract.Presenter {

    private SettingContract.View mView;

    private CompositeSubscription mSubscriptions;

    public SettingPresenter(SettingContract.View view) {
        mView = view;
    }

    @Override
    public void subscribe() {
        mSubscriptions = new CompositeSubscription();
        mView.setSwitchCompatsColor(ThemeManage.INSTANCE.getColorPrimary());
        mView.setToolbarBackgroundColor(ThemeManage.INSTANCE.getColorPrimary());
        // 初始化开关显示状态
        mView.changeSwitchState(ConfigManage.INSTANCE.isListShowImg());
        mView.changeIsShowLauncherImgSwitchState(ConfigManage.INSTANCE.isShowLauncherImg());
        mView.changeIsAlwaysShowLauncherImgSwitchState(ConfigManage.INSTANCE.isAlwaysShowLauncherImg());

        setImageQualityChooseIsEnable(ConfigManage.INSTANCE.isListShowImg());
        setIsLauncherAlwaysShowImgEnable(ConfigManage.INSTANCE.isShowLauncherImg());

        mView.setAppVersionNameInTv(PackageUtil.getVersionName(App.getInstance()));
        setThumbnailQuality(ConfigManage.INSTANCE.getThumbnailQuality());
        mView.showCacheSize(DataCleanManager.getTotalCacheSize());
    }

    private void setThumbnailQualityInfo(int quality) {
        String thumbnailQuality = "";
        switch (quality) {
            case 0:
                thumbnailQuality = "原图";
                break;
            case 1:
                thumbnailQuality = "默认";
                break;
            case 2:
                thumbnailQuality = "省流";
                break;
        }
        mView.setThumbnailQualityInfo(thumbnailQuality);
    }

    @Override
    public void unsubscribe() {
        mSubscriptions.clear();
    }

    @Override
    public void saveIsListShowImg(boolean isListShowImg) {
        ConfigManage.INSTANCE.setListShowImg(isListShowImg);
        setImageQualityChooseIsEnable(isListShowImg);
    }

    @Override
    public void saveIsLauncherShowImg(boolean isLauncherShowImg) {
        ConfigManage.INSTANCE.setShowLauncherImg(isLauncherShowImg);
        setIsLauncherAlwaysShowImgEnable(isLauncherShowImg);
        if (isLauncherShowImg) {
            mView.setShowLauncherTip("没有妹子太寂寞");
        } else {
            mView.setShowLauncherTip("基佬怎么会需要妹子");
        }
    }

    @Override
    public void saveIsLauncherAlwaysShowImg(boolean isLauncherAlwaysShowImg) {
        ConfigManage.INSTANCE.setAlwaysShowLauncherImg(isLauncherAlwaysShowImg);
        if (isLauncherAlwaysShowImg) {
            mView.setAlwaysShowLauncherTip("偶尔来个惊喜就行");
        } else {
            mView.setAlwaysShowLauncherTip("我每次都要幸临，没毛病");
        }
    }

    private void setIsLauncherAlwaysShowImgEnable(boolean isEnable) {
        if (isEnable) {
            mView.setLauncherImgProbabilityEnable();
        } else {
            mView.setLauncherImgProbabilityUnEnable();
        }
    }

    private void setImageQualityChooseIsEnable(boolean isEnable) {
        if (isEnable) {
            mView.setImageQualityChooseEnable();
        } else {
            mView.setImageQualityChooseUnEnable();
        }
    }

    @Override
    public int getColorPrimary() {
        return ThemeManage.INSTANCE.getColorPrimary();
    }

    @Override
    public int getThumbnailQuality() {
        return ConfigManage.INSTANCE.getThumbnailQuality();
    }

    @Override
    public void setThumbnailQuality(int quality) {
        ConfigManage.INSTANCE.setThumbnailQuality(quality);
        setThumbnailQualityInfo(quality);
    }

    @Override
    public void cleanCache() {
        if (DataCleanManager.clearAllCache()) {
            ConfigManage.INSTANCE.setBannerURL("");
            mView.showSuccessTip("缓存清理成功！");
        } else {
            mView.showFailTip("缓存清理失败！");
        }

        try {
            mView.showCacheSize(DataCleanManager.getTotalCacheSize());
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}

package me.bakumon.ugank.module.setting;

import me.bakumon.ugank.App;
import me.bakumon.ugank.ConfigManage;
import me.bakumon.ugank.ThemeManage;
import me.bakumon.ugank.utills.GlideCacheUtil;
import me.bakumon.ugank.utills.PackageUtil;

/**
 * SettingPresenter
 * Created by bakumon on 2016/12/15 17:08.
 */
public class SettingPresenter implements SettingContract.Presenter {

    private SettingContract.View mView;

    public SettingPresenter(SettingContract.View view) {
        mView = view;
    }

    @Override
    public void subscribe() {
        mView.setSwitchCompatsColor(ThemeManage.INSTANCE.getColorPrimary());
        mView.setToolbarBackgroundColor(ThemeManage.INSTANCE.getColorPrimary());
        // 初始化开关显示状态
        mView.changeSwitchState(ConfigManage.INSTANCE.isListShowImg());
        mView.setCacheSizeInTv(cachedSize());
        mView.setAppVersionNameInTv(PackageUtil.getVersionName(App.getInstance()));
    }

    @Override
    public void unsubscribe() {

    }

    /**
     * 获取 Glide 已经缓存的大小
     */
    private String cachedSize() {
        return GlideCacheUtil.getInstance().getCacheSize(App.getInstance());
    }

    @Override
    public void saveIsListShowImg(boolean isListShowImg) {
        ConfigManage.INSTANCE.setListShowImg(isListShowImg);
    }
}

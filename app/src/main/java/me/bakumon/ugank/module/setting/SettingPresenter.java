package me.bakumon.ugank.module.setting;

import android.support.annotation.NonNull;

import me.bakumon.ugank.App;
import me.bakumon.ugank.ConfigManage;
import me.bakumon.ugank.ThemeManage;
import me.bakumon.ugank.utills.PackageUtil;
import rx.subscriptions.CompositeSubscription;

/**
 * SettingPresenter
 * Created by bakumon on 2016/12/15 17:08.
 */
public class SettingPresenter implements SettingContract.Presenter {

    private SettingContract.View mView;

    @NonNull
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
        setImageQualityChooseIsEnable(ConfigManage.INSTANCE.isListShowImg());
        mView.setAppVersionNameInTv(PackageUtil.getVersionName(App.getInstance()));
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
}

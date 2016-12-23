package me.bakumon.gank.module.setting;

import android.content.Context;
import android.content.SharedPreferences;

import me.bakumon.gank.App;
import me.bakumon.gank.ConfigManage;
import me.bakumon.gank.ThemeManage;

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
    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void saveIsListShowImg(boolean isListShowImg) {
        SharedPreferences sharedPreferences = App.getInstance().getSharedPreferences("app_config", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isListShowImg", isListShowImg);
        if (editor.commit()) {
            ConfigManage.INSTANCE.setListShowImg(isListShowImg);
        }
    }
}

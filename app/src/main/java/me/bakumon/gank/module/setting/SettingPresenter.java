package me.bakumon.gank.module.setting;

import android.content.Context;
import android.content.SharedPreferences;

import me.bakumon.gank.App;

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
        mView.setToolbarBackgroundColor(App.colorPrimary);
        // 初始化开关显示状态
        mView.changeSwitchState(App.isListShowImg);
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
            App.isListShowImg = isListShowImg;
//            mView.changeSwitchState(App.isListShowImg);
        }
    }
}
